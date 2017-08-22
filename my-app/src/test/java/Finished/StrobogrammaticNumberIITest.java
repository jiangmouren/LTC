package Finished;

import org.junit.Test;
import java.util.*;

/**
 * Created by eljian on 8/18/2017.
 */
public class StrobogrammaticNumberIITest {
    StrobogrammaticNumberII objectUnderTest = new StrobogrammaticNumberII();
    @Test
    public void find() throws Exception {
        printList(objectUnderTest.find(1));
        printList(objectUnderTest.find(2));
        printList(objectUnderTest.find(3));
    }

    private void printList(List<List<Integer>> input){
        for(List<Integer> list : input){
            for(int val : list){
                System.out.print(val);
            }
            System.out.println();
        }
    }

}