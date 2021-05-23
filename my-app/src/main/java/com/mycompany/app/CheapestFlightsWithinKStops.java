package com.mycompany.app;

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

//首先可以判断这是一道Dijkstra问题，然后这是一道变种题
//多了Path长度<=K+2这个限制条件，需要把原来的distance[] & visited[]都扩展成一个2-D数组
//本质上说，我需要keet track of在每个path长度上，每个node所能取得的最小值。
//以为，对某个Id来说，在长度要求内它能取得的最小值，不见得是可以帮助dst在长度要求内取得最小值
public class CheapestFlightsWithinKStops {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        //int[0]: id; int[1]: dist
        List<List<int[]>> graph = buildGraph(n, flights);
        int[][] distance = new int[n][K+2];//src+K+dst， 一共K+2
        for(int[] row : distance){
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        distance[src][0] = 0;
        boolean[][] visited = new boolean[n][K+2];

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b)->a.dist-b.dist);
        pq.add(new Node(src, 0, distance[src][0]));
        while(!pq.isEmpty()){
            Node cur = pq.poll();
            visited[cur.id][cur.pos] = true;
            if(cur.pos<=K){
                for(int[] nbor : graph.get(cur.id)){
                    if(!visited[nbor[0]][cur.pos+1]){
                        int distNew = cur.dist + nbor[1];
                        if(distNew<distance[nbor[0]][cur.pos+1]){
                            distance[nbor[0]][cur.pos+1] = distNew;
                            pq.add(new Node(nbor[0], cur.pos+1, distNew));
                        }
                    }
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for(int val : distance[dst]){
            res = Math.min(res, val);
        }
        return res==Integer.MAX_VALUE ? -1 : res;
    }

    private List<List<int[]>> buildGraph(int n, int[][] flights){
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<>());
        }

        for(int[] flight : flights){
            graph.get(flight[0]).add(new int[]{flight[1], flight[2]});
        }
        return graph;
    }

    class Node{
        int id;
        int pos;
        int dist;
        public Node(int id, int pos, int dist){
            this.id = id;
            this.pos = pos;
            this.dist = dist;
        }
    }
}
