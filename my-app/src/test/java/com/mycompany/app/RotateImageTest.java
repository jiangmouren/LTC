package com.mycompany.app;

import com.mycompany.app.RotateImage;
import org.junit.Test;

/**
 * Created by eljian on 6/29/2017.
 */
public class RotateImageTest {
    RotateImage objectUnderTest = new RotateImage();
    int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    @Test
    public void rotate() throws Exception {
        printMatrix(matrix);
        System.out.println();
        objectUnderTest.rotate1(matrix);
        printMatrix(matrix);
    }

    private void printMatrix(int[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                System.out.print(matrix[i][j] + ", ");
            }
            System.out.println();
        }
    }

}