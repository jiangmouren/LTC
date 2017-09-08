package finished;
/**
 * Question:
 * Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
 * For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
 * 3, 5, 2, 1, 6, 4
 * 3, 5, 1, 2, 6, 4
 * 3, 5, 1, 6, 2, 4
 */

/**
 * Analysis:
 * A altered bubble sort.
 * Just a small trick but need to memorize this one.
 * The primary difference here is normal sort, the relationship to the left side is different from the relationship to
 * the right side. Here the relationship to both sides are the same. Because of this, we only need to swap one round!!!
 */

public class WiggleSort{
    public void wiggleSort(int[] nums){
        for(int i=0; i<nums.length-1; i++){
            if((i%2==0)&&nums[i]>nums[i+1] || (i%2==1)&&nums[i]<nums[i+1]){
                int tmp = nums[i];
                nums[i] = nums[i+1];
                nums[i+1] = tmp;
            }
        }
    }

}
