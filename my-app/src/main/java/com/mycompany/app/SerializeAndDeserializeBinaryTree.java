package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
 * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.
 * For example, you may serialize the following tree
 *   1
 *  / \
 * 2   3
 *    / \
 *   4   5
 * as "[1,2,3,null,null,4,5]", just the same as how LeetCode OJ serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
 * Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
 */


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));

public class SerializeAndDeserializeBinaryTree {

    public static void main(String[] args){
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        root.left = node2;
        root.right = node3;
        node3.left = node4;
        node3.right = node5;
        node4.left = node6;
        node4.right = node7;

        SerializeAndDeserializeBinaryTree instance = new SerializeAndDeserializeBinaryTree();
        String data = instance.serialize(root);
        System.out.println(data);
        TreeNode res = instance.deserialize(data);
        System.out.println(instance.serialize(res));
    }

    static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        list.add(root);
        int ptr0 = 0;
        int ptr1 = 1;
        while(ptr0<list.size()){
            while(ptr0<ptr1){
                TreeNode cur = list.get(ptr0);
                if(cur!=null){
                    list.add(cur.left);
                    list.add(cur.right);
                }
                ptr0++;
            }
            ptr1 = list.size();
        }
        while(!list.isEmpty() && list.get(list.size()-1)==null){
            list.remove(list.size()-1);
        }
        StringBuilder buf = new StringBuilder();
        for(TreeNode node : list){
            if(node!=null){
                if(buf.length()>0){
                    buf.append(",");
                }
                buf.append(node.val);
            }
            else{
                if(buf.length()>0){
                    buf.append(",");
                }
                buf.append("null");
            }
        }
        return buf.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data==null || data.length()==0){
            return null;
        }
        String[] tokens = data.split(",");
        List<TreeNode> list = new ArrayList<>();
        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        list.add(root);
        int ptr0 = 0;
        int ptr1 = 1;
        int ptr2 = 1;

        while(ptr2<tokens.length && ptr0<list.size()){
            while(ptr0<ptr1){
                TreeNode cur = list.get(ptr0);
                if(cur!=null){
                    TreeNode left = null;
                    if(!tokens[ptr2].equals("null")){
                        left = new TreeNode(Integer.parseInt(tokens[ptr2]));
                    }
                    list.add(left);
                    cur.left = left;
                    ptr2++;
                    if(ptr2>=tokens.length){
                        break;
                    }
                    TreeNode right = null;
                    if(!tokens[ptr2].equals("null")){
                        right = new TreeNode(Integer.parseInt(tokens[ptr2]));
                    }
                    list.add(right);
                    cur.right = right;
                    ptr2++;
                    if(ptr2>=tokens.length){
                        break;
                    }
                }
                ptr0++;
            }
            ptr1 = ptr2;
        }
        return root;
    }
}
