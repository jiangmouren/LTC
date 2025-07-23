package com.mycompany.app.graph.dijkstra;

import java.util.*;

/**
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 * There are n cities connected by m flights. Each flight starts from city u and arrives at v with a price w.
 * Now given all the cities and flights, together with starting city src and the destination dst,
 * your task is to find the cheapest price from src to dst with up to k stops.
 * If there is no such route, output -1.
 *
 * Example 1:
 * Input:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 1
 * Output: 200
 * Explanation:
 * The graph looks like this:
 * The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.
 *
 * Example 2:
 * Input:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 0
 * Output: 500
 * Explanation:
 * The graph looks like this:
 *
 * The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.
 *
 * Constraints:
 * The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
 * The size of flights will be in range [0, n * (n - 1) / 2].
 * The format of each flight will be (src, dst, price).
 * The price of each flight will be in the range [1, 10000].
 * k is in the range of [0, n - 1].
 * There will not be any duplicated flights or self cycles.
 */

/**
 * 借着这个题把Dijkstra一类问题讲清楚
 * 首先是Dijkstra算法的适用条件：
 * 算法导论的原文是这么写的：Dijkstra's algorithm solves the single-source shortest-paths problem on a weighted,
 * directed graph G=(V,E) for the case in which all edges weights are nonnegative.
 * 1. 但凡是能solve directed graph问题，自然也能solve undirected graph问题
 * 2. 对于graph里面有没有cycle是没有要求的
 * 3. 唯一的要求就是所有edge的weight必须是非负数，其实还要额外加一个条件就是不能有non-positive weighted cycle
 *
 * 然后是Dijkstra的implementation:
 * 1. graph的表示，相较于其它很多graph的问题，除了需要存储所有的children之外，还要存储到每个child的cost
 * 所以graph的表示从：
 * List<List<Integer>>(如果node可以用int来identity)或者Map<String, List<String>>(如果node需要用string来identify)
 * 变化成：
 * List<List<int[]>> (int[]: id, cost) 或者 Map<String, List<Node>> (Node{id, cost})
 *
 * 2. 不需要visited来记录已经访问过的node，以为我们的relaxation条件，以及不能有non-positive weighted cycle，
 * 所以无限循环的loop必然倒是cost上升，所以就不会被选中去expand，也就是不会stuck在loop里面的原因
 *
 * 3. 需要一个int[] distances或者 Map<Integer> distances来记录每个node最新的relaxation的结果
 *
 * 4. PriorityQueue里面需要记录(cost, node_id)，通常需要加一个comparator
 */
//首先可以判断这是一道Dijkstra问题，然后这是一道变种题
//多了Path长度<=K+2这个限制条件
public class CheapestFlightsWithinKStops {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        List<List<int[]>> graph = buildGraph(n, flights);
        //cost, id, stops
        Queue<int[]> queue = new PriorityQueue<>((a, b)->a[0]-b[0]);
        int[] distance = new int[n];
        int[] curStops = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(curStops, Integer.MAX_VALUE);
        distance[src] = 0;
        curStops[src] = 0;
        queue.add(new int[]{distance[src], src, 0});
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            int cost = cur[0];
            int id = cur[1];
            int stops = cur[2];
            if(id==dst){
                return cost;
            }
            if(stops<=K){
                for(int[] child : graph.get(id)){
                    int city = child[0];
                    int edgeCost = child[1];
                    int totalCost = cost + edgeCost;
                    int newStops = stops + 1;
                    //Better choice
                    if(totalCost<distance[city]){
                        queue.add(new int[]{totalCost, city, newStops});
                        distance[city] = totalCost;
                        curStops[city] = newStops;
                    }
                    //better steps，这个新加的relaxation条件是限定步数之后做的相应的算法改动
                    else if(newStops<curStops[city]){
                        queue.add(new int[]{totalCost, city, newStops});
                    }
                }
            }
        }
        return -1;
    }
    private List<List<int[]>> buildGraph(int n, int[][] flights){
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] flight : flights){
            int src = flight[0];
            int dst = flight[1];
            int cost = flight[2];
            graph.get(src).add(new int[]{dst, cost});
        }
        return graph;
    }
}
