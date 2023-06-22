package odogwuHotels.data.repositories;

import odogwuHotels.data.models.FeedBack;

import java.util.List;
import java.util.Map;

public interface FeedBackRepository {
    FeedBack saveFeedBack(FeedBack feedBack);
    FeedBack findFeedBackById(int id);
    Map<Integer,FeedBack> getIdsOfAllFeedBacks();
    List<FeedBack> findAllFeedBacks();
    void deleteFeedBackById(int id);
    void deleteAll();
}
