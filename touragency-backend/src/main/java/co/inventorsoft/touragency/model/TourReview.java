package co.inventorsoft.touragency.model;

/**
 * Class {@code TourReview} represents a single review left by a user for a tour.
 * It contains the following fields:
 * <ul>
 *     <li>User - an instance of {@link User} who wrote a review</li>
 *     <li>Tour - an instance of {@link Tour} which a review is referred to</li>
 *     <li>Review - a String containing the actual content of a review</li>
 * </ul>
 * */
public class TourReview implements BaseEntity {
    private int id;
    private User user;
    private Tour tour;
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
