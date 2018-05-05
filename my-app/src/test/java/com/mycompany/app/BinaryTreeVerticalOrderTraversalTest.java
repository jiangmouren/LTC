package com.mycompany.app;

import com.mycompany.app.BinaryTreeVerticalOrderTraversal;
import org.junit.Test;
import com.mycompany.app.BinaryTreeVerticalOrderTraversal.*;
import java.util.*;

/**
 * Created by eljian on 9/30/2017.
 */
public class BinaryTreeVerticalOrderTraversalTest {
    BinaryTreeVerticalOrderTraversal obj = new BinaryTreeVerticalOrderTraversal();
    Node root = new Node(3);
    {
        root.left = new Node(9);
        root.right = new Node(8);
        root.left.left = new Node(4);
        root.left.right = new Node(0);
        root.right.left = new Node(1);
        root.right.right = new Node(7);
        root.left.right.left = new Node(5);
        root.left.right.right = new Node(2);
    }
    @Test
    public void verticalTraversal() throws Exception {
        printList(obj.verticalTraversal(root));
    }

    private void printList(List<List<Integer>> input){
        for(List<Integer> list : input){
            for(Integer token : list){
                System.out.print(token);
            }
            System.out.println();
        }
    }
}