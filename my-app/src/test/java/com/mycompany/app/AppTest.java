package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/1/17.
 */

/**JUNIT 4 uses jdk 1.5. So some java 1.7 features are not supported.
 *   https://github.com/junit-team/junit4/blob/master/pom.xml
 * Map<K,V> map = new HashMap<>(); //is Okay in java 1.7 and latter, not Okay in java 1.5
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
