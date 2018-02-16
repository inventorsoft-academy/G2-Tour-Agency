package co.inventorsoft.touragency.service;

import co.inventorsoft.touragency.controller.dao.files.BookingFileSystemDao;
import co.inventorsoft.touragency.exceptions.TourNotAvailableException;
import co.inventorsoft.touragency.exceptions.TourSoldOutException;
import co.inventorsoft.touragency.model.BookedTour;
import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.User;
import co.inventorsoft.touragency.model.validation.BookedTourFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class {@code BookingService} is a service that provides data on tour bookings
 * performed by users.
 * Main functions of this service include: creation and cancelling of a booking
 */
@Service
public class BookingService {

    private BookingFileSystemDao dao;
    private AuthenticationService authenticationService;
    private TourService tourService;

    private List<BookedTour> bookedTours;
    private Map<Tour, Integer> bookingStats;

    @Autowired
    public BookingService(BookingFileSystemDao dao,
                          AuthenticationService authenticationService,
                          TourService tourService) {
        this.dao = dao;
        this.authenticationService = authenticationService;
        this.tourService = tourService;
    }

    @PostConstruct
    private void getBookedTours() {
        this.bookedTours = dao.getAll();
    }

    @PreDestroy
    private void saveAll() {
        boolean status = dao.saveAll(bookedTours);
    }

    public List<BookedTour> getBookedTours(String username, boolean isUser) {
        return bookedTours.stream()
                .filter(bookedTour -> bookedTour.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public List<BookedTour> getBookedTours(String agency) {
        return bookedTours.stream()
                .filter(bookedTour -> bookedTour.getTour().getAgency().equals(agency))
                .collect(Collectors.toList());
    }

    public BookedTour bookTour(int userId, int tourId) throws
            TourSoldOutException, TourNotAvailableException {

        try {
            User user = authenticationService.getUsers().get(userId - 1);
            Tour tour = tourService.getTours(false).get(tourId - 1);

            if (!tour.isActive()) {
                throw new TourNotAvailableException("Tour was cancelled!");
            }

            int alreadyBooked = (int) bookedTours.stream()
                    .filter(bookedTour -> bookedTour.getTour().getId() == tourId)
                    .count();

            if (alreadyBooked < tour.getCapacity()) {
                return new BookedTourFactory(user, tour, true).create();
            } else {
                throw new TourSoldOutException("Tour sold out!");
            }

        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
    }
}
