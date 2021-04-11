package com.mycompany.app;

/**
 * quickSelect的复杂度：
 * Best Case: O(n)
 * Average Case: O(n)
 * Worst Case: O(n^2)
 * Complexity Analysis: src\main\resources\QuickSortQuickSelectComplexity.PNG
 */
public class QuickSelect {
    public static void main(String[] args){
        QuickSelect instance = new QuickSelect();
        int[] nums = {4, 1, 2, 4, 6, 10};
        System.out.println(instance.quickSelect(nums, 1));
        System.out.println(instance.quickSelect(nums, 2));
        System.out.println(instance.quickSelect(nums, 3));
        System.out.println(instance.quickSelect(nums, 4));
        System.out.println(instance.quickSelect(nums, 5));
        System.out.println(instance.quickSelect(nums, 6));
        System.out.println(instance.quickSelect(nums, 7));
    }

    /**
     * 关于quickSelect一点说明：第K个数，只能说明其之前的数都小于等于它，之后的数都大于等于它，但要注意带着这个"等于号"
     * 简单的例子[3, 3, 3, 3]的第二个数是3，那么前后都是3，所以一定要带着"等于号"。
     * 不要跟partition function里面混淆，误以为后面都严格大于。
     */
    public int quickSelect(int[] nums, int k){
        if(k==0 || k>nums.length){
            return Integer.MIN_VALUE;
        }
        return search(nums, k);
    }

    private int search(int[] nums, int k){
        int left = 0;
        int right = nums.length-1;
        int res = 0;
        while(left<=right){
            int idx = partition(nums, left, right);
            if(idx==k-1){
                res = nums[idx];
                break;
            }
            else if(idx<k-1){
                left = idx+1;
                continue;
            }
            else{
                right = idx-1;
                continue;
            }
        }
        return res;
    }

    private int partition(int[] nums, int start, int end){
        int left = start + 1;
        int right = end;
        //注意这里要包含等号，为了处理left和right起始位置相同(既只有2个元素的情况)，但right需要左移的情况
        while(left<=right){
            while(right>=left && nums[right]>nums[start]){
                right--;
            }
            while(left<=right && nums[left]<=nums[start]){
                left++;
            }
            if(left<right){
                swap(nums, right, left);
            }
        }
        swap(nums, start, right);
        return right;
    }

    private void swap(int[] nums, int ptr1, int ptr2){
        int tmp = nums[ptr1];
        nums[ptr1] = nums[ptr2];
        nums[ptr2] = tmp;
    }

    //Search function iterative implementation
    //private int search(int[] nums, int left, int right, int k){
    //    //termination condition
    //    if(left==right){
    //        return nums[left];
    //    }

    //    int idx = partition(nums, left, right);
    //    if(idx==k-1){
    //        return nums[idx];
    //    }
    //    else if(idx>k-1){
    //        return search(nums, left, idx-1, k);
    //    }
    //    else{
    //        return search(nums, idx+1, right, k);
    //    }
    //}
}
