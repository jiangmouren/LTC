package com.mycompany.app.tree;

/**
 * Find the first common ancestor or two nodes in a binary tree(not a binary search tree).
 */
public class FirstCommonAncestor {
    class TreeNode{
        TreeNode left;
        TreeNode right;
        int value;
        TreeNode(int value){
            this.value = value;
        }
    }
    class Result{
        public TreeNode node;
        public boolean isAncestor;
        public Result(TreeNode n, boolean isAnc){
            this.node = n;
            this.isAncestor = isAnc;
        }
    }

    TreeNode commonAncestor(TreeNode root, TreeNode p, TreeNode q){
        Result r = Helper(root, p, q);
        if(r.isAncestor){
            return r.node;
        }
        return null;
    }

    Result Helper(TreeNode root, TreeNode p, TreeNode q){
        //base case
        if(root == null){
            return new Result(null, false);
        }
        if(root == p && root == q){
            return new Result(root, true);
        }

        //Mostly a post-order with some in-order check for performance gain.
        Result rLeft = Helper(root.left, p, q);
        if(rLeft.isAncestor){
            return rLeft;
        }
        //This is the in-order check moved ahead from post-order check
        else if(rLeft.node != null && (root == p || root == q)){
            return new Result(root, true);
        }

        Result rRight = Helper(root.right, p, q);
        if(rRight.isAncestor){
            return rRight;
        }
        else if(rRight.node != null && (root == p || root == q)){
            return new Result(root, true);
        }

        if(rLeft.node != null && rRight.node != null){
            return new Result(root, true);
        }
        else{
            return new Result(rLeft != null ? rLeft.node : rRight.node, false);
        }
    }
}
