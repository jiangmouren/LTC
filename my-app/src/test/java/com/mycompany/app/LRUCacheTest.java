package com.mycompany.app;

import com.mycompany.app.LRUCache;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 9/21/2017.
 */
public class LRUCacheTest {
    LRUCache obj = new LRUCache(2);
    @Test
    public void test() throws Exception {
        obj.put(1, 1);
        obj.put(2, 2);
        assertEquals(obj.get(1), 1);
        //System.out.print(obj.get(1));
        obj.put(3, 3);
        assertEquals(obj.get(2), -1);
        obj.put(4, 4);
        assertEquals(obj.get(1), -1);
        assertEquals(obj.get(3), 3);
        assertEquals(obj.get(2), -1);
    }
}