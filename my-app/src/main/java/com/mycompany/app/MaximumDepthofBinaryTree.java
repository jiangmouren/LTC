package com.mycompany.app;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given a binary tree, find its maximum depth.
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/**
 * Analysis:
 * Either DFS or BFS can solve this.
 * DFS is more intuitive, because the traversing path is also the depth path you care about.
 * Just need to passing along the depth info while traversing.
 * Either through a global object or just pass a value.
 * BFS can also do the job, just need to carry extra depth info with the node info inside the queue.
 */
import java.util.*;
public class MaximumDepthofBinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    //DFS(pre-order) with value passing.
    public int maxDepth1(TreeNode root) {
        int depth=0;
        //max has to be global
        int[] max= new int[1];
        max[0]=0;
        helper(root, max, depth);
        return max[0];
    }

    //BFS with depth info in queue.
    public int maxDepth2(TreeNode root) {
        if(root==null) return 0;
        int max=1, depth=1;
        SupTreeNode supRoot = new SupTreeNode(root, depth);
        Queue<SupTreeNode> queue = new LinkedList<>();
        queue.add(supRoot);
        while(!queue.isEmpty()){
            SupTreeNode tmp = queue.poll();
            TreeNode left = tmp.node.left;
            TreeNode right = tmp.node.right;
            if(left!=null) queue.add(new SupTreeNode(left, tmp.depth+1));
            if(right!=null) queue.add(new SupTreeNode(right, tmp.depth+1));
            max=Math.max(max,tmp.depth);
        }
        return max;
    }

    private class SupTreeNode{
        TreeNode node;
        int depth;
        SupTreeNode(TreeNode node, int depth){
            this.node = node;
            this. depth = depth;
        }
    }

    private void helper(TreeNode root, int[] max, int depth){
        //API: the "depth" passed in is the depth of the parent node.
        //The node should count its own depth and update that with max.
        //So the "max" passed in is also up to parent the current max.
        //Benefit of passing value instead of global object, is no need to clean up at function return.
        //Unless something need to be passed back at call back, should use value instead of object.

        //Termination Case
        if(root==null)
            return;
        //Recursive Case
        //update max at every node or only at leaf nodes
        max[0] = Math.max(max[0], ++depth);
        helper(root.left, max, depth);
        helper(root.right, max, depth);
        return;
    }
}
