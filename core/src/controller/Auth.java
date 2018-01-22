package controller;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Auth {
    private static List<User> users;

    public static void initialize() {
        users = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("users.mta"));
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
        for (User user : users) {
            if (!(user.getUsername().contains(username) && user.getEmail().contains(email))) {
                User newUser = new User(username, email, password, isAdmin);
                return true;
            }
        }
        return false;
    }
}
