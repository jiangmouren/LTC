package com.mycompany.app;
/**
 * Question: https://leetcode.com/problems/path-sum/
 * Given a binary tree and a sum,
 * determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
 * For example:
 * Given the below binary tree and sum = 22,
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \      \
 *         7    2      1
 * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 */

/**
 * Analysis:
 * This is a typical Backtracking problem.
 */
public class PathSum {
    public class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        TreeNode(int x){
            this.val = x;
        }
    }
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null){
            return false;
        }
        return backtracking(root, targetSum, 0);
    }

    private boolean backtracking(TreeNode root, int target, int sum){
        //注意判断leaf node，只能通过判断左右子树均为null来确定，不能认为null的parent，就一定是leaf node
        sum += root.val;
        if(root.left==null && root.right==null){
            return sum == target;
        }
        if(root.left!=null && backtracking(root.left, target, sum) || root.right!=null && backtracking(root.right, target, sum)){
            return true;
        }
        sum -= root.val;
        return false;
    }
}
