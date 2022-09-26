package com.mycompany.app;
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
    public int minDepth(TreeNode root) {
        if(root==null){
            return 0;
        }

        int[] min = new int[1];
        min[0] = Integer.MAX_VALUE;
        dfs(root, min, 0);
        return min[0];
    }

    private void dfs(TreeNode root, int[] min, int cnt){
        cnt ++;
        if(cnt>=min[0]){
            return;
        }
        if(root.left==null && root.right==null){
            min[0] = Math.min(min[0], cnt);
            return;
        }
        else{
            if(root.left!=null){
                dfs(root.left, min, cnt);
            }
            if(root.right!=null){
                dfs(root.right, min, cnt);
            }
        }
    }

}
