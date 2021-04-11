package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/time-based-key-value-store/
 * Create a timebased key-value store class TimeMap, that supports two operations.
 *
 * 1. set(string key, string value, int timestamp)
 * Stores the key and value, along with the given timestamp.
 *
 * 2. get(string key, int timestamp)
 * Returns a value such that set(key, value, timestamp_prev) was called previously, with timestamp_prev <= timestamp.
 * If there are multiple such values, it returns the one with the largest timestamp_prev.
 * If there are no values, it returns the empty string ("").
 *
 * Example 1:
 * Input: inputs = ["TimeMap","set","get","get","set","get","get"], inputs = [[],["foo","bar",1],["foo",1],["foo",3],["foo","bar2",4],["foo",4],["foo",5]]
 * Output: [null,null,"bar","bar",null,"bar2","bar2"]
 * Explanation:
 * TimeMap kv;
 * kv.set("foo", "bar", 1); // store the key "foo" and value "bar" along with timestamp = 1
 * kv.get("foo", 1);  // output "bar"
 * kv.get("foo", 3); // output "bar" since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 ie "bar"
 * kv.set("foo", "bar2", 4);
 * kv.get("foo", 4); // output "bar2"
 * kv.get("foo", 5); //output "bar2"
 *
 * Example 2:
 * Input: inputs = ["TimeMap","set","set","get","get","get","get","get"], inputs = [[],["love","high",10],["love","low",20],["love",5],["love",10],["love",15],["love",20],["love",25]]
 * Output: [null,null,null,"","high","high","low","low"]
 *
 * Note:
 * All key/value strings are lowercase.
 * All key/value strings have length in the range [1, 100]
 * The timestamps for all TimeMap.set operations are strictly increasing.
 * 1 <= timestamp <= 10^7
 * TimeMap.set and TimeMap.get functions will be called a total of 120000 times (combined) per test case.
 */

/**
 * 要点在于“timestamps for all TimeMap.set operations are strictly increasing”
 * 如此就等于说每个Map entry里面的list都是sorted by timestamp
 * 所以就可以Binary Search
 */
public class TimeBasedKeyValueStore {
    class Pair{
        String value;
        int timestamp;
        public Pair(String value, int timestamp){
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    Map<String, List<Pair>> map;

    /** Initialize your data structure here. */
    public TimeBasedKeyValueStore() {
        this.map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        Pair pair = new Pair(value, timestamp);
        if(this.map.containsKey(key)){
            List<Pair> list = map.get(key);
            list.add(pair);
        }
        else{
            List<Pair> list = new ArrayList<>();
            list.add(pair);
            map.put(key, list);
        }
    }

    public String get(String key, int timestamp) {
        if(!this.map.containsKey(key)){
            return "";
        }
        else{
            List<Pair> list = this.map.get(key);
            if(list.get(0).timestamp>timestamp){
                return "";
            }
            else if(list.get(list.size()-1).timestamp<=timestamp){
                return list.get(list.size()-1).value;
            }
            else{
                int left = 0;
                int right = list.size()-1;
                String res = "";
                while(left<=right){
                    int mid = (left + right)/2;
                    if(list.get(mid).timestamp==timestamp){
                        res = list.get(mid).value;
                        break;
                    }
                    if(list.get(mid).timestamp<timestamp){
                        if(list.get(mid+1).timestamp<=timestamp){
                            left = mid + 1;
                        }
                        else{
                            res = list.get(mid).value;
                            break;
                        }
                    }
                    else{
                        right = mid - 1;
                    }
                }
                return res;
            }
        }
    }
}
