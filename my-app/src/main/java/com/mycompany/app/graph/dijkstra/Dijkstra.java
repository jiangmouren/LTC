package com.mycompany.app.graph.dijkstra;

import java.util.*;

/**
 * Question:
 * Given a graph, represented as List<List<int[]>>, instead of List<List<Integer>>
 * here we use int[], with the first entry represent the node_id, the second entry represent the cost
 * to get to that node from current node. Find the shortest path from "start" to "dest".
 * All the costs are non-negative.
 */
public class Dijkstra {
    //TODO: test this.
    public List<Integer> shortestPath(int start, int dest, List<List<int[]>> graph){
        //use int[] for Dijkstra node, arr[0]: node_id; arr[1]: distance from Start; arr[2]: parent for shortest path
        Map<Integer, int[]> map = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(11, (a, b)->a[1]-b[1]);
        pq.add(new int[]{start, 0, start});
        boolean found = false;
        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            if(!map.containsKey(cur[0])){
                map.put(cur[0], cur);
            }
            //terminate once distance to dest finalized
            if(cur[0]==dest){
                found = true;
                break;
            }
            for(int[] nbor : graph.get(cur[0])){
                pq.add(new int[]{nbor[0], cur[1]+nbor[1], cur[0]});
            }
        }
        List<Integer> res = new ArrayList<>();
        if(found){
            int ptr = dest;
            while(ptr!=start){
                res.add(ptr);
                ptr = map.get(ptr)[2];
            }
            res.add(start);
            Collections.reverse(res);
        }
        return res;
    }
}
