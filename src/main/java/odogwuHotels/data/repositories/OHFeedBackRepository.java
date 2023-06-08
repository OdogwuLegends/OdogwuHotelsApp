package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.FeedBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OHFeedBackRepository implements FeedBackRepository{
    List<FeedBack> feedBacks = new ArrayList<>();

    @Override
    public FeedBack saveFeedBack(FeedBack feedBack) {
        feedBack.setId(Utils.generateId());
        feedBacks.add(feedBack);
        return feedBack;
    }

    @Override
    public FeedBack findFeedBackById(int id) {
        for (FeedBack foundFeedBack : feedBacks){
            if(Objects.equals(foundFeedBack.getId(),id)) return foundFeedBack;
        }
        return null;
    }
    public List<FeedBack> findAllFeedBacks(){
        return feedBacks;
    }

    @Override
    public void deleteFeedBackById(int id) {
        FeedBack foundFeedBack = findFeedBackById(id);
        if(foundFeedBack != null) feedBacks.remove(foundFeedBack);
    }
}
