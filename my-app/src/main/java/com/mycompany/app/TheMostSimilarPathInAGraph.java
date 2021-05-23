package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/the-most-similar-path-in-a-graph/
 * We have n cities and m bi-directional roads where roads[i] = [ai, bi] connects city ai with city bi.
 * Each city has a name consisting of exactly 3 upper-case English letters given in the string array names.
 * Starting at any city x, you can reach any city y where y != x
 * (i.e. the cities and the roads are forming an undirected connected graph).
 * You will be given a string array targetPath.
 * You should find a path in the graph of the same length and with the minimum edit distance to targetPath.
 * You need to return the order of the nodes in the path with the minimum edit distance,
 * The path should be of the same length of targetPath and should be valid
 * (i.e. there should be a direct road between ans[i] and ans[i + 1]).
 * If there are multiple answers return any one of them.
 * The edit distance is defined as follows:
 *
 * Follow-up: If each node can be visited only once in the path, What should you change in your solution?
 *
 * Example 1:
 * Input: n = 5, roads = [[0,2],[0,3],[1,2],[1,3],[1,4],[2,4]], names = ["ATL","PEK","LAX","DXB","HND"], targetPath = ["ATL","DXB","HND","LAX"]
 * Output: [0,2,4,2]
 * Explanation: [0,2,4,2], [0,3,0,2] and [0,3,1,2] are accepted answers.
 * [0,2,4,2] is equivalent to ["ATL","LAX","HND","LAX"] which has edit distance = 1 with targetPath.
 * [0,3,0,2] is equivalent to ["ATL","DXB","ATL","LAX"] which has edit distance = 1 with targetPath.
 * [0,3,1,2] is equivalent to ["ATL","DXB","PEK","LAX"] which has edit distance = 1 with targetPath.
 *
 * Example 2:
 * Input: n = 4, roads = [[1,0],[2,0],[3,0],[2,1],[3,1],[3,2]], names = ["ATL","PEK","LAX","DXB"], targetPath = ["ABC","DEF","GHI","JKL","MNO","PQR","STU","VWX"]
 * Output: [0,1,0,1,0,1,0,1]
 * Explanation: Any path in this graph has edit distance = 8 with targetPath.
 *
 * Example 3:
 * Input: n = 6, roads = [[0,1],[1,2],[2,3],[3,4],[4,5]], names = ["ATL","PEK","LAX","ATL","DXB","HND"], targetPath = ["ATL","DXB","HND","DXB","ATL","LAX","PEK"]
 * Output: [3,4,5,4,3,2,1]
 * Explanation: [3,4,5,4,3,2,1] is the only path with edit distance = 0 with targetPath.
 * It's equivalent to ["ATL","DXB","HND","DXB","ATL","LAX","PEK"]
 *
 * Constraints:
 * 2 <= n <= 100
 * m == roads.length
 * n - 1 <= m <= (n * (n - 1) / 2)
 * 0 <= ai, bi <= n - 1
 * ai != bi
 * The graph is guaranteed to be connected and each pair of nodes may have at most one direct road.
 * names.length == n
 * names[i].length == 3
 * names[i] consists of upper-case English letters.
 * There can be two cities with the same name.
 * 1 <= targetPath.length <= 100
 * targetPath[i].length == 3
 * targetPath[i] consists of upper-case English letters.
 */

/**
 * Analysis:
 *                 0.     1.   2.   3.   4
 *    names        [ATL, PEK, LAX, DXB, HND]
 *
 *                 0.      1.    2.    3
 *    targetPath   ["ATL","DXB","HND","LAX"]
 *
 *     DO THIS STARTING AT EVERY NODE
 *     ATL at idx = 0 is it different? no, so add to cost + 0 = 0
 *         3
 *         DXB at idx = 1 is it different? no, so add to cost + 0 = 0
 *             ATL at idx = 2 is it different? yes, so add to cost + 1 = 1
 *                 DXB at idx = 3 is it different? yes, so add to cost + 1 AND RETURN = 2
 *                 LAX at idx = 3 is it different? no, so add to cost + 0 AND RETURN = 1
 *             PEK at idx = 2 is it different? yes, so add to cost + 1 = 1
 *                 DXB at idx = 3 SOLVED ALREADY RETURN PREV   RETURN
 *                 LAX at idx = 3 SOLVED ALREADY RETURN PREV
 *                 HND at idx = 3 is it different? yes, so add to cost + 1 AND RETURN
 *         LAX at idx = 1 is it different? yes, so add to cost + 1
 *             ATL at idx = 2, SOLVED ALREADY RETURN PREV
 *             PEK at idx = 2, SOLVED ALREADY RETURN PREV
 *             HND at idx = 2 is it different? no, so addto cost + 0
 *                 PEK at idx = 3 is it diffferent? yes, so add to cost +
 *                 LAX at idx = 3 SOLVED ALREADY RETURN PREV
 */
public class TheMostSimilarPathInAGraph {
    //这个题的基本思路，就是从每个点出发做backtracking/dfs，然后看那条路径是cost最小的
    //这个题目下面这样写成dfs是比较麻烦的，因为要加memeory的原因，写成没有memory的backtracking会简单很多，但是复杂度会差
    //这个题还有一点要注意的是，可以走回头路!!!但是因为有pos的控制，所以不用担心无限循环的情况。
    public List<Integer> mostSimilar(int n, int[][] roads, String[] names, String[] targetPath) {
        List<List<Integer>> graph = buildGraph(n, roads);
        //visited & bestChoice都是looking forward的，跟来时的path没有关系，所以里面存的值，都是globally valid
        int[][] visited = new int[n][targetPath.length];
        for(int[] row : visited){
            Arrays.fill(row, -1);
        }

        int[][] bestChoice = new int[n][targetPath.length];
        //from each node, get min cost
        int min = Integer.MAX_VALUE;
        int start = 0;
        for(int i=0; i<n; i++){
            int cur = dfs(i, 0, graph, names, targetPath, visited, bestChoice);
            if(cur<min){
                start = i;
                min = cur;
            }
        }

        //construct the path, based on bestChoice matrix
        List<Integer> res = new ArrayList<>();
        while(res.size()<targetPath.length){
            res.add(start);
            start = bestChoice[start][res.size()-1];
        }
        return res;
    }

    private List<List<Integer>> buildGraph(int n, int[][] roads){
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] road : roads){
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }
        return graph;
    }

    private int dfs(int id, int pos, List<List<Integer>> graph, String[] names, String[] targetPath, int[][] visited, int[][] bestChoice){
        //termination
        // IF WE VISITED IT ALREADY, RETURN THE PREVIOUS COUNT
        if(visited[id][pos]!=-1){
            return visited[id][pos];
        }
        int cost = names[id].equals(targetPath[pos]) ? 0 : 1;
        // IF WE FILLED UP THE PATH, WE'RE DONE
        if(pos==targetPath.length-1){
            return cost;
        }

        // FROM EACH NEIGHBOR, CALCULATE MIN COST AND SAVE THE CITY THAT GAVE THE MIN COST
        int min = Integer.MAX_VALUE;
        for(int nbor : graph.get(id)){
            int nborCost = dfs(nbor, pos+1, graph, names, targetPath, visited, bestChoice);
            if(nborCost<min){
                min = nborCost;
                bestChoice[id][pos] = nbor;
            }
        }

        cost += min;
        visited[id][pos] = cost;
        return cost;
    }
}
