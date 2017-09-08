package finished.graph.backtracking;

import org.junit.Test;
import java.util.*;

/**
 * Created by eljian on 6/24/2017.
 */
public class GenerateParenthesesTest {
    GenerateParentheses objectUnderTest = new GenerateParentheses();
    @Test
    public void generateParentheses() throws Exception {
        List<String> result0 = objectUnderTest.generateParentheses(0);
        List<String> result1 = objectUnderTest.generateParentheses(1);
        List<String> result2 = objectUnderTest.generateParentheses(2);
        List<String> result3 = objectUnderTest.generateParentheses(3);
        printList(result0);
        printList(result1);
        printList(result2);
        printList(result3);
    }

    private void printList(List<String> list){
        for(String str : list){
            System.out.println(str);
        }
        System.out.println();
    }

}