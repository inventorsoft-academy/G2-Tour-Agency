package co.inventorsoft.touragency.controller.dao.files;

import co.inventorsoft.touragency.controller.dao.BaseDao;
import co.inventorsoft.touragency.model.User;
import co.inventorsoft.touragency.model.validation.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class UserFileSystemDao implements BaseDao<User> {

    private final String PATH = "D:\\Progs\\JAVA\\2018\\ACADEMY\\TourAgency\\src\\main\\" +
            "resources\\data\\users.mta";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<User> getAll() {
        File file = new File(PATH);
        List<User> users = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] input = line.split("; ");
                String username = input[0];
                String password = input[1];
                String email = input[2];
                boolean isAdmin = Boolean.valueOf(input[3]);
                String agency = input[4];

                User user = new UserFactory(username, password, email, isAdmin, agency)
                        .create();
                if (user != null) {
                    users.add(user);
                }
            }
            assignIdentifiers(users);

            logger.info("Successfully imported users' list from file");

            return users;
        } catch (IOException e) {
            logger.error("Failed to import users' list from a text file. " + e.toString());
        }
        return null;
    }

    @Override
    public boolean saveAll(List<User> data) {
        File file = new File(PATH);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            for (User user : data) {
                fileWriter.write(user.getUsername() + "; " +
                        user.getPassword() + "; " +
                        user.getEmail() + "; " +
                        user.isAdmin() + "; " +
                        user.getAgencyStr());
                fileWriter.write("\n");
            }
            fileWriter.close();

            logger.info("Successfully saved user data to text file");

            return true;
        } catch (IOException e) {

            logger.error("Failed to save user's data to text file." + e.toString());
        }
        return false;
    }
}
