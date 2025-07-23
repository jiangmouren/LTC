package com.mycompany.app.tree; /**
 * https://leetcode.com/problems/construct-binary-tree-from-string/
 * You need to construct a binary tree from a string consisting of parenthesis and integers.
 * The whole input represents a binary tree. It contains an integer followed by zero, one or two pairs of parenthesis. The integer represents the root's value and a pair of parenthesis contains a child binary tree with the same structure.
 * You always start to construct the left child node of the parent first if it exists.
 *
 * Example:
 * Input: "4(2(3)(1))(6(5))"
 * Output: return the tree root node representing the following tree:
 *
 *        4
 *      /   \
 *     2     6
 *    / \   /
 *   3   1 5
 * Note:
 * There will only be '(', ')', '-' and '0' ~ '9' in the input string.
 * An empty tree is represented by "" instead of "()".
 *
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

public class ConstructBinaryTreeFromString{
    public TreeNode str2tree(String s) {
        return helper(s, 0, s.length()-1);
    }

    private TreeNode helper(String s, int start, int end){
        //termination
        if(start>end){
            return null;
        }

        Struct struct = split(s, start, end);
        //System.out.println("left: "+struct.left[0]+", "+struct.left[1]);
        //System.out.println("right: "+struct.right[0]+", "+struct.right[1]);
        TreeNode root = new TreeNode(struct.val);
        root.left = helper(s, struct.left[0], struct.left[1]);
        root.right = helper(s, struct.right[0], struct.right[1]);
        return root;
    }

    private Struct split(String s, int start, int end){
        int sign = 1;
        int ptr = start;
        if(s.charAt(ptr)=='-'){
            sign = -1;
            ptr++;
        }
        int val = 0;
        while(ptr<=end && Character.isDigit(s.charAt(ptr))){
            val = val * 10 + s.charAt(ptr) - '0';
            ptr++;
        }
        val *= sign;

        int[] left = {1, 0};
        int[] right = {1, 0};
        if(ptr<end){
            //这里是一个容易出错的点，就是ptrArr传进去之后，需要在外面重新在set ptr的值
            int[] ptrArr = new int[]{ptr};
            getSub(s, left, ptrArr, end);
            ptr = ptrArr[0];
        }
        if(ptr<end){
            int[] ptrArr = new int[]{ptr};
            getSub(s, right, ptrArr, end);
        }
        return new Struct(val, left, right);
    }

    class Struct{
        int val;
        int[] left;
        int[] right;
        public Struct(int val, int[] left, int[] right){
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private void getSub(String s, int[] res, int[] ptr, int end){
        int cnt = 1;
        res[0] = ++ptr[0];
        while(ptr[0]<=end && cnt>0){
            if(s.charAt(ptr[0])=='('){
                cnt++;
            }
            if(s.charAt(ptr[0])==')'){
                cnt--;
            }
            ptr[0]++;
        }
        res[1] = ptr[0] - 2;
    }

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
}
