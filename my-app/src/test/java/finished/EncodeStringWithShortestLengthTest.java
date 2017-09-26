package finished;

import finished.EncodeStringWithShortestLength;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 9/17/2017.
 */
public class EncodeStringWithShortestLengthTest {
    EncodeStringWithShortestLength obj = new EncodeStringWithShortestLength();
    String s1 = "aaa";
    String s2 = "aaaaa";
    String s3 = "aaaaaaaaaa";
    String s4 = "aabcaabcd";
    String s5 = "abbbabbbcabbbabbbc";

    @Test
    public void encode() throws Exception {
        System.out.println(obj.encode(s1));
        System.out.println(obj.encode(s2));
        System.out.println(obj.encode(s3));
        System.out.println(obj.encode(s4));
        System.out.println(obj.encode(s5));
    }
}