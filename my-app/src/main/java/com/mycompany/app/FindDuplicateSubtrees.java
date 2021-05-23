package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/find-duplicate-subtrees/
 * Given the root of a binary tree, return all duplicate subtrees.
 * For each kind of duplicate subtrees, you only need to return the root node of any one of them.
 * Two trees are duplicate if they have the same structure with the same node values.
 *
 * Example 1:
 * Input: root = [1,2,3,4,null,2,4,null,null,4]
 * Output: [[2,4],[4]]
 *
 * Example 2:
 * Input: root = [2,1,1]
 * Output: [[1]]
 *
 * Example 3:
 * Input: root = [2,2,2,3,null,3,null]
 * Output: [[2,3],[3]]
 *
 * Constraints:
 * The number of the nodes in the tree will be in the range [1, 10^4]
 * -200 <= Node.val <= 200
 */
public class FindDuplicateSubtrees {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //这个题最直观的思路只使用，一个cntMap。
    //同样是做postOrder traversal, 对每个subtree把其postOrder traversal的serialization做key，就可以找到所有重复的subtree
    //这样做的问题，在于生成serialization是O(n)的操作，需要把左边的，右边的，还有root.val，接起来，所以整个过程就是O(n^2)
    //破解这里serialization问题的方法，就是不把原始的serialization传上去，而是针对每一个Unique的serialization都生成一个新的id，然后把这个id传上去
    //这样“left+root+right”就变成了O(1)的操作来生成当下的serialization，相同的serialization就share同一个id，所以加多一个idMap,总的complexity就降到了O(n)
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        //cntMap选在用val，而不是TreeNode做key，避免写hashcode function
        Map<Integer, Integer> cntMap = new HashMap<>();
        Map<String, Integer> idMap = new HashMap<>();
        List<TreeNode> res = new ArrayList<>();
        postOrder(root, cntMap, idMap, new int[]{1}, res);
        return res;
    }

    //null nodes share 0; other nodes start from 1
    //因为要return List<TreeNode>，所以必须在traverse的时候，生成res,无法事后做
    private int postOrder(TreeNode root, Map<Integer, Integer> cntMap, Map<String, Integer> idMap, int[] Id, List<TreeNode> res){
        //termination
        if(root==null){
            return 0;
        }

        int leftId = postOrder(root.left, cntMap, idMap, Id, res);
        int rightId = postOrder(root.right, cntMap, idMap, Id, res);
        String curLable = "" + leftId + root.val + rightId;
        if(idMap.containsKey(curLable)){
            int curId = idMap.get(curLable);
            cntMap.put(curId, cntMap.get(curId)+1);
            //注意一定是在cnt==2，而不是cnt>=2的时候，因为重复的subtree，只需要任意return一个root
            if(cntMap.get(curId)==2){
                res.add(root);
            }
            return curId;
        }
        else{
            int curId = ++Id[0];
            idMap.put(curLable, curId);
            cntMap.put(curId, 1);
            return curId;
        }
    }
}
