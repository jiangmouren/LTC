package com.mycompany.app.companies.cruise;
import java.util.*;

/**
 * https://www.freecodecamp.org/news/design-a-key-value-store-in-go/
 */
public class TransactionalCache {
    Map<String, String> map;
    Stack<Map<String, String>> stack;
    public TransactionalCache(){
        this.map = new HashMap<>();
        this.stack = new Stack<>();
    }

    public void begin(){
        Map<String, String> tMap = new HashMap<>(this.map);
        this.stack.push(tMap);
    }

    public void rollBack(){
        this.stack.pop();
    }

    public void commit(){
        Map<String, String> topMap = this.stack.pop();
        Map<String, String> peekMap = this.stack.isEmpty() ? this.map : this.stack.peek();
        for(String key : topMap.keySet()){
            peekMap.put(key, topMap.get(key));
        }
    }

    public void put(String key, String val){
        Map<String, String> topMap = this.stack.peek();
        topMap.put(key, val);
    }

    public String get(String key){
        return this.stack.peek().get(key);
    }
}
