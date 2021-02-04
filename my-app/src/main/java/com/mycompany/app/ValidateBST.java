package com.mycompany.app;

/**
 * https://leetcode.com/problems/validate-binary-search-tree/
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * A valid BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 * Example 1:
 * Input: root = [2,1,3]
 * Output: true
 *
 * Example 2:
 * Input: root = [5,1,4,null,null,3,6]
 * Output: false
 * Explanation: The root node's value is 5 but its right child's value is 4.
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 104].
 * -2^31 <= Node.val <= 2^31 - 1
 */


public class ValidateBST {
    public static class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        public TreeNode(int val){
            this.val = val;
        }
    }

    //题目里面有个小trap，Node.val可以取到Integer.MAX_VALUE, Integer.MIN_VALUE,所以max&min都得用long
    public boolean isValidBST(TreeNode root) {
        long max = Long.MAX_VALUE;
        long min = Long.MIN_VALUE;
        return check(root, max, min);
    }
    private boolean check(TreeNode root, long max, long min){
        if(root==null){
            return true;
        }
        if(root.val>=max || root.val<=min){
            return false;
        }
        long leftMax = Math.min(max, root.val);
        long rightMin = Math.max(min, root.val);
        return check(root.left, leftMax, min) && check(root.right, max, rightMin);
    }
}
