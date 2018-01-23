package controller;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Auth {
    //private static List<User> users;
    private static Set<User> users;

    private static final String USERS_PATH = "D:\\Progs\\JAVA\\2018\\1\\G2-Tour-Agency\\core\\res\\users.mta";

    public static void initialize() {
        //users = new ArrayList<>();
        users = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_PATH));
            while (bufferedReader.readLine() != null) {
                String username = bufferedReader.readLine();
                String password = bufferedReader.readLine();
                String email = bufferedReader.readLine();
                boolean isAdmin = Boolean.valueOf(bufferedReader.readLine());
                users.add(new User(username, email, password, isAdmin));
            }
        } catch (IOException e) {
            System.out.println("Failed to read users list. Please try again later.");
            System.exit(-1);
        }
    }

    @Nullable
    public static User login(@NotNull String username, @NotNull String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean register(@NotNull String username, @NotNull String password,
                                @NotNull String email, boolean isAdmin) {

        boolean result = users.add(new User(username, email, password, isAdmin));

        if (result) {
            login(username, password);
            return true;
        } else {
            System.out.println("User with username or email already exists");
            return false;
        }
    }
}
