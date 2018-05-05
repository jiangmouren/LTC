package com.mycompany.app;

import com.mycompany.app.Trie;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 8/18/2017.
 */
public class TrieTest {
    Trie obj = new Trie();
    @Test
    public void test() throws Exception {
        obj.insert("word");
        assertTrue(obj.search("word"));
        assertTrue(obj.startsWith("wor"));
        assertFalse(obj.search("ford"));
    }

}