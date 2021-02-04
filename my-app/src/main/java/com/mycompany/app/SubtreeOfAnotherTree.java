package com.mycompany.app;

/**
 * https://leetcode.com/problems/subtree-of-another-tree/
 * Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. The tree s could also be considered as a subtree of itself.
 *
 * Example 1:
 * Given tree s:
 *
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return true, because t has the same structure and node values with a subtree of s.
 *
 *
 * Example 2:
 * Given tree s:
 *
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 *     /
 *    0
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return false.
 */
public class SubtreeOfAnotherTree {
    //没什么花头，暴力挨个比较
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
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s.val==t.val){
            if(isIdentical(s, t)){
                return true;
            }
        }
        if(s.left!=null && isSubtree(s.left, t)){
            return true;
        }
        if(s.right!=null && isSubtree(s.right, t)){
            return true;
        }
        return false;
    }

    private boolean isIdentical(TreeNode node1, TreeNode node2){
        if(node1==null && node2==null){
            return true;
        }
        else if(node1==null && node2!=null || node1!=null && node2==null){
            return false;
        }
        else{
            if(node1.val!=node2.val){
                return false;
            }
            else{
                return isIdentical(node1.left, node2.left) && isIdentical(node1.right, node2.right);
            }
        }
    }
}
