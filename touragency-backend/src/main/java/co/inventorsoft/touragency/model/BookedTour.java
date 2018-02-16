package co.inventorsoft.touragency.model;

public class BookedTour implements BaseEntity {
    private int id;
    private Tour tour;
    private User user;
    private boolean status;

    public BookedTour(User user, Tour tour, boolean status) {
        this.user = user;
        this.tour = tour;
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + ": \t. User:  " + getUser().getUsername() + ". Tour: "  + getTour().getDestination() +
                ", " + getTour().getCountry() + "; " + getTour().getStartDate() + " - " +
                getTour().getEndDate();
    }

    public Tour getTour() {
        return tour;
    }

    public User getUser() {
        return user;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
