package util;

/**
 * This class creates a model for an exception in case a {@link model.User} object passes as
 * a parameter to any class overriding {@code start()} method of the {@link controller.session.Session}
 * interface is null or inadequate. The class contains a single field that is a String containing
 * an error message and a constructor.
 * */
public class UserSessionException extends Throwable {
    private String message;

    public UserSessionException(String message) {
        this.message = message;
    }
}
