package com.mycompany.app.tree;

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
        int[] res = {root.val};
        binarySearch(root, target, res);
        return res[0];
    }

    private void binarySearch(TreeNode root, double target, int[] res){
        res[0] = Math.abs(res[0]-target)>Math.abs(root.val-target) ? root.val : res[0];
        if(root.val == target){
            return;
        }
        if(root.val<target && root.right!=null){
            binarySearch(root.right, target, res);
        }
        if(root.val>target && root.left!=null){
            binarySearch(root.left, target, res);
        }
    }

}
