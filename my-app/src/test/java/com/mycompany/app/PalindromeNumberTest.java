package com.mycompany.app;

import com.mycompany.app.PalindromeNumber;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/23/2017.
 */
public class PalindromeNumberTest {
    PalindromeNumber objectUnderTest = new PalindromeNumber();
    @Test
    public void isPalindrome() throws Exception {
        //System.out.println(objectUnderTest.isPalindrome(1));
        assertTrue(objectUnderTest.isPalindrome(1));
        assertTrue(objectUnderTest.isPalindrome(212));
        assertFalse(objectUnderTest.isPalindrome(3134));
    }

}