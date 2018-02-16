package co.inventorsoft.touragency.model;

import java.util.Objects;

/**
 * The {@code User} class contains information about users that will utilize the application.
 * The users are of two types. The first type users are regular users who act as customers at
 * a tour agency. They can filter a list of available tours, book a tour, cancel a booking or
 * write a review of a tour.
 * The second type of users are administrators (or moderators). They can add review a list
 * of tours their agency offers, add a new tour, cancel a tour, view tour's reviews and export
 * them into a text file as well as import tours from a text file.
 * The {@code User} class holds both regular users (a.k.a customers) and administrators. The
 * distinction between them is in the boolean field {@code isAdmin}.
 * */
public class User implements BaseEntity {
    private int id;
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    private String agency;

    /**
     * Default constructor without parameters. Initializes class fields with default
     * Java values.
     * */
    public User() {
    }

    /**
     * Constructs a new {@code User} objects and initializes its fields with values provided as
     * parameters.
     * @param username user's username
     *                 @param password user's password
     *                                 @param email user's email address
     *                                              @param isAdmin indicates if a user has administrative
     *                                                             privileges
     *                                                             @param agency user's agency (admins only)
     * */
    public User(String username, String password, String email, boolean isAdmin, String agency) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
        this.agency = agency;
    }

    /**
     * Overrides default {@code equals()} method provided by the {@link Object} class.
     * Performs checking on all fields for equality except for the admin privileges flag.
     * @param o an object to be compared to
     * @return true if all fields, except for admin privileges flag, of both objects are equal.
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(agency, user.agency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public boolean match(String username, String password) {
        return this.getUsername().equals(username) && this.getPassword().equals(password);
    }

    public boolean match(String username, String password, String email) {
        return this.getUsername().equals(username) && this.getPassword().equals(password) &&
                this.getEmail().equals(email);
    }

    /**
     * @return user's identifier
     * */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id a value to be set as user's ID
     * */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return user's username
     * */
    public String getUsername() {
        return username;
    }

    /**
     * @return user's password
     * */
    public String getPassword() {
        return password;
    }

    /**
     * @return user's email
     * */
    public String getEmail() {
        return email;
    }

    /**
     * @return true if the user has administrative privileges
     * */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * @return user's agency
     * */
    public String getAgency() {
        return agency;
    }
}
