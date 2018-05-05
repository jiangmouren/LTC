package com.mycompany.app;

import com.mycompany.app.SearchA2DMatrixII;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 8/31/2017.
 */
public class SearchA2DMatrixIITest {
    SearchA2DMatrixII obj = new SearchA2DMatrixII();
    int[][] matrix = {
            {1,   4,  7, 11, 15},
            {2,   5,  8, 12, 19},
            {3,   6,  9, 16, 22},
            {10, 13, 14, 23, 24},
            {18, 21, 23, 26, 30}
    };
    int target1 = 5;
    int target2 = 20;
    @Test
    public void find() throws Exception {
        assertTrue(obj.find(target1, matrix));
        assertFalse(obj.find(target2, matrix));
    }

}