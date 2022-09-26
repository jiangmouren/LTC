package com.mycompany.app.tree;

/**
 * https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
 * Given an array of integers preorder, which represents the preorder traversal of a BST (i.e., binary search tree),
 * construct the tree and return its root.
 * It is guaranteed that there is always possible to find a binary search tree with the given requirements for the given test cases.
 * A binary search tree is a binary tree where for every node, any descendant of Node.left has a value strictly less than Node.val,
 * and any descendant of Node.right has a value strictly greater than Node.val.
 * A preorder traversal of a binary tree displays the value of the node first,
 * then traverses Node.left, then traverses Node.right.
 *
 * Example 1:
 * Input: preorder = [8,5,1,7,10,12]
 * Output: [8,5,10,1,7,null,12]
 *
 * Example 2:
 * Input: preorder = [1,3]
 * Output: [1,null,3]
 *
 * Constraints:
 * 1 <= preorder.length <= 100
 * 1 <= preorder[i] <= 1000
 * All the values of preorder are unique.
 */
public class ConstructBinarySearchTreeFromPreorderTraversal {
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

    public TreeNode bstFromPreorder(int[] preorder) {
        int[] idx = {0};
        int up = Integer.MAX_VALUE;
        int low = Integer.MIN_VALUE;
        return construct(preorder, idx, up, low);
    }

    private TreeNode construct(int[] preorder, int[] idx, int up, int low){
        if(idx[0]>=preorder.length || preorder[idx[0]]>=up || preorder[idx[0]]<=low){
            return null;
        }

        int val = preorder[idx[0]];
        TreeNode root = new TreeNode(val);
        idx[0]++;
        root.left = construct(preorder, idx, val, low);
        root.right = construct(preorder, idx, up, val);
        return root;
    }
}
