package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.isAdmin = false;

        if (validateEmail(email)) {
            this.email = email;
        } else {
            this.email = "";
        }
    }

    public User(String username, String email, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;

        if (validateEmail(email)) {
            this.email = email;
        } else {
            this.email = "";
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (validateEmail(email)) {
            this.email = email;
        } else {
            this.email = "";
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    private boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}" +
                "\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
