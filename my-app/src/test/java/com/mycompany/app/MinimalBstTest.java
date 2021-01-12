package com.mycompany.app;
import com.mycompany.app.MinimalBst.*;
import org.junit.Test;

public class MinimalBstTest {

    @Test
    public void minimalBstTest(){
        int[] nums1 = {0, 1, 2, 3, 4, 5};
        int[] nums2 = {0, 1, 2, 3, 4};
        int[] nums3 = {0};
        int[] nums4 = {};
        MinimalBst obj = new MinimalBst();
        Node bfsResult1 = obj.iterativeBfs(nums1);
        Node bfsResult2 = obj.iterativeBfs(nums2);
        Node bfsResult3 = obj.iterativeBfs(nums3);
        Node bfsResult4 = obj.iterativeBfs(nums4);
        System.out.println("BFS result1: ");
        inOrderTraversal(bfsResult1);
        System.out.println("BFS result2: ");
        inOrderTraversal(bfsResult2);
        System.out.println("BFS result3: ");
        inOrderTraversal(bfsResult3);
        System.out.println("BFS result4: ");
        inOrderTraversal(bfsResult4);

        Node dfsResult1 = obj.iterativeDfs(nums1);
        Node dfsResult2 = obj.iterativeDfs(nums2);
        Node dfsResult3 = obj.iterativeDfs(nums3);
        Node dfsResult4 = obj.iterativeDfs(nums4);
        System.out.println("DFS result1: ");
        inOrderTraversal(dfsResult1);
        System.out.println("DFS result2: ");
        inOrderTraversal(dfsResult2);
        System.out.println("DFS result3: ");
        inOrderTraversal(dfsResult3);
        System.out.println("DFS result4: ");
        inOrderTraversal(dfsResult4);
    }

    private void inOrderTraversal(Node root){
        //base case
        if(root==null){
            return;
        }

        inOrderTraversal(root.left);
        System.out.println(root.value);
        inOrderTraversal(root.right);
    }
}
