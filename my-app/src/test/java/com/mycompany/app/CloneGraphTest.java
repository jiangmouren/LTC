package com.mycompany.app;

import com.mycompany.app.graph.CloneGraph;
import org.junit.Test;

import java.util.*;

/**
 * Created by eljian on 9/30/2017.
 */
public class CloneGraphTest {
    CloneGraph obj = new CloneGraph();
    UndirectedGraphNode root = new UndirectedGraphNode(0);
    {
        root.neighbors.add(new UndirectedGraphNode(1));
        root.neighbors.add(new UndirectedGraphNode(2));
        root.neighbors.add(new UndirectedGraphNode(3));
        root.neighbors.add(new UndirectedGraphNode(4));
        UndirectedGraphNode tmp = new UndirectedGraphNode(5);
        tmp.neighbors.add(new UndirectedGraphNode(6));
        tmp.neighbors.add(new UndirectedGraphNode(7));
        root.neighbors.add(tmp);
    }
    @Test
    public void cloneGraph() throws Exception {
        UndirectedGraphNode res = obj.cloneGraph(root);
        bFSPrint(res);
    }

    @Test
    public void cloneGraph2() throws Exception {
        UndirectedGraphNode res = obj.cloneGraph2(root);
        bFSPrint(res);
    }

    private void bFSPrint(UndirectedGraphNode root){
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            UndirectedGraphNode tmp = queue.remove();
            System.out.print(tmp.label);
            queue.addAll(tmp.neighbors);
        }
        return;
    }

}