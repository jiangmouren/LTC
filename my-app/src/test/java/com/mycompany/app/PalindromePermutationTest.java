package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/1/17.
 */
public class PalindromePermutationTest {
    PalindromePermutation objectUnderTest = new PalindromePermutation();
    @Test
    public void canPermutePalindromeEdgeCases() throws Exception {
        assertFalse(objectUnderTest.canPermutePalindrome(null));
        assertFalse(objectUnderTest.canPermutePalindrome(""));
        assertTrue(objectUnderTest.canPermutePalindrome("a"));
    }

    @Test
    public void canPermutePalindromeNormalCases() throws Exception {
        assertFalse(objectUnderTest.canPermutePalindrome("abc"));
        assertFalse(objectUnderTest.canPermutePalindrome("aacccb"));
        assertTrue(objectUnderTest.canPermutePalindrome("aaabbccdddd"));
    }
}