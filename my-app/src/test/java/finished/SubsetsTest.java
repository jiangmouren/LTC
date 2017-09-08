package finished;

import org.junit.Test;
import java.util.*;

/**
 * Created by eljian on 8/2/2017.
 */
public class SubsetsTest {
    Subsets objectUnderTest = new Subsets();
    int[] input = {1, 2, 3};
    @Test
    public void subsets() throws Exception {
        printSubset(objectUnderTest.subsets(input));
    }

    private void printSubset(List<List<Integer>> list){
        for(List<Integer> subset : list){
            for(int tmp : subset){
                System.out.print(tmp);
            }
            System.out.println();
        }

    }

}