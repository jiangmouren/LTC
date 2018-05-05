package com.mycompany.app;

import com.mycompany.app.BinaryTreePaths;
import org.junit.Test;
import com.mycompany.app.BinaryTreePaths.*;

import java.util.*;

/**
 * Created by eljian on 8/4/2017.
 */
public class BinaryTreePathsTest {
    BinaryTreePaths objectUnderTest = new BinaryTreePaths();
    TreeNode root = new TreeNode(1);
    {
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
    }
    @Test
    public void find() throws Exception {
        printList(objectUnderTest.find(root));
    }

    private void printList(List<List<TreeNode>> list){
        for(List<TreeNode> path : list){
            for(TreeNode node : path){
                System.out.print(node.val + ", ");
            }
            System.out.println();
        }
    }

}