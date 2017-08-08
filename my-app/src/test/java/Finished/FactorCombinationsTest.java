package Finished;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by eljian on 8/7/2017.
 */
public class FactorCombinationsTest {
    FactorCombinations objectUnderTest = new FactorCombinations();
    @Test
    public void find() throws Exception {
        assertEquals(objectUnderTest.find(1).size(), 0);
        assertEquals(objectUnderTest.find(37).size(), 0);
        printList(objectUnderTest.find(12));
        printList(objectUnderTest.find(32));
    }

    private void printList(List<List<Integer>> input){
        for(List<Integer> list : input){
            for(int i : list){
                System.out.print(i + ", ");
            }
            System.out.println();
        }
    }

}