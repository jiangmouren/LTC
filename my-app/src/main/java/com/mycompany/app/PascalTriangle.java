package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/pascals-triangle/
 * Given an integer numRows, return the first numRows of Pascal's triangle.
 *
 * In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:
 * src\main\resources\pascals-triangle.png
 *
 * Example 1:
 * Input: numRows = 5
 * Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 *
 * Example 2:
 * Input: numRows = 1
 * Output: [[1]]
 *
 * Constraints:
 * 1 <= numRows <= 30
 */

public class PascalTriangle{
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list0 = new ArrayList<>();
        list0.add(1);
        res.add(list0);
        //注意这里要单独生成一个ptr, 不能直接用list0，因为list0已经被加进res里，不应再改变其指向
        List<Integer> pre = list0;
        for(int i=1; i<numRows; i++){
            List<Integer> list = new ArrayList<>();
            populate(pre, list);
            res.add(list);
            pre = list;
        }
        return res;
    }

    private void populate(List<Integer> l0, List<Integer> l1){
        l1.add(1);
        for(int i=0; i<l0.size()-1; i++){
            l1.add(l0.get(i)+l0.get(i+1));
        }
        l1.add(1);
    }
}