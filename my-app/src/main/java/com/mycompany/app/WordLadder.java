package com.mycompany.app;
/**
 * Given two words (beginWord and endWord), and a dictionary's word list,
 * find the length of shortest transformation sequence from beginWord to endWord, such that:
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 * For example,
 * Given:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log","cog"]
 * As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 * Note:
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 * UPDATE (2017/1/20):
 * The wordList parameter had been changed to a list of strings (instead of a set of strings).
 * Please reload the code definition to get the latest changes.
 */

/**
 * Analysis:
 * A good backtracking problem.
 * There are two ways to write it.
 * At every step, you change one bit of at a time and see if the result is in the dictionary or not.
 * This way requires we put the wordlist into a Set.
 * Another way at every step, we loop through the wordlist and try to find a dif by one word, until find the target.
 * In the first approach, the complexity is (26^k)*k!, because at 1st step we have 26k options, next step
 * will have 26(k-1), then 26(k-2), total (26^k)*k!.
 * In the second approach, the complexity is n!. Because at 1st step, n options and then n-1, then n-2, total n!.
 */

/**
 * New Analysis:
 * In the above analysis, the second approach is actually not a backtracking, but a DFS.
 * But actually even the first approach can be viewed as a DFS.
 * However, to find the shortest distance, we should use BFS.
 * So essentially this is not a Backtracking problem, this should be a tree BFS problem.
 * A word is a node, if diff by one char, that is a connection.
 * Start word is the root, target word is the node, you want to find.
 * The topology is a tree structure, meaning there is not loop.
 * Meaning if A is connected to B directly, then that's the only possible connection they have.
 * TODO: BFS
 */

import java.util.*;

public class WordLadder {
    public static void main(String[] args){
        WordLadder instance = new WordLadder();
        String beginWord = "cet";
        String endWord = "ism";
        List<String> wordList = new ArrayList<>(Arrays.asList("kid","tag","pup",
                "ail","tun","woo","erg","luz","brr","gay","sip","kay","per","val",
                "mes","ohs","now","boa","cet","pal","bar","die","war","hay","eco",
                "pub","lob","rue","fry","lit","rex","jan","cot","bid","ali","pay",
                "col","gum","ger","row","won","dan","rum","fad","tut","sag","yip",
                "sui","ark","has","zip","fez","own","ump","dis","ads","max","jaw",
                "out","btu","ana","gap","cry","led","abe","box","ore","pig","fie",
                "toy","fat","cal","lie","noh","sew","ono","tam","flu","mgm","ply",
                "awe","pry","tit","tie","yet","too","tax","jim","san","pan","map",
                "ski","ova","wed","non","wac","nut","why","bye","lye","oct","old",
                "fin","feb","chi","sap","owl","log","tod","dot","bow","fob","for",
                "joe","ivy","fan","age","fax","hip","jib","mel","hus","sob","ifs",
                "tab","ara","dab","jag","jar","arm","lot","tom","sax","tex","yum",
                "pei","wen","wry","ire","irk","far","mew","wit","doe","gas","rte",
                "ian","pot","ask","wag","hag","amy","nag","ron","soy","gin","don",
                "tug","fay","vic","boo","nam","ave","buy","sop","but","orb","fen",
                "paw","his","sub","bob","yea","oft","inn","rod","yam","pew","web",
                "hod","hun","gyp","wei","wis","rob","gad","pie","mon","dog","bib",
                "rub","ere","dig","era","cat","fox","bee","mod","day","apr","vie",
                "nev","jam","pam","new","aye","ani","and","ibm","yap","can","pyx",
                "tar","kin","fog","hum","pip","cup","dye","lyx","jog","nun","par",
                "wan","fey","bus","oak","bad","ats","set","qom","vat","eat","pus",
                "rev","axe","ion","six","ila","lao","mom","mas","pro","few","opt",
                "poe","art","ash","oar","cap","lop","may","shy","rid","bat","sum",
                "rim","fee","bmw","sky","maj","hue","thy","ava","rap","den","fla",
                "auk","cox","ibo","hey","saw","vim","sec","ltd","you","its","tat",
                "dew","eva","tog","ram","let","see","zit","maw","nix","ate","gig",
                "rep","owe","ind","hog","eve","sam","zoo","any","dow","cod","bed",
                "vet","ham","sis","hex","via","fir","nod","mao","aug","mum","hoe",
                "bah","hal","keg","hew","zed","tow","gog","ass","dem","who","bet",
                "gos","son","ear","spy","kit","boy","due","sen","oaf","mix","hep",
                "fur","ada","bin","nil","mia","ewe","hit","fix","sad","rib","eye",
                "hop","haw","wax","mid","tad","ken","wad","rye","pap","bog","gut",
                "ito","woe","our","ado","sin","mad","ray","hon","roy","dip","hen",
                "iva","lug","asp","hui","yak","bay","poi","yep","bun","try","lad",
                "elm","nat","wyo","gym","dug","toe","dee","wig","sly","rip","geo",
                "cog","pas","zen","odd","nan","lay","pod","fit","hem","joy","bum",
                "rio","yon","dec","leg","put","sue","dim","pet","yaw","nub","bit",
                "bur","sid","sun","oil","red","doc","moe","caw","eel","dix","cub",
                "end","gem","off","yew","hug","pop","tub","sgt","lid","pun","ton",
                "sol","din","yup","jab","pea","bug","gag","mil","jig","hub","low",
                "did","tin","get","gte","sox","lei","mig","fig","lon","use","ban",
                "flo","nov","jut","bag","mir","sty","lap","two","ins","con","ant",
                "net","tux","ode","stu","mug","cad","nap","gun","fop","tot","sow",
                "sal","sic","ted","wot","del","imp","cob","way","ann","tan","mci",
                "job","wet","ism","err","him","all","pad","hah","hie","aim","ike",
                "jed","ego","mac","baa","min","com","ill","was","cab","ago","ina",
                "big","ilk","gal","tap","duh","ola","ran","lab","top","gob","hot",
                "ora","tia","kip","han","met","hut","she","sac","fed","goo","tee",
                "ell","not","act","gil","rut","ala","ape","rig","cid","god","duo",
                "lin","aid","gel","awl","lag","elf","liz","ref","aha","fib","oho",
                "tho","her","nor","ace","adz","fun","ned","coo","win","tao","coy",
                "van","man","pit","guy","foe","hid","mai","sup","jay","hob","mow",
                "jot","are","pol","arc","lax","aft","alb","len","air","pug","pox",
                "vow","got","meg","zoe","amp","ale","bud","gee","pin","dun","pat","ten","mob"));
        int res = instance.ladderLengthWrong(beginWord, endWord, wordList);
        System.out.println(res);
    }


    /**
     *
     * 下面这种写法是根本性错误的。而且我在2017年跟现在重复犯了相同的错误。
     * 说明这道题对我来说是易错题！！！
     * 绝对不能用DFS来找Shortest Path，这不光是efficiency的问题，而是DFS根本算不出shortest path!!!
     * 比如对下图：src\main\resources\NoDfs4ShortestDistance.PNG
     * 就按照ladderLengthWrong的写法，“有可能”会得到A-->D的最短距离是5！！！
     * 这里说的是“有可能”，因为DFS找出来的结果，取决于先走哪条路径。
     * 具体就这个图中的例子，如果按照图中示意的路径，那么从D返回到B之后，因为A&C都在B“来时的路上”，
     * 所B不会去explore B-C-D这条路径，而是会直接take 4.
     *
     */
    public int ladderLengthWrong(String beginWord, String endWord, List<String> wordList) {
        //很明显的backtracking问题。
        //这里面关于如何findNext，有两个选项，需要在里面做一个选择：
        //    1. 每次把wordList过一遍，把符合条件的都找出来，当然要避免跟之前已经使用过的word重复
        //    2. 每次改变一个字母，遍历26个字母，然后在wordList当中查找这个变化后的word时候在里面，当然也要避免cycle back回之前已经使用过的word
        //上面这里两个方法，要根据题目给出的constraints去做一个选择：
        //如果用第一种方法，每一步findNext需要遍历5000个word，对每个word还要比较需要10，所以worst case需要5000*10 = 50000
        //如果使用第二种方法，每次改变一个字母，最多10个字母，那么每次需要判断的word数为(26-1)*10（注意这里是25*10是不是pow(25,10)，因为每次只能有一个在变），再考虑到每个word的比较10，所以worst case: 250*10=2500
        //所以选第二种方法。
        //需要把wordList放进HashSet里，此外就是需要manage这个set把已经访问过的word移除，结束之后还要把移除过的再放回去
        //另外需要注意的就是重复出现的子问题在这里是存在的，比如"hit->dog"，不止一种途径可以变过去，所以需要加mem

        HashMap<String, Integer> mem = new HashMap<>();
        HashSet<String> dict = new HashSet<>();
        dict.addAll(wordList);
        int res = search(beginWord, endWord, dict, mem);
        if(res==Integer.MAX_VALUE){
            return 0;
        }
        return res;
    }

    private int search(String beginWord, String endWord, HashSet<String> dict, HashMap<String, Integer> mem){
        //termination conditions
        if(beginWord.equals(endWord)){
            return 1;
        }

        //remove the beginWord from dict first avoid cycle
        dict.remove(beginWord);

        StringBuilder buf = new StringBuilder();
        buf.append(beginWord);
        int res = Integer.MAX_VALUE;
        for(int i=0; i<beginWord.length(); i++){
            for(char newChar='a'; newChar<='z'; newChar++){
                char old = beginWord.charAt(i);
                if(newChar!=old){
                    buf.setCharAt(i, newChar);
                    String next = buf.toString();
                    if(beginWord.equals("cet") && next.equals("cat")){
                        System.out.println("Here");
                    }

                    if(mem.containsKey(next)){
                        res = Math.min(res, mem.get(next));
                        //if(beginWord.equals("cet")){
                        //    System.out.println(next);
                        //    System.out.println(mem.get(next));
                        //}
                    }
                    else if(dict.contains(next)){
                        int temp = search(next, endWord, dict, mem);
                        res = Math.min(res, temp);
                        //if(beginWord.equals("cet")){
                        //    System.out.println(next);
                        //    System.out.println(temp);
                        //}
                    }
                    buf.setCharAt(i, old);
                }
            }
        }

        //add back beginWord
        dict.add(beginWord);

        if(res!=Integer.MAX_VALUE){
            res += 1;

        }
        mem.put(beginWord, res);
        if(beginWord.equals("cat")){
            System.out.println(beginWord);
            System.out.println(res);
        }
        if(beginWord.equals("can")){
            System.out.println(beginWord);
            System.out.println(res);
        }
        return res;
    }
}
