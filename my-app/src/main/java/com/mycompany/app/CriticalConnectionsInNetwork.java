package com.mycompany.app;

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
    //下面第一种写法是比较naive的写法，会需要O(n^2)的time complexity.
    //尤其对于“大圈套小圈”的情况对于下面这种写法非常不利，详细的分析见下图：
    //src\main\resources\DfsWithBacktrackingComplexity.jpg
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        //解题的思路就是如果[a,b]不是critical edge，说明从a到b右不止一条路径，所以在a,b之间有一个cycle
        //所以一旦找到一个圆，那么dfs当下圆闭环的这个点沿着call stack backtrack，在回到这个点的路径就都不是critical edge
        //沿着call stack往回找，不能直接return call，而应该选择用parent指针的方式，否则control will be very messy
        List<List<Integer>> graph = buildGraph(n, connections);
        Set<Integer> excSet = new HashSet<>();
        //0: not visited; 1: visiting; 2: visited
        //这里一定要用到"visiting"这个状态，否则就会在"backTrack" method "pre = parent[pre]"
        //位置出现index out of bound的问题，因为"pre==-1"，原因如下：
        //src\main\resources\WhyUseVisiting.jpg
        int[] visited = new int[n];
        int[] parent = new int[n];
        for(int i=0; i<n; i++){
            parent[i]=-1;
        }
        //all connected, so simply pick to start from 0
        dfs(0, excSet, graph, visited, parent, n);
        List<List<Integer>> res = new ArrayList<>();
        for(List<Integer> edge : connections){
            int d0 = edge.get(0)>edge.get(1) ? edge.get(0) : edge.get(1);
            int d1 = edge.get(0)>edge.get(1) ? edge.get(1) : edge.get(0);
            int key = d1*n + d0;
            if(!excSet.contains(key)){
                res.add(edge);
            }
        }
        return res;
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

    private void dfs(int start, Set<Integer> excSet, List<List<Integer>> graph, int[] visited, int[] parent, int n){
        visited[start] = 1;
        for(int child : graph.get(start)){
            if(child!=parent[start] && visited[child]==0){
                parent[child] = start;
                dfs(child, excSet, graph, visited, parent, n);
                parent[child] = -1;
            }
            else if(child!=parent[start] && visited[child]==1){
                backTrack(excSet, child, start, n, parent);
            }
        }
        visited[start] = 2;
    }

    private void backTrack(Set<Integer> excSet, int end, int start, int n, int[] parent){
        int pre = parent[start];
        int cur = start;
        while(cur!=end){
            int d0 = cur > pre ? cur : pre;
            int d1 = cur > pre ? pre : cur;
            int key= d1*n + d0;
            excSet.add(key);
            cur = pre;
            pre = parent[pre];
        }
        //add edge between start and end
        int d0 = start > end ? start : end;
        int d1 = start > end ? end : start;
        int key = d1*n + d0;
        excSet.add(key);
    }

    /**
     * 这题是图论里面的"biconnected component"问题，具体说是找"bridge"的问题。
     * 在CLRS里面"Problem 22-2"就是这个，具体可以网上查询相关关键词，比如：
     * https://www.geeksforgeeks.org/biconnected-components/
     * https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
     */

    //具体实现上，一种就是我下面的这种写法，用rank，就是每个vertex到root在dfs路径中的距离
    //还有一种就是用timestamp，或者discover time. 具体就是用一个global的时间，去标定每一个vertex在dfs中被发现的时刻，就是下面第3种解法。
    //这两种解法的主要的区别在于sibling tree所标定的数值上，但是每一个tree内部的child, parent, ancestor的相对标定值上一样的。
    //所以对于发现"back edge"这件事情来说是没有区别的。详见：src\main\resources\Timestamp-Rank.PNG
    public List<List<Integer>> criticalConnectionsSln2(int n, List<List<Integer>> connections) {
        List<List<Integer>> graph = buildGraph(n, connections);
        List<List<Integer>> res = new ArrayList<>();
        int[] low = new int[n];
        int[] rank = new int[n];
        for(int i=0; i<n; i++){
            rank[i] = -1;
        }
        rank[0] = 0;
        dfs(0, graph, low, rank, res, -1);
        return res;
    }
    //不需要用parent pointer去backtracking,所以就不需要用array都记录下来了
    //用rank[i]==-1表达没有被visit过
    private void dfs(int root, List<List<Integer>> graph, int[] low, int[] rank, List<List<Integer>> res, int pre){
        int curRank = rank[root];
        low[root] = curRank;
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
            else if(node!=pre && rank[node]!=-1){
                low[root] = Math.min(low[root], rank[node]);
            }
        }
    }

    public List<List<Integer>> criticalConnectionsSln3(int n, List<List<Integer>> connections) {
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
