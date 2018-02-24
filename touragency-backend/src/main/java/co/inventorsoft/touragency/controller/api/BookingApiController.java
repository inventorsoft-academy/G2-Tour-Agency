package co.inventorsoft.touragency.controller.api;

import co.inventorsoft.touragency.exceptions.TourNotAvailableException;
import co.inventorsoft.touragency.exceptions.TourSoldOutException;
import co.inventorsoft.touragency.model.Booking;
import co.inventorsoft.touragency.model.SimpleBooking;
import co.inventorsoft.touragency.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Class {@code BookingApiController} is a controller for the tour agency's Booking API.
 * This class provides a set of GET, POST and PUT Http methods for manipulation
 * of booking data.
 * {@code BookingApiController} depends on {@link BookingService} for its operations of tour
 * booking etc. {@link BookingService} is a Spring component marked with a {@code @Service}
 * annotation. This class acts as a bridge between the client (front-end) and the corresponding
 * data service.
 * {@code BookingApiController} provides the following REST API methods:
 * <ul>
 *     <li>{@code getAgencyBookedTours(String agency)} - returns a list of all bookings
 *     of all tours offered by a specified agency</li>
 *     <li>{@code getUserBookedTours(String username, boolean flag} - returns a list of all
 *     tours booked by a particular user</li>
 *     <li>{@code bookTour(Booking bookedTour)} - performs a booking operation</li>
 * </ul>
 * All REST methods provided by this class are available at URL "/booking"
 * */
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
@RestController
@RequestMapping(value = "/booking")
public class BookingApiController {

    private BookingService bookingService;
    private final Logger logger = LoggerFactory.getLogger(BookingApiController.class);

    /**
     * This is a default constructor for the {@code BookingApiController} class. It is used
     * to perform dependency injection of the {@link BookingService} for the class that requires
     * it for proper functioning.
     * This constructor is marked as {@link Autowired}, so it is not invoked explicitly in the
     * code but rather invoked by the Spring framework.
     * @param bookingService an instance of {@link BookingService}, which is a service that
     *                       provides data for the REST API.
     * */
    @Autowired
    public BookingApiController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * {@code getAgencyBookedTours(String agency)} method is a GET method implementation.
     * This method returns a JSON with a list of {@link Booking} objects that correspond
     * to bookings made for tours that are offered by a specified agency.
     * This GET method can be referenced via './booking/agency' URL.
     * Returns HTTP status code 200 in case of a successful operation.
     * @return a JSON with retrieved bookings
     * */
    @GetMapping(value = "/agency")
    public ResponseEntity<List<Booking>> getAgencyBookedTours(@RequestParam String agency) {
        return ResponseEntity.ok(bookingService.getBookedTours(agency));
    }

    /**
     * {@code getUserBookedTours(String username)} method is an implementation of a GET method
     * that retrieves a list of bookings made by a particular user.
     * This method can be accessed and is invoked via './booking/user' URL.
     * Returns HTTP status code 200 in case of a successful operation.
     * @return a JSON with retrieved bookings
     * */
    @GetMapping(value = "/user")
    public ResponseEntity<List<Booking>> getUserBookedTours(@RequestParam String username) {
        return ResponseEntity.ok(bookingService.getBookedTours(username, true));
    }

    /**
     * {@code bookTour(Booking booking} method is an implementation of a POST method
     * that creates a new booking.
     * This method consumes a JSON serialized object of {@link Booking} class in a body
     * of a POST request.
     * This method catches {@link TourSoldOutException} and {@link TourNotAvailableException}
     * exceptions thrown by {@link BookingService} if a tour is not available or all available
     * places for a tour had already been sold out.
     * Returns HTTP status 201 (Created) if all validations passed successfully and a booking
     * was added and HTTP status 418 (I'm a teapot) otherwise.
     * {@code bookTour(Booking booking} can be referenced via './booking/book' URL with
     * {@link Booking} serialized object in method request body.
     * @param booking a serialized JSON of a {@link Booking} object that contains details
     *                   on object that will be constructed.
     *                   @return {@link ResponseEntity} with server's response
     * */
    @PostMapping(value = "/book/v0", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> bookTourV0(@RequestBody Booking booking) {
        try {
            final Booking newBooking = bookingService.bookTour(booking.getUser().getId(),
                    booking.getTour().getId());
            return ResponseEntity.created(URI.create(String.format("/book/v0/%d",
                    newBooking.getId()))).build();
        } catch (TourSoldOutException | TourNotAvailableException e) {
            logger.error("Failed to book a tour. " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> bookTour(@RequestBody SimpleBooking mBooking) {
        try {
            final Booking newBooking = bookingService.bookTour(mBooking.getUserId(),
                    mBooking.getTourId());
            logger.info("Successfully booked tour with id " + mBooking.getTourId() +
                    " for user with id " + mBooking.getUserId());
            return ResponseEntity.created(URI.create(String.format("/book/%d",
                    newBooking.getId()))).build();
        } catch (TourSoldOutException | TourNotAvailableException e) {
            logger.error("Failed to book a tour. " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    /**
     * {@code cancelBooking(int id)} method allows a user to cancel a booking he made.
     * This method takes an integer value referring to booking identifier as a path variable
     * for the PUT Http request.
     * @param id an identifier of a booking that would be cancelled
     *           @return Http status code OK if the operation was performed successfully or
     *           Http status NOT_FOUND otherwise.
     * */
    @PutMapping("/cancel/{id:\\d+}")
    public ResponseEntity cancelBooking(@PathVariable int id) {
        return bookingService.cancelBooking(id) ?
                new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
