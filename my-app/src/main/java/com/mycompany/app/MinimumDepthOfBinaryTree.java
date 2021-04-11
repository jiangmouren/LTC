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
    public int minDepth(TreeNode root){
        if(root==null){
            return 0;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int cnt = q.size();
        int depth = 1;
        boolean found = false;
        while(!q.isEmpty()){
            while(cnt>0){
                TreeNode cur = q.poll();
                cnt--;
                if(cur.left==null && cur.right==null){
                    found = true;
                    break;
                }
                if(cur.left!=null){
                    q.add(cur.left);
                }
                if(cur.right!=null){
                    q.add(cur.right);
                }
            }
            if(found){
                break;
            }
            cnt = q.size();
            depth++;
        }
        return depth;
    }

    public int minDepthDfs(TreeNode root) {
        if(root==null){
            return 0;
        }
        int[] min = {Integer.MAX_VALUE};
        preOrder(root, 1, min);
        return min[0];
    }

    private void preOrder(TreeNode root, int depth, int[] min){
        //termination
        if(root.left==null && root.right==null){
            min[0] = Math.min(min[0], depth);
            return;
        }

        if(root.left!=null){
            preOrder(root.left, depth+1, min);
        }
        if(root.right!=null){
            preOrder(root.right, depth+1, min);
        }
    }
}
