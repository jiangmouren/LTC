package Finished;

import Finished.ValidAnagram;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/10/17.
 */
public class ValidAnagramTest {
    ValidAnagram objectUnderTest = new ValidAnagram();
    @Test
    public void isAnagram() throws Exception {
        assertTrue(objectUnderTest.isAnagram("", ""));
        assertTrue(objectUnderTest.isAnagram("anagram", "ganaram"));
        assertFalse(objectUnderTest.isAnagram(null,"whatever"));
        assertFalse(objectUnderTest.isAnagram("ssss","sss"));
    }

}