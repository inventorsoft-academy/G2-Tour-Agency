package co.inventorsoft.touragency.model.validation;

import co.inventorsoft.touragency.model.Booking;
import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.User;

public class BookedTourFactory implements BaseFactory<Booking> {

    private User user;
    private Tour tour;
    private boolean isActive;

    public BookedTourFactory(User user, Tour tour, boolean isActive) {
        this.user = user;
        this.tour = tour;
        this.isActive = isActive;
    }

    @Override
    public Booking create() {
        return new Booking(user, tour, isActive);
    }
}
