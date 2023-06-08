package odogwuHotels.services;

import odogwuHotels.data.models.FeedBack;
import odogwuHotels.data.repositories.FeedBackRepository;
import odogwuHotels.data.repositories.OHFeedBackRepository;
import odogwuHotels.dto.responses.DeleteResponse;

import java.util.List;

public class OHFeedBackService implements FeedBackService{
    private final FeedBackRepository feedBackRepository = new OHFeedBackRepository();

    @Override
    public String giveAFeedBack(String feedBack) {
        FeedBack customerFeedBack = new FeedBack();
        customerFeedBack.setMessage(feedBack);
        feedBackRepository.saveFeedBack(customerFeedBack);
        return "Feed back received. Thank You";
    }

    @Override
    public String findFeedBackById(int id) {
        return null;
    }

    @Override
    public List<FeedBack> findAllFeedBacks() {
        return feedBackRepository.findAllFeedBacks();
    }

    @Override
    public DeleteResponse deleteFeedBackById(int id) {
        return null;
    }
}