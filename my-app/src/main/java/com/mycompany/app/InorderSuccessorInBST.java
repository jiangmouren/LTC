/**
 * Question: https://leetcode.com/problems/inorder-successor-in-bst/
 * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
 * The successor of a node p is the node with the smallest key greater than p.val.
 *
 * Example 1:
 * Input: root = [2,1,3], p = 1
 * Output: 2
 * Explanation: 1's in-order successor node is 2. Note that both p and the return value is of TreeNode type.
 *
 * Example 2:
 * Input: root = [5,3,6,2,4,null,null,1], p = 6
 * Output: null
 * Explanation: There is no in-order successor of the current node, so the answer is null.
 *
 * Note:
 * If the given node has no in-order successor in the tree, return null.
 * It's guaranteed that the values of the tree are unique.
 */

/**
 * Inorder predecessor与successor刚好是镜像关系:
 * 对于BST的node来说，if(node.left!=null), then max(node.left).
 * Otherwise, then look back the ancestors, the first left side ancestor is the predecessor.
 */

package com.mycompany.app;
import java.util.*;
public class InorderSuccessorInBST {
    //对于BST的node来说，if(node.right!=null), then minimum(node.right).
    //Otherwise, then look back the ancestors, the first right side ancestor is the successor.
    public static class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        public TreeNode(int x){
            this.val = x;
        }
    }

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if(p.right!=null){
            return minimum(p.right);
        }
        else{
            TreeNode[] res = {null};
            search(root, p, res);
            return res[0];
        }
    }

    private TreeNode minimum(TreeNode root){
        TreeNode ptr = root;
        while(ptr.left!=null){
            ptr = ptr.left;
        }
        return ptr;
    }

    //这个Search可以有2种思路，4种写法：
    //第一种思路就是在search path返回的路上，找到第一个right side ancestor，然后分别可以用recursion或者iteratively用stack
    //第二种思路就是在search path前进的路上，每次找到right side ancestor，都buf这个值，
    //那么最后那个buf里面存的就是Lowest right side ancestor，依然可以有recursion或者iteratively用stack两种写法

    //思路1，recursion
    private boolean search(TreeNode root, TreeNode target, TreeNode[] res){
        if(root==null){
            return false;
        }
        else if(root.val == target.val){
            return true;
        }
        else if(root.val<target.val){
            return search(root.right, target, res);
        }
        else{
            if(search(root.left, target, res)){
                if(res[0]==null){
                    res[0] = root;
                }
                return true;
            }
            else return false;
        }
    }

    //思路1，iteration
    public TreeNode search(TreeNode root, TreeNode target){
        Stack<TreeNode> stack = new Stack<>();
        TreeNode ptr = root;
        while(ptr!=null){
            if(ptr.val==target.val){
                break;
            }
            else if(ptr.val>target.val){
                stack.push(ptr);
                ptr = ptr.left;
            }
            else{
                stack.push(ptr);
                ptr = ptr.right;
            }
        }
        while(!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            if(tmp.val>ptr.val){
                return tmp;
            }
        }
        return null;
    }

    //思路2，recursion
    private boolean search2(TreeNode root, TreeNode target, TreeNode[] res){
        if(root==null){
            return false;
        }
        else if(root.val == target.val){
            return true;
        }
        else if(root.val<target.val){
            return search2(root.right, target, res);
        }
        else{
            res[0] = root;
            return search2(root.left, target, res);
        }
    }

    //思路2，iteration
    public TreeNode search2(TreeNode root, TreeNode node){
        Stack<TreeNode> stack = new Stack<>();
        TreeNode ptr = root;
        TreeNode res = null;
        while(ptr!=null){
            if(ptr.val==node.val){
                break;
            }
            else if(ptr.val>node.val){
                res = ptr;
                stack.push(ptr);
                ptr = ptr.left;
            }
            else{
                stack.push(ptr);
                ptr = ptr.right;
            }
        }
        return res;
    }
}