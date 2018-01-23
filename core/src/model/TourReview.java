package model;

/**
 * Model class for tour reviews. Contains two fields - one representing a specific tour
 * that is reviewed and the other is a String containing content of a review itself.
 * */
public class TourReview {
    private Tour tour;
    private String review;


    /**
     * Default constructor to create a tour review taking two parameters
     * @param tour a tour for which user desires write a review
     *             @param review a String value representing content of a review itself
     * */
    public TourReview(Tour tour, String review) {
        this.tour = tour;
        this.review = review;
    }

    public Tour getTour() {
        return tour;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
