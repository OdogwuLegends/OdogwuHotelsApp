package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.data.models.FeedBack;
import odogwuHotels.data.repositories.FeedBackRepository;
import odogwuHotels.data.repositories.OHFeedBackRepository;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.FeedBackResponse;

import java.util.List;

public class OHFeedBackService implements FeedBackService{
    private final FeedBackRepository feedBackRepository = new OHFeedBackRepository();

    @Override
    public FeedBackResponse giveAFeedBack(String feedBack) {
        FeedBack customerFeedBack = new FeedBack();
        customerFeedBack.setMessage(feedBack);
        FeedBack savedFeedBack = feedBackRepository.saveFeedBack(customerFeedBack);
        FeedBackResponse response = Map.feedBackToResponse(savedFeedBack);
        response.setResponse( "Your feed back has been received. Thank You");
        return response;
    }

    @Override
    public FeedBackResponse findFeedBackById(int id) {
        FeedBack foundFeedBack = feedBackRepository.findFeedBackById(id);
        if(foundFeedBack == null){
            throw new IllegalArgumentException("Feedback not found");
        }
        return Map.feedBackToResponse(foundFeedBack);
    }

    @Override
    public List<FeedBack> findAllFeedBacks() {
        return feedBackRepository.findAllFeedBacks();
    }

    @Override
    public DeleteResponse deleteFeedBackById(int id) {
        FeedBack toBeDeleted = feedBackRepository.findFeedBackById(id);
        if(toBeDeleted == null){
            throw new IllegalArgumentException("Feedback not found");
        }
        feedBackRepository.deleteFeedBackById(toBeDeleted.getId());
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Feedback Deleted");
        return response;
    }
}
