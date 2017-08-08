package Finished;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 7/20/2017.
 */
public class HouseRobberTest {
    HouseRobber objectUnderTest = new HouseRobber();
    int[] money = {0, 2, 4, 2, 7};
    @Test
    public void maxRobber() throws Exception {
        int result = objectUnderTest.maxRobber(money);
        assertEquals(result, 11);
    }

}