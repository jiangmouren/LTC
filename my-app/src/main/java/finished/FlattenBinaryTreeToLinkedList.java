package finished;
/**
 * Given a binary tree, flatten it to a linked list in-place.
 * For example,
 * Given
 *
 *          1
 *         / \
 *        2   5
 *       / \   \
 *      3   4   6
 * The flattened tree should look like:
 *    1
 *     \
 *      2
 *       \
 *        3
 *         \
 *          4
 *           \
 *            5
 *             \
 *              6
 */

/**
 * Analysis:
 * It's important to understand it's intention for "in-place"
 * 1. You cannot traverse(pre-order) the tree and creating a list of new nodes
 * 2. You cannot traverse the tree into a list then convert the whole list
 *
 * Use dumHead for LinkedList construction.
 * Save children vertices during DFS/BFS if we are modifying the graph along the way.
 */
import java.util.*;
public class FlattenBinaryTreeToLinkedList{
    public static class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        public TreeNode(int x){
            this.val = x;
        }
    }
    public TreeNode flat1(TreeNode root){
        TreeNode dumHead = new TreeNode(0);
        TreeNode ptr = dumHead;
        Stack<TreeNode> stack = new Stack();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            ptr.right = tmp;
            ptr = ptr.right;
            if(tmp.right!=null) stack.push(tmp.right);
            if(tmp.left!=null) stack.push(tmp.left);
        }
        return root;
    }

    public TreeNode flat2(TreeNode root){
        TreeNode[] ptr = new TreeNode[1];
        ptr[0] = new TreeNode(0);//This ptr is the dumHead
        helper(root, ptr);
        return root;
    }

    private void helper(TreeNode root, TreeNode[] ptr){
        //edge case
        if(root==null) return;

        //normal cases
        ptr[0].right = root;
        ptr[0] = ptr[0].right;
        //Attention: It is very important that we save the current left and right children,
        // otherwise, at next level, root.right will changed, so when we come back at helper(root.right, ptr)
        // root.right are no longer pointing to the original right child.
        //!!!If we are modifying the graph along DFS/BFS, we need to save children vertices!!!
        TreeNode left = root.left;
        TreeNode right = root.right;
        helper(left, ptr);
        helper(right, ptr);
    }
}
