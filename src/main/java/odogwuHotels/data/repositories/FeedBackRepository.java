package odogwuHotels.data.repositories;

import odogwuHotels.data.models.FeedBack;

import java.util.List;

public interface FeedBackRepository {
    FeedBack saveFeedBack(FeedBack feedBack);
    FeedBack findFeedBackById(int id);
    List<FeedBack> findAllFeedBacks();
    void deleteFeedBackById(int id);
}
