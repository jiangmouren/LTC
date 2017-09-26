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
 * What will use HashMap as the base structure and add LinkedList structure on top of it.
 * Should we link the key up or the value up?
 * If you chose to link the key, you need to override both hashCode() and equals() method.
 * If you chose to link the value, you need to include the key value in the "Value" object,
 * otherwise, so you can O(1) remove the last from the Map(you need key to remove from map).
 *
 * Here is an explanation why you need to override both:
 * Think about it in the case of a bucketed chained implementation of HashMap.
 * You need the hashCode() method to decide which bucket to search;
 * Then within that bucket, you will need the equals() method to check if anything in the bucket really
 * equals to this key.
 *
 *
 * Attention: This is good example showing you should try not to hash on non-premitive type, in case you do not want to use
 * the address for the hashing.
 */
class LRUCacheWrong {

    private Map<KeyNode, Integer> map;
    private int capacity;
    private KeyNode dummyHead;
    private KeyNode dummyTail;

    public LRUCacheWrong(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        dummyHead = new KeyNode(-1);
        dummyTail = new KeyNode(-2);
        dummyHead.next = dummyTail;
        dummyTail.pre = dummyHead;
    }

    public int get(int key) {
        KeyNode keyNode = new KeyNode(key);
        if(this.map.containsKey(keyNode)){
            /**
             * need to refresh the order for this access activity.
             * but you cannot use the the newly generated keyNode to do refresh(),
             * because it's a different object!!! Now you will want to get the actual object after accessing
             * the Map, you can in this case, but does not worth the effort!!!
             * Conclusion: Better to put the link on the value side!!!
             */
            refresh(keyNode);
            return map.get(keyNode);
        }
        else return -1;
    }

    public void put(int key, int value) {
        KeyNode keyNode = new KeyNode(key);
        //refresh if exit
        if(this.map.containsKey(keyNode)){
            refresh(keyNode);
        }
        //else put
        else{
            if(map.size()<this.capacity){
                insert(keyNode, value);
            }
            else{
                remove();
                insert(keyNode, value);
            }
        }
    }

    private void refresh(KeyNode keyNode){
        //break old links
        //Because keyNode.pre and keyNode.next can be null, so this is why it is good to use dummyHead and dummyTail
        keyNode.pre.next = keyNode.next;
        keyNode.next.pre = keyNode.pre;

        //reinsert
        insert(keyNode);
        return;
    }

    private void insert(KeyNode keyNode, int value){
        insert(keyNode);
        this.map.put(keyNode, value);
        return;
    }

    private void insert(KeyNode keyNode){
        keyNode.next = dummyHead.next;
        dummyHead.next.pre = keyNode;
        keyNode.pre = dummyHead;
        dummyHead.next = keyNode;
        return;
    }

    private void remove(){
        KeyNode last = dummyTail.pre;
        last.pre = dummyTail;
        dummyTail.pre = last.pre;
        this.map.remove(last);
        return;
    }

    private class KeyNode{
        int key;
        KeyNode next;
        KeyNode pre;
        public KeyNode(int key){
            this.key = key;
        }
        @Override
        public int hashCode(){
            return Objects.hashCode(this.key);
        }
        @Override
        public boolean equals(Object obj){
            if(obj.getClass().equals(this.getClass())){
                KeyNode input = (KeyNode) obj;
                return (input.key==this.key);
            }
            return false;
        }
    }
}
