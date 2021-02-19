package com.mycompany.app;

/**
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/
 * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them.
 * A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.
 * The path sum of a path is the sum of the node's values in the path.
 * Given the root of a binary tree, return the maximum path sum of any path.
 *
 * Example 1:
 *      1
 *     / \
 *    2   3
 * Input: root = [1,2,3]
 * Output: 6
 * Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.
 *
 * Example 2:
 *     -10
 *     / \
 *    9  20
 *      /  \
 *     15   7
 * Input: root = [-10,9,20,null,null,15,7]
 * Output: 42
 * Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 3 * 104].
 * -1000 <= Node.val <= 1000
 */

/**
 * Tree中的maxPathSum要么出现在leftSubTree，要么在rightSubTree，要么就是经过root的一条path。
 * 只有这三种可能。
 * 所以对于left & right subTree来说，如果其能够返回其内部所能达到的maxPathSum，以及以其root为端点的maxPathSum
 * 那么在当下tree当中，就可以根据leftTree && rightTree返回的信息，结合当下root的信息，return相同的东西。
 * 显然这是一个postOrder.
 */
public class BinaryTreeMaximumPathSum{
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
    public int maxPathSum(TreeNode root) {
        int[] max = {Integer.MIN_VALUE};
        postOrder(root, max);
        return max[0];
    }

    //return以root为端点的maxPathSum
    private int postOrder(TreeNode root, int[] max){
        //termination case
        if(root==null){
            return 0;
        }

        int left = postOrder(root.left, max);
        int right = postOrder(root.right, max);
        int res = Math.max(left+root.val, right+root.val);
        res = Math.max(res, root.val);
        max[0] = Math.max(max[0], res);
        max[0] = Math.max(max[0], left+root.val+right);
        return res;
    }
}
