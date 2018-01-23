package controller.session;

import com.sun.istack.internal.Nullable;
import main.TourAgencyApplication;
import model.Tour;
import model.TourType;
import model.User;
import util.UserSessionException;
import util.Utilities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Class representing a regular user's activity session with the application. This class provides
 * interface for interaction of one user with an application at a time. It contains all actions
 * that a regular user can perform except login and registration that are implemented in a
 * {@link controller.Auth} class. A class holds a {@link User} object of a current user and
 * contains fields corresponding to user's activities. These are mainly memory collections of data
 * a user can interact with.
 * */
public class UserSession implements Session {
    private static User currentUser;

    private List<Tour> tours;
    private Map<User, Tour> bookedTours;

    /**
     * Starts a new user session. This method is usually invoked after login operation was successful.
     * Assigns a {@link User} object returned by {@link controller.Auth} class as a current user
     * for this session.
     * @param user {@link User} object with identity of a user as returned after login. This parameter
     * */
    @Nullable
    public static UserSession start(User user) throws UserSessionException {
        if (user != null) {
            currentUser = user;
            return new UserSession();
        } else {
            throw new UserSessionException("Failed to start user's session. " +
                    "Please try again.");
        }
    }

    @Override
    public void initialize() {
        System.out.println("=== WELCOME, " + currentUser.getUsername() + "===");
        while (true) {
            System.out.println("Please choose an action: ");
            System.out.println("1 to retrieve a list of hot tours");
            System.out.println("2 to retrieve a list of tours in date range");
            System.out.println("3 to retrieve a list of all our tours");
            System.out.println("4 to book a tour");
            System.out.println("5 to get a list of all your booked tours");
            System.out.println("6 to cancel a booking");
            System.out.println("7 to write a review for a tour");
            System.out.println("8 to log out");

            tours = Utilities.retrieveTours();

            Scanner in = new Scanner(System.in);
            int choice = in.nextInt();

            switch (choice) {
                case 1:
                    getHotTours();
                    break;
                case 2:
                    getToursFromRange();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    close();
                default:
                    System.out.println("Please choose a valid option");
                    break;
            }

            System.out.println("--- --- ---");
        }
    }

    @Override
    public void close() {
        currentUser = null;

        // TODO: Save all data from collections (from memory) into file system
        try {
            TourAgencyApplication.chooseOptions();
        } catch (UserSessionException e) {
            System.out.println("Something went wrong... Please try again later");
            System.exit(0);
        }
    }

    private void getHotTours() {
        ArrayList<Tour> hotTours = getTours(TourType.HOT);
        System.out.println("Hot tours: ");
        for (Tour tour : hotTours) {
            System.out.println(tour.toString());
        }
    }

    private ArrayList<Tour> getTours(TourType filter) {
        ArrayList<Tour> filteredTours = new ArrayList<>();
        for (Tour tour : tours) {
            if (tour.getTourType() == filter) {
                filteredTours.add(tour);
            }
        }
        return filteredTours;
    }

    private ArrayList<Tour> getTours(LocalDate start, LocalDate end) {
        ArrayList<Tour> filteredTours = new ArrayList<>();
        boolean condition1, condition2;

        for (Tour tour : tours) {
            condition1 = tour.getStartDate().isAfter(start) && tour.getStartDate().isBefore(end);
            condition2 = tour.getStartDate().isEqual(start) || tour.getStartDate().isEqual(end);

            if (condition1 || condition2) {
                filteredTours.add(tour);
            }
        }
        return filteredTours;
    }

    private void bookTour(Tour tour) {
        // TODO: Implement tour booking operation
    }

    private void writeReview(Tour tour) {
        // TODO: Implement reviews feature
    }

    private boolean cancelBooking(Tour tour) {
        // TODO: Implement cancelling of a booking operation
        return false;
    }

    private void getToursFromRange() {
        Scanner in = new Scanner(System.in);

        System.out.println("You can filter our tours by a range of the start date. " +
                "All tours that start in this range will be displayed.");
        System.out.println("!!! Please enter the date like this: DD/MM/YYYY");
        System.out.println("Start date: ");
        String startDateStr = in.nextLine();
        System.out.println("End date: ");
        String endDateStr = in.nextLine();

        ArrayList<Tour> tours = getTours(
                LocalDate.parse(startDateStr, Utilities.formatter),
                LocalDate.parse(endDateStr, Utilities.formatter));

        System.out.println("Available tours: ");
        for (Tour tour : tours) {
            System.out.println(tour.toString());
        }
    }
}
