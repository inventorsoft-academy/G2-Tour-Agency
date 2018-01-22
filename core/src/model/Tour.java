package model;

import java.time.LocalDateTime;

public class Tour {

    private String destination;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TourType tourType;
    private int capacity;

    public Tour(String destination, LocalDateTime startDate, LocalDateTime endDate,
                TourType tourType) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tourType = tourType;
        this.capacity = 50;
    }

    public Tour(String destination, LocalDateTime startDate, LocalDateTime endDate,
                TourType tourType, int capacity) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tourType = tourType;
        this.capacity = capacity;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public TourType getTourType() {
        return tourType;
    }

    public void setTourType(TourType tourType) {
        this.tourType = tourType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
