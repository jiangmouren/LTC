package Finished.graph;

/**
 * Question:
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
 * write a function to check whether these edges make up a valid tree.
 * For example:
 * Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
 * Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
 * Note: you can assume that no duplicate edges will appear in edges.
 * Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
 */

/**
 * Analysis:
 * 1. connected; 2. no cycle.
 * Because it is connected, so it does not matter which node you decide to start,
 * traverse the whole graph, preferably using DFS, which will find loops sooner,
 * check for loops along the path and check for connectivity by the end.
 *
 * This edge list is hard to use, need to pre-process it into a adjacency list format that is easier to use.
 * "List<List<Integer>> graph = new ArrayList<List<Integer>>()" is Okay, but
 * "List<List<Integer>> graph = new ArrayList<ArrayList<Integer>>()" is wrong.
 * What's inside "<>" is specific and cannot be sub-typed.
 * And also, because java cannot do Genetic Array, we have to use list of list instead of array of list
 */
import java.util.*;
public class GraphValidTree {
    public boolean graphValidTree(int[][] edgeList, int n){
        if(edgeList==null) throw new IllegalArgumentException("Inputs cannot be null");
        if(edgeList.length<2) return true;
        List<Set<Integer>> graph = constructGraph(edgeList, n);
        boolean[] color = new boolean[n];
        boolean noLoop = checkLoop(graph, 0, color);
        boolean connected = true;
        for(boolean visited : color){
            connected = connected && visited;
        }
        return noLoop&&connected;
    }

    //Better to use array of list since we already not the number of nodes
    //because we need to remove, so it is better use HashSet
    private List<Set<Integer>> constructGraph(int[][] edgeList, int n){
        List<Set<Integer>> graph = new ArrayList<Set<Integer>>(n);
        //initialize the graph list, it has to be initialized before used.
        for(int i=0; i<n; i++){
            graph.add(i, new HashSet<Integer>());
        }
        for(int[] edge : edgeList){
            //ArrayList is different from array,
            //even though you can specify the initial capacity, it still can be empty.
            //And you cannot just insert to any place and assuming that entry is already there like an array.
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    /**
     * Only use DFS to check for loops, cannot check for connectivity, because don't know the sub-graph size.
     * And check sub-graph connectivity is not necessary.
     * @param graph
     * @param node
     * @param color: 0-->clean; 1-->visited; For undirected graph, there is no grey state
     * @return
     */
    private boolean checkLoop(List<Set<Integer>> graph, int node, boolean[] color){
        //Termination Cases
        if(color[node]) return false;

        else {
            color[node] = true;
            for(int neighbor : graph.get(node)){
                //Because we have to do this remove operation, it's better to use "Set" instead of "List"
                //It will be O(n) operation to remove.
                graph.get(neighbor).remove(node);//Need to remove to avoid looking back
                if(!checkLoop(graph, neighbor, color)) return false;
                //graph.get(neighbor).add()//If we don't care about protecting the graph, no need to add it back.
            }
            return true;
        }

    }
}
