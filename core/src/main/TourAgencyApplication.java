package main;

import com.sun.istack.internal.Nullable;
import controller.Auth;
import controller.session.AdminSession;
import controller.session.UserSession;
import model.User;
import util.UserSessionException;

import java.util.Scanner;

/**
 * This class is an entry point of the application. It contains very basic navigation
 * elements: login and registration. The class has two intermediate methods to perform
 * authentication operations which call respective methods from the {@link Auth} utility class.
 * Depending on the type of user, this class with call {@link UserSession} and {@link AdminSession}
 * {@code start()} methods to initialize user sessions that correspond to a regular user or administrator
 * role as provided in the registration.
 * */
public class TourAgencyApplication {
    /**
     * Main method of the application. Acts as action chooser and allows either login operation for
     * existing users or registration of a new user. User login is combined for both regular users and
     * administrators.
     * */
    public static void main(String[] args) throws UserSessionException {
        initializeAuthenticationService();
        chooseOptions();
    }

    /**
     * Method that displays options a user may choose on the top hierarchy of navigation.
     * @throws UserSessionException if {@link Auth} class returns {@link User} object,
     * representing a current user, which is effectively null.
     * */
    public static void chooseOptions() throws UserSessionException {
        System.out.println("=== TOUR AGENCY ===");
        System.out.println("Please choose an action: ");
        System.out.println("1 to login as a client");
        System.out.println("2 to register as a client");
        System.out.println("3 to register as an administrator");
        System.out.println("4 to exit");

        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register(false);
                break;
            case 3:
                register(true);
                break;
            case 4:
                System.out.println("Thank you for choosing our agency!");
                System.exit(0);
                break;
            default:
                System.out.println("Error selecting action. Please choose a valid option.");
                chooseOptions();
        }
    }

    /**
     * Handler for the login operation. Implements dialogues with a user.
     * */
    private static void login() throws UserSessionException {
        System.out.println("/// USER LOGIN ///");
        Scanner in = new Scanner(System.in);
        System.out.println("Username: ");
        String username = in.nextLine();
        System.out.println("Password: ");
        String password = in.nextLine();
        User user = Auth.login(username, password);

        if (user != null) {
            if (user.isAdmin()) {
                AdminSession adminSession = AdminSession.start(user);
                adminSession.initialize();
            } else {
                UserSession userSession = UserSession.start(user);
                userSession.initialize();
            }
        } else {
            int counter = 0;
            System.out.println("Username or password is incorrect OR user is not registered." +
                    " Please try again.");
            counter++;
            if (counter >= 3) {
                System.out.println("Choose 1 to continue...");
                System.out.println("Choose 2 to exit...");
                int choice = in.nextInt();
                if (choice == 1) {
                    login();
                } else {
                    System.exit(0);
                }
            } else {
                login();
            }
        }
    }

    private static void register(@Nullable boolean isAdmin) throws UserSessionException {
        System.out.println("/// USER REGISTRATION ///");
        Scanner in = new Scanner(System.in);
        System.out.println("Username: ");
        String username = in.nextLine();
        System.out.println("Email: ");
        String email = in.nextLine();
        System.out.println("Password: ");
        String password = in.nextLine();

        boolean result = Auth.register(username, password, email, isAdmin);

        if (result) {
            User user = Auth.login(username, password);

            if (user != null) {
                if (user.isAdmin()) {
                    AdminSession adminSession = AdminSession.start(user);
                    adminSession.initialize();
                } else {
                    UserSession userSession = UserSession.start(user);
                    userSession.initialize();
                }
            } else {
                System.out.println("Username or password is incorrect OR user is not registered." +
                        " Please try again.");
                login();
            }
        } else {
            register(isAdmin);
        }
    }

    private static void initializeAuthenticationService() {
        Auth.initialize();
    }
}
