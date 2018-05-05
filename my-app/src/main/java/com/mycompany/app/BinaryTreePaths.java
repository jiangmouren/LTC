/**
 * Given a binary tree, return all root-to-leaf paths.
 * For example, given the following binary tree:
 *
 *    1
 *  /   \
 * 2     3
 *  \
 *   5
 * All root-to-leaf paths are:
 *
 * ["1->2->5", "1->3"]
 */

/**
 * Analysis:
 * Typical Backtracking problem.
 */
package com.mycompany.app;
import java.util.*;
public class BinaryTreePaths {
    public static class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        public TreeNode(int x){
            this.val = x;
        }
    }
    public List<List<TreeNode>> find(TreeNode root){
        List<List<TreeNode>> result = new ArrayList<>();
        List<TreeNode> buf = new LinkedList<>();
        helper(root, result, buf);
        return result;
    }

    private void helper(TreeNode root, List<List<TreeNode>> result, List<TreeNode> buf){
        /**
         * Cannot use the following way to decide leaf node, otherwise 1->2 will also be added to the result
         * in the above example.
         * Need to check at current level, cannot let it fall to the null node.
         * //edge case
         * if(root==null){
         *     List<TreeNode> tmp = new ArrayList<>();
         *     tmp.addAll(buf);
         *     result.add(tmp);
         *     return;
         * }
         */

        buf.add(root);
        //edge case
        if(root.left==null && root.right==null){
            List<TreeNode> tmp = new ArrayList<>();
            tmp.addAll(buf);
            result.add(tmp);
            buf.remove(buf.size()-1);
            return;
        }

        //Normal cases
        if(root.left!=null) helper(root.left, result, buf);
        if(root.right!=null) helper(root.right, result, buf);
        buf.remove(buf.size()-1);
    }

}
