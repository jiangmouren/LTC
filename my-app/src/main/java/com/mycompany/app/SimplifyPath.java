package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/simplify-path/
 * Given a string path, which is an absolute path (starting with a slash '/') to a file or directory in a Unix-style file system, convert it to the simplified canonical path.
 *
 * In a Unix-style file system, a period '.' refers to the current directory, a double period '..' refers to the directory up a level, and any multiple consecutive slashes (i.e. '//') are treated as a single slash '/'. For this problem, any other format of periods such as '...' are treated as file/directory names.
 *
 * The canonical path should have the following format:
 *
 * The path starts with a single slash '/'.
 * Any two directories are separated by a single slash '/'.
 * The path does not end with a trailing '/'.
 * The path only contains the directories on the path from the root directory to the target file or directory (i.e., no period '.' or double period '..')
 * Return the simplified canonical path.
 *
 * Example 1:
 * Input: path = "/home/"
 * Output: "/home"
 * Explanation: Note that there is no trailing slash after the last directory name.
 *
 * Example 2:
 * Input: path = "/../"
 * Output: "/"
 * Explanation: Going one level up from the root directory is a no-op, as the root level is the highest level you can go.
 *
 * Example 3:
 * Input: path = "/home//foo/"
 * Output: "/home/foo"
 * Explanation: In the canonical path, multiple consecutive slashes are replaced by a single one.
 *
 * Example 4:
 * Input: path = "/a/./b/../../c/"
 * Output: "/c"
 *
 * Constraints:
 * 1 <= path.length <= 3000
 * path consists of English letters, digits, period '.', slash '/' or '_'.
 * path is a valid absolute Unix path.
 */

public class SimplifyPath {
    //用一个stack可以用类似处理括号的方式处理path
    //实际使用一个list,方便后面reconstruct path
    public String simplifyPath(String path) {
        List<String> list = new ArrayList<>();
        for(int i=0; i<path.length(); i++){
            if(path.charAt(i)=='/'){
                int ptr = i;
                while(ptr<path.length() && path.charAt(ptr)=='/'){
                    ptr++;
                }
                i = ptr - 1;
                //有可能list最后一个也是一个"/"，因为之前可能刚好跳过一个'.'，或者".."
                if(list.size()>0 && list.get(list.size()-1).equals("/")){
                    list.remove(list.size()-1);
                }
                list.add("/");
            }
            else{
                int ptr = i;
                while(ptr<path.length() && path.charAt(ptr)!='/'){
                    ptr++;
                }
                //注意这里要拿出一个token，之后在判断，而不能简单根据path.charAt(i)是'.', or alphabetic来判断，
                //因为..home..会被当成valid directory name
                String token = path.substring(i, ptr);
                i = ptr - 1;
                if(token.equals("..")){
                    for(int j=0; j<2; j++){
                        if(list.size()>1){
                            list.remove(list.size()-1);
                        }
                    }
                }
                else if(token.equals(".")){
                    continue;
                }
                else{
                    list.add(token);
                }
            }
        }
        if(list.size()>1 && list.get(list.size()-1).equals("/")){
            list.remove(list.size()-1);
        }

        return String.join("", list);
    }
}