package Finished;

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by eljian on 8/31/2017.
 */
public class WordLadderTest {
    WordLadder obj = new WordLadder();
    List<String> wordList = new ArrayList<>();
    String beginWord = "hit";
    String endWord = "cog";
    {
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        wordList.add("cog");
    }
    @Test
    public void ladderLength() throws Exception {
        assertEquals(5, obj.ladderLength(beginWord, endWord, wordList));
    }

}