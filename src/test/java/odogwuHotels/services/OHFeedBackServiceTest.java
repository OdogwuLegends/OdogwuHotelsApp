package odogwuHotels.services;

import odogwuHotels.data.models.FeedBack;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.FeedBackResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OHFeedBackServiceTest {
    private final FeedBackService feedBackService = new OHFeedBackService();

    FeedBackResponse firstFeedBack;
    FeedBackResponse secondFeedBack;

    @BeforeEach
    void setUp(){
       firstFeedBack = feedBackService.giveAFeedBack(first());
       secondFeedBack = feedBackService.giveAFeedBack(second());
    }

    @Test
    void giveAFeedBack(){
        assertNotNull(firstFeedBack);
        assertNotNull(secondFeedBack);

        List<FeedBack> allFeedBacks = feedBackService.findAllFeedBacks();
        assertEquals(2,allFeedBacks.size());
    }
    @Test
    void findFeedBackById(){
        FeedBackResponse foundFeedBack = feedBackService.findFeedBackById(secondFeedBack.getId());
        assertEquals(secondFeedBack.getId(),foundFeedBack.getId());
    }
    @Test
    void nullFeedBacksCannotBeReturned(){
        FeedBackResponse feedBack = new FeedBackResponse();
        feedBack.setMessage("Hello there");
        assertThrows(IllegalArgumentException.class,()-> feedBackService.findFeedBackById(feedBack.getId()));
    }
    @Test
    void findAllFeedBacks(){
        List<FeedBack> allFeedBacks = feedBackService.findAllFeedBacks();
        assertEquals(2,allFeedBacks.size());
        assertNotEquals(3,allFeedBacks.size());
    }
    @Test
    void deleteFeedBackById(){
        DeleteResponse response = feedBackService.deleteFeedBackById(firstFeedBack.getId());
        assertEquals("Feedback Deleted",response.getMessage());
    }
    @Test
    void nullFeedBacksCannotBeDeleted(){
        FeedBackResponse feedBack = new FeedBackResponse();
        feedBack.setMessage("Hello there");
        assertThrows(IllegalArgumentException.class,()-> feedBackService.deleteFeedBackById(feedBack.getId()));
    }


    private String first(){
        return "It came out nice. Thank You!";
    }
    private String second(){
        return "I would prefer a home delivery next time.";
    }

    @AfterEach
    void cleanUp(){
        feedBackService.deleteFeedBackById(firstFeedBack.getId());
        feedBackService.deleteFeedBackById(secondFeedBack.getId());
    }

}