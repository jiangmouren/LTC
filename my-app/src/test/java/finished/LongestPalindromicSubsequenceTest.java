package finished;

import finished.LongestPalindromicSubsequence;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 9/19/2017.
 */
public class LongestPalindromicSubsequenceTest {
    LongestPalindromicSubsequence obj = new LongestPalindromicSubsequence();
    String s1 = "bbbab";
    String s2 = "cbbd";
    String s3 = "bcbbabc";
    @Test
    public void longestPalindromeSubseq() throws Exception {
        assertEquals(obj.longestPalindromeSubseq(s1), 4);
        assertEquals(obj.longestPalindromeSubseq(s2), 2);
        assertEquals(obj.longestPalindromeSubseq(s3), 5);
    }
}