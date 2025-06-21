package com.mycompany.app.companies.tuSimple;
import com.sun.source.tree.Tree;

import java.util.*;

/**
 * 电面一面：给一个大小为N的有序数组，随机替换其中k个元素（k << N），用一种复杂度小于O（NlogN）的办法重新排序数组
 */

/**
 * 思路：充分利用大部分已经sorted这个条件，把被替换过的数组拆分成若干段fully sorted的数组，然后再做一个merge sort
 * 最多可以拆分成 K+1段
 * 做merge sort的时候，最多把全部element过一遍，所以整个复杂度就是O(N)
 */
public class ReOrderArray {
    public int[] reOrder(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> buf = new ArrayList<>();
        for(int i=0; i<nums.length; i++){
            buf.add(nums[i]);
            if(i+1<nums.length && nums[i]>nums[i+1]){
                res.add(buf);
                //either create a new list and copy everything into the new list
                //then store the new list and clear the buf or point buf to a new list
                //prefer the second approach, more efficient, no need to copy and clear.
                buf = new ArrayList<>();
            }
        }
        //不要忘记最后一段没有在里面收录
        res.add(buf);
        return merge(res);
    }

    class Node{
        int val;
        int idx;
        public Node(int val, int idx){
            this.val = val;
            this.idx = idx;
        }
    }
    private int[] merge(List<List<Integer>> inputs){
        List<Integer> ptrs = new ArrayList<>();
        for(int i=0; i<inputs.size(); i++){
            ptrs.add(0);
        }
        //注意这里我开始用了一个TreeMap，但其实不能用TreeMap，因为map里不能有duplicated keys
        //TreeMap跟Heap虽然都能保持最小值，但是最大的不同就是能不能有duplicated key。
        //还有一点就是在实践中，heap是用array存储的，其cache hit rate会更高。
        PriorityQueue<Node> heap = new PriorityQueue<>((a, b)->{
            return a.val - b.val;
        });
        for(int i=0; i<ptrs.size(); i++){
            Node node = new Node(inputs.get(i).get(0), i);
            heap.add(node);
        }
        List<Integer> res = new ArrayList<>();
        while(heap.size()>1){
            Node node = heap.poll();
            int min = node.val;
            res.add(min);
            int idx = node.idx;
            int ptr = ptrs.get(idx) + 1;
            if(ptr<inputs.get(idx).size()){
                ptrs.set(idx, ptr);
                heap.add(new Node(inputs.get(idx).get(ptr), idx));
            }
        }
        int idx = heap.poll().idx;
        List<Integer> list = inputs.get(idx);
        List<Integer> subList = list.subList(ptrs.get(idx), list.size());
        res.addAll(subList);
        int[] output = new int[res.size()];
        for(int i=0; i<res.size(); i++){
            output[i] = res.get(i);
        }
        return output;
    }
    public static void main(String[] args){
        ReOrderArray instance = new ReOrderArray();
        int[] nums = {0, 1, 11, 3, 4, 55, 6, -1, 8, 9};
        int[] output = instance.reOrder(nums);
        for(int e : output){
            System.out.println(e);
        }
    }
}
