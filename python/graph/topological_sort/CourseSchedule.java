package com.mycompany.app.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/course-schedule/
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
 * which is expressed as a pair: [0,1]
 * Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
 * For example:
 * 2, [[1,0]]
 * There are a total of 2 courses to take. To take course 1 you should have finished course 0. So it is possible.
 * 2, [[1,0],[0,1]]
 * There are a total of 2 courses to take. To take course 1 you should have finished course 0,
 * and to take course 0 you should also have finished course 1. So it is impossible.
 * Note:
 * The input prerequisites is a graph represented by a list of edges, not adjacency matrices.
 * Read more about how a graph is represented.
 * You may assume that there are no duplicate edges in the input prerequisites.
 * click to show more hints.
 *
 * Hints:
 * This problem is equivalent to finding if a cycle exists in a directed graph.
 * If a cycle exists, no topological ordering exists and therefore it will be impossible to take all courses.
 * Topological Sort via DFS - A great video tutorial (21 minutes) on Coursera explaining the basic concepts of Topological Sort.
 * Topological sort could also be done via BFS.
 *
 */ 

public class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //不能认为fully connected
        //对全部vertices做toplogical sort，没有出现loop则可以完成
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        int[] visit = new int[numCourses];
        for(int i=0; i<numCourses; i++){
            if(visit[i]==0 && !dfs(i, graph, visit)){
                return false;
            }
        }
        return true;
    }

    private List<List<Integer>> buildGraph(int n, int[][] prerequisites){
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] pair : prerequisites){
            graph.get(pair[1]).add(pair[0]);
        }
        return graph;
    }

    //visit: 0->not visited; 1->visiting; 2->visited
    private boolean dfs(int root, List<List<Integer>> graph, int[] visit){
        visit[root] = 1;
        for(int child : graph.get(root)){
            if(visit[child]==1){
                return false;
            }
            if(visit[child]==0 && !dfs(child, graph, visit)){
                return false;
            }
        }
        visit[root] = 2;
        return true;
    }
}
