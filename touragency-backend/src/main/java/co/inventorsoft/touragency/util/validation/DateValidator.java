package co.inventorsoft.touragency.util.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * {@code DateValidator} is a utility class that consists solely of static methods. Its main
 * function is provide various kinds of validation for date-related entities. These include
 * validation of a string that supposedly contains a date, checking matches to accepted
 * date format (DD/MM/YYYY), checking date objects to prevent retroactive and irrelevant
 * results as well as checking date range in order to assure its non-retroactive character
 * and that this range exists (i.e. contains at least one day in it)
 * */
public class DateValidator {

    //public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    private static final Pattern dateStringPattern = Pattern.compile(
            "^[0-9]{2}/[0-9]{2}/[0-9]{4}");
    private static final Pattern isoDateStringPattern = Pattern.compile(
            "^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}");

    public static boolean validateDateString(String dateString) {
        return //dateStringPattern.matcher(dateString).matches() ||
                isoDateStringPattern.matcher(dateString).matches();
    }

    public static boolean validateDateRange(LocalDate start, LocalDate end) {
        return end.isEqual(start) || end.isAfter(start);
    }
}
