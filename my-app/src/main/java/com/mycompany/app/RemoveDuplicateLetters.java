package com.mycompany.app;
import java.util.*;
/**
 * Given a string which contains only lowercase letters,
 * remove duplicate letters so that every letter appear once and only once.
 * You must make sure your result is the smallest in lexicographical order among all possible results.

Example:
Given "bcabc"
Return "abc"

Given "cbacdcbc"
Return "acdb"
 */

class RemoveDuplicatedLetters {
    //这个问题最好的解法是用Mono-Stack
    //我要尽可能把小的留在前面，同时我要保证最后一次出现的不能被删掉，这两个条件就构成了我Mono-Stack Update的规则
    public String removeDuplicateLetters(String s){
        Map<Character, Integer> map = new HashMap<>();//need a map to record last occur position
        for(int i=0; i<s.length(); i++){
            map.put(s.charAt(i), i);
        }
        Set<Character> set = new HashSet<>(); //need a set to record if we have already take this letter
        Stack<Character> stack = new Stack<>();
        for(int i=0; i<s.length(); i++){
            if(!set.contains(s.charAt(i))){
                while(!stack.isEmpty() && stack.peek()>s.charAt(i) && map.get(stack.peek())>i){
                    char c = stack.pop();
                    set.remove(c);
                }
                stack.push(s.charAt(i));
                set.add(s.charAt(i));
            }
        }
        StringBuilder buf = new StringBuilder();
        //下面这种enhanced for loop 出来stack竟然是按照FIFO的顺序出来的，这是一个java的known bug，不应该利用这点来避免reverse:
        //https://www.techiedelight.com/potential-bug-stack-class-workaround/
        //for(char c : stack){
        //    buf.append(c);
        //}
        while(!stack.isEmpty()){
            buf.append(stack.pop());
        }
        buf.reverse();
        return buf.toString();
    }

    //核心思路就是：从左到右找到第一个不能drop的letter，也就是最后一次occur的
    //这这个过程中记录最小的那个，那么那个就是我本次search要留下的letter
    //从那个位置往后，把本次留下的letter剔除，剩下的就是一个新的subproblem
    //下面这个recursion解法的time complexity O(n), space complexity O(n)
    //space的复杂度这么理解：最多26个字母，所以最多recurse 26次，然后每次生成一个新的string,所以总的复杂度是O(n)
    public String removeDuplicateLettersRecur(String s) {
        //System.out.println(s);
        if(s.length()==0){
            return s;
        }
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0; i<s.length(); i++){
            if(!map.containsKey(s.charAt(i))){
                map.put(s.charAt(i), 0);
            }
            int cnt = map.get(s.charAt(i));
            cnt++;
            map.put(s.charAt(i), cnt);
        }
        int pos = 0;//smallest char position
        int ptr = 0;
        while(ptr<s.length()){
            if(s.charAt(ptr)<s.charAt(pos)){
                pos = ptr;
            }
            //System.out.println("ptr: "+ptr);
            int cnt = map.get(s.charAt(ptr));
            //System.out.println("cnt: "+cnt);
            cnt--;
            map.put(s.charAt(ptr), cnt);
            if(cnt==0){
                break;
            }
            ptr++;
        }
        //System.out.println(pos);
        return s.charAt(pos) + removeDuplicateLettersRecur(s.substring(pos+1).replaceAll(""+s.charAt(pos), ""));
    }
}
