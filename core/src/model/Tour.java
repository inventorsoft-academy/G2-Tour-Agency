package model;

import java.time.LocalDate;

public class Tour {

    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private TourType tourType;
    private int capacity;
    private boolean isActive = true;

    public Tour(String destination, LocalDate startDate, LocalDate endDate,
                TourType tourType) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tourType = tourType;
        this.capacity = 50;
    }

    public Tour(String destination, LocalDate startDate, LocalDate endDate,
                TourType tourType, int capacity) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tourType = tourType;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Destination: " + destination + "; Start date: " + startDate.toString() + ", end date: " +
                endDate + ". Tour: " + tourType.toString() + ", places available: " + capacity;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
