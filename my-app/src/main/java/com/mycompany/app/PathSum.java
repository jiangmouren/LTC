package com.mycompany.app;
/**
 * Question:
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
    public static class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        TreeNode(int x){
            this.val = x;
        }
    }
    public boolean find(TreeNode root, int target){
        return helper(root, target, 0);
    }

    private boolean helper(TreeNode root, int target, int sum){
        //edge case
        if(root==null){
            return target==sum;
        }

        //normal cases
        if(helper(root.left, target, sum+root.val)) return true;//return true if any of children paths match
        else if(helper(root.right, target, sum+root.val)) return true;
        else return false;//only return false, when all children paths do not match.
    }

}
