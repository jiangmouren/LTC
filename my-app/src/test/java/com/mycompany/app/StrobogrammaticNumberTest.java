package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/22/2017.
 */
public class StrobogrammaticNumberTest {
    StrobogrammaticNumber objectUnderTest = new StrobogrammaticNumber();
    @Test
    public void strobogrammaticNumber() throws Exception {
        assertTrue(objectUnderTest.strobogrammaticNumber("0"));
        assertTrue(objectUnderTest.strobogrammaticNumber("609"));
        assertFalse(objectUnderTest.strobogrammaticNumber("304"));
        assertTrue(objectUnderTest.strobogrammaticNumber("18081"));
    }

}