package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/text-justification/submissions/
 * Given an array of words and a width maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.
 * You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.
 * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
 * For the last line of text, it should be left justified and no extra space is inserted between words.
 *
 * Note:
 * A word is defined as a character sequence consisting of non-space characters only.
 * Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
 * The input array words contains at least one word.
 *
 * Example 1:
 * Input: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
 * Output:
 * [
 *    "This    is    an",
 *    "example  of text",
 *    "justification.  "
 * ]
 *
 * Example 2:
 * Input: words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
 * Output:
 * [
 *   "What   must   be",
 *   "acknowledgment  ",
 *   "shall be        "
 * ]
 * Explanation: Note that the last line is "shall be    " instead of "shall     be", because the last line must be left-justified instead of fully-justified.
 * Note that the second line is also left-justified becase it contains only one word.
 *
 * Example 3:
 * Input: words = ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"], maxWidth = 20
 * Output:
 * [
 *   "Science  is  what we",
 *   "understand      well",
 *   "enough to explain to",
 *   "a  computer.  Art is",
 *   "everything  else  we",
 *   "do                  "
 * ]
 *
 * Constraints:
 * 1 <= words.length <= 300
 * 1 <= words[i].length <= 20
 * words[i] consists of only English letters and symbols.
 * 1 <= maxWidth <= 100
 * words[i].length <= maxWidth
 *
 */

class TestJustification{
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        List<String> buf = new ArrayList<>();
        int cnt = 0;
        for(String word : words){
            int cntNxt = buf.size()>0 ? cnt+1+word.length() : cnt+word.length();
            if(cntNxt<=maxWidth){
                if(buf.size()>0){
                    buf.add(" "+word);
                    cnt = cnt + 1 + word.length();
                }
                else{
                    buf.add(word);
                    cnt = cnt + word.length();
                }
            }
            else{
                //take line from buf
                String temp = pad(buf, maxWidth-cnt, false);
                res.add(temp);
                //reset and start and new line
                cnt = 0;
                buf.clear();
                buf.add(word);
                cnt = word.length();
            }
        }
        //handle last line
        String temp = pad(buf, maxWidth-cnt, true);
        res.add(temp);
        return res;
    }

    //check when cnt==0 should also work
    private String pad(List<String> list, int cnt, boolean last){
        StringBuilder builder = new StringBuilder();
        if(list.size()==1 || last){
            String spaces = getSpaces(cnt);
            for(String token : list){
                builder.append(token);
            }
            builder.append(spaces);
            return builder.toString();
        }
        else{
            int slotsCnt = list.size()-1;
            int extra = cnt % slotsCnt;
            int ave = (cnt-extra)/slotsCnt;
            builder.append(list.get(0));
            for(int i=1; i<list.size(); i++){
                String spaces = (i<=extra) ? getSpaces(ave+1) : getSpaces(ave);
                builder.append(spaces);
                builder.append(list.get(i));
            }
            return builder.toString();
        }
    }

    private String getSpaces(int cnt){
        StringBuilder buf = new StringBuilder();
        for(int i=0; i<cnt; i++){
            buf.append(" ");
        }
        return buf.toString();
    }
}
