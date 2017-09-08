package finished.design;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/14/2017.
 */
public class MinStackTest {
    MinStack objectUnderTest= new MinStack();
    @Test
    public void test() throws Exception {
        objectUnderTest.push(0);
        objectUnderTest.push(4);
        objectUnderTest.push(2);
        objectUnderTest.push(1);
        objectUnderTest.push(7);
        assertTrue(objectUnderTest.getMin()==0);
        assertTrue(objectUnderTest.pop()==7);
        assertTrue(objectUnderTest.getMin()==0);
        assertTrue(objectUnderTest.top()==1);
    }

}