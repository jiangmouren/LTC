package com.mycompany.app.sep22;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 9/26/17.
 */
public class BasicCalculatorIITest {
    BasicCalculatorII obj = new BasicCalculatorII();
    String input = "2+3*2";
    @Test
    public void calculate() throws Exception {
        System.out.print(obj.calculate(input));
    }

}