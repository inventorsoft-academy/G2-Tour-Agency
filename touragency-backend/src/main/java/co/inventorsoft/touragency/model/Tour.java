package co.inventorsoft.touragency.model;

import co.inventorsoft.touragency.util.validation.DateValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

//import javax.persistence.*;

/**
 * Class {@code Tour} is a model class for a tour which is the principal offering of a tour agency.
 * The concept of a tour entity is one of the basic principles of the application as for sure
 * the tour is one of the main entities in the context of this application.
 * A tour has fields that indicate its destination, country, date range (tour start and end dates),
 * number of available places (capacity), tour's price and the agency that is offering a tour.
 * Class {@code Tour} implements {@link BaseEntity} interface whose principal responsibility is
 * to define models for entities that require a unique identifier. Therefore, this class overrides
 * two mehods of {@link BaseEntity} interface that allow to set tour's identifier and getAll this
 * value.
 * <strong>Identifier's setter must be invoked only at startup while scanning the list
 * of tours from a dao source.</strong>
 * */
//@Entity
//@Table(name = "tours")
public class Tour implements BaseEntity, Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
    private int id;

//    @Column(name = "destination", nullable = false)
    private String destination;

//    @Column(name = "country", nullable = false)
    private String country;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

//    @Enumerated
//    @Column(name = "tour_type")
    private TourType tourType;

//    @Column(name = "capacity", nullable = false)
    private int capacity;

//    @Column(name = "price", nullable = false)
    private int price;


    private String agency;
    private boolean isActive;

    public Tour() {}

    /**
     * Default constructor taking all required fields except for the identifier as parameters.
     * */
    public Tour(String destination, String country, LocalDate startDate, LocalDate endDate,
                TourType tourType, int capacity, int price, String agency, boolean isActive) {
        this.destination = destination;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tourType = tourType;
        this.capacity = capacity;
        this.price = price;
        this.agency = agency;
        this.isActive = isActive;
    }

    /**
     * Default constructor taking all required fields except for the identifier as parameters.
     * */
    public Tour(String destination, String country, String startDate, String endDate,
                String tourType, int capacity, int price, String agency, boolean isActive) {
        this.destination = destination;
        this.country = country;
        this.startDate = LocalDate.parse(startDate, DateValidator.formatter);
        this.endDate = LocalDate.parse(endDate, DateValidator.formatter);
        this.tourType = TourType.valueOf(tourType);
        this.capacity = capacity;
        this.price = price;
        this.agency = agency;
        this.isActive = isActive;
    }

    /**
     * Custom implementation of {@code toString()} method of class {@link Object}.
     * */
    @Override
    public String toString() {
        return id + ": \t Destination: " + destination + ", " + country + "; From: " +
                startDate.toString() + ", to: " + endDate.toString() + ". Type: " +
                tourType.toString() + ". Places available: " + capacity + ". Price: €" + price +
                ". Agency: " + agency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id == tour.id &&
                capacity == tour.capacity &&
                price == tour.price &&
                isActive == tour.isActive &&
                Objects.equals(destination, tour.destination) &&
                Objects.equals(country, tour.country) &&
                Objects.equals(startDate, tour.startDate) &&
                Objects.equals(endDate, tour.endDate) &&
                tourType == tour.tourType &&
                Objects.equals(agency, tour.agency);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, destination, country, startDate, endDate, tourType, capacity,
                price, agency, isActive);
    }

    /**
     * @return tour's identifier
     * */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id value of an identifier to be assigned to a tour
     * */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return tour's destination
     * */
    public String getDestination() {
        return destination;
    }

    /**
     * @return country of the destination
     * */
    public String getCountry() {
        return country;
    }

    /**
     * @return date on which tour begins
     * */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @return date on which tour ends
     * */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @return a value of {@link TourType} class that indicates tour's type (hot,
     * ordinary, special offer etc.)
     * */
    public TourType getTourType() {
        return tourType;
    }

    /**
     * @param tourType a new type of the tour.
     * */
    public void setTourType(TourType tourType) {
        this.tourType = tourType;
    }

    /**
     * @return number of available places for a tour
     * */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return tour's price (for 1 person)
     * */
    public int getPrice() {
        return price;
    }

    /**
     * @return an agency that offers the tour
     * */
    public String getAgency() {
        return agency;
    }

    /**
     * @return true if the tour is available for booking (i.e. not cancelled).
     * */
    public boolean isActive() {
        return isActive;
    }

    /**
     * {@code setActive()} method is invoked when an administrator decides to cancel a tour.
     * The status change leads to impossibility to book a tour.
     * @param active a new status of the tour.
     * */
    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
