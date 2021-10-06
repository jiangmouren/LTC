package com.mycompany.app.hashMap;
import java.util.*;

/**
 * https://leetcode.com/problems/design-hashmap/
 * Design a HashMap without using any built-in hash table libraries.
 *
 * To be specific, your design should include these functions:
 *
 * put(key, value) : Insert a (key, value) pair into the HashMap. If the value already exists in the HashMap, update the value.
 * get(key): Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
 * remove(key) : Remove the mapping for the value key if this map contains the mapping for the key.
 *
 * Example:
 * MyHashMap hashMap = new MyHashMap();
 * hashMap.put(1, 1);
 * hashMap.put(2, 2);
 * hashMap.get(1);            // returns 1
 * hashMap.get(3);            // returns -1 (not found)
 * hashMap.put(2, 1);          // update the existing value
 * hashMap.get(2);            // returns 1
 * hashMap.remove(2);          // remove the mapping for 2
 * hashMap.get(2);            // returns -1 (not found)
 *
 * Note:
 *
 * All keys and values will be in the range of [0, 1000000].
 * The number of operations will be in the range of [1, 10000].
 * Please do not use the built-in HashMap library.
 */

/**
 * Analysis:
 * https://cs.stackexchange.com/questions/11029/why-is-it-best-to-use-a-prime-number-as-a-mod-in-a-hashing-function
 * Consider the set of keys K={0,1,...,100} and a hash table where the number of buckets is m=12.
 * Since 3 is a factor of 12, the keys that are multiples of 3 will be hashed to buckets that are multiples of 3:
 *
 * Keys {0,12,24,36,...} will be hashed to bucket 0.
 * Keys {3,15,27,39,...} will be hashed to bucket 3.
 * Keys {6,18,30,42,...} will be hashed to bucket 6.
 * Keys {9,21,33,45,...} will be hashed to bucket 9.
 *
 * If K is uniformly distributed (i.e., every key in K is equally likely to occur),
 * then the choice of m is not so critical. But, what happens if K is not uniformly distributed?
 * Imagine that the keys that are most likely to occur are the multiples of 3.
 * In this case, all of the buckets that are not multiples of 3 will be empty with high probability
 * (which is really bad in terms of hash table performance).
 *
 * This situation is more common that it may seem.
 * Imagine, for instance, that you are keeping track of objects based on where they are stored in memory.
 * If your computer's word size is four bytes, then you will be hashing keys that are multiples of 4.
 * Needless to say that choosing m to be a multiple of 4 would be a terrible choice:
 * you would have 3m/4 buckets completely empty, and all of your keys colliding in the remaining m/4 buckets.
 *
 * In general:
 *
 * Every key in K that shares a common factor with the number of buckets m will be hashed to
 * a bucket that is a multiple of this factor.
 * Therefore, to minimize collisions, it is important to reduce the number of common factors between m and the elements of K.
 * How can this be achieved? By choosing m to be a number that has very few factors: a prime number.
 */
public class DesignHashMap {
    class Pair<U, V>{
        public U first;
        public V second;
        public Pair(U first, V second){
            this.first = first;
            this.second = second;
        }
    }

    class Bucket{
        private List<Pair<Integer, Integer>> bucket;

        public Bucket(){
            //We do not need random access(always need to search) to the list but we need to do delete, which favors LinkedList over ArrayList
            this.bucket = new LinkedList<Pair<Integer, Integer>>();
        }

        public Integer get(Integer key){
            for(Pair<Integer, Integer> pair : this.bucket){
                if(pair.first.equals(key)){
                    return pair.second;
                }
            }
            return -1;
        }

        public void update(Integer key, Integer value){
            for(Pair<Integer, Integer> pair : this.bucket){
                if(pair.first.equals(key)){
                    pair.second = value;
                    return;
                }
            }
            //if reaches here meaning we didn't find the pair, insert new pair.
            this.bucket.add(new Pair<Integer, Integer>(key, value));
        }

        public void remove(Integer key){
            for(Pair<Integer, Integer> pair : this.bucket){
                //Question: why are we not getting concurrentmodificationexception? Because of the "break"?
                if(pair.first.equals(key)){
                    this.bucket.remove(pair);
                    break;
                }
            }
        }
    }

    //this needs to be a large prime number.
    private int bucketSize;
    private List<Bucket> hashMap;
    /** Initialize your data structure here. */
    public DesignHashMap() {
        this.bucketSize = 2069;
        this.hashMap = new ArrayList<>();
        for(int i=0; i<bucketSize; i++){
            hashMap.add(new Bucket());
        }
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        int hashCode = key % bucketSize;
        this.hashMap.get(hashCode).update(key, value);
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int hashCode = key % bucketSize;
        return this.hashMap.get(hashCode).get(key);
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int hashCode = key % bucketSize;
        this.hashMap.get(hashCode).remove(key);
    }
}
