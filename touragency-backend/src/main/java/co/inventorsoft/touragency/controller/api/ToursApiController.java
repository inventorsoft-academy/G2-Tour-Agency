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

@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
@RestController
@RequestMapping(value = "/tours")
public class ToursApiController {

    private TourService tourService;

    @Autowired
    public ToursApiController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tour>> getTours() {
        return ResponseEntity.ok(tourService.getTours(true));
    }

    @GetMapping(value = "{id:\\d+}")
    public ResponseEntity<Tour> getTourById(@PathVariable int id) {
        return ResponseEntity.ok(tourService.getTour(id));
    }

    @GetMapping(params = "type")
    public ResponseEntity<List<Tour>> getToursByType(@RequestParam String type) {
        return ResponseEntity.ok(tourService.getTours(TourType.valueOf(type)));
    }

    @GetMapping(value = "range")
    public ResponseEntity<List<Tour>> getToursFromRange(@RequestParam String fromDate,
                                                        @RequestParam String toDate) {
        return ResponseEntity.ok(tourService.getTours(
                LocalDate.parse(fromDate, DateValidator.formatter),
                LocalDate.parse(toDate, DateValidator.formatter)));
    }

    @PutMapping("/cancel/{tourId:\\d+}")
    public ResponseEntity cancelTour(@PathVariable int tourId) {
        return tourService.cancelTour(tourId) ?
                new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/type/{tourId:\\d+}")
    public ResponseEntity updateTourType(@PathVariable int tourId,
                                         @RequestParam String type) {
        return tourService.updateTourType(tourId, type) ?
                new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

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

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tour> createTour(@RequestBody Tour tour) {
        Tour newTour = new TourFactory(tour.getDestination(),
                tour.getCountry(),
                tour.getStartDate().toString(),
                tour.getEndDate().toString(),
                tour.getTourType().toString(), tour.getCapacity(), tour.getPrice(),
                tour.getAgency(), true).create();
        if (newTour != null) {
            tourService.createTour(newTour);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }
    }
}