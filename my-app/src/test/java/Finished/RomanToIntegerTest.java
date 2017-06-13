package Finished;

import Finished.RomanToInteger;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/10/17.
 */
public class RomanToIntegerTest {
    RomanToInteger objectUnderTest = new RomanToInteger();
    String s1 = "I";
    String s2 = "II";
    String s3 = "III";
    String s4 = "IV";
    String s5 = "V";
    String s6 = "VI";
    String s7 = "VII";
    String s8 = "VIII";
    String s9 = "IX";
    String s10 = "X";
    String s11 = "XI";
    String s59 = "LIX";
    @Test
    public void romanToInt() throws Exception {
        assertTrue(objectUnderTest.romanToInt(s1)==1);
        assertTrue(objectUnderTest.romanToInt(s2)==2);
        assertTrue(objectUnderTest.romanToInt(s3)==3);
        assertTrue(objectUnderTest.romanToInt(s4)==4);
        assertTrue(objectUnderTest.romanToInt(s5)==5);
        assertTrue(objectUnderTest.romanToInt(s6)==6);
        assertTrue(objectUnderTest.romanToInt(s7)==7);
        assertTrue(objectUnderTest.romanToInt(s8)==8);
        assertTrue(objectUnderTest.romanToInt(s9)==9);
        assertTrue(objectUnderTest.romanToInt(s10)==10);
        assertTrue(objectUnderTest.romanToInt(s11)==11);
        assertTrue(objectUnderTest.romanToInt(s59)==59);
    }

}