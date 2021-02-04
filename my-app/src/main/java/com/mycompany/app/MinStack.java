/**
 * https://leetcode.com/problems/min-stack/submissions/
 * Question:
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 * Example:
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> Returns -3.
 * minStack.pop();
 * minStack.top();      --> Returns 0.
 * minStack.getMin();   --> Returns -2.
 */

package com.mycompany.app;
import java.util.*;

public class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;

    /** initialize your data structure here. */
    public MinStack() {
        this.stack = new Stack<>();
        this.minStack = new Stack<>();
    }

    public void push(int x) {
        this.stack.push(x);
        if(this.minStack.isEmpty() || x<=this.minStack.peek()){
            this.minStack.push(x);
        }
    }

    public void pop() {
        if(this.stack.pop()<=this.minStack.peek()){
            this.minStack.pop();
        }
    }

    public int top() {
        return this.stack.peek();
    }

    public int getMin() {
        return this.minStack.peek();
    }
}
