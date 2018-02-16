package co.inventorsoft.touragency.controller.dao.files;

import co.inventorsoft.touragency.controller.dao.BaseDao;
import co.inventorsoft.touragency.model.TourReview;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewsFileSystemDao implements BaseDao<TourReview> {

    @Override
    public List<TourReview> getAll() {
        return null;
    }

    @Override
    public boolean saveAll(List<TourReview> data) {
        return false;
    }
}
