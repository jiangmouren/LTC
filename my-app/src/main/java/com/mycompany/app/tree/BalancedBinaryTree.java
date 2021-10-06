package com.mycompany.app.tree;

/**
 * Question: https://leetcode.com/problems/balanced-binary-tree/
 * Given a binary tree, determine if it is height-balanced.
 * For this problem, a height-balanced binary tree is defined as:
 * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 *
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: true
 *
 * Example 2:
 * Input: root = [1,2,2,3,3,null,null,4,4]
 * Output: false
 *
 * Example 3:
 * Input: root = []
 * Output: true
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 5000].
 * -104 <= Node.val <= 104
 */

public class BalancedBinaryTree {

    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int x){this.val = x;}
    }

    public boolean isBalanced(TreeNode root) {
        int[] height = new int[1];
        return isBalanced(root, height);
    }

    private boolean isBalanced(TreeNode root, int[] height){
        //termination case
        if(root==null){
            height[0] = 0;
            return true;
        }

        int[] leftH = new int[1];
        boolean leftBalanced = isBalanced(root.left, leftH);
        int[] rightH = new int[1];
        boolean rightBalanced = isBalanced(root.right, rightH);
        height[0] = Math.max(leftH[0], rightH[0]) + 1;
        return leftBalanced && rightBalanced && Math.abs(leftH[0]-rightH[0])<2;
    }
}

