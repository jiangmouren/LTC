package com.mycompany.app.tree;
import java.util.*;

/**
 * https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
 * We are given a binary tree (with root node root), a target node, and an integer value K.
 *
 * Return a list of the values of all nodes that have a distance K from the target node.
 * The answer can be returned in any order.
 *
 * Example 1:
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
 * Output: [7,4,1]
 * Explanation:
 * The nodes that are a distance 2 from the target node (with value 5)
 * have values 7, 4, and 1.
 *
 * Note that the inputs "root" and "target" are actually TreeNodes.
 * The descriptions of the inputs above are just serializations of these objects.
 *
 * Note:
 * The given tree is non-empty.
 * Each node in the tree has unique values 0 <= node.val <= 500.
 * The target node is a node in the tree.
 * 0 <= K <= 1000.
 */
public class AllNodesDistanceKInBinaryTree {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //思路就是先构造从root到target的path, 再反向沿着这条Path做BFS或者pre-order dfs，依次找K, K-1, K-2
    //当然要往另外一侧找
    //具体写法上：
    //1. 可以分两次，先写一个dfs构造上述Path存在一个list里，然后在反向walk through那个list,这样写的好处是逻辑比较清晰
    //2. 直接利用构造Path的call stack，不explicitly构造上述list
    //下面是利用call stack的解法，这个题目主要的trick就是如何做到：某一侧Populated过之后，在其parent level，只处理另外一侧
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> buf = new ArrayList<>();
        search(root, target, K, buf);
        return buf;
    }

    /**
     * @param root
     * @param target
     * @param K
     * @param buf： buf for results
     * @return Return the distance, the parent node need to needs to look for
     */
    private int search(TreeNode root, TreeNode target, int K, List<Integer> buf){
        if(root==target){
            populate(root, K, buf);
            return K-1;
        }

        if(root.left!=null){
            int leftRes = search(root.left, target, K, buf);
            //注意这里要分成等于0和大于0两种情况，这样就在处理大于0的时候，就可以避开已经populate过的另外一半subtree.
            if(leftRes==0){
                buf.add(root.val);
                return leftRes-1;
            }
            if(leftRes>0){
                if(root.right!=null){
                    populate(root.right, leftRes-1, buf);
                }
                return leftRes-1;
            }
        }
        if(root.right!=null){
            int rightRes = search(root.right, target, K, buf);
            if(rightRes==0){
                buf.add(root.val);
                return rightRes-1;
            }
            if(rightRes>0){
                if(root.left!=null){
                    populate(root.left, rightRes-1, buf);
                }
                return rightRes-1;
            }
        }
        return -1;
    }

    private void populate(TreeNode root, int K, List<Integer> buf){
        //termination
        if(K==0){
            buf.add(root.val);
            return;
        }

        if(root.left!=null){
            populate(root.left, K-1, buf);
        }
        if(root.right!=null){
            populate(root.right, K-1, buf);
        }
    }
}
