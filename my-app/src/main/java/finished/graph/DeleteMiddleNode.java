package finished.graph;

/**
 * Question:
 * You are given access to a middle node of a linkedlist.
 * You are not given access to the head node.
 * You are asked to delete that middle node.
 */

/**
 * Analysis:
 * Because you are not given access to the head node, so you can only access
 * nodes afterwards. You cannot try to reconnect previous to next.
 * All you can do is to copy data from next to current.
 * If current.next is not null.
 */
public class DeleteMiddleNode {
    public boolean delete(Node node){
        if(node==null || node.next==null){
            return false;
        }
        node.value = node.next.value;
        node.next = node.next.next;
        return true;
    }

    private class Node{
        int value;
        Node next;
        Node(int value){
            this.value = value;
        }
    }
}
