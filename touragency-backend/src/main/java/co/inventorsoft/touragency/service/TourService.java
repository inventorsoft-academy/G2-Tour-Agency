package co.inventorsoft.touragency.service;

import co.inventorsoft.touragency.controller.dao.files.TourFileSystemDao;
import co.inventorsoft.touragency.model.Tour;
import co.inventorsoft.touragency.model.TourType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * {@code TourService} class is a service provider for the application that performs
 * all operations referring to tours manipulations. These include retrieving a list of tours
 * based on several criteria, creating a new tour, cancelling a tour or editing it.
 * */
@Service
public class TourService {
    private TourFileSystemDao dao;
    private List<Tour> tours;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Autowired constructor for {@code TourService} class. Performs dependency injection
     * of the {@link co.inventorsoft.touragency.controller.dao.BaseDao} concrete implementation
     * */
    @Autowired
    public TourService(TourFileSystemDao dao) {
        this.dao = dao;
        logger.info("Instantiated TourService object successfully");
    }

    /**
     * {@code getAllTours()} method will be automatically invoked after an instance of
     * {@code TourService} would be successfully created. The main purpose of this method
     * is to call DAO object to retrieve a list of all tours registered with the system,
     * including cancelled ones.
     * */
    @PostConstruct
    private void getAllTours() {
        this.tours = dao.getAll();
        logger.info("Retrieved tours list from " + dao.getClass().getName());
    }

    /**
     * {@code preDestroy()} method would be automatically invoked before an instance
     * of this class would be destroyed. The main purpose of this method is to save all
     * modified data to the storage to ensure its consistency, integrity and relevance.
     * In case of an unsuccessful saving operation, a corresponding message would be logged.
     * */
    @PreDestroy
    private void saveAll() {
        boolean status = dao.saveAll(tours);
        if (status) {
            logger.info("Saved tours data into text file ");
        } else {
            logger.error("Failed to save tour data");
        }
    }

    /**
     * @param id identifier of a tour
     * @return a {@link Tour} object with a given id
     * */
    public Tour getTour(int id) {
        return tours.get(id - 1);
    }

    /**
     * {@code getTours()} method performs retrieving of a list of tours based on the
     * criterion of whether a tour was cancelled or not.
     * @param includeCancelled a flag that indicates whether the method should include tours
     *                         that were cancelled and thus are not available for booking
     *                         @return a list of {@link Tour} objects registered in the application
     * */
    public List<Tour> getTours(boolean includeCancelled) {
        if (includeCancelled) {
            return tours;
        } else {
            return tours.stream()
                    .filter(Tour::isActive)
                    .filter(tour -> tour.getStartDate().isAfter(LocalDate.now()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Retrieves a list of tours offered by a specified agency
     * @param agency name of an agency which might offer tours
     *               @return a list of tours offered by a specified agency
     * */
    public List<Tour> getTours(String agency) {
        return tours.stream()
                .filter(tour -> tour.getAgency().equals(agency))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of tours that are of a specified type (i.e. contain one of the
     * defined labels in the {@link TourType} enumeration.
     * @param tourType type of a tour which is a criterion for selection
     *                 @return list of tours with a specified tour type
     * */
    public List<Tour> getTours(TourType tourType) {
        return tours.stream()
                .filter(tour -> tour.getTourType().equals(tourType))
                .filter(Tour::isActive)
                .filter(tour -> tour.getStartDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of tours that start (but not necessarily end) in the specified
     * date range independently of their type, price or agency that offers them and are
     * available for booking (i.e. were not cancelled). Filtered tours would BEGIN on
     * any day in range including start and end date.
     * <strong>This method does not check neither tour duration nor its end date</strong>
     * @param from start date of a period of search
     *             @param to end date of a period of search
     * */
    public List<Tour> getTours(LocalDate from, LocalDate to) {
        return tours.stream()
                .filter(Tour::isActive)
                .filter(tour -> tour.getStartDate().isEqual(from) ||
                                tour.getStartDate().isAfter(from))
                .filter(tour -> tour.getEndDate().isEqual(to) ||
                                tour.getEndDate().isBefore(to))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new instance of {@link Tour} class corresponding to a new tour record.
     * {@code createTour()} method does not perform any validation. It should be effectively
     * invoked with a parameter value obtained as a result of
     * {@link co.inventorsoft.touragency.model.validation.TourFactory}'s {@code create()} method.
     * @param tour a tour that would be created and added to the database
     *             @return instance of a new tour
     * */
    public Tour createTour(final Tour tour) {
        boolean status = tours.add(tour);
        tours.get(tours.size() - 1).setId(tours.size());
        return tours.get(tours.size() - 1);
    }

    /**
     * {@code updateTourType()} method performs modification of a tour's type (or label)
     * and sets it to a value provided as a parameter. This value must be one of the elements of
     * {@link TourType} enumeration.
     * The tour to be updated is referenced via its identifier which is also passed as
     * a parameter.
     * This method might potentially throw one of the subclasses of {@link RuntimeException}.
     * In any case of exception catch, a false value would be returned.
     * @param tourId identifier of a tour for which a new tour type value should be set
     *               @param tourType a new value of {@link TourType} to be set for a tour
     *                               @return true if the operation succeeded
     * */
    public boolean updateTourType(int tourId, String tourType) {
        try {
            tours.get(tourId - 1).setTourType(TourType.valueOf(tourType));

            logger.info("Tour type of a tour with id " + tourId + " was successfully updated to" +
                        tourType);
            return true;
        } catch (NullPointerException | NoSuchElementException | IllegalArgumentException e) {
            logger.error("Failed to update tour type of a tour with id " + tourId +
                        String.valueOf(e.getCause() + e.getMessage() +
                                Arrays.toString(e.getStackTrace())));
            return false;
        }
    }

    /**
     * {@code updateTourType()} method performs modification of a tour's price
     * and sets it to a value provided as a parameter.
     * The tour to be updated is referenced via its identifier which is also passed as
     * a parameter.
     * This method performs basic validation for tour price value by assuring it is
     * always non-negative.
     * This method might potentially throw one of the subclasses of {@link RuntimeException}.
     * In any case of exception catch, a false value would be returned.
     * @param tourId identifier of a tour for which a new tour price would should be set
     *               @param newPrice a new value of tour's price
     *                               @return true if the operation succeeded
     * */
    public boolean updateTourPrice(int tourId, int newPrice) {
        if (newPrice <= 0) {
            logger.error("Price cannot be negative. Attempted to set price value for tour " + tourId +
            " to the value: " + newPrice);
            return false;
        }

        try {
            tours.get(tourId - 1).setPrice(newPrice);
            return true;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            logger.error("Failed to change tour's price. Tour id: " + tourId);
            return false;
        }
    }

    /**
     * {@code cancelTour()} method performs cancel operation for a tour whose identifier is
     * provided as a parameter.
     * This method might throw {@link NullPointerException} or {@link NoSuchElementException}
     * if no tour with a provided identifier was found in the database.
     * This method would return true if cancel operation succeeds and false otherwise.
     * @param tourId an id of a tour to be cancelled
     *               @return true if operation succeeded
     * */
    public boolean cancelTour(int tourId) {
        try {
            tours.get(tourId - 1).setActive(false);

            logger.info("Tour with id " + tourId + " was successfully cancelled");
            return true;
        } catch (NullPointerException | NoSuchElementException e) {
            logger.error("Failed to cancel a tour with id  " + tourId +
                    String.valueOf(e.getCause() + e.getMessage() +
                            Arrays.toString(e.getStackTrace())));
            return false;
        }
    }
}
