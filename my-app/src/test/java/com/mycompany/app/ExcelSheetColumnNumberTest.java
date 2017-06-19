package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/18/17.
 */
public class ExcelSheetColumnNumberTest {
    ExcelSheetColumnNumber objectUnderTest = new ExcelSheetColumnNumber();
    @Test
    public void titleToNumber() throws Exception {
        //System.out.println(objectUnderTest.titleToNumber("AB"));
        assertTrue(objectUnderTest.titleToNumber("A")==1);
        assertTrue(objectUnderTest.titleToNumber("C")==3);
        assertTrue(objectUnderTest.titleToNumber("AB")==28);
        assertTrue(objectUnderTest.titleToNumber("CBA")==2081);
    }

}