package com.mycompany.app;
import java.util.*;

public class App
{
    public static void main(String[] args){
        //no overlap

    }
    //矩形1：A, B; 矩形2：c, D
    public int overlapArea(int[] A, int[] B, int[] C, int[] D){
        //no overlap
        if(B[0]<=C[0] || B[1]>=C[1]) {
            return 0;
        }
        else{
            int left = Math.max(A[0], C[0]);
            int right = Math.min(B[0], D[0]);
            int up = Math.min(A[1], C[1]);
            int bottom = Math.max(B[1], D[1]);
            return (right-left)*(up-bottom);
        }
    }
}