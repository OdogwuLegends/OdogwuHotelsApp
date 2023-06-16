package odogwuHotels.services;

import odogwuHotels.data.models.FeedBack;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.FeedBackResponse;

import java.util.List;

public interface FeedBackService {
    FeedBackResponse giveAFeedBack(String feedBack);
    FeedBackResponse findFeedBackById(int id);
    List<FeedBack> findAllFeedBacks();
    DeleteResponse deleteFeedBackById(int id);
}
