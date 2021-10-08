package com.mycompany.app.math;

import java.util.*;

/**
 * https://leetcode.com/problems/guess-the-word/
 * This is an interactive problem.
 * You are given an array of unique strings wordlist where wordlist[i] is 6 letters long,
 * and one word in this list is chosen as secret. * You may call Master.guess(word) to guess a word.
 * The guessed word should have type string and must be from the original list with 6 lowercase letters.
 * This function returns an integer type, representing the number of exact matches (value and position)
 * of your guess to the secret word. Also, if your guess is not in the given wordlist, it will return -1 instead.
 * For each test case, you have exactly 10 guesses to guess the word. At the end of any number of calls,
 * if you have made 10 or fewer calls to Master.guess and at least one of these guesses was secret,
 * then you pass the test case.
 *
 * Example 1:
 * Input: secret = "acckzz", wordlist = ["acckzz","ccbazz","eiowzz","abcczz"], numguesses = 10
 * Output: You guessed the secret word correctly.
 * Explanation:
 * master.guess("aaaaaa") returns -1, because "aaaaaa" is not in wordlist.
 * master.guess("acckzz") returns 6, because "acckzz" is secret and has all 6 matches.
 * master.guess("ccbazz") returns 3, because "ccbazz" has 3 matches.
 * master.guess("eiowzz") returns 2, because "eiowzz" has 2 matches.
 * master.guess("abcczz") returns 4, because "abcczz" has 4 matches.
 * We made 5 calls to master.guess and one of them was the secret, so we pass the test case.
 *
 * Example 2:
 * Input: secret = "hamada", wordlist = ["hamada","khaled"], numguesses = 10
 * Output: You guessed the secret word correctly.
 *
 * Constraints:
 * 1 <= wordlist.length <= 100
 * wordlist[i].length == 6
 * wordlist[i] consist of lowercase English letters.
 * All the strings of wordlist are unique.
 * secret exists in wordlist.
 * numguesses == 10
 */
public class GuessTheWord {
    interface Master{
        public int guess(String word);
    }
    //Solution 1: Random Guess
    public void findSecretWord(String[] wordList, Master master){
        int x = 0;
        for(int i=0; i<10 && x<6; i++){
            int random = new Random().nextInt(wordList.length);
            String guess = wordList[random];
            x = master.guess(guess);
            List<String> list = new ArrayList<>();
            for(String word : wordList){
                if(match(word, guess)==x){
                    list.add(word);
                }
            }
            wordList = list.toArray(new String[0]);
        }
    }

    private int match(String a, String b){
        int matches = 0;
        //We already know all the words are of the same length, so no need to worry about index out of bound for b
        for(int i=0; i<a.length(); i++){
            if(a.charAt(i)==b.charAt(i)){
                matches++;
            }
        }
        return matches;
    }

    //Solution 2: Greedy based on statistics
    //概率上来说，在wordList中任意两个word，有7中match可能性，match 0, 1, 2, 3, 5, 6
    //其中match_0的概率最大：(25/26)^6; match_6的概率最低：(1/26)^6
    //这种情况下就考虑如何在match_0的时候做最优选择，就是如果match_0，留下的candidate最少
    //所以preprocess wordList，从match_0最少的一个开始猜。
    public void findSecretWordGreedy(String[] wordList, Master master){
        int x = 0;

        for(int i=0; i<10; i++){//这个i只是用来控制guess次数的，不是用来Index anything的
            List<Group> guessList = sort(wordList);
            Group guess = guessList.get(0);
            x = master.guess(guess.word);
            if(x==6){
                break;
            }
            Set<String> set = new HashSet<>();
            if(x==0){
                set = guess.set;
            }
            else{
                for(String word : wordList){
                    if(!guess.set.contains(word)){
                        set.add(word);
                    }
                }
                //记得把当前的guess排除
                set.remove(guess.word);
            }

            //guessList now remains sorted
            wordList = set.toArray(new String[0]);
        }
    }

    private List<Group> sort(String[] wordList){
        List<Group> res = new ArrayList<>();
        for(int i=0; i<wordList.length; i++){
            Group group = new Group(wordList[i]);
            group.set = new HashSet<>();
            res.add(group);
        }

        for(int i=0; i<wordList.length; i++){
            for(int j=i+1; j<wordList.length; j++){
                int x = match(wordList[i], wordList[j]);
                if(x==0){
                    res.get(i).set.add(wordList[j]);
                    res.get(j).set.add(wordList[i]);
                }
            }
        }

        Collections.sort(res, (a, b)->a.set.size()-b.set.size());
        return res;
    }

    class Group{
        String word;
        Set<String> set;
        public Group(String word){
            this.word = word;
            this.set = new HashSet<>();
        }
    }
}
