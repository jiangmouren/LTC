package com.mycompany.app.graph.unionfind;

import java.util.*;

/**
 * https://leetcode.com/problems/longest-consecutive-sequence/
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 *
 * Example 1:
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 *
 * Example 2:
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 *
 * Constraints:
 * 0 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 *
 * Follow up: Could you implement the O(n) solution?
 */

/**
 * Analysis:
 * 这题目最直观的想法就是sort，然后两个pointer找最长的连续值，复杂度达不到要求。
 * 其实这类Disjoint-set grouping的问题都可以用Union-Find方法在O(n)的复杂度下解决。
 * 构造Union-Find的复杂度是O(n)，每次union(x, y) or find(x)的复杂度都是O(1).
 * 具体详见：https://www.youtube.com/watch?v=VJnUwsE4fWA
 * src\main\resources\UnionFind.PNG
 */
public class LongestConsecutiveSequence{
    public static void main(String[] args){
        LongestConsecutiveSequence instance = new LongestConsecutiveSequence();
        int[] nums = new int[]{100, 4, 7, 200, 1, 6, 3, 2, 8, 9, 10};
        System.out.println(instance.longestConsecutive(nums));
    }
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int num : nums){
            set.add(num);
        }
        Set<Integer> visited = new HashSet<>();
        int max = 0;
        for(int num : nums){
            if(!visited.contains(num)){
                int cnt0 = visited.size();
                dfs(set, num, visited);
                int cnt1 = visited.size();
                max = Math.max(max, cnt1-cnt0);
            }
        }
        return max;
    }

    private void dfs(Set<Integer> set, int root, Set<Integer> visited){
        //System.out.println(root);
        visited.add(root);
        if(set.contains(root-1) && !visited.contains(root-1)){
            dfs(set, root-1, visited);
        }
        if(set.contains(root+1) && !visited.contains(root+1)){
            dfs(set, root+1, visited);
        }
    }

    //先把UnionFind的class写出来，而且不会变，所以记住就行
    class UnionFind{
        int[] parents;
        int[] ranks;
        public UnionFind(int n){
            this.parents = new int[n];
            this.ranks = new int[n];
            for(int i=0; i<n; i++){
                this.parents[i] = i;
                this.ranks[i] = 0;
            }
        }

        public int find(int x){
            if(x!=this.parents[x]){
                this.parents[x] = find(this.parents[x]);//path compression;
            }
            return this.parents[x];
        }

        //return true if x & y unioned; return false if x & y already in the same cluster
        public boolean union(int x, int y){
            int pX = find(x);
            int pY = find(y);
            if(pX==pY){
                return false;
            }
            else{
                if(this.ranks[pX]>this.ranks[pY]){
                    this.parents[pY] = pX;
                }
                else if(this.ranks[pX]<this.ranks[pY]){
                    this.parents[pX] = pY;
                }
                else{
                    this.parents[pY] = pX;
                    this.ranks[pX]++;
                }
                return true;
            }
        }
    }

    public int longestConsecutiveUF(int[] nums) {
        //构造Union-Find，给每个Node标记ID，然后用个map存node到id的映射
        //因为后面Union的条件发生在原Node的之间，而实际的Union的操作确实做在其对应的id身上。
        Map<Integer, Integer> map = new HashMap<>();
        int id = 0;
        for(int num : nums){
            if(!map.containsKey(num)){
                map.put(num, id++);
            }
        }
        UnionFind uf = new UnionFind(id);

        //通常第二步是Union
        Set<Integer> keySet = map.keySet();
        for(int x : keySet){
            if(map.containsKey(x-1)){
                uf.union(map.get(x), map.get(x-1));
            }
            if(map.containsKey(x+1)){
                uf.union(map.get(x), map.get(x+1));
            }
        }

        //第三步或者是数有多少个不同的group，或者是找最大的group的size.
        int[] cnts = new int[id];
        for(int x : keySet){
            int p = uf.find(map.get(x));
            cnts[p]++;
        }
        int max = 0;
        for(int cnt : cnts){
            max = Math.max(max, cnt);
        }
        return max;
    }

    /**
     * 这个题也可以用dfs处理。只不过不需要explicitly构造出adjacency-list
     * 而用一个HashSet/Map入上面解法找相邻点一样，就可以解决所有node的邻居查询。
     * 于是就可以记录下一次dfs visit了多少个点，对每个dfs求visit的最多的点数。
     * TODO:
     */
}