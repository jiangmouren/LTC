package finished.design;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 9/13/2017.
 */
public class MinStackOptimizedTest {
    MinStackOptimized obj = new MinStackOptimized();
    @Test
    public void push() throws Exception {
        obj.push(0);
        obj.push(4);
        obj.push(2);
        obj.push(1);
        obj.push(7);
        assertTrue(obj.getMin()==0);
        assertTrue(obj.pop()==7);
        assertTrue(obj.getMin()==0);
        assertTrue(obj.top()==1);
    }

}