package Finished.graph.tree;

/**
 * Question:
 * Given a binary tree, determine if it is height-balanced.
 * For this problem, a height-balanced binary tree
 * is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 */

/**
 * Analysis:
 * I think the tricky part of this problem is understanding the complexity of the recursion,
 * and how to optimize it.
 * Specifically for this problem, the recursion logic without any decoration, should be:
 * boolean Balanced(root){
 *     return Balanced(root.left) && Balanced(root.right) && Math.abs(Depth(root.left)-Depth(right))<2;
 * }
 *
 * Then the problem is how to do Depth()?
 * We need to derive parent tree Depth from subtree Depths so as to avoid subtree depths recalculation.
 *
 * This is more like a DP. I am doing conduction from leaf to root, so every node will only need to be
 * visited once. If the other way round, from root, to leaf, many nodes will need to be visited
 * multiple times. In that case, we need to store those nodes' value some where like a HashMap.
 */
public class BalancedBinaryTree {

    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int x){this.val = x;}
    }

    private class NodeStats {
        boolean balanced;
        int depth;
        NodeStats(boolean balanced, int depth){
            this.balanced = balanced;
            this.depth = depth;
        }
    }

    boolean balanced(TreeNode root){
        return helper(root).balanced;
    }

    private NodeStats helper(TreeNode root){
        //Termination Cases
        if(root==null){
            return new NodeStats(true, 0);
        }
        //Recursive cases
        boolean balanced = helper(root.left).balanced && helper(root.right).balanced
                && Math.abs(helper(root.left).depth - helper(root.right).depth)<2;
        int depth = Math.max(helper(root.left).depth, helper(root.right).depth)+1;
        return new NodeStats(balanced, depth);
    }
}

