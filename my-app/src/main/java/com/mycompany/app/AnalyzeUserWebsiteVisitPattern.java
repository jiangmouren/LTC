package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/analyze-user-website-visit-pattern/
 * We are given some website visits: the user with name username[i] visited the website website[i] at time timestamp[i].
 * A 3-sequence is a list of websites of length 3 sorted in ascending order by the time of their visits.
 * (The websites in a 3-sequence are not necessarily distinct.)
 * Find the 3-sequence visited by the largest number of users. If there is more than one solution,
 * return the lexicographically smallest such 3-sequence.
 *
 *
 *
 * Example 1:
 * Input: username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"],
 * timestamp = [1,2,3,4,5,6,7,8,9,10],
 * website = ["home","about","career","home","cart","maps","home","home","about","career"]
 * Output: ["home","about","career"]
 * Explanation:
 * The tuples in this example are:
 * ["joe", 1, "home"]
 * ["joe", 2, "about"]
 * ["joe", 3, "career"]
 * ["james", 4, "home"]
 * ["james", 5, "cart"]
 * ["james", 6, "maps"]
 * ["james", 7, "home"]
 * ["mary", 8, "home"]
 * ["mary", 9, "about"]
 * ["mary", 10, "career"]
 * The 3-sequence ("home", "about", "career") was visited at least once by 2 users.
 * The 3-sequence ("home", "cart", "maps") was visited at least once by 1 user.
 * The 3-sequence ("home", "cart", "home") was visited at least once by 1 user.
 * The 3-sequence ("home", "maps", "home") was visited at least once by 1 user.
 * The 3-sequence ("cart", "maps", "home") was visited at least once by 1 user.
 *
 *
 * Note:
 * 3 <= N = username.length = timestamp.length = website.length <= 50
 * 1 <= username[i].length <= 10
 * 0 <= timestamp[i] <= 10^9
 * 1 <= website[i].length <= 10
 * Both username[i] and website[i] contain only lowercase characters.
 * It is guaranteed that there is at least one user who visited at least 3 websites.
 * No user visits two websites at the same time.
 *
 * [158931262;562600350;148438945]
 */

/**
 * 整体上说这是一道偏engineering的题目，并不是特别的算法。
 * 比较讨厌的是这个题目说饿很不清楚。
 * 首先什么东西构成一个3-sequence? 同一个用户，前后3次访问的网址构成一个3-sequence.
 * 然后题目给人一种错觉就是给定的3个array是按照timestamp排序，并按照username聚类过的，其实并没有。
 * 更一般性的输入如下：
 * username = ["h","eiy","cq","h","cq","txldsscx","cq","txldsscx","h","cq","cq"]
 * timestamp = [527896567,334462937,517687281,134127993,859112386,159548699,51100299,444082139,926837079,317455832,411747930]
 * website = ["hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","yljmntrclw","hibympufi","yljmntrclw"]
 */
public class AnalyzeUserWebsiteVisitPattern {
    class Touple{
        String username;
        int timestamp;
        String website;
    }

    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        int n = username.length;
        Touple[] touples = new Touple[n];
        for(int i=0; i<n; i++){
            touples[i] = new Touple();
            touples[i].timestamp = timestamp[i];
            touples[i].username = username[i];
            touples[i].website = website[i];
        }
        Arrays.sort(touples, (a, b)->a.timestamp-b.timestamp);

        Map<String, List<String>> visitMap = new HashMap<>();
        for(Touple touple : touples){
            String key = touple.username;
            if(visitMap.containsKey(key)){
                visitMap.get(key).add(touple.website);
            }
            else{
                List<String> list = new ArrayList<>();
                list.add(touple.website);
                visitMap.put(key, list);
            }
        }

        List<String> buf = new ArrayList<>();
        Map<List<String>, Integer> map = new HashMap<>();
        for(Map.Entry<String,List<String>> entry : visitMap.entrySet()){
            Set<List<String>> set = new HashSet<>();
            backtracking(entry.getValue(), 0, entry.getValue().size(), set, buf);
            for(List<String> list : set){
                if(map.containsKey(list)){
                    int cnt = map.get(list);
                    map.put(list, ++cnt);
                }
                else{
                    map.put(list, 1);
                }
            }
        }

        Set<Map.Entry<List<String>,Integer>> entrySet = map.entrySet();
        List<String> res = null;
        int max = 0;
        for(Map.Entry<List<String>,Integer> entry : entrySet){
            if(entry.getValue()>max){
                max = entry.getValue();
                res = entry.getKey();
            }
            else if(entry.getValue()==max && compare(res, entry.getKey())>0){//res > entry.getKey()
                res = entry.getKey();
            }
        }
        return res;
    }

    //right-left>=3 to call this function, if not, nothing will happen, so no need to check before calling
    private void backtracking(List<String> website, int left, int right, Set<List<String>> set, List<String> buf){
        //termination
        if(buf.size()>=3){
            List<String> key = new ArrayList<>();
            key.addAll(buf);
            set.add(key);
            return;
        }

        int rightBnd = right - (2 - buf.size()); //right is not inclusive
        for(int i=left; i<rightBnd; i++){//left inclusive
            buf.add(website.get(i));
            //注意这里不做swap，因为要遵循time order，被用过的后面就不能再用了
            backtracking(website, i+1, right, set, buf);
            buf.remove(buf.size()-1);
        }
        return;
    }

    //return neg if list1<list2
    private int compare(List<String> list1, List<String> list2){
        int cmp = 0;
        for(int i=0; i<3; i++){
            int temp = list1.get(i).compareTo(list2.get(i));
            if(temp != 0){
                cmp = temp;
                break;
            }
        }
        return cmp;
    }
}
