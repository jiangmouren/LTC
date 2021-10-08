package com.mycompany.app.hashMap;
import java.util.*;

/**
 * https://leetcode.com/problems/dot-product-of-two-sparse-vectors/
 * Given two sparse vectors, compute their dot product.
 *
 * Implement class SparseVector:
 *
 * SparseVector(nums) Initializes the object with the vector nums
 * dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
 * A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and compute the dot product between two SparseVector.
 *
 * Follow up: What if only one of the vectors is sparse?
 *
 * Example 1:
 * Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
 * Output: 8
 * Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
 * v1.dotProduct(v2) = 1*0 + 0*3 + 0*0 + 2*4 + 3*0 = 8
 *
 * Example 2:
 * Input: nums1 = [0,1,0,0,0], nums2 = [0,0,0,0,2]
 * Output: 0
 * Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
 * v1.dotProduct(v2) = 0*0 + 1*0 + 0*0 + 0*0 + 0*2 = 0
 *
 * Example 3:
 * Input: nums1 = [0,1,0,0,2,0,0], nums2 = [1,0,0,0,3,0,4]
 * Output: 6
 *
 * Constraints:
 * n == nums1.length == nums2.length
 * 1 <= n <= 10^5
 * 0 <= nums1[i], nums2[i] <= 100
 */
public class DotProductOfTwoSparseVectors {
    Map<Integer, Integer> map;
    DotProductOfTwoSparseVectors(int[] nums) {
        this.map = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            if(nums[i]!=0){
                this.map.put(i, nums[i]);
            }
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(DotProductOfTwoSparseVectors vec) {
        //新create两个指针，不要改变原来两个vec的内部的map指向
        Map<Integer, Integer> map2 = vec.map;
        Map<Integer, Integer> map1 = this.map;
        //确保map1指向比较小的那个
        if(map1.size()>map2.size()){
            map1 = map2;
            map2 = this.map;
        }

        int res = 0;
        for(Map.Entry<Integer,Integer> entry : map1.entrySet()){
            int key = entry.getKey();
            if(map2.containsKey(key)){
                res += entry.getValue() * map2.get(key);
            }
        }
        return res;
    }
}
