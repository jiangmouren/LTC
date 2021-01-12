package com.mycompany.app;

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
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        return !findCycle(graph);
    }

    //Better to create a Node class
    private List<List<Integer>> buildGraph(int numCourses, int[][] prerequisites){
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<numCourses; i++){
            graph.add(new ArrayList<Integer>());
        }
        for(int[] pair : prerequisites){
            graph.get(pair[0]).add(pair[1]);
        }
        return graph;
    }

    //do dfs, return true if find cycle.
    private boolean findCycle(List<List<Integer>> graph){
        List<Integer> status = new ArrayList<>();
        for(int i=0; i<graph.size(); i++){
            status.add(0);
        }
        for(int i=0; i<graph.size(); i++){
            if(status.get(i)==0){
                if(dfs(graph, status, i)){
                    return true;
                }
            }
        }
        return false;
    }

    //better to create Enum for different status
    //0: unvisited; 1: visiting; 2: visited
    //return true if find cycle
    private boolean dfs(List<List<Integer>> graph, List<Integer> status, int cur){
        //termination, no need to handle, empty prerequisites list will stop automatically

        //set cur to visiting
        status.set(cur, 1);

        //recursion
        for(int prerequisite : graph.get(cur)){
            if(status.get(prerequisite)==1){
                //find cycle
                return true;
            }
            if(status.get(prerequisite)==0){//only traverse unvisited nodes
                if(dfs(graph, status, prerequisite)){
                    return true;
                }
            }
        }

        //set cur to visited after all children visited
        status.set(cur, 2);
        return false;
    }
}
