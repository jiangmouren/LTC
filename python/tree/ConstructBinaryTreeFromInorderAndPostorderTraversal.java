package com.mycompany.app.tree;

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
        int[] pos = {postorder.length-1};
        return helper(inorder, postorder, 0, inorder.length-1, pos);
    }

    private TreeNode helper(int[] inorder, int[] postorder, int left, int right, int[] pos){
        int val = postorder[pos[0]];
        pos[0]--;
        TreeNode root = new TreeNode(val);
        int ptr = left;
        while(ptr<=right && inorder[ptr]!=val){
            ptr++;
        }
        if(ptr<right){
            root.right = helper(inorder, postorder, ptr+1, right, pos);
        }
        if(ptr>left){
            root.left = helper(inorder, postorder, left, ptr-1, pos);
        }
        return root;
    }
}
