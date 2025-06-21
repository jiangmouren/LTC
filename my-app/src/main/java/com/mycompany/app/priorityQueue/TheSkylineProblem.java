package com.mycompany.app.priorityQueue;
import java.util.*;
/**
 * https://leetcode.com/problems/the-skyline-problem/
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance.
 * Given the locations and heights of all the buildings, return the skyline formed by these buildings collectively.
 *
 * The geometric information of each building is given in the array buildings where buildings[i] = [lefti, righti, heighti]:
 *
 * lefti is the x coordinate of the left edge of the ith building.
 * righti is the x coordinate of the right edge of the ith building.
 * heighti is the height of the ith building.
 * You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
 *
 * The skyline should be represented as a list of "key points" sorted by their x-coordinate in the form [[x1,y1],[x2,y2],...].
 * Each key point is the left endpoint of some horizontal segment in the skyline except the last point in the list,
 * which always has a y-coordinate 0 and is used to mark the skyline's termination where the rightmost building ends.
 * Any ground between the leftmost and rightmost buildings should be part of the skyline's contour.
 *
 * Note: There must be no consecutive horizontal lines of equal height in the output skyline.
 * For instance, [...,[2 3],[4 5],[7 5],[11 5],[12 7],...] is not acceptable;
 * the three lines of height 5 should be merged into one in the final output as such: [...,[2 3],[4 5],[12 7],...]
 *
 * Example 1:
 * Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
 * Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
 * Explanation:
 * Figure A shows the buildings of the input.
 * Figure B shows the skyline formed by those buildings.
 * The red points in figure B represent the key points in the output list.
 *
 * Example 2:
 * Input: buildings = [[0,2,3],[2,5,3]]
 * Output: [[0,3],[5,0]]
 *
 *
 * Constraints:
 * 1 <= buildings.length <= 10^4
 * 0 <= lefti < righti <= 2^31 - 1
 * 1 <= heighti <= 2^31 - 1
 * buildings is sorted by left.i in non-decreasing order.
 */

/**
 * 注意edge list里面存的内容跟priorityQueue里面存的内容不一样
 * 然后读到左侧edge的时候，把右侧edge放进去，用idx把左右edge关联起来
 * 存右侧高度，及其pos用以判断是否继续有效。
 */
class TheSkylineProblem {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        //queue contains (height, pos)
        Queue<int[]> queue = new PriorityQueue<>((a, b)->b[0]-a[0]);
        //edge contains (pos, idx, type)
        List<int[]> edges = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0; i<buildings.length; i++){
            //0: left; 1: right
            int[] left = {buildings[i][0], i, 0};
            int[] right = {buildings[i][1], i, 1};
            edges.add(left);
            edges.add(right);
        }
        edges.sort((a, b)->{
            int cmp = a[0]-b[0];
            if(cmp==0){
                cmp = a[2]-b[2];//at the same position, make sure left edge is considered before right edge
            }
            return cmp;
        });
        int ptr = 0;
        while(ptr<edges.size()){
            int[] edge = edges.get(ptr);
            if(edge[2]==0){
                //这里一定要先把同一个pos的left edges全都加上，为了下面这种case: [[1,2,1],[1,2,2],[1,2,3]]
                while(ptr<edges.size() && edges.get(ptr)[0]==edge[0] && edges.get(ptr)[2]==0){
                    int idx = edges.get(ptr)[1];
                    int[] right = {buildings[idx][2], buildings[idx][1]};
                    //if current edge is a left edge, add the corresponding right edge into the queue
                    queue.add(right);
                    ptr++;
                }
                updateRes(queue, res, edge);
            }
            else{
                while(!queue.isEmpty() && queue.peek()[1]<=edge[0]){
                    queue.poll();
                }
                updateRes(queue, res, edge);
                ptr++;
            }
        }
        return res;
    }

    private void updateRes(Queue<int[]> queue, List<List<Integer>> res, int[] edge){
        int max = 0;
        if(!queue.isEmpty()){
            max = queue.peek()[0];
        }
        if(res.size()==0 || max!=res.get(res.size()-1).get(1)){
            List<Integer> temp = new ArrayList<>();
            temp.add(edge[0]);//current pos
            temp.add(max);//current height
            res.add(temp);
        }
    }

    public static void main(String[] args){
        TheSkylineProblem instance = new TheSkylineProblem();
        //[[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
        int[][] buildings0 = {{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}};
        List<List<Integer>> res0 = instance.getSkyline(buildings0);
        for(List<Integer> pos : res0){
            System.out.println("x: "+pos.get(0)+", "+"y: "+pos.get(1));
        }
    }
}