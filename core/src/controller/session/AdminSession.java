package controller.session;

import com.sun.istack.internal.NotNull;
import main.TourAgencyApplication;
import model.Tour;
import model.User;
import util.UserSessionException;
import util.Utilities;

import java.util.List;
import java.util.Scanner;

public class AdminSession implements Session {

    private static User admin;
    private List<Tour> tours;

    @NotNull
    public static AdminSession start(User user) {
        admin = user;
        return new AdminSession();
    }

    @Override
    public void initialize() {
        System.out.println("=== WELCOME, " + admin.getUsername() + "===");
        while (true) {
            System.out.println("Please choose an action: ");
            System.out.println("1 to view company tours");
            System.out.println("2 to view booked tours");
            System.out.println("3 to view tours' reviews");
            System.out.println("4 to add a tour");
            System.out.println("5 to cancel a tour");
            System.out.println("6 to add a tour label");
            System.out.println("7 to import tours from file");
            System.out.println("8 to export tour reviews to file");
            System.out.println("9 to log out");

            tours = Utilities.retrieveTours();

            Scanner in = new Scanner(System.in);
            int choice = in.nextInt();

            switch (choice) {
                case 1:
                    displayTours();
                    break;
                case 2:
                    displayBookedTours();
                    break;
                case 3:
                    displayTourReviews();
                    break;
                case 4:
                    createTour();
                    break;
                case 5:
                    cancelTour();
                    break;
                case 6:
                    addTourType();
                    break;
                case 7:
                    importToursFromFile();
                    break;
                case 8:
                    exportReviewsToFile();
                    break;
                case 9:
                    close();
                    break;
                default:
                    System.out.println("Please choose a valid option");
                    break;
            }

            System.out.println("--- --- ---");
        }
    }

    @Override
    public void close() {
        admin = null;

        // TODO: Save all data from collections (from memory) into file system
        try {
            TourAgencyApplication.chooseOptions();
        } catch (UserSessionException e) {
            System.out.println("Something went wrong... Please try again later");
            System.exit(0);
        }
    }

    private void displayTours() {
        System.out.println("Company tours: ");
        for (Tour tour : tours) {
            System.out.println(tour.toString());
        }
    }

    private void displayBookedTours() {
        System.out.println("This feature has yet to be implemented");
    }

    private void displayTourReviews() {
        System.out.println("This feature has yet to be implemented");
    }

    private void createTour() {
        System.out.println("This feature has yet to be implemented");
    }

    private void cancelTour() {
        System.out.println("This feature has yet to be implemented");
    }

    private void addTourType() {
        System.out.println("This feature has yet to be implemented");
    }

    private void importToursFromFile() {
        System.out.println("This feature has yet to be implemented");
    }

    private void exportReviewsToFile() {
        System.out.println("This feature has yet to be implemented");
    }
}
