package finished;

import java.util.*;

/**
 * Question:
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and put.
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present.
 * When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 * Example:
 * LRUCache cache = new LRUCache(2);
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // returns 1
 * cache.put(3, 3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 * cache.put(4, 4);    // evicts key 1
 * cache.get(1);       // returns -1 (not found)
 * cache.get(3);       // returns 3
 * cache.get(4);       // returns 4
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */


/**
 * Analysis:
 * I need a HashMap to give it O(1) operation for get() and put();
 * I also need a Queue structure to maintain the order.
 * But unlike a typical queue, I need to "refresh" the order, every time a old element is accessed.
 *
 * Will use HashMap as the base structure and add LinkedList structure on top of it.
 * Should we link the key up or the value up?
 * If you chose to link the key, you will have "not the same object problem".
 * Even if you override both hashCode() and equals() methods, it will only let you find the key-value pair in HashMap,
 * you will still have "not the same object problem" when you are trying to manipulate the link.
 * If you chose to link the value, you need to include the key value in the "Value" object,
 * otherwise, so you cannot do O(1) remove the oldest from the Map(you need key to remove from map).
 *
 * Will link value, and leave keys primitive.
 *
 */

public class LRUCache {
    private Map<Integer, Node> map;
    private int capacity;
    private Node dummyHead;
    private Node dummyTail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        dummyHead = new Node(-1, -1);
        dummyTail = new Node(-2, -2);
        dummyHead.next = dummyTail;
        dummyTail.pre = dummyHead;
    }

    public int get(int key) {
        if(this.map.containsKey(key)){
            //need to refresh the order for this access activity.
            Node node = map.get(key);
            refresh(node);
            return node.value;
        }
        else return -1;
    }

    public void put(int key, int value) {
        Node node = new Node(key, value);
        //refresh if exit
        if(this.map.containsKey(key)){
            refresh(map.get(key));
        }
        //else put
        else{
            if(map.size()>=this.capacity){
                remove();
            }
            insert(key, node);
        }
    }

    private void refresh(Node node){
        //break old links
        //Because keyNode.pre and keyNode.next can be null, so this is why it is good to use dummyHead and dummyTail
        node.pre.next = node.next;
        node.next.pre = node.pre;

        //reinsert
        insert(node);
        return;
    }

    private void insert(int key, Node node){
        insert(node);
        this.map.put(key, node);
        return;
    }

    private void insert(Node node){
        node.next = dummyHead.next;
        dummyHead.next.pre = node;
        node.pre = dummyHead;
        dummyHead.next = node;
        return;
    }

    private void remove(){
        Node last = dummyTail.pre;
        last.pre.next = dummyTail;
        dummyTail.pre = last.pre;
        this.map.remove(last.key);
        return;
    }

    private class Node{
        int value;
        int key;
        Node next;
        Node pre;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }
}
