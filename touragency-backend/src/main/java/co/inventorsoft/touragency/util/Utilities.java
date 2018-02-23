package co.inventorsoft.touragency.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Utilities {

    public static final String WORKING_DIRECTORY = "F:\\TourAgency";

    private Utilities() {
    }

    public static List<File> getAllFilesFromInputDirectory() {
        try {
            return Arrays.asList(
                    Objects.requireNonNull(new File(
                            WORKING_DIRECTORY + "\\import")
                            .listFiles()));
        } catch (NullPointerException e) {

            ConsoleHelper.write("Failed to load files!");
            return null;
        }
    }
}
