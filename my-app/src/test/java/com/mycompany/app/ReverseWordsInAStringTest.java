package com.mycompany.app;

import com.mycompany.app.ReverseWordsInAString;
import org.junit.Test;

/**
 * Created by eljian on 9/1/2017.
 */
public class ReverseWordsInAStringTest {
    ReverseWordsInAString obj = new ReverseWordsInAString();
    //String input1 = "the sky is blue";
    //String input2 = "    the sky is blue";
    String input3 = "    the sky is     blue     ";
    @Test
    public void reverseWords() throws Exception {
        //String res1 = obj.reverseWords(input1);
        //String res2 = obj.reverseWords(input2);
        String res3 = obj.reverseWords(input3);
        //System.out.println(res1);
        //System.out.println(res2);
        System.out.println(res3);
    }

}