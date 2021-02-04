package com.mycompany.app;

/**
 * https://leetcode.com/problems/strong-password-checker/
 * A password is considered strong if the below conditions are all met:
 *
 * It has at least 6 characters and at most 20 characters.
 * It contains at least one lowercase letter, at least one uppercase letter, and at least one digit.
 * It does not contain three repeating characters in a row (i.e., "...aaa..." is weak, but "...aa...a..." is strong, assuming other conditions are met).
 * Given a string password, return the minimum number of steps required to make password strong. if password is already strong, return 0.
 *
 * In one step, you can:
 * Insert one character to password,
 * Delete one character from password, or
 * Replace one character of password with another character.
 *
 * Example 1:
 * Input: password = "a"
 * Output: 5
 *
 * Example 2:
 * Input: password = "aA1"
 * Output: 3
 *
 * Example 3:
 * Input: password = "1337C0d3"
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= password.length <= 50
 * password consists of letters, digits, dot '.' or exclamation mark '!'.
 */

/**
 * Analysis:
 * 这是一个ad-hoc的设计构造的题目。具体分析如下：
 * 这种题目的入手就是先从简单的例子入手，在具体解决的过程中发现规律。
 * 1. 从l<6的情况入手。
 * 有3种问题需要关心：
 *     1. 有没有missing letters/digits；
 *     2. 6-l是多少，就是差几个char；
 *     3. 有没有连续超过3个的char
 * 简单几个例子之后就会发现，在l<6的时候不用“担心”第3类问题，因为在解觉前两个问题的时候总可以“顺道”把第3类问题也解决了。
 * 而在前两个问题当中，优先使用insert解觉第2类问题，因为在解决第2类问题的时候也可以“顺道”把missing的letters/chars
 * 给insert进来。然后就本明显在l<6的时候问题的解就是Math.max(# of missing letters/digits, 6-l)
 *
 * 2. 然后很自然的想到考虑6<=l<=20的情况，因为这种情况下不需要考虑长度的问题。
 * 这种情况下只要考虑第1和第3类问题。第3类问题比较突出，因为l变长了，可以连续出现的字符串也变长了。
 * 这种情况下就导致第3类问题无法“顺道”被解决掉，虽然在解决第1类问题的时候依然在解决第3类问题，但是因为连续字符串长度的原因，
 * 在解决掉第1类问题之后，可能需要额外的steps去解决第3类问题。
 * 在手段上，在这种情况下应该只考虑使用replacement，因为：
 *     1. 使用insert || delete没有任何额外的优势
 *     2. 使用replacement还能确保长度不变，不用担心l的问题
 * 在具体解决第3类问题时，每3个（每隔2个）replace一个，比如：
 * a,a,a,a,a,a,a --> a,a,A,a,a,1,a
 * 所以处理每个连续字符串需要的 # of steps = k/3, k是连续字符串的长度，求和既得到处理连续字符串需要的总步数Sum(k)
 * 所以在6<=l<=20的情况下，问题的解就是Math.max(# of missing letters/digits, Sum(k));
 *
 * 3. l>20的情况。
 * 这种情况比上面的情况又多了长度的问题需要考虑。
 * 首先l-20是必须要delete掉的，这样就解决了长度的问题。
 * 没有必要在此基础上再delete掉更多的char，因为就像前面提到的，在没有长度问题的情况下，delete/insert并不比replace有任何优势。
 * 所以在这种情况下解决这个问题就要分两步走：
 *     1. 使用什么样的策略去完成l-20的delete操作
 *     2. 余下的问题就跟上面的6<=l<=20的情况一样了。
 * l-20的delete操作的原则，应该是: 使余下的问题最容易被解决掉.
 * 怎么实现上面的这个intention？要考虑delete除了解决长度问题之外，如何能帮到另外两类问题？
 * 很明显帮不到miss letters/digits的情况，但是对于“连续字符串”的情况是可以有帮助的。
 * 那么这个问题就进一步具体到：l-20个delete如何操作能最有利于“连续字符串“问题的解决？
 * 最简单的情况：l-20个delete能消灭掉所有“连续字符串“问题。如果不能呢？回过头去观察上面处理“连续字符串”的例子：
 * a,a,a,a,a,a,a --> a,a,A,a,a,1,a
 * 因为是“每隔2个”replace一个，所以上面2个replace所能解决掉的最大的“连续字符串”应该是：a,a,A,a,a,1,a + a
 * 也就是说当字符串的长度满足3m+2的模式的时候，每一个replace操作的效能才是最大化的。
 * 这里我们可以推出一个结论：从最大化后续replace操作的效能的角度来看，3m+2优于3m+1,3m+1优于3m
 * 问题分析到这里也不能简单认为我们把遇到的每个“连续字符串”，不加区别的都尝试去trim成3m+2的模式就是最优策略，
 * 因为毕竟我们的delete的次数是有限的只有l-20次。
 * 原因是上面我们只讨论了各种模式的“收益”，还必须要考虑构造每种模式的“成本”：既构造"3m+2,3m+1,3m+1"的模式分别需要消耗多少delete?
 * 只有“成本”和“收益”都分析清楚了，我们才能进行比较，在给定的条件下，对策略进行优劣排序。
 * 对于给定的“连续字符串”，其长度有3中可能：3m, 3m+1, 3m+2。分别分析如下：
 *     1. 如果本来就是3m+2的情况就先不做任何操作
 *     2. 对于3m的情况：option1: 1次delete变成3m+2，option2: 2次delete变成3m+1.显然option2直接排除。
 *     3. 对于3m+1的情况：2次delete变成3m+2
 * 很显然最优的操作是3m->3m+2，次之的是3m+1->3m+2。
 * 所以应该优先完成前者，l-20个delete还有剩余，就去做后者。
 * 如果还有剩余怎么办？
 * 还是类似于上面从收“收益”和“成本”两个角度分析。
 * 此时面对的是“连续字符串”全是3m+2的情况。再次回到上面8个“a”的例子：
 * (a,a,a),(a,a,a),a,a
 * 会发现，如果我delete 1个或者2个a，根本不影响需要replace的次数，最少需要delete 3个a才能省去1次replace.
 * 也就是说如果只是删去1个或者2个a，完全就是在浪费delete。进而会发现要避免浪费delete，需要以3的倍数削减a.
 * 所以“收益”和“成本”的角度得出的结论是：从任何一个3m+2的“连续字符串”当中，删掉1个或者2个a，收益为0；删掉3个a，收益为1.
 * 上面的结论对于我们余下的delete操作有以下3点指导意义：
 *     1. 我们3个为一组的进行delete
 *     2. 如果有余下1个或者2个delete，既l-20不被3整除的情况，随便从“连续字符串”当中delete就可以了，因为并没有更优势的策略，
 *        而我们又因为长度原因，必须delete.
 *     3. “任何”。在所有余下的3m+2的“连续字符串”上进行上述两种操作效果是一样的。并不需要考虑“选择”哪个进行上述操作的问题，
 *        因为从“谁”身上删，收益和成本都是一样的。
 *
 * 至此全题的解题思路就全不清晰了。在具体实现上，要注意到上面第二，第三种情况有需要share的部分，注意代码的结构和优化。
 *
 */
public class StrongPasswordChecker {
    public static void main(String[] args){
        StrongPasswordChecker instance = new StrongPasswordChecker();
        String input = "FFFFFFFFFFFFFFF11111111111111111111AAA";
        int res = instance.strongPasswordChecker(input);
        System.out.println(res);
    }
    public int strongPasswordChecker(String s) {
        int res = 0, a = 1, A = 1, d = 1;
        char[] carr = s.toCharArray();
        int[] arr = new int[carr.length];

        for (int i = 0; i < arr.length;) {
            if (Character.isLowerCase(carr[i])) a = 0;
            if (Character.isUpperCase(carr[i])) A = 0;
            if (Character.isDigit(carr[i])) d = 0;

            int j = i;
            while (i < carr.length && carr[i] == carr[j]) i++;
            arr[j] = i - j;
        }

        int totalMissing = (a + A + d);

        if (arr.length < 6) {
            res += Math.max(6 - arr.length, totalMissing);

        } else {
            int overLen = arr.length>20 ? arr.length-20 : 0;
            //leftOver是做完l-20个delete之后，解决“连续字符串”问题，还需要的replace数目
            int leftOver = 0;
            res += overLen;

            //先做3m->3m+2的delete
            for (int i = 0; i < arr.length && overLen > 0; i++) {
                if(arr[i]>=3 && arr[i]%3==0){
                    arr[i] -= 1;
                    overLen -= 1;
                }
            }
            //再做3m+1->3m+2的delete
            for (int i = 0; i < arr.length && overLen > 0; i++) {
                if(arr[i]>3 && arr[i]%3==1){
                    //如果余下的delete只有1的时候，面对的全是3m+2 & 3m+1，这时候随便在什么地方-1，对结果无影响
                    arr[i] -= Math.min(overLen, 2);
                    //最后overLen到0或者负值
                    overLen -= 2;
                }
            }
            //还有余下的delete，就3个一组的减，不够3个delete时，就随便从什么地方减掉1or2
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] >= 3 && overLen > 0) {
                    int need = arr[i] - 2;
                    if(overLen>=need){
                        arr[i] -= need;
                    }
                    else{
                        arr[i] -= overLen;
                    }
                    //最后overLen到0或者负值
                    overLen -= need;
                }
                if (arr[i] >= 3) leftOver += arr[i] / 3;
            }

            res += Math.max(totalMissing, leftOver);
        }

        return res;
    }
}
