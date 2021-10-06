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
        List<Integer> result = new ArrayList<>();
        //Better to use Enum. 0: unvisited; 1: visiting; 2 visited.
        int[] status = new int[numCourses];
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        for(int i=0; i<numCourses; i++){
            if(status[i]==0){
                if(!dfs(result, graph, status, i)){
                    //found cycle, return empty array(length 0 array)
                    //System.out.println("exit here");
                    //System.out.println("i: "+i );
                    return new int[0];
                }
            }
        }
        int[] resultArray = new int[numCourses];
        //System.out.println("I'm here");
        //System.out.println(result);
        for(int i=0; i<numCourses; i++){
            resultArray[i] = result.get(i);
        }
        return resultArray;
    }

    //return true if no cycle found
    private boolean dfs(List<Integer> result, List<List<Integer>> graph, int[] status, int cur){
        //set cur to visiting first
        status[cur] = 1;

        //end condition
        if(graph.get(cur).size()==0){//find a leaf node
            result.add(cur);
            status[cur] = 2;
            return true;
        }

        for(int prerequisite : graph.get(cur)){
            if(status[prerequisite]==1){
                return false;//found cycle
            }
            if(status[prerequisite]==0){
                if(!dfs(result, graph, status, prerequisite)){
                    return false;//found cycle
                }
            }
        }
        //After all children visited, add cur to result
        result.add(cur);
        //set cur to visited before end
        status[cur] = 2;
        return true;
    }

    //Better to create Node class.
    private List<List<Integer>> buildGraph(int numCourses, int[][] prerequisites){
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<numCourses; i++){
            graph.add(new ArrayList<Integer>());
        }
        for(int[] prerequisite : prerequisites){
            graph.get(prerequisite[0]).add(prerequisite[1]);
        }
        return graph;
    }
}
