package co.inventorsoft.touragency.controller.api;

import co.inventorsoft.touragency.model.TourReview;
import co.inventorsoft.touragency.model.validation.ReviewFactory;
import co.inventorsoft.touragency.service.ReviewsService;
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
 * {@code ReviewsApiController} is a REST controller class that provides tour agency's
 * Tour Reviews API. This API is used to compose and retrieve data on tour reviews.
 * */
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
@RestController
@RequestMapping(value = "/reviews")
public class ReviewsApiController {
    private ReviewsService reviewsService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReviewsApiController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    /**
     *
     * */
    @SuppressWarnings("unchecked")
    @GetMapping(value = "{id:\\d+}")
    public ResponseEntity<List<TourReview>> getTourReviews(@PathVariable int id) {
        return ResponseEntity.ok(reviewsService.getReviewsForTour(id));
    }

    @GetMapping(params = "agency")
    public ResponseEntity<List<TourReview>> getAgencyTourReviews(@RequestParam String agency) {
        return ResponseEntity.ok(reviewsService.getAgencyTourReviews(agency));
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TourReview> writeReview(@RequestBody TourReview tourReview) {
        try {
            final TourReview newReview = new ReviewFactory(tourReview.getUser(), tourReview.getTour(),
                    tourReview.getReview()).create();
            return ResponseEntity.created(URI.create(
                    String.format("/create/%d", newReview.getId()))).build();
        } catch (RuntimeException e) {
            logger.error("Failed to create a new review. " + e.getMessage());
            return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
