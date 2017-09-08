/**
 * Question:
 * Implement an iterator over a binary search tree (BST).
 * Your iterator will be initialized with the root node of a BST.
 * Calling next() will return the next smallest number in the BST.
 * Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
 */

/**
 * Analysis:
 * Essentially what we need is a "Iterative In-Order Traversal".
 * Cannot do it recursively, because we need that stack in our control.
 */

package finished.graph.tree;

import java.util.Stack;

public class BinarySearchTreeIterator {
    public static class Node{
        Node left;
        Node right;
        int val;
        Node(int x){
            this.val = x;
        }
    }
    private Stack<Node> stack = new Stack<>();

    //Initializer
    BinarySearchTreeIterator(Node root){
        pushStack(root, this.stack);
    }

    public boolean hasNext(){
        return stack.size()!=0;
    }

    public Node next(){
        if(stack.isEmpty()) return null;
        else{
            Node result = stack.pop();
            pushStack(result.right, this.stack);
            return result;
        }
    }
    private void pushStack(Node root, Stack<Node> stack){
        Node ptr = root;
        while(ptr!=null){
            stack.push(ptr);
            ptr = ptr.left;
        }
        return;
    }

}
