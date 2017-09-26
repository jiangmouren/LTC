package finished.graph;

import java.util.*;

/**
 * Analysis:
 * There 2 primary differences in practice between directed and undirected graph.
 * 1. When you are doing a traversal(DFS/BFs), instead of unvisited/visited, you will have one more state as
 * on current path. Basically, not all connected vertices will be visited because of the directions. This is
 * important in finding cycles.
 * 2. In undirected graph case, between two vertices v1 and v2, there can be only 1 edge, so when you are traversing
 * from v1 to v2, doesn't matter DFS/BFS, you will need to remove v1 from v2's adjacency list. You don't want to
 * traverse one edge twice. But in directed graph case, v1-->v2 is one edge while v2-->v2 is another edge.
 * You do not need to remove v1 from v2's adjacency list, moving from v1 to v2. Because v1 might not even in
 * v2's adjacency list. Because of this, the adjacency matrix of a directed graph is not necessarily to be symmetric.
 *
 * What does it mean for shortest distance in case of a directed graph?
 * In the undirected graph, that is a 2-way distance, but in directed graph, it is a 1-way distance.
 * Meaning the path from v1-->v2, and the one from v2-->v1 are different.
 * So in this case, you cannot do a 2-way BFS. And if there do exit both paths from v1-->v2 and v2-->v1,
 * that means there is a cycle containing v1 and v2.
 * A derived question from the above analysis could be:
 * In a given directed graph, what is the smallest cycle that contains both v1 and v2?
 * What you do is do shortest path for both v1-->v2 and v2-->v1 and then combine them.
 *
 * Attention when doing DFS or BFS, unless told graph is a DAG, otherwise has to be careful for cycles.
 */
public class DirectedGraph {
    List<Node> graph;
    public DirectedGraph(List<Node> list){
        this.graph = list;
    }

    //In case need to check cycles, add a extra HashSet argument
    public DFS dFS(Node root, Set<Node> visitedSet){
        visitedSet.add(root);
        /**
         * Logic for current node
         */
        for(Node nbor: root.neighbors){
            if(!visitedSet.contains(nbor)){
                dFS(nbor, visitedSet);
            }
        }
        /**
         * Logic after all neighbors returned.
         */

        //In case not returned above, return here
        return new DFS();
    }

    public DFS dFs(){
        Set<Node> vistedSet = new HashSet<>();
        for(Node node : graph){
            if(!vistedSet.contains(node)){
                dFS(node, vistedSet);
            }
        }
        //if not returned above
        return new DFS();
    }

    public BFS bFS(Node root, Set<Node> visitedSet){
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node current = queue.remove();
            if(!visitedSet.contains(current)){
                /**
                 * Logic for the current node
                 */
                visitedSet.add(current);
                queue.addAll(current.neighbors);
            }
        }
        //return case, if not returned above
        return new BFS();
    }

    public BFS bFS(){
        Set<Node> visitedSet = new HashSet<>();
        for(Node node : graph){
            if(!visitedSet.contains(node)){
                bFS(node, visitedSet);
            }
        }

        //return case, if not returned in above
        return new BFS();
    }

    public int shortD(Node node1, Node node2){
        Queue<SNode> queue = new LinkedList<>();
        Set<Node> visitedSet = new HashSet<>();
        queue.add(new SNode(node1, 0));
        while(!queue.isEmpty()){
            SNode tmp = queue.remove();
            Node current = tmp.node;
            int distance = tmp.distance;
            if(current==node2){
                return distance;
            }
            for(Node nbor : current.neighbors){
                if(!visitedSet.contains(nbor)){
                    SNode nxt = new SNode(nbor, distance+1);
                    queue.add(nxt);
                }
            }
        }
        //if cannot find, return -1;
        return -1;
    }

    private class SNode{
        Node node;
        int distance;
        public SNode(Node node, int distance){
            this.node = node;
            this.distance = distance;
        }
    }
    public static class Node{
        int label;
        List<Node> neighbors;
        public Node(int label){
            this.label = label;
        }
    }

    public static class DFS{

    }

    public static class BFS{

    }

}
