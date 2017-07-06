package Finished;

import org.junit.Test;
import Finished.MeetingRoomsII.*;
import static org.junit.Assert.*;

/**
 * Created by eljian on 6/27/2017.
 */
public class MeetingRoomsIITest {
    MeetingRoomsII objectUnderTest = new MeetingRoomsII();
    Meeting[] meetings = {new Meeting(0, 30), new Meeting(5, 10), new Meeting(15, 20)};

    @Test
    public void minMeetingRooms() throws Exception {
        int result = objectUnderTest.minMeetingRooms(meetings);
        assertTrue(result==2);
    }

}