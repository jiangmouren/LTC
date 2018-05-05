package com.mycompany.app;

import com.mycompany.app.EditDistance;
import org.junit.Test;

/**
 * Created by eljian on 9/14/2017.
 */
public class EditDistanceTest {
    EditDistance obj = new EditDistance();
    String word1 = "";
    String word2 = "";

    String word3 = "something";
    String word4 = "sonetgin";

    String word5 = "something";
    String word6 = "meth";
    @Test
    public void minDistance() throws Exception {
        System.out.println(obj.minDistance(word1, word2));
        System.out.println(obj.minDistance(word3, word4));
        System.out.println(obj.minDistance(word5, word6));
    }

}