package finished.bit_manipulation;

import org.junit.Test;

/**
 * Created by eljian on 6/21/2017.
 */
public class GrayCodeTest {
    GrayCode objectUnderTest = new GrayCode();
    @Test
    public void grayCode() throws Exception {
        int[] result0 = objectUnderTest.grayCode(0);
        int[] result1 = objectUnderTest.grayCode(1);
        int[] result2 = objectUnderTest.grayCode(2);
        int[] result3 = objectUnderTest.grayCode(3);
        int[] result4 = objectUnderTest.grayCode(4);
        printList(result0);//it's Okay to iterate through an empty array
        printList(result1);
        printList(result2);
        printList(result3);
        printList(result4);
    }

    private void printList(int[] result){
        for(int token : result){
            System.out.print(token + ", ");
        }
        System.out.println();
    }

}