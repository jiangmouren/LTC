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

    //我只要知道left往下的最大深度，以及right往下的最大深度，那么就可以知道通过root的最大的diameter
    //同样的道理可以获得通过每个node的最大的diameter，只要求得每个点往下的最大深度，就可以求的通过每个点的最大的diameter
    public int diameterOfBinaryTree(TreeNode root) {
        int[] max = {0};
        postOrder(root, max);
        return max[0];
    }

    //需要global access的做成int[]传，只有Local需要的值，就直接return
    private int postOrder(TreeNode root, int[] max){
        int maxPath = 0;
        int depthLeft = 0;
        int depthRight = 0;

        if(root.left!=null){
            depthLeft = postOrder(root.left, max) + 1;
        }
        if(root.right!=null){
            depthRight = postOrder(root.right, max) + 1;
        }
        maxPath = depthLeft + depthRight;
        max[0] = Math.max(max[0], maxPath);
        return Math.max(depthLeft, depthRight);
    }
}
