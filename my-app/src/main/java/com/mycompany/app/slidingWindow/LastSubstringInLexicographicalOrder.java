package com.mycompany.app.slidingWindow;

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
 *
 * 于是参考了下面的解法：
 * https://leetcode.com/problems/last-substring-in-lexicographical-order/discuss/363662/Short-python-code-O(n)-time-and-O(1)-space-with-proof-and-visualization
 * 上面链接的代码是正确的，但是解释很confusing，解释如下：
 * 从2个挨着的ptr开始，目标是：ptr1前面(i<ptr1)的都是被筛选过的candidates，ptr0指向所有被筛选过的candidates里面的suffix最大的位置。
 * 搜索开始：
 * 如果，s[ptr1]<s[ptr0]，ptr1指向下一个，因为第一个char都小，所以不可能是解
 * 直到找到s[ptr1]==s[ptr0] || s[ptr1]>s[ptr0]的地方。
 * 如果s[ptr1]>s[ptr0]，那么ptr0 = ptr1, ptr1 = ptr0 + 1.
 * 如果s[ptr1]==s[ptr0]，这种情况，就look ahead，直到找到diff，然后分类讨论：
 * src\main\resources\LastSubstringLexicographicalOrder.jpg
 * 除了上图中的的性质之外，还有就是图中“1”部分与“3”部分相同；“2”部分与“4“部分相同；
 * 在s.charAt(ptr0+k)<s.charAt(ptr1+k)的上图中的b类情况下，[ptr1, ptr0+k]部分是符合被drop的？
 * 只要把图中的'2'部分跟'4'部分比较，就会发现'4'部分是更优选择，所以'2'部分可以被drop掉。
 * 而s[ptr0+k+1]是有可能大于s[ptr1+k]的所以‘3’-‘2’那一段不能drop
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
                k=0;//注意reset offset everytime ptr0/ptr1 changes
            }
            else{
                ptr0 = Math.max(ptr0+k+1, ptr1);
                ptr1 = ptr0 + 1;
                k=0;//注意reset offset
            }
        }
        return s.substring(ptr0, n);
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
}
