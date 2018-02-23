package co.inventorsoft.touragency.model;

//import javax.persistence.*;

/**
 * Class {@code TourReview} represents a single review left by a user for a tour.
 * It contains the following fields:
 * <ul>
 *     <li>User - an instance of {@link User} who wrote a review</li>
 *     <li>Tour - an instance of {@link Tour} which a review is referred to</li>
 *     <li>Review - a String containing the actual content of a review</li>
 * </ul>
 * */
//@Entity
//@Table(name = "reviews")
public class TourReview implements BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
    private int id;

//    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
    private Tour tour;

//    @Column(name = "review", nullable = false)
    private String review;

    public TourReview(User user, Tour tour, String review) {
        this.user = user;
        this.tour = tour;
        this.review = review;
    }

    @Override
    public String toString() {
        return user.getId() + "; " + tour.getId() + "; " + review;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Tour getTour() {
        return tour;
    }

    public String getReview() {
        return review;
    }
}
