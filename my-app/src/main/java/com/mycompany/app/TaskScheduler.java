package com.mycompany.app;

/**
 * https://leetcode.com/problems/task-scheduler/
 * Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a different task.
 * Tasks could be done in any order. Each task is done in one unit of time.
 * For each unit of time, the CPU could complete either one task or just be idle.
 * However, there is a non-negative integer n that represents the cooldown period between two same tasks (the same letter in the array),
 * that is that there must be at least n units of time between any two same tasks.
 * Return the least number of units of times that the CPU will take to finish all the given tasks.
 *
 * Example 1:
 * Input: tasks = ["A","A","A","B","B","B"], n = 2
 * Output: 8
 * Explanation:
 * A -> B -> idle -> A -> B -> idle -> A -> B
 * There is at least 2 units of time between any two same tasks.
 *
 * Example 2:
 * Input: tasks = ["A","A","A","B","B","B"], n = 0
 * Output: 6
 * Explanation: On this case any permutation of size 6 would work since n = 0.
 * ["A","A","A","B","B","B"]
 * ["A","B","A","B","A","B"]
 * ["B","B","B","A","A","A"]
 * ...
 * And so on.
 *
 * Example 3:
 * Input: tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
 * Output: 16
 * Explanation:
 * One possible solution is
 * A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> idle -> idle -> A -> idle -> idle -> A
 *
 * Constraints:
 * 1 <= task.length <= 104
 * tasks[i] is upper-case English letter.
 * The integer n is in the range [0, 100].
 */
public class TaskScheduler {
    public int leastInterval(char[] tasks, int n) {
        //跟 https://leetcode.com/problems/reorganize-string/ 很像
        //也是把最多的那个先摆开，然后其余的插空。目标就是idle的时间越少越好。
        //A___A___A___A, 入右图所示，如果A是最多的一个，那么如果B的个数也是4，那么就放在最后一个A后面（要注意这个时候要把时间加一）
        //对于所有个数小于4的都往上面那3个空隙当中插，先往里插，都插满了之后，再插的时候要相相应增加时间

        int[] cnts = new int[26];
        for(char c : tasks){
            cnts[c-'A']++;
        }
        int maxCnt = Integer.MIN_VALUE;
        int maxIdx = -1;
        for(int i=0; i<26; i++){
            if(cnts[i]>maxCnt){
                maxCnt = cnts[i];
                maxIdx = i;
            }
        }

        int vacnt = (maxCnt-1) * n;
        int time = maxCnt + vacnt;

        for(int i=0; i<26; i++){
            if(i==maxIdx){
                continue;
            }
            if(vacnt>=cnts[i]){//等号一定要放在这里，等于的情况需要考虑考虑maxCnt-1的情况
                if(cnts[i]==maxCnt){
                    vacnt -= (maxCnt-1);
                    time += 1;
                }
                else{
                    vacnt -= cnts[i];
                }
            }
            else{
                int remaining = cnts[i] - vacnt;
                time += remaining;
                vacnt = 0;
            }
        }
        return time;
    }
}
