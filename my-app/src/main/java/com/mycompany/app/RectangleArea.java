package com.mycompany.app;

/**
 * https://leetcode.com/problems/rectangle-area/
 * Given the coordinates of two rectilinear rectangles in a 2D plane,
 * return the total area covered by the two rectangles.
 * The first rectangle is defined by its bottom-left corner (A, B) and its top-right corner (C, D).
 * The second rectangle is defined by its bottom-left corner (E, F) and its top-right corner (G, H).
 *
 * Example 1:
 * Rectangle Area
 * Input: A = -3, B = 0, C = 3, D = 4, E = 0, F = -1, G = 9, H = 2
 * Output: 45
 *
 * Example 2:
 * Input: A = -2, B = -2, C = 2, D = 2, E = -2, F = -2, G = 2, H = 2
 * Output: 16
 *
 * Constraints:
 * -10^4 <= A, B, C, D, E, F, G, H <= 10^4
 */
public class RectangleArea {
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int overlap = 0;
        int left = Math.max(A, E);
        int right = Math.min(C, G);
        int up = Math.min(D, H);
        int bottom = Math.max(B, F);
        if(right>left && up>bottom){
            overlap = (right-left)*(up-bottom);
        }

        return (C-A)*(D-B) + (G-E)*(H-F) - overlap;
    }
}
