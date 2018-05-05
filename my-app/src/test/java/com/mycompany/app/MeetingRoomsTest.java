package com.mycompany.app;

import com.mycompany.app.MeetingRooms;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/6/17.
 */
public class MeetingRoomsTest {
    MeetingRooms objectUnderTest = new MeetingRooms();

    @Test
    public void canAttendMeetings() throws Exception {
        MeetingRooms.Interval interval1 = new MeetingRooms.Interval(1,5);
        MeetingRooms.Interval interval2 = new MeetingRooms.Interval(5,7);
        MeetingRooms.Interval interval3 = new MeetingRooms.Interval(9,14);
        MeetingRooms.Interval[] intervals = {interval1, interval2, interval3};
        assertTrue(objectUnderTest.canAttendMeetings(intervals));
    }

    @Test
    public void canAttendMeetingsFalse() throws Exception {
        MeetingRooms.Interval interval1 = new MeetingRooms.Interval(1,5);
        MeetingRooms.Interval interval2 = new MeetingRooms.Interval(4,7);
        MeetingRooms.Interval interval3 = new MeetingRooms.Interval(9,14);
        MeetingRooms.Interval[] intervals = {interval1, interval2, interval3};
        assertFalse(objectUnderTest.canAttendMeetings(intervals));
    }
}