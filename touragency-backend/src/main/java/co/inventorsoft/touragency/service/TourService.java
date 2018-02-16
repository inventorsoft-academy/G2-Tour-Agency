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

@Service
public class TourService {
    private TourFileSystemDao dao;
    private List<Tour> tours;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TourService(TourFileSystemDao dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void getAllTours() {
        this.tours = dao.getAll();
        logger.info("Retrieved tours list from " + dao.getClass().getName());
    }

    @PreDestroy
    private void saveAll() {
        boolean status = dao.saveAll(tours);
        if (status) {
            logger.info("Saved tours data into text file ");
        } else {
            logger.error("Failed to save tour data");
        }
    }

    public Tour getTour(int id) {
        return tours.get(id - 1);
    }

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

    public List<Tour> getTours(String agency) {
        return tours.stream()
                .filter(tour -> tour.getAgency().equals(agency))
                .collect(Collectors.toList());
    }

    public List<Tour> getTours(TourType tourType) {
        return tours.stream()
                .filter(tour -> tour.getTourType().equals(tourType))
                .filter(Tour::isActive)
                .filter(tour -> tour.getStartDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public List<Tour> getTours(LocalDate from, LocalDate to) {
        return tours.stream()
                .filter(Tour::isActive)
                .filter(tour -> tour.getStartDate().isEqual(from) ||
                                tour.getStartDate().isAfter(from))
                .filter(tour -> tour.getEndDate().isEqual(to) ||
                                tour.getEndDate().isBefore(to))
                .collect(Collectors.toList());
    }

    public Tour createTour(final Tour tour) {
        boolean status = tours.add(tour);
        tours.get(tours.size() - 1).setId(tours.size());
        return tours.get(tours.size() - 1);
    }

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

    public boolean updateTourPrice(int tourId, int newPrice) {
        try {
            tours.get(tourId - 1).setPrice(newPrice);
            return true;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            logger.error("Failed to change tour's price. Tour id: " + tourId);
            return false;
        }
    }

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
