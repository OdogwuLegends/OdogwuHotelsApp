package odogwuHotels.services;

import odogwuHotels.data.models.FeedBack;
import odogwuHotels.dto.responses.DeleteResponse;

import java.util.List;

public interface FeedBackService {
    String giveAFeedBack(String feedBack);
    String findFeedBackById(int id);
    List<FeedBack> findAllFeedBacks();
    DeleteResponse deleteFeedBackById(int id);
}
