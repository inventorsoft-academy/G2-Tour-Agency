package co.inventorsoft.touragency.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInterface implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        int choice;
        do {
            ConsoleHelper.write("PRESS 0 TO EXIT");
            choice = ConsoleHelper.readInt();
            if (choice == 0) {
                System.exit(0);
            }
        } while (choice != 0);
    }
}
