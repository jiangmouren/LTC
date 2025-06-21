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
        Queue<TreeNode> queue = new LinkedList<>();
        List<String> list = new ArrayList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            if(node!=null){
                list.add(""+node.val);
                queue.add(node.left);
                queue.add(node.right);
            }
            else{
                list.add("null");
            }
        }
        int ptr = list.size()-1;
        while(ptr>=0 && list.get(ptr).equals("null")){
            list.remove(ptr);
            ptr--;
        }
        String res = String.join(",", list);//用逗号不能用"-"，因为会有负数
        //System.out.println(res);
        return res;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data==null || data.length()==0){
            return null;
        }
        //System.out.println(data);
        String[] tokens = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        queue.add(root);
        int ptr = 1;
        while(!queue.isEmpty() && ptr<tokens.length){
            TreeNode node = queue.poll();
            String leftStr = tokens[ptr];
            node.left = getNode(leftStr);
            ptr++;
            if(ptr<tokens.length){
                String rightStr = tokens[ptr];
                node.right = getNode(rightStr);
                ptr++;
            }
            if(node.left!=null){
                queue.add(node.left);
            }
            if(node.right!=null){
                queue.add(node.right);
            }
        }
        return root;
    }

    private TreeNode getNode(String str){
        //System.out.println(str);
        if(str.equals("null")){
            return null;
        }
        else{
            return new TreeNode(Integer.parseInt(str));
        }
    }
}
