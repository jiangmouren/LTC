package finished;

/**
 * Analysis:
 * We can construct binary trees from either:
 * "post-order+in-order" or "pre-order+in-order", you cannot do it with "pre + post"
 * The reason we can do the first two is because either pre/post will give us the "root" or the current problem
 * Then using "root" and the in-order, we can then split the in-order into left-subprobelm and right-subproblem.
 * We can then solve this one recursively. But with pre+post, you remove root, you have same thing from both sides
 * left behind, not usable.
 * We need two pointers to track subprolems in in-order array and one pointer to track current root in post-order.
 *
 */

public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return helper(inorder, postorder, postorder.length-1, 0, inorder.length-1);
    }

    private TreeNode helper(int[] inorder, int[] postorder, int postEnd, int inStart, int inEnd){
        //edge case
        if(inStart>inEnd){
            return null;
        }
        //recursive case
        TreeNode root = new TreeNode(postorder[postEnd]);
        //find position in inorder
        int rootIdx=inStart;
        for(int i=inStart; i<inEnd; i++){
            if(inorder[i]==postorder[postEnd]){
               rootIdx = i;
            }
        }
        //because we have postorder, so do right subtree first
        root.right = helper(inorder, postorder, postEnd-1, rootIdx+1, inEnd);
        //inEnd-rootIdx is the number of elements in right subtree
        root.left = helper(inorder, postorder, postEnd-1-inEnd+rootIdx, inStart, rootIdx-1);
        return root;
    }
}
