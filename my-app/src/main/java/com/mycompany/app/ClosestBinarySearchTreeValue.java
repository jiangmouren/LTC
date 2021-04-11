package com.mycompany.app;

/**
 * Question: https://leetcode.com/problems/closest-binary-search-tree-value/
 * Given the root of a binary search tree and a target value,
 * return the value in the BST that is closest to the target.
 *
 * Example 1:
 * Input: root = [4,2,5,1,3], target = 3.714286
 * Output: 4
 *
 * Example 2:
 * Input: root = [1], target = 4.428571
 * Output: 1
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 104].
 * 0 <= Node.val <= 109
 * -109 <= target <= 109
 *
 */

public class ClosestBinarySearchTreeValue {
    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int x){val=x;}
    }

    public int closestValue(TreeNode root, double target) {
        int[] value = {root.val};
        inOrder(root, target, value);
        return value[0];
    }

    private void inOrder(TreeNode root, double target, int[] value){
        //termination
        if(root==null){
            return;
        }
        inOrder(root.left, target, value);
        if(Math.abs((double)value[0]-target)>Math.abs((double)root.val-target)){
            value[0] = root.val;
        }
        inOrder(root.right, target, value);
    }
}
