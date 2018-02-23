package co.inventorsoft.touragency.controller.dao.files;

import co.inventorsoft.touragency.controller.dao.BaseDao;
import co.inventorsoft.touragency.model.TourReview;
import co.inventorsoft.touragency.model.validation.ReviewFactory;
import co.inventorsoft.touragency.service.AuthenticationService;
import co.inventorsoft.touragency.service.TourService;
import co.inventorsoft.touragency.util.ConsoleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code ReviewsFileSystemDao} is a concrete implementation of the
 * {@link BaseDao} interface that provides functionality for file system read/write
 * operations for tour reviews.
 * This class provides functionality that allows the application read and write
 * reviews data from the filesystem data storage
 * */
@Component
public class ReviewsFileSystemDao implements BaseDao<TourReview> {

    private final String PATH = "D:\\Progs\\JAVA\\2018\\ACADEMY\\G2-Tour-Agency\\src\\main\\" +
            "resources\\data\\reviews.mta";
    private AuthenticationService authenticationService;
    private TourService tourService;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public ReviewsFileSystemDao(AuthenticationService authenticationService,
                                TourService tourService) {
        this.authenticationService = authenticationService;
        this.tourService = tourService;
    }

    @Override
    public List<TourReview> getAll() {
        File file = new File(PATH);
        List<TourReview> reviews = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] input = line.split("; ");
                int userId = Integer.valueOf(input[0]);
                int tourId = Integer.valueOf(input[1]);
                String review = input[2];

                TourReview tourReview = new ReviewFactory(
                        authenticationService.getUsers().get(userId - 1),
                        tourService.getTour(tourId),
                        review).create();
                if (tourReview != null) {
                    reviews.add(tourReview);
                }
            }
            assignIdentifiers(reviews);

            logger.info("Successfully imported reviews list from file");

            return reviews;
        } catch (IOException e) {
            logger.error("Failed to import reviews list from a text file. " + e.toString());
        }
        return null;
    }

    @Override
    public boolean saveAll(List<TourReview> data) {
        File file = new File(PATH);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            for (TourReview review : data) {
                fileWriter.write(
                        review.getUser().getId() + "; " +
                                review.getTour().getId() + "; " +
                                review.getReview());
                fileWriter.write("\n");
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            ConsoleHelper.write("Exception!");
        }

        return false;
    }
}
