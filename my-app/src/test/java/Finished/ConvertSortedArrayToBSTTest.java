package Finished;

import Finished.ConvertSortedArrayToBST;
import org.junit.Test;
import Finished.ConvertSortedArrayToBST.*;

/**
 * Created by jiangmouren on 6/9/17.
 */
public class ConvertSortedArrayToBSTTest {
    ConvertSortedArrayToBST objectUnderTest = new ConvertSortedArrayToBST();
    int[] nums1 = {1, 2, 3, 4, 10};
    int[] nums2 = {3, 4, 8, 9};
    int[] nums3 = {1};
    @Test
    public void sortedArrayToBST() throws Exception {
        TreeNode root1 = objectUnderTest.sortedArrayToBST(nums1);
        TreeNode root2 = objectUnderTest.sortedArrayToBST(nums2);
        TreeNode root3 = objectUnderTest.sortedArrayToBST(nums3);
        inOderTree(root1);
        System.out.println();
        inOderTree(root2);
        System.out.println();
        inOderTree(root3);
        System.out.println();
    }

    public void inOderTree(TreeNode root){
        //Termination Conditions
        if(root==null) return;
        inOderTree(root.left);
        System.out.print(root.val);
        inOderTree(root.right);
    }
}