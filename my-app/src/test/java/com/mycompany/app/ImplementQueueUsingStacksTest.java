package com.mycompany.app;

import com.mycompany.app.stack.ImplementQueueUsingStacks;
import org.junit.Test;

public class ImplementQueueUsingStacksTest {
    ImplementQueueUsingStacks queue = new ImplementQueueUsingStacks();
    @Test
    public void push() throws Exception {
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.push(4);
        queue.push(5);
        System.out.println(queue.peek());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
    }
}