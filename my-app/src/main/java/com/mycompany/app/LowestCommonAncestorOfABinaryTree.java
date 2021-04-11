package com.mycompany.app;
/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * According to the definition of LCA on Wikipedia
 * : “The lowest common ancestor is defined between two nodes v and w as the lowest node in T that has both v and w
 * as descendants (where we allow a node to be a descendant of itself).”
 *
 *         _______3______
 *        /              \
 *     ___5__          ___1__
 *    /      \        /      \
 *    6      _2       0       8
 *          /  \
 *          7   4
 * For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3.
 * Another example is LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 */

import java.util.*;

/**
 * Analysis:
 */

public class LowestCommonAncestorOfABinaryTree {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    class Status{
        boolean findP;
        boolean findQ;
        TreeNode node;
        public Status(boolean p, boolean q, TreeNode node){
            this.findP = p;
            this.findQ = q;
            this.node = node;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return search(root, p, q).node;
    }

    //一个简单的post order traversal，唯一要注意的是不要丢掉在root位置发现一个node的可能性
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
        //下面这种写法虽不是performance最优的，但却是代码最简洁的
        //做完左侧之后，如果左侧发现一个，在root又发现一个，其实可以避开查询右侧，但是那样代码会有很多分叉，显臃肿
        boolean findP = leftRes.findP || rightRes.findP || root==p;
        boolean findQ = leftRes.findQ || rightRes.findQ || root==q;
        TreeNode node = findP && findQ ? root : null;
        return new Status(findP, findQ, node);
    }

}
