package Finished;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by eljian on 8/7/2017.
 */
public class UniqueBinarySearchTreesTest {
    UniqueBinarySearchTrees objectUnderTest = new UniqueBinarySearchTrees();
    @Test
    public void uniqueBSTs() throws Exception {
        assertEquals(objectUnderTest.uniqueBSTs(3), 5);
        assertEquals(objectUnderTest.uniqueBSTs(4), 14);
    }
}