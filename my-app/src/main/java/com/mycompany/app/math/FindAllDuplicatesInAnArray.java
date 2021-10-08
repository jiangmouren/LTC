package com.mycompany.app.math;

import java.util.*;

/**
 * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 * Find all the elements that appear twice in this array.
 * Could you do it without extra space and in O(n) runtime?
 *
 * Example:
 * Input:
 * [4,3,2,7,8,2,3,1]
 * Output:
 * [2,3]
 */
public class FindAllDuplicatesInAnArray {

    /**
     * 下面这种解法的“计数”设计，导致可以顺序处理每一个entry，而不需要沿着“link”做graph walk
     */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();

        for (int num : nums) {
            nums[Math.abs(num) - 1] *= -1;//这里之所以需要用abs，是因为对于出现两次的，需要翻转两次，第二次的时候面对的是负值
        }

        for (int num : nums) {
            if (nums[Math.abs(num) - 1] > 0) {
                res.add(Math.abs(num));
                nums[Math.abs(num) - 1] *= -1;//这里之所以需要把正值在变成负的，是保证只在num第一次出现的时候res add一次
            }
        }

        return res;
    }

    /**
     * 下面是我自己想到的解法，总体思路是跟上面的解法一致的，但是具体在“计数”设计上，上面还是技高一招！
     * 以至于上面的代码要比我下面的代码简单很多，下面其实相当于写了个iterative的dfs，而且还是个multi-graph，不是特别典型的dfs写法
     * 比如说2->3这个edge就出现了两次，而且两次都是有效的。上面解法，跟这种解法的complexity跟实际跑出时间都是一样的。
     * 思路有借鉴'Find the Duplicate Number'的部分，但有不一样，都是利用nums里面的数的取值区间与Index区间吻合
     * 所以我可以利用题目中给的nums本身做HashMap，找到了相应的数，就去相应的entry里去计数
     * 只不过在去计数之前，需要先把原entry里的值，因为那就是下一个需要被记录的数
     * 根据'Find the Duplicate Number'的分析经验，或者过一个例子：[4,3,2,7,8,2,3,1]
     * 就会发现，有duplicate当前情况下，一遍不能保证所有的entry都cover到，所以需要向dfs一样，在外层再加一个Loop
     * 然后就是在计数的时候，因为原来的数值都是[1,n]的，所以要避开这个区间的，从0开始计数，反向计数，每多一个就-1
     */
    public List<Integer> findDuplicatesDfs(int[] nums) {

        for(int i=0; i<nums.length; i++){
            if(nums[i]<1){
                continue;
            }
            //System.out.println(i);
            int ptr = nums[i]-1;
            while(true){
                if(nums[ptr]>0){
                    int temp = nums[ptr];
                    nums[ptr] = 0;
                    if(ptr<=i){//回到起始点及起始点之前的点，就只操作那个点，不再操作后续点
                        break;
                    }
                    ptr = temp-1;
                }
                else if(nums[ptr]==0){
                    nums[ptr]--;//到-1就必须停
                    break;
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        for(int i=0; i<nums.length; i++){
            if(nums[i]==-1){
                res.add(i+1);
            }
        }
        return res;
    }
}
