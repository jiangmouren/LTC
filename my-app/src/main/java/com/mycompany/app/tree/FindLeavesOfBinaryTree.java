package com.mycompany.app.tree;
import java.util.*;
/**
 * https://leetcode.com/problems/find-leaves-of-binary-tree/
 * Given the root of a binary tree, collect a tree's nodes as if you were doing this:
 *
 * Collect all the leaf nodes.
 * Remove all the leaf nodes.
 * Repeat until the tree is empty.
 *
 * Example 1:
 * Input: root = [1,2,3,4,5]
 * Output: [[4,5,3],[2],[1]]
 * Explanation:
 * [[3,5,4],[2],[1]] and [[3,4,5],[2],[1]] are also considered correct answers since per each level it does not matter the order on which elements are returned.
 *
 * Example 2:
 * Input: root = [1]
 * Output: [[1]]
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 100].
 * -100 <= Node.val <= 100
 */
public class FindLeavesOfBinaryTree {
    class TreeNode {
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

    public List<List<Integer>> findLeaves(TreeNode root) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int height = dfs(root, map);
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0; i<=height; i++){
            res.add(map.get(i));
        }
        return res;
    }

    //get the height of each subTree, store the <height, List<val>> into a map
    //通过root的height就知道整个tree的最高height -> H，然后肯定从0到这个H每个height肯定都有
    //逐个把list从map里读出来就可以了
    private int dfs(TreeNode root, Map<Integer, List<Integer>> map){
        //leaf case
        if(root.left==null && root.right==null){
            if(!map.containsKey(0)){
                map.put(0, new ArrayList<>());
            }
            map.get(0).add(root.val);
            return 0;
        }
        int leftH = 0;
        int rightH = 0;
        if(root.left!=null){
            leftH = dfs(root.left, map);
        }
        if(root.right!=null){
            rightH = dfs(root.right, map);
        }
        int height = 1 + Math.max(leftH, rightH);
        if(!map.containsKey(height)){
            map.put(height, new ArrayList<>());
        }
        map.get(height).add(root.val);
        return height;
    }
}
