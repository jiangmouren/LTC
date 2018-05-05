package com.mycompany.app;

import com.mycompany.app.BasicCalculatorGeneric;
import org.junit.Test;

public class BasicCalculatorGenericTest {
    BasicCalculatorGeneric obj = new BasicCalculatorGeneric();
    String input1 = "2*3-2";
    String input2 = "  2  *3-2  ";
    String input3 = "2+3*2";
    String input4 = "2*(3-2)";
    String input5 = "2-1 + 2";
    //String input6 = "-3 * 2";
    //String input7 = " -3 * -2";
    @Test
    public void calculate() throws Exception {
        System.out.println(obj.calculate(input1));
        System.out.println(obj.calculate(input2));
        System.out.println(obj.calculate(input3));
        System.out.println(obj.calculate(input4));
        System.out.println(obj.calculate(input5));
    }

}