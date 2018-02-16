package co.inventorsoft.touragency.util.validation;

import java.util.regex.Pattern;

/**
 * {@code BasicValidator} is a utility class whose principal responsibility is providing
 * validation for individual parameters. This validation includes null checks, checks for
 * matching a regex pattern (length and allowed characters of the string etc.)
 * {@code BasicValidator} does not provide various kinds of validation for date and time
 * entities. For these purposes use {@link DateValidator}
 * @see DateValidator
 * */
public class BasicValidator {
    private static final Pattern generalTextPattern = Pattern.compile(
            "^[a-zA-Z,\\-.]{2,}$");
    private static final Pattern credentialPattern = Pattern.compile(
            "^[a-zA-Z0-9._]{3,}$");
    private static final Pattern emailPattern = Pattern.compile(
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}" +
                    "\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
    private static final Pattern agencyPattern = Pattern.compile(
            "^[a-zA-Z0-9.,& \\-]{3,}$");

    private BasicValidator() {
    }

    /**
     * This method performs validation of a credential value (either username, aka nickname or
     * a password). Both fields must be at least three characters long and contain letters and
     * digits.
     * @param credential a Sring value that has to be validated
     *                   @return true if a credential value passed as a parameter is valid
     * */
    public static boolean validateCredential(String credential) {
        return credentialPattern.matcher(credential).matches();
    }

    /**
     * This method performs validation of an email address. An email address will be valid
     * if it matches a regex pattern provided as a static class member.
     * @param email a String containing a value that has to be checked if is a valid email address
     *              @return true if the {@code email} parameter corresponds to a valid email address
     * */
    public static boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

    public static boolean validateText(String text) {
        return generalTextPattern.matcher(text).matches();
    }

    public static boolean validateAgency(String agency) {
        return agencyPattern.matcher(agency).matches();
    }
}
