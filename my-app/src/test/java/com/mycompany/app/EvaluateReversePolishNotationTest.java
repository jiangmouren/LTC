package com.mycompany.app;

import com.mycompany.app.EvaluateReversePolishNotation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 9/14/2017.
 */
public class EvaluateReversePolishNotationTest {
    EvaluateReversePolishNotation obj = new EvaluateReversePolishNotation();
    String[] input1 = {"2", "1", "+", "3", "*"};
    String[] input2 = {"4", "13", "5", "/", "+"};

    @Test
    public void evalRPN() throws Exception {
        assertEquals(obj.evalRPN(input1), 9);
        assertEquals(obj.evalRPN(input2), 6);
    }
}