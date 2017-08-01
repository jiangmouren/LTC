package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 7/20/2017.
 */
public class FindTheCelebrityTest {
    FindTheCelebrity objectUnderTest = new FindTheCelebrity();
    @Test
    public void find() throws Exception {
        int result = objectUnderTest.find(10);
        System.out.println(result);
    }

}