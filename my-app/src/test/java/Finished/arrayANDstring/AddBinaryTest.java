package Finished.arrayANDstring;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/13/17.
 */
public class AddBinaryTest {
    AddBinary objectUnderTest = new AddBinary();
    @Test
    public void sum() throws Exception {
        assertTrue(objectUnderTest.sum("0", "1").equals("1"));
        assertTrue(objectUnderTest.sum("01", "1").equals("10"));
        assertTrue(objectUnderTest.sum("111", "1").equals("1000"));
        assertTrue(objectUnderTest.sum("", "1").equals("1"));
    }

}