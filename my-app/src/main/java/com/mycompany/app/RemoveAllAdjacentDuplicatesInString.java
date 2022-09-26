package com.mycompany.app;

/**
 * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
 * Given a string S of lowercase letters, a duplicate removal consists of choosing two adjacent and equal letters, and removing them.
 * We repeatedly make duplicate removals on S until we no longer can.
 * Return the final string after all such duplicate removals have been made.  It is guaranteed the answer is unique.
 *
 * Example 1:
 * Input: "abbaca"
 * Output: "ca"
 * Explanation:
 * For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible move.
 * The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is "ca".
 *
 * Note:
 * 1 <= S.length <= 20000
 * S consists only of English lowercase letters.
 */
public class RemoveAllAdjacentDuplicatesInString {
    //用stack
    public String removeDuplicates(String s){
        StringBuilder buf = new StringBuilder();
        for(char c : s.toCharArray()){
            if(buf.length()!=0 && buf.charAt(buf.length()-1)==c){
                buf.setLength(buf.length()-1);
            }
            else{
                buf.append(c);
            }
        }
        return buf.toString();
    }
    //以下这种用recusion的写法最intuitive
    //但是这种写法的
    //Time Complexity: O(n^2). 因为recursion的深度是n/2，然后没一次都是从ptr0==0开始找，所以每次查找是O(n)，总的就是O(n^2)
    //Space Complexity: O(n^2). 因为stack的深度是n/2, 然后每一次都要建一个stringbuilder，所以是O(n),总的是O(n^2)
    public String removeDuplicates2(String S) {
        StringBuilder buf = new StringBuilder();
        boolean found = false;
        int ptr0 = 0;
        int ptr1 = 1;
        while(ptr0<S.length()){
            //注意：1. 每次只处理2个，题目中明确说了（陷阱）2. 注意ptr1不要out of bound.
            if(ptr1<S.length() && S.charAt(ptr0)==S.charAt(ptr1)){
                found = true;
                ptr0 = ptr1+1;
                ptr1 = ptr0+1;
            }
            else{
                buf.append(S.charAt(ptr0));
                ptr0++;
                ptr1++;
            }
        }
        //System.out.println(buf.toString());
        return found ? removeDuplicates2(buf.toString()) : S;
    }

    //Solution 3:
    //可以先把string convert成doubly linked list
    //然后用两个指针ptr0, ptr1, 一前一后紧挨着，从左向右滑动，过程中检测连个Node的值是否相同
    //如果发现两个Node的值相同，那么把这两个Node从List中摘除，同时把ptr0向左移动一次，把ptr1向右移动一次
    //此时两个ptr依然是挨着的，然后重复上述操作，直到ptr1滑行到最右侧
    //这么做要注意：1. head & tail的edge case处理；2. 如何找到处理过的linkedList的head，然后构建string
    //以上只是一种想法，实现起来比较复杂， 不值得。
    //但是这么做的好处就是time and space的complexity都变成了O(n)，因为ptr1永远只在往右走（尽管ptr0有左右滑动），
    //而且ptr1指向的node永远只会参与一次消除操作，有点sliding window的意思
}
