package com.mycompany.app.cache;

import java.util.*;

public class LRUWithTimeAndPriority {
    public static void main(String[] args){
        LRUWithTimeAndPriority instance = new LRUWithTimeAndPriority(5);
        instance.set("A", 1, 5, 100, 0);
        instance.set("B", 2, 15, 3, 0);
        instance.set("C", 3, 5, 10, 0);
        instance.set("D", 4, 1, 15, 0);
        instance.set("E", 5, 5, 150, 0);

        System.out.println("test 1:");
        CacheValue res = instance.get("C");
        if(res.valid){
            System.out.println(res.value);
        }

        System.out.println("test 2:");
        res = instance.get("F");
        if(res.valid){
            System.out.println(res.value);
        }
        else{
            System.out.println("Cache Miss");
        }

        System.out.println("test 3:");
        instance.setCapacity(4, 5);
        for(String key : instance.timeMap.values()){
            System.out.println(key);
        }

        System.out.println("test 4:");
        instance.setCapacity(3, 5);
        for(String key : instance.timeMap.values()){
            System.out.println(key);
        }

        System.out.println("test 5:");
        instance.setCapacity(2, 5);
        for(String key : instance.timeMap.values()){
            System.out.println(key);
        }

        System.out.println("test 6:");
        instance.setCapacity(1, 5);
        for(String key : instance.timeMap.values()){
            System.out.println(key);
        }
    }

    class Node{
        int value;
        String key;
        int time;
        int priority;
        Node next;
        Node pre;
        public Node(String key, int value, int time, int priority){
            this.key = key;
            this.value = value;
            this.time = time;
            this.priority = priority;
        }
    }

    class OrderedList{
        Node dummyHead;
        Node dummyTail;

        public OrderedList() {
            dummyHead = new Node("dummyHead", -1, 0, 0);
            dummyTail = new Node("dummyTail", -1, 0, 0);
            dummyHead.next = dummyTail;
            dummyTail.pre = dummyHead;
        }

        public void refresh(Node node){
            //break old links
            remove(node);

            //reinsert
            insert(node);
            return;
        }

        public void insert(Node node){
            node.next = dummyHead.next;
            dummyHead.next.pre = node;
            node.pre = dummyHead;
            dummyHead.next = node;
            return;
        }

        public void remove(Node node){
            node.pre.next = node.next;
            node.next.pre = node.pre;
            node.next = null;
            node.pre = null;
            return;
        }

        public void poll(){
            Node last = dummyTail.pre;
            last.pre.next = dummyTail;
            dummyTail.pre = last.pre;
            return;
        }

        public boolean isEmpty(){
            return dummyHead.next==dummyTail;
        }

        public Node getLast(){
            return dummyTail.pre;
        }
    }

    class CacheValue{
        boolean valid;
        int value;
        public CacheValue(boolean valid, int value){
            this.valid = valid;
            this.value = value;
        }
    }

    TreeMap<Integer, String> timeMap;
    TreeMap<Integer, OrderedList> priorityMap;
    Map<String, Node> map;
    int capacity;

    public LRUWithTimeAndPriority(int capacity){
        this.capacity = capacity;
        this.timeMap = new TreeMap<>();
        this.priorityMap = new TreeMap<>();
        this.map = new HashMap<>();
    }

    public void set(String key, int value, int priority, int expTime, int curTime){
        if(map.containsKey(key)){
            //clean up old cached data
            Node node = map.get(key);
            int priorityPre = node.priority;
            int timePre = node.time;
            this.map.remove(key);
            OrderedList list = this.priorityMap.get(priorityPre);
            list.remove(node);
            if(list.isEmpty()){
                this.priorityMap.remove(priorityPre);
            }
            this.timeMap.remove(timePre);
        }
        //set new values
        Node node = new Node(key, value, expTime, priority);
        this.map.put(key, node);
        if(this.priorityMap.containsKey(priority)){
            OrderedList list = this.priorityMap.get(priority);
            list.insert(node);
        }
        else{
            OrderedList list = new OrderedList();
            list.insert(node);
            this.priorityMap.put(priority, list);
        }
        this.timeMap.put(expTime, key);
        if(this.map.size()>this.capacity){
            this.evict(curTime);
        }
    }

    public CacheValue get(String key){
        if(!this.map.containsKey(key)){
            return new CacheValue(false, 0);
        }
        else{
            Node node = this.map.get(key);
            int priority = node.priority;
            //update the order of the node
            this.priorityMap.get(priority).refresh(node);
            return new CacheValue(true, node.value);
        }
    }

    public void evict(int curTime){
        Map.Entry<Integer, String> firstTimeEntry = this.timeMap.firstEntry();
        int firstTime = firstTimeEntry.getKey();
        if(firstTime<curTime){
            //remove this entry
            String key = firstTimeEntry.getValue();
            int priority = this.map.get(key).priority;
            OrderedList candidates = this.priorityMap.get(priority);
            candidates.poll();
            if(candidates.isEmpty()){
                this.priorityMap.remove(priority);
            }
            this.map.remove(key);
            this.timeMap.remove(firstTime);
        }
        else{
            Map.Entry<Integer, OrderedList> firstPriorityEntry = this.priorityMap.firstEntry();
            int priority = firstPriorityEntry.getKey();
            OrderedList list = firstPriorityEntry.getValue();
            Node firstNode = list.getLast();
            String key = firstNode.key;
            int time = firstNode.time;
            this.map.remove(key);
            list.poll();
            if(list.isEmpty()){
                this.priorityMap.remove(priority);
            }
            this.timeMap.remove(time);
        }
    }

    public void setCapacity(int capacity, int curTime){
        if(capacity<this.capacity){
            int diff = this.capacity - capacity;
            while(diff>0){
                evict(curTime);
                diff--;
            }
        }
        this.capacity = capacity;
    }
}
