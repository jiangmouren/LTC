package com.mycompany.app.unsolved;

/**
 * https://leetcode.com/problems/second-highest-salary/
 * Write a SQL query to get the second highest salary from the Employee table.
 *
 * +----+--------+
 * | Id | Salary |
 * +----+--------+
 * | 1  | 100    |
 * | 2  | 200    |
 * | 3  | 300    |
 * +----+--------+
 * For example, given the above Employee table, the query should return 200 as the second highest salary. If there is no second highest salary, then the query should return null.
 *
 * +---------------------+
 * | SecondHighestSalary |
 * +---------------------+
 * | 200                 |
 * +---------------------+
 */

// Write your MySQL query statement below
/*
底下这个subquery使用的是“scalar-subqueries”，详见下面link:
https://dev.mysql.com/doc/refman/8.0/en/scalar-subqueries.html
里面明确说明了“SELECT (SELECT s2 FROM t1)”，如果subquery是empty,外面的select return NULL
*/

/*
SELECT
(SELECT DISTINCT
Salary
FROM
Employee
ORDER BY Salary DESC
LIMIT 1 OFFSET 1) AS SecondHighestSalary ;
 */