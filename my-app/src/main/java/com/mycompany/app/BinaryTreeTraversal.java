/**
 * Question:
 * Given a binary tree, do all 3 types of traversal.
 * For example:
 * Given binary tree {1,#,2,3},
 *    1
 *     \
 *      2
 *     /
 *    3
 * return [1,2,3].
 * Note: Recursive solution is trivial, could you do it iteratively?
 */

/**
 * Analysis:
 * To do it iteratively, we need a stack.
 * The real problem is the flow control.
 * It is interesting to compare how we use stack in the in-order case vs this pre-order case.
 * Will put together 3 types of tree traversal together and do some comparison and analysis.
 * In the end, I only need to memorize the conclusion here. But it's good for the unerstanding to explain here.
 *
 */

package com.mycompany.app;
import java.util.*;
public class BinaryTreeTraversal {
    public static class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        public TreeNode(int x){
            this.val = x;
        }
    }

    /**
     * PreOrder Solution 1: Recursion
     */
    public List<TreeNode> preOrder1(TreeNode root){
        List<TreeNode> list = new ArrayList<>();
        preOrderHelper1(root,list);
        return list;
    }

    //This is essentially returning the whole path, so we need a void type helper function.
    private void preOrderHelper1(TreeNode root, List<TreeNode> list){
        //edge case
        if(root==null) return;

        //normal case
        list.add(root);
        preOrderHelper1(root.left, list);
        preOrderHelper1(root.right, list);
    }

    /**
     * PreOrder Solution 2: Iteration
     */
    public List<TreeNode> preOrder2(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        List<TreeNode> list = new ArrayList<>();
        preOrderHelper2(root, stack, list);
        while(!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            preOrderHelper2(tmp.right, stack, list);
        }
        return list;
    }

    private void preOrderHelper2(TreeNode ptr, Stack<TreeNode> stack, List<TreeNode> list){
        while(ptr!=null){
            list.add(ptr);
            stack.push(ptr);
            ptr = ptr.left;
        }
    }

    /**
     * PreOrder Solution 3: Iteration
     * This is essentially the same thing comparing to "Solution 2", if you look at the topology order.
     */
    public List<TreeNode> preOrder3(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        List<TreeNode> list = new ArrayList<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            list.add(tmp);
            if(tmp.right!=null){
                stack.push(tmp.right);
            }
            if(tmp.left!=null){
                stack.push(tmp.left);
            }
        }
        return list;
    }

    /**
     * InOrder Solution 1: Recursion
     * This is kind of like Backtracking. But unlike preOrder, which construct the path a long the path, path construction here is half backward and half forward, more generic.
     */ 
    public List<TreeNode> inOrder1(TreeNode root){
        List<TreeNode> list = new ArrayList<>();
        inOrderHelper1(root, list);
        return list;
    }

    private void inOrderHelper1(TreeNode root, List<TreeNode> list){
        //edge case
        if(root==null) return;

        //Normal case
        inOrderHelper1(root.left, list);
        list.add(root);
        inOrderHelper1(root.right, list);
    }

    /**
     * InOrder Solution 2: Iteration
     */ 
    public List<TreeNode> inOrder2(TreeNode root){
        List<TreeNode> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        inOrderHelper1(root, stack);
        while(!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            list.add(tmp);
            inOrderHelper1(tmp.right, stack);
        }
        return list;
    }

    private void inOrderHelper1(TreeNode ptr, Stack<TreeNode> stack){
        while(ptr!=null){
            stack.push(ptr);
            ptr = ptr.left;
        }
    }

    /**
     * PostOrder Solution 1: Recursion
     */ 
    public List<TreeNode> postOrder1(TreeNode root){
        List<TreeNode> list = new ArrayList<>();
        postOrderHelper1(root, list);
        return list;
    }

    private void postOrderHelper1(TreeNode root, List<TreeNode> list){
        //edge case
        if(root==null) return;

        //normal case
        postOrderHelper1(root.left, list);
        postOrderHelper1(root.right, list);
        list.add(root);
    }


    /**
     * PostOrder Solution 2: Iteration
     * PostOrder iterative solution is the most fun part of all the 3 traversals. 
     * I can prove that without an extened TreeNode, I cannot do a intuitive stack approach.
     * The typical way for post-order is to use a reversed a pre-order.
     * for pre-order, we have left-right-root, what we want for post-order is left-right-root. 
     * As you can see there is a rotation between root, and left-right. 
     * With the knowledge of the array rotation, what I can do is at every level of the traversal, make sure left-right is reversed first.
     * That is to say we will have right-left-root, as result of a pre-order like traversal. 
     * Then all we need is just reverse the result from the above, we will have root-left-right.
     * Doesn't matter which pre-order traversal you choose to use, mathmatically the above theory will hold true.
     * TODO: prove cannot to left-first stack approach without using ExtendedTreeNode.
     */ 
    public List<TreeNode> postOrder2(TreeNode root){
        List<TreeNode> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        postOrderHelper2(root, stack, list);
        while(!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            postOrderHelper2(tmp.left, stack, list);
        }
        Collections.reverse(list);
        return list;
    }

    private void postOrderHelper2(TreeNode ptr, Stack<TreeNode> stack, List<TreeNode> list){
        while(ptr!=null){
            list.add(ptr);
            stack.push(ptr);
            ptr = ptr.right;
        }
    }


    /**
     * PostOrder Solution 3: Iteration
     */ 
    public List<TreeNode> postOrder3(TreeNode root){
        List<TreeNode> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            list.add(tmp);
            if(tmp.left!=null) stack.push(tmp.left);
            if(tmp.right!=null) stack.push(tmp.right);
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * PostOrder Solution 4: Iteration
     * In this case, we will use wrapper class to do a more intuitive mimick of the recursion
     */ 
    public List<TreeNode> postOrder4(TreeNode root){
        List<TreeNode> list = new ArrayList<>();
        Stack<ExtendedNode> stack = new Stack<>();
        stack.push(new ExtendedNode(false, root));
        while(!stack.isEmpty()){
            ExtendedNode tmp = stack.peek();
            if(tmp.visited){
                list.add(tmp.node);
                stack.pop();
            }
            else{
                if(tmp.node.right!=null) stack.add(new ExtendedNode(false, root.right));
                if(tmp.node.left!=null) stack.add(new ExtendedNode(false, root.left));
                tmp.visited = true;
            }

        }
        return list;
    }

    private class ExtendedNode{
        boolean visited;
        TreeNode node;
        public ExtendedNode(boolean visted, TreeNode node){
            this.visited = visited;
            this.node = node;
        }
    }

}
