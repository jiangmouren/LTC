/**
 * https://leetcode.com/problems/binary-tree-paths/
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
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        preOrder(res, buf, root);
        return res;
    }

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
    private void preOrder(List<String> res, StringBuilder buf, TreeNode root){
        int l = buf.length();
        if(buf.length()==0){
            buf.append(root.val);
        }
        else{
            buf.append("->");
            buf.append(root.val);
        }
        //termination
        if(root.left==null && root.right==null){
            res.add(buf.toString());
            buf.setLength(l);
            return;
        }
        if(root.left!=null){
            preOrder(res, buf, root.left);
        }
        if(root.right!=null){
            preOrder(res, buf, root.right);
        }
        buf.setLength(l);
    }
}
