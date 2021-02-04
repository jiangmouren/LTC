package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/symmetric-tree/
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 * But the following [1,2,2,null,3,null,3] is not:
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 */

public class SymmetricTree{
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
    //比较明显的有3中解法：
    //1. 做一个in-order的traversal,生成一个list,这个list应该是中心对称的。
    //2. 写一个function去比较left-subtree & right-subtree，判断他们互为revert，可以recursively写
    //3. 与2相同的思路，但是iteratively写，类似于BFS的写法，但是左右两边访问desendents的顺序反过来，然后每次从各自queue里出来的应该都是一样的
    public boolean isSymmetric(TreeNode root) {
        if(root==null){
            return true;
        }
        else{
            return isRevert(root.left, root.right);
        }
    }

    private boolean isRevert(TreeNode root1, TreeNode root2){
        //termination cases
        if(root1==null && root2==null){
            return true;
        }
        else if(root1==null && root2!=null || root1!=null && root2==null){
            return false;
        }
        else{//recursive case
            return root1.val == root2.val && isRevert(root1.left, root2.right) && isRevert(root1.right, root2.left);
        }
    }

    public boolean isSymmetricSln2(TreeNode root) {
        if(root==null){
            return true;
        }
        else{
            return isRevertSln2(root.left, root.right);
        }
    }

    private boolean isRevertSln2(TreeNode root1, TreeNode root2){
        //termination cases
        if(root1==null && root2==null){
            return true;
        }
        else if(root1==null && root2!=null || root1!=null && root2==null){
            return false;
        }
        else{//recursive case
            Queue<TreeNode> queue1 = new LinkedList<>();
            Queue<TreeNode> queue2 = new LinkedList<>();
            //Because dealing with tree no need to worry about cycle
            queue1.add(root1);
            queue2.add(root2);
            while(!queue1.isEmpty() && !queue2.isEmpty()){
                TreeNode node1 = queue1.poll();
                TreeNode node2 = queue2.poll();
                if(node1.val!=node2.val){
                    return false;
                }
                if(node1.left!=null){
                    queue1.add(node1.left);
                }
                else{//对null的case做了测试就没必要测非null的case了，对称的。
                    if(node2.right!=null){
                        return false;
                    }
                }
                if(node1.right!=null){
                    queue1.add(node1.right);
                }
                else{
                    if(node2.left!=null){
                        return false;
                    }
                }
                if(node2.right!=null){
                    queue2.add(node2.right);
                }
                else{
                    if(node1.left!=null){
                        return false;
                    }
                }
                if(node2.left!=null){
                    queue2.add(node2.left);
                }
                else{
                    if(node1.right!=null){
                        return false;
                    }
                }
            }
            return queue1.isEmpty() && queue2.isEmpty();
        }
    }
}
