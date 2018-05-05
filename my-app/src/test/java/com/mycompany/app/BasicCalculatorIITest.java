package com.mycompany.app;

import com.mycompany.app.BasicCalculatorII;
import org.junit.Test;

/**
 * Created by jiangmouren on 9/26/17.
 */
public class BasicCalculatorIITest {
    BasicCalculatorII obj = new BasicCalculatorII();
    String input1 = "2+3*2";
    String input2 = "3+2*2";
    String input3 = "  3/2  ";
    String input4 = "3+5  /  2";
    @Test
    public void calculate() throws Exception {
        System.out.println(obj.calculate(input1));
        System.out.println(obj.calculate(input2));
        System.out.println(obj.calculate(input3));
        System.out.println(obj.calculate(input4));
    }

}