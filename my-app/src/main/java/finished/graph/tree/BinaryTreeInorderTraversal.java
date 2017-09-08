package finished.graph.tree;

/**
 * Question:
 * Given a binary tree, return the inorder traversal of its nodes' values.
 * For example:
 * Given binary tree [1,null,2,3],
 *    1
 *     \
 *      2
 *     /
 *    3
 * return [1,3,2].
 * Note: Recursive solution is trivial, could you do it iteratively?
 */
import java.util.*;
public class BinaryTreeInorderTraversal {
    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){this.val = x;}
    }

    public List<Integer> iterativeInorderTraversal(TreeNode root){
        List<Integer> result = new LinkedList<>();
        if(root==null) return result;
        Stack<TreeNode> stack = new Stack<>();
        //Initialize Stack
        pushStack(root, stack);

        while(!stack.empty()){
            TreeNode tmp = stack.pop();
            result.add(tmp.val);
            if(tmp.right!=null){
                pushStack(tmp.right, stack);
            }
        }
        return result;
    }

    private void pushStack(TreeNode root, Stack<TreeNode> stack){
        TreeNode ptr = root;
        while(ptr!=null){
            stack.push(ptr);
            ptr = ptr.left;
        }
    }

    public List<Integer> recursiveInorderTraversal(TreeNode root){
        //Termination Cases
        if(root==null) return new LinkedList<Integer>();

        List<Integer> leftList = recursiveInorderTraversal(root.left);
        leftList.add(root.val);
        List<Integer> rightList = recursiveInorderTraversal(root.right);
        leftList.addAll(rightList);
        return leftList;
    }

    public List<Integer> recursiveInorderTraversal2(TreeNode root){
        List<Integer> result = new LinkedList<>();
        recursionHelper(root, result);
        return result;
    }

    private void recursionHelper(TreeNode root, List<Integer> list){
        //Termination Cases
        if(root==null) return;

        recursionHelper(root.left, list);
        list.add(root.val);
        recursionHelper(root.right, list);
    }


    /**
     * Another way I have thought about is to use a dummy root,
     * just like the dummy head we use in LinkedList.
     * And the reason for that is to avoid the initial initialization loop,
     * but as we can reuse the initialization code, it will be cleaner to do the above.
     * Using a Dummy Root, we need to ignore the first element in the result list.
     */

}
