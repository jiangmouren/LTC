package finished;
/**
 * Question:
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
 * Note:
 * You may assume k is always valid, 1 ? k ? BST's total elements.
 * Follow up:
 * What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently?
 * How would you optimize the kthSmallest routine?
 */

/**
 * Analysis:
 * Either a recursion or iteration in-order-traversal should solve the problem.
 * The only thing that is interesting is that we are counting backward instead of forward.
 * Because of this it's not increment as you proceed, it's instead increment as you return.
 *
 * To deal with the insert/delete, I need a priorityQueue.
 * Basically, I will keep a MaxHeap and maintain the size to be k.
 * Every time insert, check the size of the heap, if larger than k, compare the new to the max and decide replace or not.
 * Because we are always removing the max out, those in the heap will be the k smallest.
 */
import java.util.*;

public class KthSmallestElementInBST{
    public static class Node{
        Node left;
        Node right;
        int val;
        public Node(int x){
            this.val = x;
        }
    }

    public Node find1(Node root, int k){
        Stack<Node> stack = new Stack<>();
        push(root, stack);
        int cnt = 0;
        Node ptr = null;
        while(!stack.isEmpty() && cnt<k){
            ptr = stack.pop();
            if(ptr.right!=null){
                push(ptr.right, stack);
            }
            cnt++;
        }
        //because we assume k is always valid, so once exit the loop, it can only be cnt==k.
        return ptr;
    }

    private void push(Node root, Stack<Node> stack){
        Node ptr = root;
        while(ptr!=null){
            stack.push(ptr);
            ptr = ptr.left;
        }
    }

    public Node find2(Node root, int k){
        int[] cnt = {0};
        Node[] ptr = {null};
        helper(root, cnt, k, ptr);
        return ptr[0];
    }

    private void helper(Node root, int[] cnt, int k, Node[] ptr){
        if(root==null) return;

        helper(root.left, cnt, k, ptr);
        if(cnt[0]==k) return;
        else{
            cnt[0]++;
            if(cnt[0]==k){
                ptr[0] = root;
                return;
            }
            helper(root.right, cnt, k, ptr);
        }

    }

}
