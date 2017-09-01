package Finished;
/**
 * Given an input string, reverse the string word by word.
 * For example,
 * Given s = "the sky is blue",
 * return "blue is sky the".
 * Update (2015-02-12):
 * For C programmers: Try to solve it in-place in O(1) space.
 * click to show clarification.
 * Clarification:
 * What constitutes a word?
 * A sequence of non-space characters constitutes a word.
 * Could the input string contain leading or trailing spaces?
 * Yes. However, your reversed string should not contain leading or trailing spaces.
 * How about multiple spaces between two words?
 * Reduce them to a single space in the reversed string.
 */

/**
 * Analysis: Take care of edge cases.
 * First reverse all characters then reverse char in every single word. Play with 2 pointers.
 * Idea is easy, but not easy to write it right to cover all corner cases.
 */

public class ReverseWordsInAString {
    public String reverseWords(String s) {
        StringBuilder buf = new StringBuilder();
        int ptr1 = -1;
        PreSpace preSpace1 = new PreSpace(true);
        while(ptr1<s.length()){
            ptr1 = nextChar(ptr1, s, buf, preSpace1);
        }
        //it is very hard to find this trailing space check without debugging
        if(buf.charAt(buf.length()-1)==' '){
            buf.deleteCharAt(buf.length()-1);
        }
        swap(buf, 0, buf.length()-1);
        ptr1 = 0;
        int ptr2 = 0;
        while(ptr1<buf.length()){
            ptr2 = nextSpace(ptr2, buf);
            swap(buf, ptr1, ptr2-1);
            ptr1 = ptr2 + 1;
        }
        return buf.toString();
    }

    //find next valid char, including space; set the preSpace value; and remove invalid spaces
    private int nextChar(int ptr, String s, StringBuilder buf, PreSpace preSpace){
        ptr++;
        if(preSpace.value){
            while(ptr<s.length() && s.charAt(ptr)==' '){
                ptr++;
            }
        }
        if(ptr<s.length()){
            preSpace.value = (s.charAt(ptr) == ' ');
            buf.append(s.charAt(ptr));
        }
        return ptr;
    }

    //swap chars
    private void swap(StringBuilder buf, int ptr1, int ptr2){
        while(ptr1<ptr2){
            char tmp = buf.charAt(ptr1);
            buf.setCharAt(ptr1, buf.charAt(ptr2));
            buf.setCharAt(ptr2, tmp);
            ptr1++;
            ptr2--;
        }
    }

    //find next space
    private int nextSpace(int ptr, StringBuilder buf){
        ptr++;
        while(ptr<buf.length() && buf.charAt(ptr)!=' '){
            ptr++;
        }
        return ptr;
    }

    //helper class to carry preSpace info
    private class PreSpace{
        boolean value;
        PreSpace(boolean value){
            this.value = value;
        }
    }
}
