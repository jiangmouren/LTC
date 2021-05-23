package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/
 * Given two nodes of a binary tree p and q, return their lowest common ancestor (LCA).
 * Each node will have a reference to its parent node. The definition for Node is below:
 *
 * class Node {
 *     public int val;
 *     public Node left;
 *     public Node right;
 *     public Node parent;
 * }
 * According to the definition of LCA on Wikipedia: "The lowest common ancestor of two nodes p and q in a tree T is the lowest node that has both p and q as descendants (where we allow a node to be a descendant of itself)."
 *
 * Example 1:
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * Output: 3
 * Explanation: The LCA of nodes 5 and 1 is 3.
 *
 * Example 2:
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * Output: 5
 * Explanation: The LCA of nodes 5 and 4 is 5 since a node can be a descendant of itself according to the LCA definition.
 *
 * Example 3:
 * Input: root = [1,2], p = 1, q = 2
 * Output: 1
 *
 * Constraints:
 * The number of nodes in the tree is in the range [2, 105].
 * -109 <= Node.val <= 109
 * All Node.val are unique.
 * p != q
 * p and q exist in the tree.
 */

public class LowestCommonAncestorOfABinaryTreeIII {
    //这有点类似于linkedList找交点的题目
    public Node lowestCommonAncestor(Node p, Node q) {
        List<Node> list1 = new ArrayList<>();
        List<Node> list2 = new ArrayList<>();
        Node ptr0 = p;
        Node ptr1 = q;
        while(ptr0!=null){
            list1.add(ptr0);
            ptr0 = ptr0.parent;
        }
        while(ptr1!=null){
            list2.add(ptr1);
            ptr1 = ptr1.parent;
        }

        int idx0 = list1.size()-1;
        int idx1 = list2.size()-1;
        while(idx0>=0 && idx1>=0 && list1.get(idx0)==list2.get(idx1)){
            idx0--;
            idx1--;
        }

        return list1.get(idx0+1);
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    };
}
