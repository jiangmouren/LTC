/**
 * Question:
 * Given a binary tree, return the level order traversal of its nodes' values.
 * (ie, from left to right, level by level).
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * return its level order traversal as:
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 */

/**
 * Analysis:
 * The only catch in this problem is how to identify the end of a level.
 * What we can do is to insert a null after the last node in every level in side the queue.
 * The right child of the right most node in current level is also the last node of the last level.
 */

package Finished;
import java.util.*;
public class BinaryTreeLevelOrderTraversal {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){this.val = x;}
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(root==null) return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(null);
        queue.add(root);
        queue.add(null);
        while(!queue.isEmpty()){
            TreeNode tmp = queue.poll();
            if(tmp==null){
                result.add(new ArrayList<Integer>());
            }
            else{
                boolean check = (queue.peek()==null);
                if(tmp.left!=null) queue.add(tmp.left);
                if(tmp.right!=null) queue.add(tmp.right);
                if(check) queue.add(null);
                result.get(result.size()-1).add(tmp.val);
            }
        }
        return result;
    }
}
