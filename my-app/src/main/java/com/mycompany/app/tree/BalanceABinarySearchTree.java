package com.mycompany.app.tree;
import java.util.*;

/**
 * https://leetcode.com/problems/balance-a-binary-search-tree/
 * Given a binary search tree, return a balanced binary search tree with the same node values.
 * A binary search tree is balanced if and only if the depth of the two subtrees of every node never differ by more than 1.
 * If there is more than one answer, return any of them.
 *
 * Example 1:
 * Input: root = [1,null,2,null,3,null,4,null,null]
 * Output: [2,1,3,null,null,null,4]
 * Explanation: This is not the only correct answer, [3,1,4,null,2,null,null] is also correct.
 *
 * Constraints:
 * The number of nodes in the tree is between 1 and 10^4.
 * The tree nodes will have distinct values between 1 and 10^5
 */

public class BalanceABinarySearchTree {
    //思路其实很简单，就是先Inorder把list of Node生成
    //然后按照手动构造balanced binary search tree会做的那样去构造,每次取中间做root
    //至于这么构造是否一定能够保证结果是balanced,我也不知道如何从数学上加以证明，只是试了几个例子，确实是可以的
    public TreeNode balanceBST(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        inOrder(root, list);
        return construct(list, 0, list.size()-1);
    }

    private void inOrder(TreeNode root, List<TreeNode> list){
        //termination
        if(root==null){
            return;
        }
        inOrder(root.left, list);
        list.add(root);
        inOrder(root.right, list);
    }

    private TreeNode construct(List<TreeNode> list, int left, int right){
        //termination
        if(left>right){
            return null;
        }

        int mid = (left+right)/2;
        TreeNode root = list.get(mid);
        root.left = construct(list, left, mid-1);
        root.right = construct(list, mid+1, right);
        return root;
    }

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
}
