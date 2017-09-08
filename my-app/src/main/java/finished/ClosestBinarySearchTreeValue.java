package finished;

/**
 * Question:
 * Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.
 * Note:
 * Given target value is a floating point.
 * You are guaranteed to have only one unique value in the BST that is closest to the target.
 */

/**
 * Analysis:
 * The 1st catch in this question is that even target is larger than the root, you cannot just go to right and discard the rest of the tree. 
 * You can discard the left tree in this case for sure, but not necessarily the root.
 * Because the root might be closer to the target than the smalllest node in the right subTree.
 * What we can do is keep track of the current smallest difference and corresponding node along the path. 
 * This way we can get the result in O(lgn) time.
 */

public class ClosestBinarySearchTreeValue {
     public static class TreeNode{
         int val;
         TreeNode left;
         TreeNode right;
         public TreeNode(int x){val=x;}
     }

     public int find(TreeNode root, float target){
         TreeNode minPtr = root;
         TreeNode ptr = root;
         float min = Integer.MAX_VALUE;//It's important to use float for min, otherwise 0.6 will be rounded to 0.
         while(ptr!=null){
             if(ptr.val==target){
                 minPtr=ptr;
                 break;
             }
             if(Math.abs(ptr.val-target)<min){
                 minPtr = ptr;
                 min = Math.abs(ptr.val-target);
             }
             if(target>ptr.val){
                 ptr = ptr.right;
             }
             else{
                 ptr = ptr.left;
             }

         }
         return minPtr.val;
     }


     public int findRecursive(TreeNode root, float target){
         TreeNode[] ptr = new TreeNode[1];
         ptr[0] = root;
         float min = Integer.MAX_VALUE;
         helper(root, target, ptr, min);
         return ptr[0].val;
     }

     //because we are not finding on a node, we are mostly finding it along the path, so we use void type and use global variable to store the finding.
     //Attention: Initially, I was using TreeNode ptr as the argument, which always return the root.val to me.
     //The key point here is passing the reference/address of a content and that content will have global access,
     //but the reference/address itself is nothing but a normal value.
     private void helper(TreeNode root, float target, TreeNode[] ptr, float min){
         //Termination Cases
         if(root==null) return;

         //Normal Cases
         if(root.val==target){
             ptr[0] = root;
             return;
         }
         if(Math.abs(root.val-target)<min){
             min = Math.abs(root.val-target);
             ptr[0] = root;
         }
         if(target>root.val){
             helper(root.right, target, ptr, min);
         }
         else{
             helper(root.left, target, ptr, min);
         }
     }

}
