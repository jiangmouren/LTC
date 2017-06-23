package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/23/2017.
 */
public class ExcelSheetColumnTitleTest {
    ExcelSheetColumnTitle objectUnderTest = new ExcelSheetColumnTitle();

    @Test
    public void title() throws Exception {
        System.out.println(objectUnderTest.title(1));
        System.out.println(objectUnderTest.title(2));
        System.out.println(objectUnderTest.title(27));
        System.out.println(objectUnderTest.title(677));
    }

}