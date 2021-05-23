package com.mycompany.app;

/**
 * https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
 * Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.
 * You can think of the left and right pointers as synonymous to the predecessor and successor pointers in a doubly-linked list. For a circular doubly linked list, the predecessor of the first element is the last element, and the successor of the last element is the first element.
 * We want to do the transformation in place. After the transformation, the left pointer of the tree node should point to its predecessor, and the right pointer should point to its successor. You should return the pointer to the smallest element of the linked list.
 *
 * Example 1:
 * Input: root = [4,2,5,1,3]
 * Output: [1,2,3,4,5]
 * Explanation: The figure below shows the transformed BST. The solid line indicates the successor relationship, while the dashed line means the predecessor relationship.
 *
 * Example 2:
 * Input: root = [2,1,3]
 * Output: [1,2,3]
 *
 * Example 3:
 * Input: root = []
 * Output: []
 * Explanation: Input is an empty tree. Output is also an empty Linked List.
 *
 * Example 4:
 * Input: root = [1]
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 2000].
 * -1000 <= Node.val <= 1000
 * All the values of the tree are unique.
 */
public class ConvertBinarySearchTreeToSortedDoublyLinkedList {
    //本质是需要做in-order traversal,既可以iterative也可以recursive
    public Node treeToDoublyList(Node root) {
        if(root==null){
            return null;
        }

        Node dummyHead = new Node();
        Node[] ptr = new Node[]{dummyHead};
        inOrder(ptr, root);
        dummyHead.right.left = ptr[0];
        ptr[0].right = dummyHead.right;
        return dummyHead.right;
    }

    //注意我这里的ptr是要追着root跑的，所以要用Node[]而非Node，这是最容易犯的错误
    private void inOrder(Node[] ptr, Node root){
        //termination
        if(root==null){
            return;
        }

        inOrder(ptr, root.left);
        ptr[0].right = root;
        root.left = ptr[0];
        ptr[0] = ptr[0].right;
        inOrder(ptr, root.right);
    }

    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    };
    //TODO: do this iteratively using stack
}
