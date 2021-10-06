package com.mycompany.app.tree;
/**
 * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
 * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).
 * example 1:
 * Given binary tree [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * return its zigzag level order traversal as:
 * [ [3], [20,9], [15,7] ]
 *
 * Example 2:
 * Input: root = [1]
 * Output: [[1]]
 *
 * Example 3:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 2000].
 * -100 <= Node.val <= 100
 */

import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class BinaryTreeZigzagLevelOrderTraversal {
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

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        //原则上就是一个批量的bfs， 因为要zig-zag，所以需要时不时的对一个批次的entry reverse一下
        //这种情况下使用list+pointers可以做到读出的时候直接倒着读，免去reverse的运算
        //但是这样pointer的manipulation会比较复杂，不划算
        List<List<Integer>> res = new ArrayList<>();
        if(root==null){
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int cnt = queue.size();
        boolean reverse = false;
        while(!queue.isEmpty()){
            List<Integer> buf = new ArrayList<>();
            while(cnt>0){
                TreeNode cur = queue.poll();
                cnt--;
                if(cur.left!=null){
                    queue.add(cur.left);
                }
                if(cur.right!=null){
                    queue.add(cur.right);
                }
                buf.add(cur.val);
            }
            cnt = queue.size();
            if(reverse){
                Collections.reverse(buf);
            }
            res.add(buf);
            reverse = !reverse;
        }
        return res;
    }
}
