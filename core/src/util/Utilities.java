package util;

import model.Tour;
import model.TourType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a collection of several static utility methods used in the application.
 * It contains implementations for such operations like reading and writing to files, email
 * validation etc.
 * */
public class Utilities {
    public static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("d/MM/yyyy");

    /**
     * Performs validation of an email address by matching it to a regex pattern.
     * @param email A String that has to be validated as an email address
     *              @return true if the input String is a valid email address
     * */
    public static boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}" +
                "\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * This method performs read operation of the content of tours.mta file that contains data
     * on all tours offered by the agency.
     * @return list of all tours offered by the tour agency
     * */
    public static List<Tour> retrieveTours() {
        ArrayList<Tour> tours = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("D:\\Progs\\JAVA\\2018\\1\\G2-Tour-Agency\\core\\res\\tours.mta"));
            while (bufferedReader.readLine() != null) {
                String destination = bufferedReader.readLine();
                LocalDate startDate = LocalDate.parse(bufferedReader.readLine(), formatter);
                LocalDate endDate = LocalDate.parse(bufferedReader.readLine(), formatter);
                TourType tourType = TourType.valueOf(bufferedReader.readLine().toUpperCase());
                int capacity = Integer.valueOf(bufferedReader.readLine());
                tours.add(new Tour(destination, startDate, endDate, tourType, capacity));
            }
            return tours;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
