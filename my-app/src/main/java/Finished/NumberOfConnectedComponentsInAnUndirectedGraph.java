package Finished;
/**
 * Question:
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
 * write a function to find the number of connected components in an undirected graph.
 * Example 1:
 *      0          3
 *      |          |
 *      1 --- 2    4
 * Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.
 * Example 2:
 *      0           4
 *      |           |
 *      1 --- 2 --- 3
 * Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.
 * Note:
 * You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
 */

/**
 * Typical Graph problem. 
 * Deal with Cyclic cases.
 * 1. Construct a graph;
 * 2. DFS the graph and count.
 */ 
import java.util.*;
public class NumberOfConnectedComponentsInAnUndirectedGraph{
    public int solution(int n, int[][] edges){
        //build graph, Need to initialize the graph.
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        //Count subgraphs
        boolean[] color = new boolean[n];
        int count = 0;
        for(int i=0; i<n; i++){
            if(!color[i]){
                count++;
                dFS(i, graph, color);
            }
        }
        return count;
    }

    //We use List<List<Integer>> as graph not List<Set<Integer>> because we do not need to do remove operations
    //The reason we do not need to do remove operations, is because we don't care about loop,
    //so we just bypass all visited nodes, not just neighbors.
    private void dFS(int n, List<List<Integer>> graph, boolean[] color){
        //root visited already, this check is redundant in this application, as we check !color[i].
        //But good to have to make better modular design.
        if(color[n]) return;

        else{
            color[n] = true;
            //do DFS here
            for(int i : graph.get(n)){
                dFS(i, graph, color);
            }
        }
    }

}
