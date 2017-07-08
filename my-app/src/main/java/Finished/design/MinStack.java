/**
Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
getMin() -- Retrieve the minimum element in the stack.
Example:
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> Returns -3.
minStack.pop();
minStack.top();      --> Returns 0.
minStack.getMin();   --> Returns -2.
 */

/**
 * Analysis:
 * 2 primary types of data structures come into picture: Stack or MinHeap.
 * Or use both.
 * Because we only have getMin(), not popMin() so Stack would be easier to modify.
 *
 * Use a stack and inside the stack store both the value and up to point min value.
 */

package Finished.design;
import java.util.*;

public class MinStack {
    private class Node {
        int val;
        int min;
        Node(int val, int min){
            this.val = val;
            this.min = min;
        }
    }
    Stack<Node> stack = new Stack<>();
    public void push(int x){
        int tmp;
        if(this.stack.empty())
            tmp = x;
        else{
            Node top = this.stack.peek();
            tmp = Math.min(top.min, x);
        }
        Node newNode = new Node(x, tmp);
        this.stack.push(newNode);
    }

    public int pop(){
        if(stack.empty()) throw new NullPointerException("stack is empty");
        return this.stack.pop().val;
    }

    public int top(){
        if(stack.empty()) throw new NullPointerException("stack is empty");
        return stack.peek().val;
    }

    public int getMin(){
        if(stack.empty()) throw new NullPointerException("stack is empty");
        return this.stack.peek().min;
    }

}
