package com.mycompany.app;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by eljian on 10/6/2017.
 */
public class WordSplitTest {
    WordSplit obj = new WordSplit();
    String input1 = "   hello   world   ";
    String input2 = "      ";
    String input3 = "";
    String input4 = "hello";
    @Test
    public void split() throws Exception {
        //printList(obj.split(input1));
        //printList(obj.split(input2));
        printList(obj.split(input4));
        //printList(obj.split(input3));
    }

    private void printList(List<String> list){
        for(String token : list){
            System.out.println(token);
        }
    }

}