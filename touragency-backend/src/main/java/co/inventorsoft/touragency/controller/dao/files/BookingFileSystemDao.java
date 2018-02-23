package co.inventorsoft.touragency.controller.dao.files;

import co.inventorsoft.touragency.controller.dao.BaseDao;
import co.inventorsoft.touragency.model.Booking;
import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.User;
import co.inventorsoft.touragency.model.validation.BookedTourFactory;
import co.inventorsoft.touragency.service.AuthenticationService;
import co.inventorsoft.touragency.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookingFileSystemDao implements BaseDao<Booking> {

    private final String PATH = "D:\\Progs\\JAVA\\2018\\ACADEMY\\G2-Tour-Agency\\" +
            "touragency-backend\\src\\main\\resources\\data\\bookings.mta";

    private List<User> users;
    private List<Tour> tours;
    private AuthenticationService authenticationService;
    private TourService tourService;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public BookingFileSystemDao(AuthenticationService authenticationService, TourService tourService) {
        this.authenticationService = authenticationService;
        this.tourService = tourService;
    }

    @PostConstruct
    private void initialize() {
        this.users = authenticationService.getUsers();
        this.tours = tourService.getTours(true);
    }

    @Override
    public List<Booking> getAll() {
        File file = new File(PATH);
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] input = line.split("; ");
                int userId = Integer.valueOf(input[0]);
                int tourId = Integer.valueOf(input[1]);
                boolean isActive = Boolean.valueOf(input[2]);

                User user = users.get(userId - 1);
                Tour tour = tours.get(tourId - 1);
                bookings.add(new BookedTourFactory(user, tour, isActive).create());
            }
            assignIdentifiers(bookings);
            logger.info("Successfully retrieved the list of bookings from a text file");
            return bookings;
        } catch (IOException e) {
            logger.error("Something went wrong while retrieving the list of bookings from " +
                    "a text file");
            return null;
        }
    }

    @Override
    public boolean saveAll(List<Booking> data) {
        File file = new File(PATH);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            for (Booking booking : data) {
                fileWriter.write(booking.getUser().getId() + "; " +
                        booking.getTour().getId() + "; " +
                        booking.getStatus());
                fileWriter.write("\n");
            }
            fileWriter.close();
            logger.info("Successfully saved bookings data to text file");
            return true;
        } catch (IOException e) {
            logger.error("Failed to save bookings data to text file." + e.toString());
        }
        return false;
    }
}
