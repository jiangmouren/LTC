package com.mycompany.app;

/**
 * https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

public class ConvertSortedArrayToBST {
    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {val = x;}
    }
    public TreeNode sortedArrayToBST(int[] nums) {
        return construct(nums, 0, nums.length-1);
    }

    private TreeNode construct(int[] nums, int left, int right){
        //termination
        if(left>right){
            return null;
        }

        int mid = (left + right)/2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = construct(nums, left, mid-1);
        root.right = construct(nums, mid+1, right);
        return root;
    }

}
