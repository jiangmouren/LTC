package com.mycompany.app;

import com.mycompany.app.math.CountPrimes;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/20/2017.
 */
public class CountPrimesTest {
    CountPrimes objectUnderTest = new CountPrimes();

    @Test
    public void countPrimes() throws Exception {
        int result = objectUnderTest.countPrimes(20);
        System.out.print(result);
        assertTrue(result==8);
    }

}