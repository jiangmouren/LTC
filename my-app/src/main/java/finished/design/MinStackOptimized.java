package finished.design;

import java.util.*;

/**
 * Created by eljian on 9/13/2017.
 * This is the same question as the "MinStack" class, just a optimized solution.
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
public class MinStackOptimized {
    private Stack<Integer> valueStack;
    private Stack<Integer> minStack;

    public MinStackOptimized(){
        valueStack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int x){
        this.valueStack.push(x);
        if(minStack.isEmpty() || x<=minStack.peek()){
            minStack.push(x);
        }
    }

    //retrieve top value
    public int pop(){
        int result = valueStack.pop();
        if(result<=minStack.peek()){
            minStack.pop();
        }
        return result;
    }

    //return top_value
    public int top(){
        return valueStack.peek();
    }

    //return min_value
    public int getMin(){
        return minStack.peek();
    }
}
