package com.mycompany.app.tree;
/**
 * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 * Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree
 * and inorder is the inorder traversal of the same tree, construct and return the binary tree.
 *
 * Example 1:
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * Output: [3,9,20,null,null,15,7]
 *
 * Example 2:
 * Input: preorder = [-1], inorder = [-1]
 * Output: [-1]
 */

public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    class TreeNode {
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

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int[] idx = {0};
        return construct(preorder, idx, inorder, 0, preorder.length-1);
    }

    private TreeNode construct(int[] preorder, int[] idx, int[] inorder, int left, int right){
        if(left>right){
            return null;
        }
        int val = preorder[idx[0]];
        TreeNode root = new TreeNode(val);
        idx[0]++;
        int ptr = left;
        while(inorder[ptr]!=val){
            ptr++;
        }

        root.left = construct(preorder, idx, inorder, left, ptr-1);
        root.right = construct(preorder, idx, inorder, ptr+1, right);
        return root;
    }
}
