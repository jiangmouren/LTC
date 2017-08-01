/**
 * Question:
 * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
 * Note: If the given node has no in-order successor in the tree, return null.
 */

/**
 * Analysis:
 * It is very intuitive to solve this problem using in-order-traversal, either iteratively or recursively.
 * But I just don't think that's the best solution, because this is for a BST.
 * But if you draw a actual example like the following:
 *           10
 *       6        20
 *    5     8  12    23
 * You can find that the successor will always be on the path from root to the node.
 * And if you look backward from the node to the path, it will be the first one that is larger than the node.
 * Or if you look from root to the node, it the first time, you make a left turn.
 *
 * All we need is to do BST search from root to that node and push all node along the path to a stack.
 * Once find the the node, just pop out every entry in that node and the first one that is larger than the target node,
 * is the one successor.
 */

package com.mycompany.app;
import java.util.*;
public class InorderSuccessorInBST {
    public static class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        public TreeNode(int x){
            this.val = x;
        }
    }
    public TreeNode successor(TreeNode root, int node){
        if(root==null) throw new IllegalArgumentException("Inputs cannot be null");

        Stack<TreeNode> stack = new Stack<>();
        TreeNode ptr = root;
        while(ptr!=null){
            if(ptr.val==node){
                break;
            }
            else if(ptr.val>node){
                stack.push(ptr);
                ptr = ptr.left;
            }
            else{
                stack.push(ptr);
                ptr = ptr.right;
            }
        }
        //if ptr==null, means we cannot find the node
        if(ptr==null) throw new IllegalArgumentException("Cannot find given node in the tree");
        else{
            while(!stack.isEmpty()){
                TreeNode tmp = stack.pop();
                if(tmp.val>ptr.val){
                    return tmp;
                }
            }
            return null;
        }
    }
}