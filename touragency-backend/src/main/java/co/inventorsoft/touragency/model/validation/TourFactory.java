package co.inventorsoft.touragency.model.validation;

import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.TourType;
import co.inventorsoft.touragency.util.validation.BasicValidator;
import co.inventorsoft.touragency.util.validation.DateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class TourFactory implements BaseFactory<Tour> {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private String destination;
    private String country;
    private String startDateStr;
    private String endDateStr;
    private String tourTypeStr;
    private int capacity;
    private int price;
    private String agency;
    private boolean isActive;

    public TourFactory(String destination, String country, String startDateStr,
                       String endDateStr, String tourTypeStr, int capacity, int price,
                       String agency, boolean isActive) {
        this.destination = destination;
        this.country = country;
        this.startDateStr = startDateStr;
        this.endDateStr = endDateStr;
        this.tourTypeStr = tourTypeStr;
        this.capacity = capacity;
        this.price = price;
        this.agency = agency;
        this.isActive = isActive;
    }

    @Override
    public Tour create() {
        try {
            boolean isDestinationValid = BasicValidator.validateText(destination);
            boolean isCountryValid = BasicValidator.validateText(country);
            boolean isStartDateValid = DateValidator.validateDateString(startDateStr);
            boolean isEndDateValid = DateValidator.validateDateString(endDateStr);
            boolean isAgencyValid = BasicValidator.validateAgency(agency);
            TourType tourType = TourType.valueOf(tourTypeStr);

            if (isStartDateValid && isEndDateValid) {
                LocalDate startDate = LocalDate.parse(startDateStr, DateValidator.formatter);
                LocalDate endDate = LocalDate.parse(endDateStr, DateValidator.formatter);

                boolean isDateRangeValid = DateValidator.validateDateRange(startDate, endDate);

                if (isDestinationValid && isCountryValid &&
                        isDateRangeValid && isAgencyValid) {
                    return new Tour(
                            destination, country, startDate, endDate, tourType,
                            capacity, price, agency, isActive);
                }
            }
        } catch (RuntimeException e) {
            logger.error("Failed to create a tour with provided parameters. " +
                    e.getCause() + ". " + e.getMessage());
        }
        return null;
    }
}
