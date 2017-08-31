package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/1/17.
 */

public class AppTest {
    App obj = new App();
    int[] input = {3, 1, 2, 2, 4};
    @Test
    public void testConcatAndUpperString() throws Exception {
        obj.customSort(input);
        for(int i : input){
            System.out.print(i);
        }
    }
}
