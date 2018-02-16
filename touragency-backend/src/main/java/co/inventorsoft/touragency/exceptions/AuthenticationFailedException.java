package co.inventorsoft.touragency.exceptions;

/**
 * Class {@code AuthenticationFailedException} defins an exception that occurs when
 * user's credentials (username and password) do not match with any existing user records.
 * If such a situation occurs, then this exception will be thrown and a user would not be
 * allowed to proceed using the application without prior authentication.
 * This class extends {@link Exception} class, so this exception will be of the checked type.
 * Any piece of code that would potentially throw {@code AuthenticationFailedException} would
 * need to handle it in a correct way to prevent application crash.
 * */
public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
