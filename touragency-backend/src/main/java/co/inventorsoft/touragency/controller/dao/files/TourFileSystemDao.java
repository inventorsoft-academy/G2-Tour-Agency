package co.inventorsoft.touragency.controller.dao.files;

import co.inventorsoft.touragency.controller.dao.BaseDao;
import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.validation.TourFactory;
import co.inventorsoft.touragency.util.ConsoleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TourFileSystemDao implements BaseDao<Tour> {

    private final String PATH = "D:\\Progs\\JAVA\\2018\\ACADEMY\\TourAgency\\src\\main\\" +
            "resources\\data\\tours.mta";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Tour> getAll() {
        File file = new File(PATH);
        List<Tour> tours = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] input = line.split("; ");
                String destination = input[0];
                String country = input[1];
                String startDateStr = input[2];
                String endDateStr = input[3];
                String tourTypeStr = input[4];
                int capacity = Integer.valueOf(input[5]);
                int price = Integer.valueOf(input[6]);
                String agency = input[7];
                boolean isActive = Boolean.valueOf(input[8]);

                Tour tour = new TourFactory(
                        destination, country, startDateStr, endDateStr,
                        tourTypeStr, capacity, price, agency, isActive)
                        .create();
                if (tour != null) {
                    tours.add(tour);
                }
            }
            assignIdentifiers(tours);
            return tours;
        } catch (IOException | NullPointerException e) {
            logger.error("Failed to retrieve a list of tours from text file!" +
                        e.getCause() + " " + e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
            return tours;
        }
    }

    @Override
    public boolean saveAll(List<Tour> data) {
        File file = new File(PATH);

        try (FileWriter fileWriter = new FileWriter(file, false)) {
            for (Tour tour : data) {
                fileWriter.write(
                        tour.getDestination() + "; " +
                                tour.getCountry() + "; " +
                                tour.getStartDate().toString() + "; " +
                                tour.getEndDate().toString() + "; " +
                               /* String.format("%02d", tour.getStartDate().getDayOfMonth()) + "/" +*/
                               /* String.format("%02d", tour.getStartDate().getMonthValue()) + "/" +*/
                               /* tour.getStartDate().getYear() + "; " +*/
                               /* String.format("%02d", tour.getEndDate().getDayOfMonth()) + "/" +*/
                               /* String.format("%02d", tour.getEndDate().getMonthValue()) + "/" +*/
                               /* tour.getEndDate().getYear() + "; " +*/
                                tour.getTourType().toString() + "; " +
                                tour.getCapacity() + "; " +
                                tour.getPrice() + "; " +
                                tour.getAgency() + "; " +
                                tour.isActive());
                fileWriter.write("\n");
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            ConsoleHelper.write("Exception!");
        }

        return false;
    }
}
