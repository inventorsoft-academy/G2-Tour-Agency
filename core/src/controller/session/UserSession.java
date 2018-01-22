package controller.session;

import controller.Auth;
import model.Tour;
import model.User;

import java.util.Map;

public class UserSession implements Session {
    private boolean isLoggedIn = false;
    private User user;

    private Map<User, Tour> bookedTours;

    public static UserSession start() {
        return new UserSession();
    }

    @Override
    public void login(String username, String password) {
        user = Auth.login(username, password);
        isLoggedIn = true;
    }

    @Override
    public void register(String username, String email, String password) {
        if (!isLoggedIn) {
            boolean result = Auth.register(username, password, email, false);
            if (result) {
                login(username, password);
            }
        }
    }

    public void bookTour(Tour tour) {

    }
}
