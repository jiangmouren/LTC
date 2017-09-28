package finished;

import finished.BasicCalculator;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicCalculatorTest {
    BasicCalculator obj = new BasicCalculator();
    String input1 = "1 + 1";
    String input2 = "2-1 + 2";
    String input3 = "(1+(4+5+2)-3)+(6+8)";
    String input4 = "122345";
    String input5 = "2-(5-6)";
    @Test
    public void calculate() throws Exception {
        //assertEquals(obj.calculate(input1), 2);
        //assertEquals(obj.calculate(input2), 3);
        //assertEquals(obj.calculate(input3), 23);
        //assertEquals(obj.calculate(input4), 122345);
    }

}