package com.mycompany.app.graph;

/**
 * https://leetcode.com/problems/critical-connections-in-a-network/
 * There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming a network
 * where connections[i] = [a, b] represents a connection between servers a and b.
 * Any server can reach any other server directly or indirectly through the network.
 * A critical connection is a connection that, if removed, will make some server unable to reach some other server.
 * Return all critical connections in the network in any order.
 *
 * Example 1:
 * Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
 * Output: [[1,3]]
 * Explanation: [[3,1]] is also accepted.
 *
 * Constraints:
 * 1 <= n <= 10^5
 * n-1 <= connections.length <= 10^5
 * connections[i][0] != connections[i][1]
 * There are no repeated connections.
 */

import java.util.*;

public class CriticalConnectionsInNetwork {
    /**
     * 这题是图论里面的"biconnected component"问题，具体说是找"bridge"的问题。
     * 在CLRS里面"Problem 22-2"就是这个，具体可以网上查询相关关键词，比如：
     * https://www.geeksforgeeks.org/biconnected-components/
     * https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
     * 具体实现上，一种就是我下面的这种写法，用rank，就是每个vertex到root在dfs路径中的距离
     * 还有一种就是用timestamp，或者discover time. 具体就是用一个global的时间，去标定每一个vertex在dfs中被发现的时刻，就是下面第2种解法。
     * 这两种解法的主要的区别在于sibling tree所标定的数值上，但是每一个tree内部的child, parent, ancestor的相对标定值上一样的。
     * 所以对于发现"back edge"这件事情来说是没有区别的。详见：src\main\resources\Timestamp-Rank.PNG
     */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> graph = buildGraph(n, connections);
        List<List<Integer>> res = new ArrayList<>();
        int[] low = new int[n];
        int[] rank = new int[n];
        for(int i=0; i<n; i++){
            rank[i] = -1;
        }
        rank[0] = 0;
        dfs(0, graph, low, rank, res, -1);//这里并不必须是-1， 0也不影响。
        return res;
    }

    //用rank[i]==-1表达没有被visit过
    //这里有一个问题就是既然已经有rank[i]用来表示visited，那么有为什么还需要pre避免parent?
    //参考：src\main\resources\WhyNeedPre.jpg
    //如果不阻止使用parent的rank作为low的开始，那么无法区分图中的两种情况，因为在1点的位置的Low都是0
    private void dfs(int root, List<List<Integer>> graph, int[] low, int[] rank, List<List<Integer>> res, int pre){
        int curRank = rank[root];
        low[root] = curRank;//当前的low都是从当前的rank算起，而不是从parent的rank算起
        for(int node : graph.get(root)){
            if(node!=pre && rank[node]==-1){
                rank[node] = curRank+1;
                dfs(node, graph, low, rank, res, root);
                low[root] = Math.min(low[root], low[node]);
                if(rank[root] < low[node]){
                    List<Integer> buf = new ArrayList<>();
                    buf.add(node);
                    buf.add(root);
                    res.add(buf);
                }
            }
            //遇到了一个已经被访问过的点，无法再traverse any further from there, so just take the rank
            else if(node!=pre && rank[node]!=-1){
                low[root] = Math.min(low[root], rank[node]);//而且发现的是Loop，不可能存在bridge，所以就不用考虑是否bridge
            }
        }
    }

    private List<List<Integer>> buildGraph(int n, List<List<Integer>> connections){
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<Integer>());
        }
        for(List<Integer> edge : connections){
            graph.get(edge.get(0)).add(edge.get(1));
            graph.get(edge.get(1)).add(edge.get(0));
        }
        return graph;
    }

    /**
     * Timestamp based Implementation.
     */
    public List<List<Integer>> criticalConnectionsSln2(int n, List<List<Integer>> connections) {
        int[] disc = new int[n], low = new int[n];
        // use adjacency list instead of matrix will save some memory, adjmatrix will cause MLE
        List<Integer>[] graph = new ArrayList[n];
        List<List<Integer>> res = new ArrayList<>();
        Arrays.fill(disc, -1); // use disc to track if visited (disc[i] == -1)
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // build graph
        for (int i = 0; i < connections.size(); i++) {
            int from = connections.get(i).get(0), to = connections.get(i).get(1);
            graph[from].add(to);
            graph[to].add(from);
        }

        for (int i = 0; i < n; i++) {
            if (disc[i] == -1) {
                dfs(i, low, disc, graph, res, i);
            }
        }
        return res;
    }

    int time = 0; // time when discover each vertex

    private void dfs(int u, int[] low, int[] disc, List<Integer>[] graph, List<List<Integer>> res, int pre) {
        disc[u] = low[u] = ++time; // discover u
        for (int j = 0; j < graph[u].size(); j++) {
            int v = graph[u].get(j);
            if (v == pre) {
                continue; // if parent vertex, ignore
            }
            if (disc[v] == -1) { // if not discovered
                dfs(v, low, disc, graph, res, u);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > disc[u]) {
                    // u - v is critical, there is no path for v to reach back to u or previous vertices of u
                    res.add(Arrays.asList(u, v));
                }
            } else { // if v discovered and is not parent of u, update low[u], cannot use low[v] because u is not subtree of v
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }



}
