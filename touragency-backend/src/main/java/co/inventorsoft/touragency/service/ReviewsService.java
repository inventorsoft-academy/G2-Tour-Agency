package co.inventorsoft.touragency.service;

import co.inventorsoft.touragency.controller.dao.BaseDao;
import co.inventorsoft.touragency.controller.dao.files.ReviewsFileSystemDao;
import co.inventorsoft.touragency.model.TourReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewsService {

    private BaseDao<TourReview> dao;
    private List<TourReview> tourReviews;

    @Autowired
    public ReviewsService(ReviewsFileSystemDao dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void getReviews() {
        this.tourReviews = dao.getAll();
    }

    @PreDestroy
    private void saveAll() {
        boolean status = dao.saveAll(tourReviews);
    }

    public List<TourReview> getTourReviews() {
        return tourReviews;
    }

    public List<TourReview> getReviewsForTour(int tourId) {
        return tourReviews.stream()
                .filter(tourReview -> tourReview.getTour().getId() == tourId)
                .collect(Collectors.toList());
    }

    public List<TourReview> getAgencyTourReviews(String agency) {
        return tourReviews.stream()
                .filter(tourReview -> tourReview.getTour().getAgency().equals(agency))
                .collect(Collectors.toList());
    }
}
