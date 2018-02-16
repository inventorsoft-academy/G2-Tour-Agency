package co.inventorsoft.touragency.model.validation;

import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.TourReview;
import co.inventorsoft.touragency.model.User;

public class ReviewFactory implements BaseFactory<TourReview> {

    private User user;
    private Tour tour;
    private String review;

    public ReviewFactory(User user, Tour tour, String review) {
        this.user = user;
        this.tour = tour;
        this.review = review;
    }

    @Override
    public TourReview create() {
        return new TourReview(user, tour, review);
    }
}
