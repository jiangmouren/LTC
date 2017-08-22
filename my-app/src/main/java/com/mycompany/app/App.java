package com.mycompany.app;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public void customSort(int[] arr) {
        Map<Integer, Integer> map1 = new HashMap<>();
        for(int tmp : arr){
            if(map1.containsKey(tmp)){
                int tmpFrequency = map1.get(tmp);
                tmpFrequency++;
                map1.put(tmp, tmpFrequency);
            }
            else{
                map1.put(tmp, 1);
            }
        }
        Map<Integer, List<Integer>> map2 = new HashMap<>();
        Set<Map.Entry<Integer, Integer>> set = map1.entrySet();
        for(Map.Entry<Integer, Integer> pair : set){
            int value = pair.getKey();
            int frequency = pair.getValue();
            if(!map2.containsKey(frequency)){
                List<Integer> list = new ArrayList<>();
                list.add(value);
                map2.put(frequency, list);
            }
            else{
                map2.get(frequency).add(value);
            }
        }

        List<Integer> frequenceList = new ArrayList<>();
        frequenceList.addAll(map2.keySet());
        Collections.sort(frequenceList);
        List<Integer> resList = new ArrayList<>();
        for(int tmp2 : frequenceList){
            List<Integer> tmpList = map2.get(tmp2);
            Collections.sort(tmpList);
            for(int token : tmpList){
                for(int i=0; i<tmp2; i++){
                    resList.add(token);
                }
            }
        }
        for(int i=0; i<arr.length; i++){
            arr[i] = resList.get(i);
        }
        return;
    }
}