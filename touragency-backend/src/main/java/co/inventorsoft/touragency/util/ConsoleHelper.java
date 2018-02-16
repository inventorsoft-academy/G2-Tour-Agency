package co.inventorsoft.touragency.util;

import co.inventorsoft.touragency.model.BaseEntity;

import java.util.List;
import java.util.Scanner;

public final class ConsoleHelper {

    private static Scanner in = new Scanner(System.in);

    private ConsoleHelper() {
    }

    /**
     * Prints message passed as parameter to console
     *
     * @param message message to be printed
     */
    public static void write(String message) {
        System.out.println(message);
    }

    public static void printList(List<? extends BaseEntity> list) {
        list.forEach(System.out::println);
    }

    /**
     * Reads an input line from console and returns it as a string
     *
     * @return user's console single-line input
     */
    public static String readString() {
        return in.nextLine();
    }

    /**
     * Reads in integer value that was put into the console and returns it to the method that
     * invoked {@code readInt()}.
     *
     * @return integer input in the console
     */
    public static int readInt() {
        return in.nextInt();
    }

    /**
     * Reads user's credentials from console
     * */
    public static String[] readCredentials(boolean isAdmin) {
        String[] credentials = new String[4];

        System.out.println("CREDENTIALS must be at lest three characters long and may contain " +
                "only letters, digits, a dot sign or an underscore sign");
        in.nextLine();

        System.out.println("Username (at least 3 characters): ");
        credentials[0] = in.nextLine();
        System.out.println("Password (at least 3 characters): ");
        credentials[1] = in.nextLine();
        System.out.println("Email: ");
        credentials[2] = in.nextLine();
        if (isAdmin) {
            System.out.println("Agency: ");
            credentials[3] = in.nextLine();
        } else {
            credentials[3] = "CUSTOMER";
        }
        return credentials;
    }
}
