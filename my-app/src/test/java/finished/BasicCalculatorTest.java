package finished;

import finished.BasicCalculator;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicCalculatorTest {
    BasicCalculator obj = new BasicCalculator();
    String input1 = "1 + 1";
    //convert the following into 2+(0-1)*1+2
    String input2 = "2-1 + 2";
    String input3 = "(1+(4+5+2)-3)+(6+8)";
    String input4 = "122345";
    String input5 = "2-(5-6)";
    @Test
    public void calculate() throws Exception {
        System.out.println(obj.calculate(input1));
        System.out.println(obj.calculate(input2));
        System.out.println(obj.calculate(input3));
        System.out.println(obj.calculate(input4));
        System.out.println(obj.calculate(input5));
    }

}