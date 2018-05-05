package com.mycompany.app;

import com.mycompany.app.GraphValidTree;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/21/2017.
 */
public class GraphValidTreeTest {
    GraphValidTree objectUnderTest = new GraphValidTree();
    int[][] edgeList1 = {{0, 1}, {0, 2}, {1, 2}, {1, 3}};
    int[][] edgeList2 = {{0, 1}, {0, 2}, {1, 3}};
    @Test
    public void graphValidTree() throws Exception {
        assertFalse(objectUnderTest.graphValidTree(edgeList1, 4));
        assertTrue(objectUnderTest.graphValidTree(edgeList2, 4));

    }

}