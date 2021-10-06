package com.mycompany.app.tree;
/**
 * Question:
 * Given a binary tree, return the vertical order traversal of its nodes' values.
 * (ie, from top to bottom, column by column).
 * If two nodes are in the same row and column, the order should be from left to right.
 *
 * Examples:
 * Given binary tree [3,9,20,null,null,15,7],
 *    3
 *   /\
 *  /  \
 *  9  20
 *     /\
 *    /  \
 *   15   7
 * return its vertical order traversal as:
 * [
 *   [9],
 *   [3,15],
 *   [20],
 *   [7]
 * ]
 * Given binary tree [3,9,8,4,0,1,7],
 *      3
 *     /\
 *    /  \
 *    9   8
 *   /\  /\
 *  /  \/  \
 *  4  01   7
 * return its vertical order traversal as:
 * [
 *   [4],
 *   [9],
 *   [3,0,1],
 *   [8],
 *   [7]
 * ]
 * Given binary tree [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left child is 5),
 *      3
 *     /\
 *    /  \
 *    9   8
 *   /\  /\
 *  /  \/  \
 *  4  01   7
 *     /\
 *    /  \
 *    5   2
 * return its vertical order traversal as:
 * [
 *   [4],
 *   [9,5],
 *   [3,0,1],
 *   [8,2],
 *   [7]
 * ]
 */

import java.util.*;

/**
 * Analysis:
 * Similar to the concept in shortestDistance problem, we can tag every node with a distance label.
 * Basically, if root is 0, then move one step to the left you -1 and one step to the right you do +1.
 * That way while you traverse the tree you will know the current "distance" and you can then put
 * the current node into a Map<Integer, List<Integer>>, you need to this kind of HashMap structure
 * since we want to group nodes based on distance.
 *
 * The next problem you need to  think about is how to guarantee the order within the same group.
 * You can either maintain the order while you inject elements into the list or you can later some
 * how sort the list. To sort the list, in general you can follow the top down order, but there will
 * be cases two nodes have same row and same column. This kind of scenarios will be hard to sort.
 * So better we maintain the order while injecting into the list: top --> down && left --> right.
 * Basically we do a level-order traversal / BFS.
 *
 * In the end we have to write a comparator to sort all the key value pairs into a set and then
 * use that sorted set to construct the result List<List<Integer>>
 */
public class BinaryTreeVerticalOrderTraversal {
    public static class Node{
        int label;
        Node left;
        Node right;
        Node(int label){
            this.label = label;
        }
    }

    public List<List<Integer>> verticalTraversal(Node root){
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        bFS(map, root);
        List<Map.Entry<Integer, List<Integer>>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new helperComparator());
        for(Map.Entry<Integer, List<Integer>> token : list){
            res.add(token.getValue());
        }
        return res;
    }

    private void bFS(Map<Integer, List<Integer>> map, Node root){
        Queue<BNode> queue = new LinkedList<>();
        queue.add(new BNode(root, 0));
        while(!queue.isEmpty()){
            BNode tmp = queue.remove();
            if(!map.containsKey(tmp.level)){
                map.put(tmp.level, new ArrayList<>());
            }
            map.get(tmp.level).add(tmp.node.label);
            if(tmp.node.left!=null){
                queue.add(new BNode(tmp.node.left, tmp.level-1));
            }
            if(tmp.node.right!=null){
                queue.add(new BNode(tmp.node.right, tmp.level+1));
            }
        }
    }

    private class BNode{
        Node node;
        int level;
        BNode(Node node, int level){
            this.node = node;
            this.level = level;
        }
    }

    private class helperComparator implements Comparator<Map.Entry<Integer, List<Integer>>>{
        @Override
        public int compare(Map.Entry<Integer, List<Integer>> o1, Map.Entry<Integer, List<Integer>> o2) {
            return o1.getKey()-o2.getKey();
        }
    }

}

















