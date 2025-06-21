package com.mycompany.app.cache;

import java.util.*;

/**
 * Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.
 *
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 *
 * Example:
 * LFUCache cache = new LFUCache(capacity);
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // returns 1
 * cache.put(3, 3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 * cache.get(3);       // returns 3.
 * cache.put(4, 4);    // evicts key 1.
 * cache.get(1);       // returns -1 (not found)
 * cache.get(3);       // returns 3
 * cache.get(4);       // returns 4
 *
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

/**
 * 注意两个点：
 * 1. 不需要priorityQueue来track minCnt。开始之所以想着需要用PriorityQueue，是因为考虑当有entry被kick out之后，
 * 如何获得新的minCnt，其实在这里不是问题，因为有东西被kick out，说明有全新的东西进来，所以新的minCnt=1
 * 2. 借鉴LRU的思想，处理cnt tie的情况，但是这里不是吧所有的node都link起来，而是只把相同cnt需要arbitrate的node link起来。
 */
public class LFUCache {
    public static void main(String[] args){
        LFUCache instance = new LFUCache(0);
        instance.put(3, 1);
        instance.put(2, 1);
        instance.put(2, 2);
        instance.put(4, 4);
        System.out.println(instance.get(2));
    }

    class Node{
        int key;
        int val;
        int cnt;
        Node nxt;
        Node pre;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }

    class DoubleLinkedList{
        Node head;
        Node tail;
        public DoubleLinkedList(){
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.nxt = tail;
            this.tail.pre = this.head;
        }

        public void remove(Node node){
            node.pre.nxt = node.nxt;
            node.nxt.pre = node.pre;
        }

        public void insert(Node node){
            Node first = this.head.nxt;
            this.head.nxt = node;
            node.pre = this.head;
            node.nxt = first;
            first.pre = node;
        }
    }

    Map<Integer, Node> nodeMap;
    Map<Integer, DoubleLinkedList> cntMap;
    int minCnt;
    int capacity;
    public LFUCache(int capacity) {
        this.nodeMap = new HashMap<>();
        this.cntMap = new HashMap<>();
        this.minCnt = 0;
        this.capacity = capacity;
    }

    //下面的get() & put()里面应该有一些代码可以share，但是对于面试来说还是按照原样来写比较自然，写完有时间再后头优化代码
    public int get(int key) {
        if(!this.nodeMap.containsKey(key)){
            return -1;
        }

        Node node = nodeMap.get(key);
        DoubleLinkedList list = this.cntMap.get(node.cnt);
        list.remove(node);
        if(list.head.nxt == list.tail){
            if(node.cnt==this.minCnt){
                this.minCnt++;
            }
            this.cntMap.remove(node.cnt);
        }
        node.cnt++;
        if(this.cntMap.containsKey(node.cnt)){
            this.cntMap.get(node.cnt).insert(node);
        }
        else{
            list = new DoubleLinkedList();
            list.insert(node);
            this.cntMap.put(node.cnt, list);
        }

        return node.val;
    }

    public void put(int key, int value) {
        if(this.capacity == 0){
            return;
        }
        if(!this.nodeMap.containsKey(key)){
            if(this.nodeMap.size()==this.capacity){
                DoubleLinkedList list = this.cntMap.get(this.minCnt);
                Node node = list.tail.pre;
                list.remove(node);
                if(list.head.nxt == list.tail){
                    this.cntMap.remove(node.cnt);
                }
                this.nodeMap.remove(node.key);
            }
            Node node = new Node(key, value);
            this.nodeMap.put(key, node);
            this.minCnt = 1;
            if(this.cntMap.containsKey(1)){
                this.cntMap.get(1).insert(node);
            }
            else{
                DoubleLinkedList list = new DoubleLinkedList();
                list.insert(node);
                this.cntMap.put(1, list);
            }
        }
        else{
            Node node = this.nodeMap.get(key);
            DoubleLinkedList list = this.cntMap.get(node.cnt);
            list.remove(node);
            if(list.head.nxt == list.tail){
                if(this.minCnt==node.cnt){
                    this.minCnt++;
                }
                this.cntMap.remove(node.cnt);
            }
            int newCnt = node.cnt + 1;
            //DoubleLinkedList list;
            if(this.cntMap.containsKey(newCnt)){
                list = this.cntMap.get(newCnt);
            }
            else{
                list = new DoubleLinkedList();
                this.cntMap.put(newCnt, list);
            }
            list.insert(node);
            node.cnt++;
            node.val = value;
        }
    }
}

//下面是另外一种实现方法，两种解法各有利弊。第一种解法，复杂度更有，但是实现起来要update minCnt更麻烦一点，下面这种解法少了update minCnt
//的麻烦，复杂度上没有上一种好
class LFUCacheSln2 {
    class LRU{
        class Node{
            int key;
            Node pre;
            Node nxt;
            public Node(int key){
                this.key = key;
            }
        }
        Map<Integer, Node> map;
        Node dummyHead;
        Node dummyTail;
        public LRU(){
            this.map = new HashMap<>();
            this.dummyHead = new Node(0);
            this.dummyTail = new Node(0);
            this.dummyHead.nxt = this.dummyTail;
            this.dummyTail.pre = this.dummyHead;
        }

        //delete a key
        public void remove(int key){
            Node node = this.map.get(key);
            this.map.remove(key);
            liftNode(node);
        }

        public void add(int key){
            if(!this.map.containsKey(key)){
                Node node = new Node(key);
                this.map.put(key, node);
                addToFirst(node);
            }
            else{
                Node node = this.map.get(key);
                liftNode(node);
                addToFirst(node);
            }
        }

        private void addToFirst(Node node){
            node.nxt = this.dummyHead.nxt;
            this.dummyHead.nxt.pre = node;
            this.dummyHead.nxt = node;
            node.pre = this.dummyHead;
        }

        private void liftNode(Node node){
            node.nxt.pre = node.pre;
            node.pre.nxt = node.nxt;
        }

        //return the evicted key
        public int evict(){
            Node node = this.dummyTail.pre;
            this.map.remove(node.key);
            //System.out.println("first: "+dummyHead.nxt.key);
            //System.out.println("evicting: "+node.key);
            liftNode(node);
            return node.key;
        }

        public int size(){
            return this.map.size();
        }
    }

    class ValObj{
        int val;
        int cnt;
        public ValObj(int val){
            this.val = val;
            this.cnt = 1;
        }
    }

    Map<Integer, ValObj> keyMap;
    TreeMap<Integer, LRU> cntMap;
    int cap;

    public LFUCacheSln2(int capacity) {
        this.keyMap = new HashMap<>();
        this.cntMap = new TreeMap<>();
        this.cap = capacity;
    }

    public int get(int key) {
        if(this.keyMap.containsKey(key)){
            ValObj valObj = this.keyMap.get(key);
            int val = valObj.val;
            touch(key, valObj);
            return val;
        }
        else{
            return -1;
        }
    }

    public void put(int key, int value) {
        if(this.cap<=0){
            return;
        }

        if(this.keyMap.containsKey(key)){
            ValObj valObj = this.keyMap.get(key);
            valObj.val = value;
            touch(key, valObj);
        }
        else{
            if(this.keyMap.size()==this.cap){
                int minCnt = this.cntMap.firstKey();
                //System.out.println("minCnt: "+minCnt);
                int evictedKey = this.cntMap.get(minCnt).evict();
                cleanUpCntMap(minCnt);
                //System.out.println("evictedKey: "+evictedKey);
                this.keyMap.remove(evictedKey);
            }
            ValObj valObj = new ValObj(value);
            this.keyMap.put(key, valObj);
            updateCntMap(key, 1);
        }
    }

    private void touch(int key, ValObj valObj){
        this.cntMap.get(valObj.cnt).remove(key);
        cleanUpCntMap(valObj.cnt);
        valObj.cnt++;
        updateCntMap(key, valObj.cnt);
    }

    private void updateCntMap(int key, int cnt){
        if(!this.cntMap.containsKey(cnt)){
            LRU lru = new LRU();
            this.cntMap.put(cnt, lru);
        }
        this.cntMap.get(cnt).add(key);
    }

    private void cleanUpCntMap(int cnt){
        if(this.cntMap.get(cnt).size()==0){
            this.cntMap.remove(cnt);
        }
    }
}
