package Finished;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by eljian on 8/18/2017.
 */
public class TwoSumIIIDataStructureDesignTest {
    TwoSumIIIDataStructureDesign objectUnderTest = new TwoSumIIIDataStructureDesign();
    @Test
    public void test() throws Exception {
        objectUnderTest.add(1);
        objectUnderTest.add(3);
        objectUnderTest.add(5);
        assertTrue(objectUnderTest.find(4));
        assertFalse(objectUnderTest.find(7));
    }
}