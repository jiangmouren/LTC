package com.mycompany.app;

/**
 * Implement the following operations of a queue using stacks.
 * push(x) -- Push element x to the back of queue.
 * pop() -- Removes the element from in front of queue.
 * peek() -- Get the front element.
 * empty() -- Return whether the queue is empty.
 * Notes:
 * You must use only standard operations of a stack -- which means only push to top, peek/pop from top, size,
 * and is empty operations are valid.
 * Depending on your language, stack may not be supported natively.
 * You may simulate a stack by using a list or deque (double-ended queue),
 * as long as you use only standard operations of a stack.
 * You may assume that all operations are valid
 * (for example, no pop or peek operations will be called on an empty queue).
 */

import java.util.*;

/**
 * The basic idea to implement a queue using stack is to use 2 stacks.
 * When you are pushing, nothing, just push to that stack.
 * When you are reading, you need to pop everything from 1 stack to the other.
 * So it essentially reversed the order, now you can read.
 *
 * Bad thing about this, if you are doing pushing and reading following each other continuously,
 * you will have to keep reversing those 2 stacks.
 *
 * To optimize this, what you can do is: to batch!!!
 * You keep stack1 as write buffer and stack2 as read stack, you do not pop from stack1 to stack2,
 * until stack2 is empty.
 */
public class ImplementQueueUsingStacks {
    Stack<Integer> stack1;//write stack
    Stack<Integer> stack2;//read stack

    public ImplementQueueUsingStacks(){
        this.stack1 = new Stack<>();
        this.stack2 = new Stack<>();
    }

    public void push(int val){
        stack1.push(val);
        return;
    }

    public int pop(){
        if(stack2.isEmpty()){
            batch();
        }
        return stack2.pop();
    }

    public int peek(){
        if(stack2.isEmpty()){
            batch();
        }
        return stack2.peek();
    }

    public boolean empty(){
        return stack1.isEmpty()&&stack2.isEmpty();
    }

    private void batch(){
        while(!this.stack1.isEmpty()){
            int tmp = this.stack1.pop();
            this.stack2.push(tmp);
        }
    }
}
















































