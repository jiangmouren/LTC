package com.mycompany.app.companies.jerry;
import java.util.*;

/**
 * https://www.1point3acres.com/bbs/thread-932171-1-1.html
 * 给定一个数组，只有一个初始数字1，对这个数组的每个数字k，做k*2+1和k*3+1，然后加入数组，要求这个数组是sorted并且没有重复元素，返回第N个element
 * 这个数组应该是[1,3,4,7,9,10,13,....]
 * 解答：
 * 这个题需要处理的点是对于两个相邻的elements - a(k) 和 a(k+1)，a(k) * 3 + 1 有可能大于 a(k+1) * 2 + 1，所以要排序。
 * 解法一：用一个TreeMap，每次从里面拿最小值去生成新值,既能保证sorted,还能不重复。复杂度 O(n logn).
 * 解法二：观察到 k*2 + 1 和 k*3 + 1两个数组是独立递增的,因为每次新生成的值都会比上一次更大，因为使用了更大的基数，所以可以维护两个deque，
 * 每次从两个deque的头去最小值，然后把新的element放到相应的队列里。有点类似merge sort，要注意的就是当遇到两边相等的时候，
 * 要同时move两边的pointer.复杂度 O(n)
 */
public class ConstructArray {
    //assume n>=1
    public int getNthElement(int n){
        List<Integer> list0 = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        int cnt = 1;
        int res = 1;
        list0.add(3);
        list1.add(4);
        int ptr0 = 0;
        int ptr1 = 0;
        while(cnt<n){
            if(list0.get(ptr0)<list1.get(ptr1)){
                res = list0.get(ptr0);
                ptr0++;
            }
            else if(list0.get(ptr0)==list1.get(ptr1)){
                res = list0.get(ptr0);
                ptr0++;
                ptr1++;
            }
            else{
                res = list1.get(ptr1);
                ptr1++;
            }
            list0.add(res*2+1);
            list1.add(res*3+1);
            cnt++;
        }
        return res;
    }
}
