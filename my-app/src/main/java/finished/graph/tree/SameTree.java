package finished.graph.tree;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given two binary trees, write a function to check if they are equal or not.
 * Two binary trees are considered equal if they are structurally identical and the nodes have the same value.
 */

/**
 * Analysis:
 * Recursive by nature. Two trees are equal if they have same root && same left subtree && same right subtree.
 */
public class SameTree {
    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){this.val = x;};
    }
    public boolean isSameTree(TreeNode p, TreeNode q) {
        //Termination Cases
        if(p==null && q==null) return true;
        if((p==null && q!=null) || (p!=null && q==null)) return false;

        //Recursive Cases
        return (p.val==q.val) && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
