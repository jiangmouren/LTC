package com.mycompany.app;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/**
 * Analysis:
 * In order to get a "height balanced" BST, it's all about picking up the right root at every level.
 * This is intrinsically recursive problem.
 */

public class ConvertSortedArrayToBST {
    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {val = x;}
    }
    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums==null) throw new IllegalArgumentException("inputs cannot be null");
        //for odd length, take the mid point; for even length, take the right side point.
        int mid = nums.length/2;
    }
}
