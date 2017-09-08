package finished.array_and_string;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 * Given word1 = “coding”, word2 = “practice”, return 3.
 * Given word1 = "makes", word2 = "coding", return 1.
 * Note:
 * You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 */

/**
 * Analysis:
 * This is one of a kind.
 * I would try to use HashMap, but even that will require N^2 complexity.
 * The key point of this problem is that not all pairs need to be checked.
 * If we think from a Invariant point of view,
 * 1st. Keep track the current min
 * 2nd. When we are moving from left to right, any time a new key is found, we update the key and min,
 * and the old key can be dropped. This essentially saying we are keeping track of the right most keys
 * of the current substring. We don't care about left keys, because they are always farther from the future
 * right side keys.
 *
 * This like the a simplified version of Dynamic Programming.
 *
 */

public class ShortestWordDistance {
    public int shortestDistance(String[] words, String word1, String word2) {
        //Good example how to init pointers off a list.
        int p1=-1, p2=-1, min=Integer.MAX_VALUE;

        for(int i=0; i<words.length; i++){
            //word1!=word2;
            if(words[i].equals(word1)) p1=i;
            if(words[i].equals(word2)) p2=i;
            if(p1>=0 && p2>=0) min=Math.min(min, Math.abs(p1-p2));
        }
        return min;
    }
}
