package com.mycompany.app;

import com.mycompany.app.OneEditDistance;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 9/14/2017.
 */
public class OneEditDistanceTest {
    OneEditDistance obj = new OneEditDistance();
    String word1 = "";
    String word2 = "";

    String word3 = "";
    String word4 = "a";

    String word5 = "something";
    String word6 = "samething";
    @Test
    public void isOneEditDistance() throws Exception {
        assertTrue(obj.isOneEditDistance(word1, word2));
        assertTrue(obj.isOneEditDistance(word3, word4));
        assertTrue(obj.isOneEditDistance(word5, word6));
    }

}