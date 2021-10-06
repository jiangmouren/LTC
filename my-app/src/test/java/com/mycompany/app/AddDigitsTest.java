package com.mycompany.app;

import com.mycompany.app.math.AddDigits;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/6/17.
 */
public class AddDigitsTest {
    AddDigits objectUnderTest = new AddDigits();
    @Test
    public void addDigits() throws Exception {
        assertTrue(objectUnderTest.addDigits(11)==2);
        assertTrue(objectUnderTest.addDigits(38)==2);
        assertTrue(objectUnderTest.addDigits(99)==9);
    }

}