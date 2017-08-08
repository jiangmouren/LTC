/**
 * Question:
 * Given a string, we can "shift" each of its letter to its successive letter, for example: "abc" -> "bcd".
 * We can keep "shifting" which forms the sequence:
 * "abc" -> "bcd" -> ... -> "xyz"
 * Given a list of strings which contains only lowercase alphabets, group all strings that belong to the same shifting sequence.
 * For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
 * A solution is:
 * [
 *   ["abc","bcd","xyz"],
 *   ["az","ba"],
 *   ["acef"],
 *   ["a","z"]
 * ]
 */


/**
 * Analysis:
 * This is essentially a simple HashMap question. 
 * Only trick is you need to realized this and able to design the <Key, Value> pair
 * Pay attention to the "rotation case".
 */
package Finished;
import java.util.*;
public class GroupShiftedStrings{
    public List<List<String>> find(List<String> list){
        Map<String, List<String>> map = new HashMap<>();
        for(String str : list){
            String key = getKey(str);
            if(!map.containsKey(key)){
                List<String> tmp = new ArrayList<>();
                tmp.add(str);
                map.put(key, tmp);
            }
            else{
                map.get(key).add(str);
            }
        }

        List<List<String>> result = new ArrayList<>();
        for(List<String> value : map.values()){
            result.add(value);
        }
        return result;
    }

    private String getKey(String str){
        StringBuilder buf = new StringBuilder();
        if(str.length()==1){
            buf.append(0);
        }
        else{
            for(int i=0; i<str.length()-1; i++){
                char tmp1 = str.charAt(i);
                char tmp2 = str.charAt(i+1);
                int tmp = tmp1-tmp2;
                //This is how we deal with the rotation case
                if(tmp<0){
                    tmp+=26;
                }
                buf.append(tmp);
            }
        }

        return buf.toString();
    }

}
