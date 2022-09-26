package com.mycompany.app;

import com.mycompany.app.slidingWindow.ShortestWordDistance;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/6/2017.
 */
public class ShortestWordDistanceTest {
    ShortestWordDistance objectUnderTest = new ShortestWordDistance();
    @Test
    public void shortestDistance() throws Exception {
        String[] words = {"practice", "makes", "perfect", "coding", "makes"};
        String word1 = "coding", word2 = "practice";
        int result = objectUnderTest.shortestDistance(words, word1, word2);
        assertTrue(result == 3);
    }
}