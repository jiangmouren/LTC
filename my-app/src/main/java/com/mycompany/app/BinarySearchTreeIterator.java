package com.mycompany.app;
import java.util.Stack;

/**
 * Question: https://leetcode.com/problems/binary-search-tree-iterator/
 * Implement an iterator over a binary search tree (BST).
 * Your iterator will be initialized with the root node of a BST.
 * Calling next() will return the next smallest number in the BST.
 * Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
 */

/**
 * Analysis:
 * Essentially what we need is a "Iterative In-Order Traversal".
 * Cannot do it recursively, because we need that stack in our control.
 */


public class BinarySearchTreeIterator {
    class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        TreeNode(int x){
            this.val = x;
        }
    }

    Stack<TreeNode> stack;
    public BinarySearchTreeIterator(TreeNode root) {
        this.stack = new Stack<>();
        pushRoot(root);
    }

    public int next() {
        TreeNode cur = this.stack.pop();
        pushRoot(cur.right);
        return cur.val;
    }

    public boolean hasNext() {
        return !this.stack.isEmpty();
    }

    private void pushRoot(TreeNode root){
        TreeNode ptr = root;
        while(ptr!=null){
            this.stack.push(ptr);
            ptr = ptr.left;
        }
    }

}
