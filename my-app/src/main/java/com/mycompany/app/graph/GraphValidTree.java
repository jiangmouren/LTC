package com.mycompany.app.graph;

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
    public boolean validTree(int n, int[][] edges) {
        //For a graph to be a tree, there are two conditions:
        //1. All connected; 2. No loop
        List<List<Integer>> graph = buildGraph(n, edges);
        boolean[] visited = new boolean[n];
        if(!dfs(graph, 0, -1, visited)){
            return false;
        }
        for(boolean visit : visited){
            if(!visit){
                return false;
            }
        }
        return true;
    }

    private List<List<Integer>> buildGraph(int n, int[][] edges){
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<Integer>());
        }
        for(int[] edge : edges){
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    //return true if no loop, otherwise return false.
    private boolean dfs(List<List<Integer>> graph, int cur, int parent, boolean[] visited){
        visited[cur] = true;
        for(int nbor : graph.get(cur)){
            if(nbor!=parent){
                if(visited[nbor]){
                    return false;
                }
                if(!dfs(graph, nbor, cur, visited)){
                    return false;
                }
            }
        }
        return true;
    }
}
