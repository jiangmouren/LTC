package com.mycompany.app.companies.rippling;
import java.util.*;
/**
 * https://leetcode.com/discuss/interview-question/1650545/rippling-se-dsalgo-round-highest-performing-rating-employee-team
 */
public class HightestPerformingRatingTeam {
    static class Employee{
        String name;
        int rating;
        List<Employee> subOrdinates;
        public Employee(String name, int rating){
            this.name = name;
            this.rating = rating;
            this.subOrdinates = new ArrayList<>();
        }
    }

    //Assume all ratings are positive numbers
    public Employee findBestTeam(Employee root){
        float[] max = {0};
        Employee[] best = {null};
        dfs(root, max, best);
        return best[0];
    }

    //0: total rating; 1: cnt
    private int[] dfs(Employee root, float[] max, Employee[] best){
        int[] res = new int[2];
        res[0] = root.rating;
        res[1] = 1;

        for(Employee e : root.subOrdinates){
            int[] eRes = dfs(e, max, best);
            res[0] += eRes[0];
            res[1] += eRes[1];
        }

        float average = (float)res[0] / (float)res[1];
        if(average>max[0]){
            max[0] = average;
            best[0] = root;
        }
        return res;
    }

    public static void main(String[] args){
        HightestPerformingRatingTeam instance = new HightestPerformingRatingTeam();
        Employee A = new Employee("A", 5);
        Employee B = new Employee("B", 3);
        Employee C = new Employee("C", 1);
        Employee D = new Employee("D", 4);
        Employee E = new Employee("E", 10);
        A.subOrdinates.add(B);
        A.subOrdinates.add(C);
        C.subOrdinates.add(D);
        C.subOrdinates.add(E);
        Employee best = instance.findBestTeam(A);
        System.out.println(best.name);
    }
}
