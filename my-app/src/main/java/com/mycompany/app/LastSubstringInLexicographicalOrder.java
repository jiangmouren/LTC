package com.mycompany.app;

/**
 * https://leetcode.com/problems/last-substring-in-lexicographical-order/
 * Given a string s, return the last substring of s in lexicographical order.
 *
 * Example 1:
 * Input: s = "abab"
 * Output: "bab"
 * Explanation: The substrings are ["a", "ab", "aba", "abab", "b", "ba", "bab"]. The lexicographically maximum substring is "bab".
 *
 * Example 2:
 * Input: s = "leetcode"
 * Output: "tcode"
 *
 * Constraints:
 * 1 <= s.length <= 4 * 105
 * s contains only lowercase English letters.
 */

/**
 * Analysis:
 * 1. 要注意到可能的解一定是input string的suffix
 * 2. 就是要在所有的suffix里面寻找lexicographically最大的一个
 * 最简单的想法就是我下面的lastSubstringNaive，这种写法的复杂度为O(n^2)，因为没一次生成substring和string的比较都需要O(n).
 * 另外一种错误的想法就是找到input string里面最大的char的最靠前的位置k，那么s[k:]就是解。
 * 这种想法是错误的，因为cacb来说，cb是大于cacb的。
 * 有上面的错误可以想到一个改进的解法就是，先把最大的char的位置都找出来，然后开始成对的比较s[k1:] s[k2:]
 * 因为最大的char的个数是O(n)，而上述每一对的比较也是O(n)，所以这种改进的解法，复杂度依然是O(n^2)
 * 于是参考了下面的解法：
 * https://leetcode.com/problems/last-substring-in-lexicographical-order/discuss/363662/Short-python-code-O(n)-time-and-O(1)-space-with-proof-and-visualization
 * 上面链接的代码是正确的，但是解释很confusing，解释如下：
 * 从2个挨着的ptr开始，目标是：ptr1前面(i<ptr1)的都是被筛选过的candidates，ptr0指向所有被筛选过的candidates里面的suffix最大的位置。
 * 搜索开始：
 * 如果，s[ptr1]<s[ptr0]，ptr1指向下一个，因为第一个char都小，所以不可能是解
 * 直到找到s[ptr1]==s[ptr0] || s[ptr1]>s[ptr0]的地方。
 * 如果s[ptr1]>s[ptr0]，那么ptr0 = ptr1, ptr1 = ptr0 + 1. 以上的过程类似于我在第二种解法里面的寻找最大的char的思路。
 * 如果s[ptr1]==s[ptr0]，这种情况就类似于我上面第二种解法需要处理其实字母相同的情况的suffix的比较。
 * 这种情况，就look ahead，直到找到diff，然后分类讨论：
 * src\main\resources\LastSubstringLexicographicalOrder.jpg
 * 除了上图中的的性质之外，还有就是图中“红1”部分与“绿1”部分相同；“红2”部分与“绿2“部分相同；
 * 而“红2”部分又与“绿1”起始部分相同，所以“红2”就是在重复“红1”的其实部分，“绿2”也是
 * 所以整个s[ptr1:ptr1+k]都是在repeat s[ptr0:ptr1]中的内容。
 *
 */
public class LastSubstringInLexicographicalOrder {

    public String lastSubstring(String s) {
        int ptr0 = 0;
        int ptr1 = 1;
        int k = 0;
        int n = s.length();
        while(ptr1+k<n){
            if(s.charAt(ptr0+k)==s.charAt(ptr1+k)){
                k++;
            }
            else if(s.charAt(ptr0+k)>s.charAt(ptr1+k)){
                ptr1 = ptr1+k+1;
                k=0;
            }
            else{
                ptr0 = Math.max(ptr0+k+1, ptr1);
                ptr1 = ptr0 + 1;
                k=0;
            }
        }
        return s.substring(ptr0, s.length());
    }

    public String lastSubstringNaive(String s) {
        int n = s.length();
        String maxString = String.valueOf(s.charAt(n-1));
        for(int i=n-2; i>=0; i--){
            String temp = s.substring(i, n);
            if(maxString.compareTo(temp)<0){
                maxString = temp;
            }
        }
        return maxString;
    }

    public String lastSubstringWrong(String s) {
        int[] map = new int[26];
        for(int i=0; i<26; i++){
            map[i] = -1;
        }
        for(int i=s.length()-1; i>=0; i--){
            char c = s.charAt(i);
            map[c-'a'] = i;
        }
        int start = 0;
        for(int i=25; i>=0; i--){
            if(map[i]!=-1){
                start = map[i];
                break;
            }
        }
        System.out.println(start);
        return s.substring(start, s.length());
    }
}
