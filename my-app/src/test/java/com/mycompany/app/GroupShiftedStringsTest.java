package com.mycompany.app;

import com.mycompany.app.GroupShiftedStrings;
import org.junit.Test;
import java.util.*;

/**
 * Created by eljian on 8/3/2017.
 */
public class GroupShiftedStringsTest {
    GroupShiftedStrings objectUnderTest = new GroupShiftedStrings();
    List<String> list = new ArrayList<>();
    {
        list.add("abc");
        list.add("bcd");
        list.add("acef");
        list.add("xyz");
        list.add("az");
        list.add("ba");
        list.add("a");
        list.add("z");
    }
    @Test
    public void find() throws Exception {
        List<List<String>> result = objectUnderTest.find(list);
        printList(result);
    }

    private void printList(List<List<String>> input){
        for(List<String> list : input){
            for(String str : list){
                System.out.print(str + ", ");
            }
            System.out.println();
        }
    }

}