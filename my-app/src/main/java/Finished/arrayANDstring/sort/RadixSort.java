package Finished.arrayANDstring.sort;

/**
 * Created by jiangmouren on 6/17/17.
 */

/**
 * Question:
 * Implement RadixSort
 */

/**
 * Analysis:
 * Memorize the key concept is to sort digit by digit from LSB to MSB.
 * The reason we can do that is because the sort(Counting Sort) we use is stable.
 * As a result all numbers will be sorted with priorities from MSB to LSB.
 */
public class RadixSort {
    public void radixSort(int[] nums){
        int m = getMax(nums);
        for(int exp=1; m/exp>1; exp=10*exp){//use max and exp to control the sort loop
            countingSort(nums, exp);
        }
        return;
    }

    /**
     * @param nums
     * @param exp: 1, 10, 100, 1000
     */
    private void countingSort(int[] nums, int exp){
        int[] count = new int[10];
        //Get the count
        for(int num : nums){
            int digit = (num/exp) % 10;
            //System.out.println(num);
            //System.out.println(exp);
            //System.out.println(digit);
            count[digit]++;
        }
        //Accumulate count
        for(int i=1; i<10; i++){
            count[i] = count[i] + count[i-1];
        }

        //Prepare tmp buf
        int[] buf = new int[nums.length];
        for(int i=0; i<nums.length; i++){
            buf[i] = nums[i];
        }

        //Dump results
        //for(int tmp : buf){
        //    int digit = tmp/exp % 10;
        //    nums[count[digit]-1] = tmp;
        //    count[digit]--;
        //}

        /**
         * Very Important: move from right to left when dumping results!!!
         */
        for(int i=buf.length-1; i>=0; i--){
            int digit = buf[i]/exp % 10;
            nums[count[digit]-1] = buf[i];
            count[digit]--;
        }

        return;
    }

    private int getMax(int[] nums){
        int mx = nums[0];
        for(int num : nums){
            if(num>mx) mx = num;
        }
        return mx;
    }

}
