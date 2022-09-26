package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/symmetric-tree/
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 * But the following [1,2,2,null,3,null,3] is not:
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 */

public class SymmetricTree{
    public class TreeNode {
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
    //2种解法：
    //1. 写一个function去比较left-subtree & right-subtree，判断他们互为mirror，可以recursively写
    //2. 第二种解法，写一个level order traversal的变种，改变node进和出的安排
    public boolean isSymmetric(TreeNode root) {
        if(root==null){
            return true;
        }
        else{
            return isMirror(root.left, root.right);
        }
    }

    private boolean isMirror(TreeNode root1, TreeNode root2){
        //termination cases
        if(root1==null && root2==null){
            return true;
        }
        else if(root1==null && root2!=null || root1!=null && root2==null){
            return false;
        }
        else{//recursive case
            return root1.val == root2.val && isMirror(root1.left, root2.right) && isMirror(root1.right, root2.left);
        }
    }

    public boolean isSymmetricSln2(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(root);
        while(!q.isEmpty()){
            TreeNode node1 = q.poll();
            TreeNode node2 = q.poll();
            if(node1==null && node2==null){
                //return true;注意这里应该是continue，而是不是return true!!!!
                continue;
            }
            else if(node1==null || node2==null){
                return false;
            }
            else{
                if(node1.val!=node2.val){
                    return false;
                }
                q.add(node1.left);
                q.add(node2.right);
                q.add(node1.right);
                q.add(node2.left);
            }
        }
        return true;
    }

    //当心下面一种错误的解法，用stack消元的办法，存在逻辑漏洞，在下面这种情况下就会出错
    //                     1
    //                   /   \
    //                  2     2
    //                    \     \
    //                      3     3
    /**
     *     public boolean isSymmetric(TreeNode root) {
     *         Stack<TreeNode> stack = new Stack<>();
     *         Queue<TreeNode> queue = new LinkedList<>();
     *         queue.add(root);
     *         stack.push(root);
     *         while(!queue.isEmpty()){
     *             TreeNode top = queue.poll();
     *             if(top.left!=null){
     *                 queue.add(top.left);
     *                 pushStack(stack, top.left);
     *             }
     *             if(top.right!=null){
     *                 queue.add(top.right);
     *                 pushStack(stack, top.right);
     *             }
     *         }
     *         return stack.size()==1 && stack.peek()==root;
     *     }
     *
     *     private void pushStack(Stack<TreeNode> stack, TreeNode root){
     *         if(stack.isEmpty()){
     *             stack.push(root);
     *         }
     *         else{
     *             if(stack.peek().val==root.val){
     *                 stack.pop();
     *             }
     *             else{
     *                 stack.push(root);
     *             }
     *         }
     *     }
     */

}
