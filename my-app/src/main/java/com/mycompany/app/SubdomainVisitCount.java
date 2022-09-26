package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/subdomain-visit-count/
 * A website domain like "discuss.leetcode.com" consists of various subdomains.
 * At the top level, we have "com", at the next level, we have "leetcode.com",
 * and at the lowest level, "discuss.leetcode.com".
 * When we visit a domain like "discuss.leetcode.com", we will also visit the parent domains "leetcode.com" and "com" implicitly.
 * Now, call a "count-paired domain" to be a count (representing the number of visits this domain received),
 * followed by a space, followed by the address. An example of a count-paired domain might be "9001 discuss.leetcode.com".
 *
 * We are given a list cpdomains of count-paired domains. We would like a list of count-paired domains,
 * (in the same format as the input, and in any order), that explicitly counts the number of visits to each subdomain.
 *
 * Example 1:
 * Input:
 * ["9001 discuss.leetcode.com"]
 * Output:
 * ["9001 discuss.leetcode.com", "9001 leetcode.com", "9001 com"]
 * Explanation:
 * We only have one website domain: "discuss.leetcode.com". As discussed above, the subdomain "leetcode.com" and "com" will also be visited. So they will all be visited 9001 times.
 *
 * Example 2:
 * Input:
 * ["900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"]
 * Output:
 * ["901 mail.com","50 yahoo.com","900 google.mail.com","5 wiki.org","5 org","1 intel.mail.com","951 com"]
 * Explanation:
 * We will visit "google.mail.com" 900 times, "yahoo.com" 50 times, "intel.mail.com" once and "wiki.org" 5 times. For the subdomains, we will visit "mail.com" 900 + 1 = 901 times, "com" 900 + 50 + 1 = 951 times, and "org" 5 times.
 *
 * Notes:
 * The length of cpdomains will not exceed 100.
 * The length of each domain name will not exceed 100.
 * Each address will have either 1 or 2 "." characters.
 * The input count in any count-paired domain will not exceed 10000.
 * The answer output can be returned in any order.
 */

//主要注意细节，一个是regex在split()里面“.”用“\\.”
//再就是注意顺序要反一下
public class SubdomainVisitCount {
    public List<String> subdomainVisits(String[] cpdomains) {
        Map<String, Integer> map = new HashMap<>();
        for(String str : cpdomains){
            String[] tokens = str.split(" ");
            int cnt = Integer.parseInt(tokens[0]);
            String[] segments = tokens[1].split("\\.");//这个地方注意要escape"."需要用到“\\.”
            List<String> buf = new ArrayList<>();
            for(int i=segments.length-1; i>=0; i--){
                String segment = segments[i];
                buf.add(segment);
                Collections.reverse(buf);//这里需要按词把顺序反一下，所以用List做buf，而不是StringBuilder比较顺手。
                String key = String.join(".", buf);//当只有"com"一个element的时候，join出来还是其自己
                Collections.reverse(buf);
                if(map.containsKey(key)){
                    map.put(key, cnt+map.get(key));
                }
                else{
                    map.put(key, cnt);
                }
            }
        }
        List<String> res = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            res.add(entry.getValue() + " " + entry.getKey());
        }
        return res;
    }
}
