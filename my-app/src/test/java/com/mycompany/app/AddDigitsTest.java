package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/6/17.
 */
public class AddDigitsTest {
    AddDigits objectUderTest = new AddDigits();
    @Test
    public void addDigits() throws Exception {
        assertTrue(objectUderTest.addDigits(11)==2);
        assertTrue(objectUderTest.addDigits(38)==2);
        assertTrue(objectUderTest.addDigits(99)==9);
    }

}