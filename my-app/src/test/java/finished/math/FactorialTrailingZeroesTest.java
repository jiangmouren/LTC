package finished.math;

import finished.math.FactorialTrailingZeroes;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 8/7/2017.
 */
public class FactorialTrailingZeroesTest {
    FactorialTrailingZeroes objectUnderTest = new FactorialTrailingZeroes();
    @Test
    public void solution() throws Exception {
        assertEquals(objectUnderTest.solution(5), 1);
        assertEquals(objectUnderTest.solution(20), 4);
    }

}