package com.mycompany.app.tree;

/**
 * Find the first common ancestor or two nodes in a binary tree(not a binary search tree).
 */
import java.util.*;

class TreeNode{
    TreeNode left;
    TreeNode right;
    int value;
    TreeNode(int value){
        this.value = value;
    }
}

class Solution {
    class Status{
        boolean findP;
        boolean findQ;
        TreeNode node;
        public Status(boolean p, boolean q, TreeNode node){
            this.findP = p;
            this.findQ = q;
            // only when both p and q are found, the node is set
            this.node = node;
        }
    }
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return search(root, p, q).node;
    }
    
    private Status search(TreeNode root, TreeNode p, TreeNode q){
        //termination
        if(root==null){
            return new Status(false, false, null);
        }
        
        Status leftRes = search(root.left, p, q);
        if(leftRes.findP && leftRes.findQ){
            return leftRes;
        }
        Status rightRes = search(root.right, p, q);
        if(rightRes.findP && rightRes.findQ){
            return rightRes;
        }
        boolean findP = leftRes.findP || rightRes.findP || root==p;
        boolean findQ = leftRes.findQ || rightRes.findQ || root==q;
        TreeNode node = findP && findQ ? root : null;
        return new Status(findP, findQ, node);
    }
}
