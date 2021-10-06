package com.mycompany.app.tree;

/**
 * https://leetcode.com/problems/diameter-of-binary-tree/
 * Given a binary tree, you need to compute the length of the diameter of the tree.
 * The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
 *
 * Example:
 * Given a binary tree
 *           1
 *          / \
 *         2   3
 *        / \
 *       4   5
 * Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
 * Note: The length of path between two nodes is represented by the number of edges between them.
 */
public class DiameterOfBinaryTree {
    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    public int diameterOfBinaryTree(TreeNode root) {
        if(root==null){
            return 0;
        }
        //我只要知道left往下的最大深度，以及right往下的最大深度，那么就可以知道通过root的最大的diameter
        //同样的道理可以获得通过每个node的最大的diameter，只要求得每个点往下的最大深度，就可以求的通过每个点的最大的diameter
        int[] diameter = new int[1];
        int[] depth = new int[1];
        getDepth(root, depth, diameter);
        return diameter[0];
    }

    private void getDepth(TreeNode root, int[] depth, int[] diameter){
        int[] leftD = new int[1];
        int[] rightD = new int[1];
        int left = 0;
        int right = 0;
        if(root.left!=null){
            getDepth(root.left, leftD, diameter);
            left = leftD[0] + 1;
        }
        if(root.right!=null){
            getDepth(root.right, rightD, diameter);
            right = rightD[0] + 1;
        }
        depth[0] = Math.max(left, right);
        diameter[0] = Math.max(diameter[0], left+right);
        return;
    }
}
