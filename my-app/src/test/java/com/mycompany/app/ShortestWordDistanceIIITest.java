package com.mycompany.app;

import com.mycompany.app.ShortestWordDistanceIII;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 8/31/2017.
 */
public class ShortestWordDistanceIIITest {
    String[] list = {"practice", "makes", "perfect", "coding", "makes"};
    ShortestWordDistanceIII obj = new ShortestWordDistanceIII();
    @Test
    public void find() throws Exception {
        assertEquals(1, obj.find(list,"makes", "coding"));
        assertEquals(3, obj.find(list,"makes", "makes"));
        assertEquals(3, obj.find(list,"coding", "practice"));
    }

}