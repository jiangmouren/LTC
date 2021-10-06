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
        int[] prePos = {0};
        return helper(preorder, prePos, inorder, 0, inorder.length-1);
    }

    private TreeNode helper(int[] preorder, int[] prePos, int[] inorder, int left, int right){
        TreeNode root = new TreeNode(preorder[prePos[0]]);
        int idx = left;
        for(int i=left; i<=right; i++){
            if(inorder[i]==preorder[prePos[0]]){
                idx = i;
                break;
            }
        }
        prePos[0]++;
        if(idx>left){
            root.left = helper(preorder, prePos, inorder, left, idx-1);
        }
        if(idx<right){
            root.right = helper(preorder, prePos, inorder, idx+1, right);
        }
        return root;
    }
}
