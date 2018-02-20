package co.inventorsoft.touragency.model;

import javax.persistence.*;

@Table(name = "bookings")
public class BookedTour implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "is_active")
    private boolean status;

    public BookedTour() {
    }

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
