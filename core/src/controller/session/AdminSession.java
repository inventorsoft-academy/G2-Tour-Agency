package controller.session;

import controller.Auth;
import model.User;

public class AdminSession implements Session {

    private User admin;
    private boolean isLoggedIn = false;

    public static AdminSession start() {
        return new AdminSession();
    }

    @Override
    public void login(String username, String password) {
        admin = Auth.login(username, password);
        isLoggedIn = true;
    }

    @Override
    public void register(String username, String email, String password) {
        if (!isLoggedIn) {
            boolean result = Auth.register(username, password, email, true);
            if (result) {
                login(username, password);
            }
        }
    }
}
