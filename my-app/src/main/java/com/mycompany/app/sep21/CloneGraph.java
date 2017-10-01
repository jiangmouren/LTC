package com.mycompany.app.sep21;
/**
 * Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.
 * OJ's undirected graph serialization:
 * Nodes are labeled uniquely.
 * We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
 * As an example, consider the serialized graph {0,1,2#1,2#2,2}.
 * The graph has a total of three nodes, and therefore contains three parts as separated by #.
 * First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
 * Second node is labeled as 1. Connect node 1 to node 2.
 * Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
 * Visually, the graph looks like the following:
 *
 *        1
 *       / \
 *      /   \
 *     0 --- 2
 *          / \
            \_/
 */

import java.util.*;

/**
 * Analysis:
 * Essentially this is a traversal issue.
 * The key to this problem is you need to traverse both graph simultaneously.
 * Either BFS/DFS will work.
 */
public class CloneGraph {
    //dFS
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        UndirectedGraphNode newRoot = new UndirectedGraphNode(node.label);
        dFS(node, newRoot);
        return newRoot;
    }

    //bFS
    public UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
        UndirectedGraphNode newRoot = new UndirectedGraphNode(node.label);
        bFS(node, newRoot);
        return newRoot;
    }

    private void bFS(UndirectedGraphNode oldRoot, UndirectedGraphNode newRoot){
        Queue<UndirectedGraphNode> queue1 = new LinkedList<>();
        Queue<UndirectedGraphNode> queue2 = new LinkedList<>();
        queue1.add(oldRoot);
        queue2.add(newRoot);
        while(!queue1.isEmpty()){
            UndirectedGraphNode tmp1 = queue1.remove();
            UndirectedGraphNode tmp2 = queue2.remove();
            for(UndirectedGraphNode tmp : tmp1.neighbors){
                UndirectedGraphNode tmp3 = new UndirectedGraphNode(tmp.label);
                tmp2.neighbors.add(tmp3);
                queue1.add(tmp);
                queue2.add(tmp3);
            }
        }
        return;
    }

    private void dFS(UndirectedGraphNode oldRoot, UndirectedGraphNode newRoot){
        //base case
        if(oldRoot==null){
            return;
        }
        for(UndirectedGraphNode tmp : oldRoot.neighbors){
            UndirectedGraphNode tmpNew = new UndirectedGraphNode(tmp.label);
            newRoot.neighbors.add(tmpNew);
            dFS(tmp, tmpNew);
        }
        return;
    }

    public static class UndirectedGraphNode {
        int label;
        List<UndirectedGraphNode> neighbors;
        UndirectedGraphNode(int x){
            label = x;
            neighbors = new ArrayList<UndirectedGraphNode>();
        }
    }

}
