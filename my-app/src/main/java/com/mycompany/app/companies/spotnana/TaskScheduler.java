package com.mycompany.app.companies.spotnana;
import java.util.*;
/**
 * You are given two 0-indexed integer arrays servers and tasks of lengths n and m respectively.
 * servers[i] is the weight of the ith server, and tasks[j] is the time needed to process the jth task in seconds.
 * Tasks are assigned to the servers using a task queue. Initially, all servers are free, and the queue is empty.
 * At second j, the jth task is inserted into the queue (starting with the 0th task being inserted at second 0).
 * As long as there are free servers and the queue is not empty,
 * the task in the front of the queue will be assigned to a free server with the smallest weight,
 * and in case of a tie, it is assigned to a free server with the smallest index.
 * If there are no free servers and the queue is not empty,
 * we wait until a server becomes free and immediately assign the next task.
 * If multiple servers become free at the same time,
 * then multiple tasks from the queue will be assigned in order of insertion following the weight and index priorities above.
 * A server that is assigned task j at second t will be free again at second t + tasks[j].
 * Build an array ans of length m, where ans[j] is the index of the server the jth task will be assigned to.
 *
 * Sample test case 1 :
 * servers = [3,3,2], tasks = [1,2,3,2,1,2]
 * Output: [2,2,0,2,1,2]
 *
 * Sample test case 2 :
 * servers = [5,1,4,3,2], tasks = [2,1,2,4,5,2,1]
 * Output: [1,4,1,4,1,3,2]
 */

/**
 * 这个题最好的解法就是写个simulation，实现起来还是有点tricky的
 * 思路就是：
 * 需要时间变量
 * 需要用到两个priorityQueue，一个存available的server，一个存已经assign出去的server
 */
public class TaskScheduler {
    public int[] schedule(int[] servers, int[] tasks){
        int[] res = new int[tasks.length];
        //0: weight; 1: idx
        Queue<int[]> availableServers = new PriorityQueue<>((a, b)->{
            int cmp = a[0]-b[0];
            if(cmp==0){
                cmp = a[1]-b[1];
            }
            return cmp;
        });
        //0: time; 1: idx
        Queue<int[]> assignedServers = new PriorityQueue<>((a, b)->a[0]-b[0]);
        int t = 0;
        int ptr = 0;
        //populate available servers
        for(int i=0; i<servers.length; i++){
            int[] server = {servers[i], i};
            availableServers.add(server);
        }

        while(ptr<tasks.length){
            //update available servers
            do{
                while(!assignedServers.isEmpty() && assignedServers.peek()[0]<=t){
                    //System.out.println("assignedServers cnt: " + assignedServers.size());
                    int[] recycled = assignedServers.poll();
                    int[] server = {servers[recycled[1]], recycled[1]};
                    availableServers.add(server);
                }
                t++;
            }while(availableServers.isEmpty());
            t--;//the first iteration should not increment t
            //assign servers
            while(!availableServers.isEmpty() && ptr<=t && ptr<tasks.length){
                //System.out.println("here");
                int[] server = availableServers.poll();
                int[] assigned = {t+tasks[ptr], server[1]};
                assignedServers.add(assigned);
                res[ptr] = server[1];
                ptr++;
            }
            //System.out.println("I'm out!");
            t++;
        }
        return res;
    }

    public static void main(String[] args){
        TaskScheduler instance = new TaskScheduler();
        int[] servers0 = {3,3,2};
        int[] tasks0 = {1,2,3,2,1,2};
        int[] servers1 = {5,1,4,3,2};
        int[] tasks1 = {2,1,2,4,5,2,1};
        int[] res0 = instance.schedule(servers0, tasks0);
        int[] res1 = instance.schedule(servers1, tasks1);
        printArr(res0);
        printArr(res1);
    }
    private static void printArr(int[] arr){
        for(int a : arr){
            System.out.println(a);
        }
    }
}
