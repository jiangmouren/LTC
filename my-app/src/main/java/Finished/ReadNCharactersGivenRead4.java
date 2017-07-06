package Finished;

/**
 * Question:
 * The API: int read4(char *buf) reads 4 characters at a time from a file.
 * The return value is the actual number of characters read.
 * For example, it returns 3 if there is only 3 characters left in the file.
 * By using the read4 API, implement the function int read(char *buf, int n) that reads n characters from the file.
 * Note:
 * The read function will only be called once for each test case.
 */

/**
 * The only problem with this question is to understand the API.
 */

public class ReadNCharactersGivenRead4 {
    /**
     * So in my API, read4 will overwrite buf contents
     */
    public static class File{
        static StringBuilder buf;
        static int ptr;
        File(String str){
            ptr = 0;
            buf = new StringBuilder();
            buf.append(str);
        }
        public static int read4(char[] buffer){
            int cnt = 0;
            while(ptr<buf.length() && cnt<4){
                buffer[cnt] = buf.charAt(ptr);
                ptr++;
                cnt++;
            }
            return cnt;
        }
    }

    public int read(char[] buf, int n){
        int readLength = 0;
        boolean eof = false;
        while(!eof && readLength<n){
            char[] tmp = new char[4];
            int sz = File.read4(tmp);
            if(sz<4) eof=true;
            int inc = Math.min(sz, n-readLength);
            System.arraycopy(tmp, 0, buf, readLength, inc);
            readLength += inc;
        }
        return readLength;
    }

}
