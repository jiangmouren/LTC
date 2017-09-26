package finished;
/**
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * According to the definition of LCA on Wikipedia
 * : “The lowest common ancestor is defined between two nodes v and w as the lowest node in T that has both v and w
 * as descendants (where we allow a node to be a descendant of itself).”
 *
 *         _______3______
 *        /              \
 *     ___5__          ___1__
 *    /      \        /      \
 *    6      _2       0       8
 *          /  \
 *          7   4
 * For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3.
 * Another example is LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 */

import java.util.*;

/**
 * Analysis:
 * At least two ways to do this:
 * 1. Essentially the goal is to find the lowest node that is able to find both nodes within its subtree.
 *    So what we can do is to do a recursive function call on both the parent and the children nodes,
 *    trying to find either of two nodes or both. We need to define this special return type.
 *    The moment the function returns try on a subtree, we can stop, because the LCA is guaranteed to be the first one
 *    to return true. We just need a global variable to hold the node reference.
 *
 * 2. We can do DFS for both of these nodes, and keep track of the current path in a list from root to that node.
 *    The moment we find the first one we save the list1;
 *    The moment we find the second one we save the list2;
 *    What we need to do next is to find the junction between these two lists:
 *    From root side, we trace the first time it diverges;(Obviously easier in this case)
 *    or from the edge side, we first trim both lists to same size then trace the first time it converge.
 *
 * Option 2 is easier to implement.
 */

public class LowestCommonAncestorOfABinaryTree {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> list1 = new ArrayList<>();
        List<TreeNode> list2 = new ArrayList<>();
        boolean[] find = new boolean[1];
        find[0] = false;
        dFS(root, p, list1, find);
        find[0] = false;
        dFS(root, q, list2, find);
        for(int i=0; i<list1.size(); i++){
            if(list1.get(i)!=list2.get(i)){
                return list1.get(i-1);
            }
        }
        return null;
    }

    //Because the "list" is used both as a "buf" and the result, we need a "find" to flag if result founded.
    //Once found, we will not clean the buf on the return path.
    private void dFS(TreeNode root, TreeNode node, List<TreeNode> list, boolean[] find){
        //base case
        if(root==null){
            return;
        }

        //recursive case
        list.add(root);
        if(root==node){
            find[0] = true;
            return;
        }
        else{
            dFS(root.left, node, list, find);
            dFS(root.right, node, list, find);
        }
        if(!find[0]){
            list.remove(list.size()-1);
        }
        return;
    }
}
