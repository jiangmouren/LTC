package finished.design;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/28/2017.
 */
public class ValidParenthesesTest {
    ValidParentheses objectUnderTest = new ValidParentheses();
    @Test
    public void validParentheses() throws Exception {
        assertTrue(objectUnderTest.validParentheses("()[]{}"));
        assertTrue(objectUnderTest.validParentheses("(()[]{})"));
        assertFalse(objectUnderTest.validParentheses("(()[]{}"));
    }

}