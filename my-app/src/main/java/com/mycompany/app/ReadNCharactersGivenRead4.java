package com.mycompany.app;

/**
 * Question: https://leetcode.com/problems/read-n-characters-given-read4/
 */

public class ReadNCharactersGivenRead4 {
    public int read(char[] buf, int n) {
        int cnt = 0;
        char[] buf4 = new char[4];
        while(cnt<n){
            int num = read4(buf4);
            int ptr = 0;
            while(ptr<num && cnt<n){
                buf[cnt] = buf4[ptr];
                cnt++;
                ptr++;
            }
            if(num<4){
                break;
            }
        }
        //System.out.println(cnt);
        return cnt;
    }

    //mock read4
    private int read4(char[] buf4){
        return 0;
    }

}
