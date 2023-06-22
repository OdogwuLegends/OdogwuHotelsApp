package odogwuHotels.data.repositories;

import odogwuHotels.myUtils.Utils;
import odogwuHotels.data.models.FeedBack;

import java.util.*;

public class OHFeedBackRepository implements FeedBackRepository{
    static List<FeedBack> feedBacks = new ArrayList<>();
    private static OHFeedBackRepository singleObject;

    private OHFeedBackRepository() {

    }
    public static OHFeedBackRepository createObject(){
        if(singleObject == null){
            singleObject = new OHFeedBackRepository();
        }
        return singleObject;
    }

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
    @Override
    public Map<Integer,FeedBack> getIdsOfAllFeedBacks(){
        Map<Integer,FeedBack> idsForFeedBacks = new TreeMap<>();
        for(FeedBack feedBack : feedBacks){
            idsForFeedBacks.put(feedBack.getId(), feedBack);
        }
        return idsForFeedBacks;
    }
    public List<FeedBack> findAllFeedBacks(){
        return feedBacks;
    }

    @Override
    public void deleteFeedBackById(int id) {
        FeedBack foundFeedBack = findFeedBackById(id);
        if(foundFeedBack != null) feedBacks.remove(foundFeedBack);
    }

    @Override
    public void deleteAll() {
        feedBacks.clear();
    }
}
