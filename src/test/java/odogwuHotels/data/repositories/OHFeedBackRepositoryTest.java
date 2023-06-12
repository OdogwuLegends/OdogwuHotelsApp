package odogwuHotels.data.repositories;

import odogwuHotels.data.models.FeedBack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OHFeedBackRepositoryTest {
    private final FeedBackRepository feedBackRepository = new OHFeedBackRepository();
    private FeedBack firstFeedBackSaved;
    private FeedBack secondFeedBackSaved;

    @BeforeEach
    void setUp(){
        firstFeedBackSaved = feedBackRepository.saveFeedBack(firstFeedBack());
        secondFeedBackSaved = feedBackRepository.saveFeedBack(secondFeedBack());
    }

    @Test
    void saveFeedBack(){
        assertNotNull(firstFeedBackSaved);
        assertNotNull(secondFeedBackSaved);
        List<FeedBack> allFeedBacks = feedBackRepository.findAllFeedBacks();
        assertEquals(2,allFeedBacks.size());
    }
    @Test
    void findFeedBackById(){
        FeedBack foundFeedBack = feedBackRepository.findFeedBackById(secondFeedBackSaved.getId());
        assertSame(secondFeedBackSaved,foundFeedBack);
        foundFeedBack = feedBackRepository.findFeedBackById(firstFeedBackSaved.getId());
        assertSame(firstFeedBackSaved,foundFeedBack);
    }
    @Test
    void findAllFeedBacks(){
        List<FeedBack> allFeedBacks = feedBackRepository.findAllFeedBacks();
        assertEquals(2,allFeedBacks.size());
        assertTrue(feedBackRepository.findAllFeedBacks().contains(firstFeedBackSaved));

        FeedBack thirdFeedBack = new FeedBack();
        assertFalse(allFeedBacks.contains(thirdFeedBack));
    }
    @Test
    void deleteFeedBackById(){
        feedBackRepository.deleteFeedBackById(secondFeedBackSaved.getId());
        List<FeedBack> allFeedBacks = feedBackRepository.findAllFeedBacks();
        assertEquals(1,allFeedBacks.size());

        feedBackRepository.deleteFeedBackById(firstFeedBackSaved.getId());
        assertEquals(0,allFeedBacks.size());
    }

    private FeedBack firstFeedBack(){
        FeedBack feedBack = new FeedBack();
        feedBack.setMessage("Hello World");
        return feedBack;
    }
    private FeedBack secondFeedBack(){
        FeedBack feedBack = new FeedBack();
        feedBack.setMessage("Hi People");
        return feedBack;
    }

    @AfterEach
    void cleanUp(){
        feedBackRepository.deleteFeedBackById(firstFeedBackSaved.getId());
        feedBackRepository.deleteFeedBackById(secondFeedBackSaved.getId());
    }

}