package com.mycompany.app.companies.wonders;

/*
 * Click `Run` to execute the snippet below!

 Customers send us tasks to do. We send these tasks to our work force for completion. When these tasks are done, we send them back to the customer.

Each task goes through three different stages:

L0 → L1 → L2 → Finished

These stages are sequential, i.e. a task must follow the exact ordering.

A task is only “complete” when it reaches the L2 stage and work is finished on the L2 stage

Workers are given task stages to work on.

Note:

One worker can only work on one task stage at a time

One task stage can only have one worker working on it at a time.

A worker can only work on a task’s stage if the worker has never worked on that task before.

For now, assume each worker takes 1 minute. They’ll take different amounts of time later.

Write a system that runs until all tasks are completed. Print when a task is assigned to a worker, when that worker completes the assignment, and the total amount of time taken to complete all tasks. It’s ok if the simulation is “inefficient.” We’re looking for correctness and printed output as the state of the world changes.





def test_case_1():
    tasks = [Task('A')]
    workers = [Worker('X'), Worker('Y'), Worker('Z')]
    run(tasks, workers)


*Sample output 1*
0
Assigning X to Task A for L0
1
Worker X finished Task A for L0
Assigning Y to Task A for L1
2
Worker Y finished Task A for L1
Assigning Z to Task A for L2
3
Worker Z finished Task A for L2
Total time taken: 3 min




def test_case_2():
    tasks = [Task('A'), Task('B')]
    workers = [Worker('X'), Worker('Y'), Worker('Z')]
    run(tasks, workers)

*Sample output 2 (one possible correct answer): *
0
Assigning X to Task A for L0
Assigning Y to Task B for L0
1
Worker X finished Task A for L0
Worker Y finished Task B for L0
Assigning Z to Task A for L1
Assigning X to Task B for L1
2
Worker Z finished Task A for L1
Worker X finished Task B for L1
Assigning Y to Task A for L2
Assigning Z to Task B for L2
3
Worker Y finished Task A for L2
Worker Z finished Task B for L2
Total time taken: 3 min
 */

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
    public static void main(String[] args) {
        List<Worker> workerList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        Task A = new Task("A");
        Task B = new Task("B");
        Task C = new Task("C");
        Task D = new Task("D");
        taskList.add(A);
        taskList.add(B);
        taskList.add(C);
        taskList.add(D);
        Worker X = new Worker("X");
        Worker Y = new Worker("Y");
        Worker Z = new Worker("Z");
        workerList.add(X);
        workerList.add(Y);
        workerList.add(Z);
        int time = 0;
        boolean complete = false;
        //List<String> completeStatements = new ArrayList<>();
        //List<String> assignmentStatements = new ArrayList<>();
        while(!complete){
            System.out.println(time);
            //check if just complete
            boolean flag = true;
            for(Task t : taskList){
                if(!t.status && ((time - t.assignTime)==1)){
                    System.out.println("Worker " + t.preWorker.id + " finished Task " + t.id + " for " + t.stage);
                    t.completeStage();
                }
                flag = flag && t.status;
            }
            complete = flag;
            //new assignments
            for(Task t : taskList){
                if(!t.status){
                    for(Worker w : workerList){
                        if(!w.ocupied && !t.history.contains(w)){
                            t.assign(w, time);
                            System.out.println("Assigning " + w.id + " to Task " + t.id + " for " + t.stage);
                            break;
                        }
                    }
                }
            }
            time++;
        }
    }

    static class Worker{
        boolean ocupied;
        //int nxtTime;
        String id;
        public Worker(String id){
            this.id = id;
            this.ocupied = false;
            //this.nxtTime = nxtTime;
        }
    }

    static class Task{
        Set<Worker> history;
        boolean status;
        int stage;
        Worker preWorker;
        int assignTime;
        //int nxtTime;
        String id;
        public Task(String id){
            this.id = id;
            this.status = false;
            this.stage = 0;
            this.history = new HashSet<>();
            this.assignTime = -10;
        }
        public void assign(Worker worker, int assignTime){
            //this.stage += 1;
            worker.ocupied = true;
            this.preWorker = worker;
            this.assignTime = assignTime;
        }
        public void completeStage(){
            this.history.add(preWorker);
            this.stage += 1;
            if(stage>2){
                this.status = true;
            }
            this.preWorker.ocupied = false;
        }
    }
}


