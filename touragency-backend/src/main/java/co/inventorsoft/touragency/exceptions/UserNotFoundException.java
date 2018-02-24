package co.inventorsoft.touragency.exceptions;

/**
 * This class describes an exception that occurs if no user with given credentials
 * was found in the list of all registered users. This exception is checked and must be
 * caught before compilation.
 * {@code UserNotFoundException} has one constructor with a parameter which receives a
 * {@link String} containing a supporting message.
 * */
public class UserNotFoundException extends Throwable {
    /**
     * This is the main constructor of the class. It takes a String with a message and
     * invokes the constructor of the {@link Throwable} class which is its direct superclass.
     * @param message a detail message
     * */
    public UserNotFoundException(String message) {
        super(message);
    }
}
