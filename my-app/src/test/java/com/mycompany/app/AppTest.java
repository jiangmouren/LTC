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
    @Test
    public void testConcatAndUpperString() throws Exception {
        String expectedValue="HELLOWORLD";
        App app=new App();
        String actualValue=app.concatAndUpperString("Hello", "World");
        assertEquals(expectedValue, actualValue);
    }


    @Test
    public void testConcatAndLowerString() throws Exception {
        String expectedValue="HELLOWORLD";
        App app=new App();
        String actualValue=app.concatAndLowerString("Hello", "World");
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void test(){
        int result = Integer.MIN_VALUE - 10;
        System.out.println("Integer MIN_VALUE: " + Integer.MIN_VALUE);
        System.out.println("Integer MIN_VALUE-10: " + result);
    }

}
