package com.mycompany.app.graph.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Question:
 * Given a graph, represented as List<List<int[]>>, instead of List<List<Integer>>
 * here we use int[], with the first entry represent the node_id, the second entry represent the cost
 * to get to that node from current node. Find the shortest path from "start" to "dest".
 * All the costs are non-negative.
 */
public class ShortestPath {
    public List<Integer> shortestPath(int start, int dest, List<List<int[]>> graph){
        //use int[] for Dijkstra node, arr[0]: node_id; arr[1]: distance from Start; arr[2]: parent for shortest path
        Map<Integer, int[]> map = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(11, (a, b)->a[1]-b[1]);
        pq.add(new int[]{start, 0, start});
        boolean found = false;
        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            // Since we only push unvisited nodes, cur[0] is guaranteed to be unvisited
            map.put(cur[0], cur);
            
            //terminate once distance to dest finalized
            if(cur[0]==dest){
                found = true;
                break;
            }
            for(int[] nbor : graph.get(cur[0])){
                // Only add unvisited neighbors to prevent infinite loops
                if(!map.containsKey(nbor[0])){
                    pq.add(new int[]{nbor[0], cur[1]+nbor[1], cur[0]});
                }
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
    
    // Test method
    public static void main(String[] args) {
        ShortestPath sp = new ShortestPath();
        
        // Test 1: Simple graph without cycles
        System.out.println("=== Test 1: Simple Graph ===");
        List<List<int[]>> graph1 = new ArrayList<>();
        graph1.add(Arrays.asList(new int[]{1, 4}, new int[]{2, 1})); // Node 0: ->1(cost 4), ->2(cost 1)
        graph1.add(Arrays.asList(new int[]{3, 1}));                 // Node 1: ->3(cost 1)
        graph1.add(Arrays.asList(new int[]{1, 2}));                 // Node 2: ->1(cost 2)
        graph1.add(new ArrayList<>());                               // Node 3: no outgoing edges
        
        List<Integer> path1 = sp.shortestPath(0, 3, graph1);
        System.out.println("Shortest path from 0 to 3: " + path1);
        System.out.println("Expected: [0, 2, 1, 3] (cost: 4)");
        
        // Test 2: Graph with cycles
        System.out.println("\n=== Test 2: Graph with Cycles ===");
        List<List<int[]>> graph2 = new ArrayList<>();
        graph2.add(Arrays.asList(new int[]{1, 1}));                 // Node 0: ->1(cost 1)
        graph2.add(Arrays.asList(new int[]{2, 1}));                 // Node 1: ->2(cost 1)
        graph2.add(Arrays.asList(new int[]{1, 1}, new int[]{3, 1})); // Node 2: ->1(cost 1), ->3(cost 1)
        graph2.add(new ArrayList<>());                               // Node 3: no outgoing edges
        
        List<Integer> path2 = sp.shortestPath(0, 3, graph2);
        System.out.println("Shortest path from 0 to 3: " + path2);
        System.out.println("Expected: [0, 1, 2, 3] (cost: 3)");
        
        // Test 3: No path exists
        System.out.println("\n=== Test 3: No Path ===");
        List<List<int[]>> graph3 = new ArrayList<>();
        graph3.add(Arrays.asList(new int[]{1, 1}));                 // Node 0: ->1(cost 1)
        graph3.add(new ArrayList<>());                              // Node 1: no outgoing edges
        graph3.add(new ArrayList<>());                              // Node 2: no outgoing edges
        
        List<Integer> path3 = sp.shortestPath(0, 2, graph3);
        System.out.println("Path from 0 to 2 (no path): " + path3);
        System.out.println("Expected: [] (empty list)");
        
        // Test 4: Self-loop
        System.out.println("\n=== Test 4: Self-loop ===");
        List<List<int[]>> graph4 = new ArrayList<>();
        graph4.add(Arrays.asList(new int[]{0, 2}, new int[]{1, 1})); // Node 0: ->0(cost 2), ->1(cost 1)
        graph4.add(Arrays.asList(new int[]{2, 1}));                   // Node 1: ->2(cost 1)
        graph4.add(new ArrayList<>());                                // Node 2: no outgoing edges
        
        List<Integer> path4 = sp.shortestPath(0, 2, graph4);
        System.out.println("Shortest path from 0 to 2: " + path4);
        System.out.println("Expected: [0, 1, 2] (cost: 2)");
        
        // Test 5: Single node
        System.out.println("\n=== Test 5: Single Node ===");
        List<List<int[]>> graph5 = new ArrayList<>();
        graph5.add(new ArrayList<>()); // Node 0: no outgoing edges
        
        List<Integer> path5 = sp.shortestPath(0, 0, graph5);
        System.out.println("Path from 0 to 0: " + path5);
        System.out.println("Expected: [0]");
        
        // Test 6: Complex cycle
        System.out.println("\n=== Test 6: Complex Cycle ===");
        List<List<int[]>> graph6 = new ArrayList<>();
        graph6.add(Arrays.asList(new int[]{1, 1}));                 // Node 0: ->1(cost 1)
        graph6.add(Arrays.asList(new int[]{2, 1}));                 // Node 1: ->2(cost 1)
        graph6.add(Arrays.asList(new int[]{3, 1}));                // Node 2: ->3(cost 1)
        graph6.add(Arrays.asList(new int[]{1, 1}, new int[]{4, 1})); // Node 3: ->1(cost 1), ->4(cost 1)
        graph6.add(new ArrayList<>());                               // Node 4: no outgoing edges
        
        List<Integer> path6 = sp.shortestPath(0, 4, graph6);
        System.out.println("Shortest path from 0 to 4: " + path6);
        System.out.println("Expected: [0, 1, 2, 3, 4] (cost: 4)");
        
        // Test 7: Pure cycle without destination (would cause infinite loop in old version)
        System.out.println("\n=== Test 7: Pure Cycle (No Destination) ===");
        List<List<int[]>> graph7 = new ArrayList<>();
        graph7.add(Arrays.asList(new int[]{1, 1}));                 // Node 0: ->1(cost 1)
        graph7.add(Arrays.asList(new int[]{2, 1}));                 // Node 1: ->2(cost 1)
        graph7.add(Arrays.asList(new int[]{0, 1}));                // Node 2: ->0(cost 1) - pure cycle
        graph7.add(new ArrayList<>());                               // Node 3: isolated
        
        List<Integer> path7 = sp.shortestPath(0, 3, graph7);
        System.out.println("Path from 0 to 3 (no path): " + path7);
        System.out.println("Expected: [] (no path)");
        
        // Test 8: Cycle with unreachable destination
        System.out.println("\n=== Test 8: Cycle with Unreachable Destination ===");
        List<List<int[]>> graph8 = new ArrayList<>();
        graph8.add(Arrays.asList(new int[]{1, 1}));                 // Node 0: ->1(cost 1)
        graph8.add(Arrays.asList(new int[]{0, 1}));                  // Node 1: ->0(cost 1) - cycle
        graph8.add(Arrays.asList(new int[]{3, 1}));                 // Node 2: ->3(cost 1) - separate component
        graph8.add(new ArrayList<>());                               // Node 3: no outgoing edges
        
        List<Integer> path8 = sp.shortestPath(0, 2, graph8);
        System.out.println("Path from 0 to 2 (unreachable): " + path8);
        System.out.println("Expected: [] (no path)");
    }
}