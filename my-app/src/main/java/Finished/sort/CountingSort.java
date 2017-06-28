package Finished.sort;

/**
 * Created by jiangmouren on 6/17/17.
 */

/**
 * Question:
 * Implement CountingSort Algorithm.
 */

/**
 * Analysis:
 * Quite often, CountingSort is used in RadixSort, but they are two different sort algorithms.
 * CountingSort, also referred to as BucketingSort, leverages the fact of inputs' value are limited.
 * The concept is very simple, how to implement this thing is the only thing interesting.
 * For the following simple question, the question would be, once I have got the count[],
 * why can't I just loop throw count[] and reconstruct arr[]?
 * The answer is: yes, you can for this specific question, but that's not a universally applicable approach.
 * Say the input is Student[] and you are required to sort student by their age.
 * Now you are sorting "object list" or essentially "reference list",
 * you are not supposed to create a new list of objects. That will be a totally different thing.
 */
public class CountingSort {
    //We assume arr[] value from {0:9}
    public void countingSort(int[] arr){
        if(arr==null) throw new IllegalArgumentException("Inputs cannot be null");
        int l = arr.length;
        if(l<2) return;

        int[] output = new int[l];
        int[] count = new int[10];
        for(int token : count){//initialize all all count[i] to 0
            token = 0;
        }
        // store count of each character
        for(int token : arr){
            ++count[token];
        }

        // Change count[i] so that count[i] now contains actual
        // position of this character in output array
        for(int i=1; i<10; i++){
            count[i] += count[i-1];
        }

        // Build the output character array
        // You have to put everything into a separate array first,
        // cannot use scan and swap and work on the original one directly.
        // That will be completely wrong. Say you swap arr[0] with arr[3],
        // then when you move to arr[3] how do I know this one I have already worked on
        // and not putting into the next arr[3]'s position or
        // even do a out of bound because count[arr[3]] goes negative.
        //for(int token: arr){
        //    output[count[token]-1] = token;
        //    --count[token];
        //}
        /**
         * Very Important to know that when dumping the result, we have to move from right to left!!!!
         */
        for(int i=arr.length-1; i>=0; i--){
            output[count[arr[i]]-1] = arr[i];
            --count[arr[i]];
        }

        // Copy the output array to arr, so that arr now
        // contains sorted characters
        for(int i=0; i<l; i++){
            arr[i] = output[i];
        }
    }
}
