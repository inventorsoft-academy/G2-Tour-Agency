package co.inventorsoft.touragency.controller.dao.files;

import co.inventorsoft.touragency.controller.dao.BaseDao;
import co.inventorsoft.touragency.model.BookedTour;
import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.User;
import co.inventorsoft.touragency.model.validation.BookedTourFactory;
import co.inventorsoft.touragency.service.AuthenticationService;
import co.inventorsoft.touragency.service.TourService;
import co.inventorsoft.touragency.util.ConsoleHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookingFileSystemDao implements BaseDao<BookedTour> {

    private final String PATH = "D:\\Progs\\JAVA\\2018\\1\\TourAgency\\src\\main\\" +
            "resources\\dao\\bookedtours.mta";

    private List<User> users;
    private List<Tour> tours;

    private AuthenticationService authenticationService;
    private TourService tourService;

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
    public List<BookedTour> getAll() {
        File file = new File(PATH);
        List<BookedTour> bookedTours = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] input = line.split("; ");
                int userId = Integer.valueOf(input[0]);
                int tourId = Integer.valueOf(input[1]);
                boolean isActive = Boolean.valueOf(input[2]);

                User user = users.get(userId - 1);
                Tour tour = tours.get(tourId - 1);
                bookedTours.add(new BookedTourFactory(user, tour, isActive).create());
            }
            assignIdentifiers(bookedTours);
            return bookedTours;
        } catch (IOException e) {
            ConsoleHelper.write("Exception!");
            return null;
        }
    }

    @Override
    public boolean saveAll(List<BookedTour> data) {
        return false;
    }
}
