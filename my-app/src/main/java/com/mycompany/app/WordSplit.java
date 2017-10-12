package com.mycompany.app;

import java.util.*;

/**
 * Created by eljian on 10/6/2017.
 */
public class WordSplit {
    public List<String> split(String str){
        if(str==null){
            throw new IllegalArgumentException("Input cannot be null");
        }
        int ptr = 0;
        List<String> res = new ArrayList<>();
        while(ptr<str.length()){
            int start = getNext(str, ptr);
            ptr = start;
            if(start<str.length()){
                while(ptr<str.length() && str.charAt(ptr)!=' '){
                    ptr++;
                }
                res.add(str.substring(start, ptr));
            }
        }
        return res;
    }

    private int getNext(String str, int start){
        int ptr = start;
        while(ptr<str.length() && str.charAt(ptr)==' '){
            ptr++;
        }
        return ptr;
    }
}
