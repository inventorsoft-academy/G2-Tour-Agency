package co.inventorsoft.touragency.model.validation;

import co.inventorsoft.touragency.model.BookedTour;
import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.User;

public class BookedTourFactory implements BaseFactory<BookedTour> {

    private User user;
    private Tour tour;
    private boolean isActive;

    public BookedTourFactory(User user, Tour tour, boolean isActive) {
        this.user = user;
        this.tour = tour;
        this.isActive = isActive;
    }

    @Override
    public BookedTour create() {
        return new BookedTour(user, tour, isActive);
    }
}
