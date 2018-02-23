package co.inventorsoft.touragency.controller.api;

import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.TourType;
import co.inventorsoft.touragency.model.validation.TourFactory;
import co.inventorsoft.touragency.service.TourService;
import co.inventorsoft.touragency.util.validation.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Class {@code ToursApiController} provides a REST Tours API for the Tour Agency Application.
 * Tours API may be used to retrieve and manipulate data relating primarily to {@link Tour} objects.
 * This API does not provide functionality for tour booking and reviewing. These are offered by
 * Booking API ({@link BookingApiController}) and Reviews API ({@link ReviewsApiController}
 * respectively.
 * All methods in this API can be accesed via ./tours/[method_name] URL.
 * */
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
@RestController
@RequestMapping(value = "/tours")
public class ToursApiController {

    private TourService tourService;

    /**
     * Autowired constructor that injects an instance of {@link TourService} service
     * as a primary dependency for the API.
     * */
    @Autowired
    public ToursApiController(TourService tourService) {
        this.tourService = tourService;
    }

    /**
     * {@code getTours()} method provides an interface to perform a GET request to obtain
     * a list of tours. After processing of data offered by {@link TourService} service,
     * this method would return a JSON response with mapped data corresponding to tours.
     * @return Http status 200 if the operation succeeded
     * */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tour>> getTours() {
        return ResponseEntity.ok(tourService.getTours(false));
    }

    /**
     * {@code getTourById(int id)} method allows access to data on an individual tour based on
     * its identifier. The tour's id is passed as a path variable in the request's URL.
     * The resulting data is returned in JSON format as in {@link #getTours()} method
     * @return Http status 200 if the operation succeeded
     * */
    @GetMapping(value = "{id:\\d+}")
    public ResponseEntity<Tour> getTourById(@PathVariable int id) {
        return ResponseEntity.ok(tourService.getTour(id));
    }

    /**
     * Returns all tours with a specified tour type. The desired tour type is specified
     * as a parameter of the GET request
     * */
    @GetMapping(params = "type")
    public ResponseEntity<List<Tour>> getToursByType(@RequestParam String type) {
        return ResponseEntity.ok(tourService.getTours(TourType.valueOf(type)));
    }

    /**
     * Returns all tours that start on a date that is in a range of specified
     * in parameters dates.
     * */
    @GetMapping(value = "range")
    public ResponseEntity<List<Tour>> getToursFromRange(@RequestParam String fromDate,
                                                        @RequestParam String toDate) {
        return ResponseEntity.ok(tourService.getTours(
                LocalDate.parse(fromDate, DateValidator.formatter),
                LocalDate.parse(toDate, DateValidator.formatter)));
    }

    /**
     * Allows to cancel a tour with a specified id
     * */
    @SuppressWarnings("unchecked")
    @PutMapping("/cancel/{tourId:\\d+}")
    public ResponseEntity cancelTour(@PathVariable int tourId) {
        return tourService.cancelTour(tourId) ?
                new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /**
     * Performs an update of a tour type with an id provided as a path variable
     * and assigns a new value for tour type passed as a parameter
     * */
    @PutMapping("/update/type/{tourId:\\d+}")
    public ResponseEntity updateTourType(@PathVariable int tourId,
                                         @RequestParam String type) {
        return tourService.updateTourType(tourId, type) ?
                new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /**
     * Performs an update of a tour's price. A tour to be updated is defined by its ID and
     * the new value is passed as a request parameter
     * */
    @PutMapping("/update/price/{tourId:\\d+}")
    public ResponseEntity updateTourPrice(@PathVariable int tourId,
                                          @RequestParam int price) {
        boolean status = tourService.updateTourPrice(tourId, price);
        return status ? new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Tour> createTourFromUrl(@ModelAttribute Tour tour) {
        final Tour createdTour = tourService.createTour(tour);
        return ResponseEntity.created(URI.create(String.format("/songs/%d", createdTour.getId())))
                .body(createdTour);
    }

    /**
     * Creates a new instance of a tour using values provided in a JSON body of the
     * POST request.
     * */
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tour> createTour(@RequestBody Tour tour) {
        Tour newTour = new TourFactory(tour.getDestination(),
                tour.getCountry(),
                tour.getStartDate().toString(),
                tour.getEndDate().toString(),
                tour.getTourType().toString(),
                tour.getCapacity(),
                tour.getPrice(),
                tour.getAgency(), true)
                .create();
        if (newTour != null) {
            tourService.createTour(newTour);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }
    }
}
