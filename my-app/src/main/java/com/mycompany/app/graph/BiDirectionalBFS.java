package com.mycompany.app.graph;

import java.util.*;

/**
 * Given n nodes, and connections among them, connections[i] = [a, b] represents a connection between nodes a and b.
 * Decide if there is a path between 2 given nodes. If so return the shortest path, if not return empty path.
 *
 * Example 1:
 * Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]], node1 = 0, node2 = 3
 * Output: [0, 1, 3]
 *
 * Example 2:
 * Input: n = 5, connections = [[0,1],[1,2],[2,0],[1,3]], node1 = 0, node2 = 4
 * Output: []
 */
public class BiDirectionalBFS {
    public static void main(String[] args){
        BiDirectionalBFS instance = new BiDirectionalBFS();
        int[][] connections = {{0,1},{1,2},{2,0},{1,3}};
        List<Integer> res1 = instance.findShortest(4, connections, 0, 3);
        for(int node : res1){
            System.out.println(node);
        }
    }
    public List<Integer> findShortest(int n, int[][] connections, int node1, int node2){
        List<Integer> res = new ArrayList<>();
        //Special case, handle initial comparison, all children compared in loop
        if(node1==node2){
            res.add(node1);
            return res;
        }
        List<List<Integer>> graph = buildGraph(n, connections);
        Queue<Integer> queue1 = new LinkedList<>();
        Queue<Integer> queue2 = new LinkedList<>();
        int[] visited1 = new int[n];
        int[] visited2 = new int[n];
        int[] parent1 = new int[n];
        int[] parent2 = new int[n];
        for(int i=0; i<n; i++){
            parent1[i] = -1;
            parent2[i] = -1;
        }
        queue1.add(node1);
        queue2.add(node2);
        visited1[node1] = 1;
        visited2[node2] = 1;
        while(!queue1.isEmpty() && !queue2.isEmpty()){
            int cur1 = queue1.poll();
            for(Integer node : graph.get(cur1)){
                if(visited2[node]==1){
                    parent1[node] = cur1;
                    populateRes(parent1, parent2, node, res);
                    return res;
                }
                else{
                    if(visited1[node]==0){
                        visited1[node] = 1;
                        parent1[node] = cur1;
                        queue1.add(node);
                    }
                }
            }
            int cur2 = queue2.poll();
            for(Integer node : graph.get(cur2)){
                if(visited1[node]==1){
                    parent2[node] = cur2;
                    populateRes(parent2, parent1, node, res);
                    return res;
                }
                else{
                    if(visited2[node]==0){
                        visited2[node] = 1;
                        parent2[node] = cur2;
                        queue2.add(node);
                    }
                }
            }
        }
        return res;
    }

    private List<List<Integer>> buildGraph(int n, int[][] connections){
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] edge : connections){
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    private void populateRes(int[] parent1, int[] parent2, int joint, List<Integer> res){
        res.add(joint);
        int ptr = joint;
        while(parent1[ptr]!=-1){
            res.add(parent1[ptr]);
            ptr = parent1[ptr];
        }
        Collections.reverse(res);
        ptr = joint;
        while(parent2[ptr]!=-1){
            res.add(parent2[ptr]);
            ptr = parent2[ptr];
        }
    }
}
