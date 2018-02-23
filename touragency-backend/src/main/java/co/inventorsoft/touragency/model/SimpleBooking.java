package co.inventorsoft.touragency.model;

public class SimpleBooking {
    private int id;
    private int userId;
    private int tourId;
    private boolean status;

    public SimpleBooking() {
    }

    public SimpleBooking(int userId, int tourId, boolean status) {
        this.userId = userId;
        this.tourId = tourId;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public int getTourId() {
        return tourId;
    }

    public boolean setStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
