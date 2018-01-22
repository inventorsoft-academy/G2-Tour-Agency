import controller.session.AdminSession;
import controller.session.UserSession;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.println("=== TOUR AGENCY ===");

        System.out.println("Please choose an action: ");
        System.out.println("1 to login as a client");
        System.out.println("2 to login as a tour agency manager");
        System.out.println("3 to register as a client");

        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        switch (choice) {
            case 1:
                UserSession userSession = UserSession.start();
                break;
            case 2:
                AdminSession adminSession = AdminSession.start();
        }
    }
}
