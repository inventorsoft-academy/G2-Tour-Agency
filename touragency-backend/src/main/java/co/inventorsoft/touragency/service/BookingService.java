package co.inventorsoft.touragency.service;

import co.inventorsoft.touragency.controller.dao.files.BookingFileSystemDao;
import co.inventorsoft.touragency.exceptions.TourNotAvailableException;
import co.inventorsoft.touragency.exceptions.TourSoldOutException;
import co.inventorsoft.touragency.model.Booking;
import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.User;
import co.inventorsoft.touragency.model.validation.BookedTourFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class {@code BookingService} is a service that provides data on tour bookings
 * performed by users.
 * Main functions of this service include: creation and cancelling of a booking as well
 * as retrieving a list of booked tours
 */
@Service
public class BookingService {

    private BookingFileSystemDao dao;
    private AuthenticationService authenticationService;
    private TourService tourService;

    private List<Booking> bookings;
    private Map<Tour, Integer> bookingStats;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Default autowired constructor invoked by the Spring framework. Performs dependency
     * injection of the objects required by {@code BookingService} class.
     * */
    @Autowired
    public BookingService(BookingFileSystemDao dao,
                          AuthenticationService authenticationService,
                          TourService tourService) {
        this.dao = dao;
        this.authenticationService = authenticationService;
        this.tourService = tourService;
    }

    /**
     * Invoked after an instance of the class is constructed. Retrieves a list of booked
     * tours from the database
     * */
    @PostConstruct
    private void getBookedTours() {
        this.bookings = dao.getAll();
    }

    /**
     * Invoked before the current instance of {@code BookingService} class would be
     * destroyed. The main action done in this method is data save.
     * */
    @PreDestroy
    private void saveAll() {
        boolean status = dao.saveAll(bookings);
    }

    /**
     * Retrieves a list of bookings made by a specific user whose username is specified
     * as a parameter
     * @param username username of a user who might have booked a tour
     *                 @param isUser a dummy parameter
     *                               @return list of bookings made by a specific user
     * */
    public List<Booking> getBookedTours(String username, boolean isUser) {
        return bookings.stream()
                .filter(booking -> booking.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all booked tours offered by a specified agency.
     * @param agency name of agency for which, bookings would be displayed
     *               @return list of all bookings for all tours offered by a specific agency
     * */
    public List<Booking> getBookedTours(String agency) {
        return bookings.stream()
                .filter(booking -> booking.getTour().getAgency().equals(agency))
                .collect(Collectors.toList());
    }

    /**
     * {@code bookTour(int userId, int tourId} method performs booking of a tour.
     * This method takes two parameters - an id of a user and an id of a tour to be booked.
     * @param userId id of a user who performs booking
     *               @param tourId id of a tour to be booked
     *                             @return new instance of {@link Booking} class containing
     *                             data for a newly created booking
     *                             @throws TourSoldOutException if all available tickets
     *                             (places) for a tour had been sold out
     *                             @throws TourNotAvailableException if a tour was previously
     *                             cancelled by a company admin
     * */
    public Booking bookTour(int userId, int tourId) throws
            TourSoldOutException, TourNotAvailableException {

        try {
            User user = authenticationService.getUsers().get(userId - 1);
            Tour tour = tourService.getTours(true).get(tourId - 1);

            if (!tour.isActive()) {
                throw new TourNotAvailableException("Tour was cancelled!");
            }

            int alreadyBooked = (int) bookings.stream()
                    .filter(booking -> booking.getTour().getId() == tourId)
                    .count();

            if (alreadyBooked < tour.getCapacity()) {
                Booking booking = new BookedTourFactory(user, tour, true).create();
                bookings.add(booking);
                bookings.get(bookings.size() - 1).setId(bookings.size());
                return bookings.get(bookings.size() - 1);
            } else {
                throw new TourSoldOutException("Tour sold out!");
            }

        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Method {@code cancelBooking(int id)} allows a user to cancel a previously made booking
     * for a tour. In order to perform the operation, a booking identifier shall be provided.
     * @param id identifier of a booking to be cancelled
     *           @return true if an operation succeeded, false otherwise
     * */
    public boolean cancelBooking(int id) {
        Optional<Booking> bookedTourOptional = Optional.of(bookings.get(id - 1));

        if (!bookedTourOptional.isPresent()) {
            logger.error("Failed to find booking with id " + id);
            return false;
        }

        bookedTourOptional.get().setStatus(false);
        logger.info("Successfully cancelled booking with id" + id);

        return true;
    }
}
