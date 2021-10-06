package com.mycompany.app.tree;

/**
 * Question: https://leetcode.com/problems/binary-tree-inorder-traversal/
 * Given a binary tree, return the inorder traversal of its nodes' values.
 * For example:
 * Given binary tree [1,null,2,3],
 *    1
 *     \
 *      2
 *     /
 *    3
 * return [1,3,2].
 * Note: Recursive solution is trivial, could you do it iteratively?
 */
import java.util.*;
public class BinaryTreeInorderTraversal {
    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){this.val = x;}
    }

    //Iterative solution
    public List<Integer> inorderTraversalIterative(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        pushStack(root, stack);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            res.add(node.val);
            if(node.right!=null){
                pushStack(node.right, stack);
            }
        }
        return res;
    }

    private void pushStack(TreeNode root, Stack<TreeNode> stack){
        TreeNode ptr = root;
        while(ptr!=null){
            stack.push(ptr);
            ptr = ptr.left;
        }
    }

    //recursive solution
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    private void inorder(TreeNode root, List<Integer> res){
        if(root==null){
            return;
        }
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }
}
