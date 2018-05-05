package com.mycompany.app;

import com.mycompany.app.ValidPalindrome;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/28/2017.
 */
public class ValidPalindromeTest {
    ValidPalindrome objectUnderTest = new ValidPalindrome();
    @Test
    public void validPalindrome() throws Exception {
        assertTrue(objectUnderTest.validPalindrome("A man, a plan, a canal: Panama"));
    }

}