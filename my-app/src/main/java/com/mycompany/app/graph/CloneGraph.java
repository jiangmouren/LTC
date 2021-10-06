package com.mycompany.app.graph;
import java.util.*;

/**
 * https://leetcode.com/problems/clone-graph/
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


/**
 * 这个题目跟copy List with random pointers是一回事，基本思路就是：
 * 先把所有的新Node都建一遍，同步，把新旧Node的对应关系，保存在一个map里面，
 * 然后再把老graph过一遍，把每个node对应的新node的adjacency list建好
 * 再上面的思路基础上再改进一下，就是可以把两件事合并到一遍traversal完成
 */
public class CloneGraph {
    public Node cloneGraph(Node node) {
        if(node==null){
            return null;
        }

        Map<Node, Node> map = new HashMap<>();
        dfs(node, map);
        return map.get(node);
    }

    private void dfs(Node root, Map<Node, Node> map){
        if(!map.containsKey(root)){
            map.put(root, new Node(root.val));
        }
        Node copy = map.get(root);

        for(Node nbor : root.neighbors){
            if(!map.containsKey(nbor)){//avoid cycle
                dfs(nbor, map);
            }
            copy.neighbors.add(map.get(nbor));
        }
    }

    class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
