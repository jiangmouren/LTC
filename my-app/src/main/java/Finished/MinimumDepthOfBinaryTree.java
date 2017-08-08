package Finished;
/**
 * Question:
 * Given a binary tree, find its minimum depth.
 * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 */

/**
 * Analysis:
 * Can be done either using DFS or BFS.
 * When using DFS, just need to track the height whenever reach a leaf, need to traverse the whole tree.
 * When using BFS, need to be able to split between levels.
 */

import java.util.*;
public class MinimumDepthOfBinaryTree {
    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){
            this.val = x;
        }
    }
    public int minDepth(TreeNode root){
        int result = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(null);
        queue.add(root);
        queue.add(null);
        while(!queue.isEmpty()){
            TreeNode tmp = queue.poll();
            if(tmp==null){
                if(!queue.isEmpty()){
                    result++;
                }
            }
            else{
                if(tmp.left==null && tmp.right==null) break;
                boolean flag = (queue.peek()==null);
                if(tmp.left!=null) queue.add(tmp.left);
                if(tmp.right!=null) queue.add(tmp.right);
                if(flag) queue.add(null);
            }
        }
        return result;
    }
}
