package com.mycompany.app.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/course-schedule-ii/
 * Question:
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
 * which is expressed as a pair: [0,1]
 * Given the total number of courses and a list of prerequisite pairs,
 * return the ordering of courses you should take to finish all courses.
 * There may be multiple correct orders, you just need to return one of them.
 * If it is impossible to finish all courses, return an empty array.
 * For example:
 * 2, [[1,0]]
 * There are a total of 2 courses to take. To take course 1 you should have finished course 0.
 * So the correct course order is [0,1]
 * 4, [[1,0],[2,0],[3,1],[3,2]]
 * There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2.
 * Both courses 1 and 2 should be taken after you finished course 0. So one correct course order is [0,1,2,3].
 * Another correct ordering is[0,2,1,3].
 * Note:
 * The input prerequisites is a graph represented by a list of edges, not adjacency matrices.
 * Read more about how a graph is represented.
 * You may assume that there are no duplicate edges in the input prerequisites.
 * click to show more hints.
 * Hints:
 * This problem is equivalent to finding the topological order in a directed graph.
 * If a cycle exists, no topological ordering exists and therefore it will be impossible to take all courses.
 * Topological Sort via DFS.
 * Topological sort could also be done via BFS.
 *
 */ 

public class CourseScheduleII {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        int[] visited = new int[numCourses];//0: not visited; 1: visiting; 2: visited
        List<Integer> res = new ArrayList<>();
        for(int i=0; i<numCourses; i++){
            if(visited[i]==0){
                if(!dfs(graph, i, visited, res)){
                    return new int[0];
                }
            }
        }
        int[] result = new int[numCourses];
        //res.toArray(result) 不行，如果result是integer[]才可以
        for(int i=0; i<numCourses; i++){
            result[i] = res.get(i);
        }
        return result;
    }

    private List<List<Integer>> buildGraph(int n, int[][] edges){
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            graph.get(edge[0]).add(edge[1]);
        }
        return graph;
    }

    private boolean dfs(List<List<Integer>> graph, int start, int[] visited, List<Integer> res){
        visited[start] = 1;
        for(int child : graph.get(start)){
            if(visited[child]==1){
                return false;
            }
            if(visited[child]==0 && !dfs(graph, child, visited, res)){
                return false;
            }
        }
        res.add(start);
        visited[start] = 2;
        return true;
    }
}
