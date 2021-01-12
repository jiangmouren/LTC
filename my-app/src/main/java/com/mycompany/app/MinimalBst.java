package com.mycompany.app;

import java.util.LinkedList;
import java.util.Stack;

public class MinimalBst {

    public static class Node{
        Node left;
        Node right;
        int value;
        public Node(int val){
            this.value = val;
        }
    }

    private static class Info{
        int left;
        int right;
        int[] nums;
        //in BFS store parent, in DFS store self
        Node node;
        public Info(int left, int right, int[] nums, Node node){
            this.left = left;
            this.right = right;
            this.nums = nums;
            this.node = node;
        }
    }

    public Node iterativeBfs(int[] nums){
        if(nums==null || nums.length==0){
            return null;
        }
        Node dummyRoot = new Node(Integer.MAX_VALUE);
        LinkedList<Info> queue = new LinkedList<>();
        Info first = new Info(0, nums.length-1, nums, dummyRoot);
        queue.add(first);
        while(!queue.isEmpty()){
            Info top = queue.poll();
            if(top!=null){
                int mid = (top.left + top.right)/2;
                Node cur = new Node(nums[mid]);
                Node parent = top.node;
                if(cur.value<=parent.value){
                    parent.left = cur;
                }
                else{
                    parent.right = cur;
                }
                if(mid>top.left){
                    Info leftInfo = new Info(top.left, mid-1, nums, cur);
                    queue.add(leftInfo);
                }
                if(mid<top.right){
                    Info rightInfo = new Info(mid+1, top.right, nums, cur);
                    queue.add(rightInfo);
                }
            }
        }
        return dummyRoot.left;
    }

    public Node iterativeDfs(int[] nums){
        if(nums==null || nums.length==0){
            return null;
        }
        int mid = (0+nums.length-1)/2;
        Node root = new Node(nums[mid]);
        Info start = new Info(0, nums.length-1, nums, root);
        Stack<Info> stack = new Stack<>();
        populateStack(start, stack);
        while(!stack.isEmpty()){
            Info cur = stack.pop();
            if(cur.right>cur.left){
                int curMid = (cur.left+cur.right)/2;
                int rightMid = (curMid+1+cur.right)/2;
                Node rightNode = new Node(nums[rightMid]);
                cur.node.right = rightNode;
                Info rightInfo = new Info(curMid+1, cur.right, nums, rightNode);
                populateStack(rightInfo, stack);
            }
        }
        return root;
    }

    private void populateStack(Info start, Stack<Info> stack){
        stack.push(start);
        int left = start.left;
        int right = (start.left + start.right)/2 -1;
        Node pre = start.node;
        while(left<=right){
            int mid = (left+right)/2;
            Node nxtNode = new Node(start.nums[mid]);
            pre.left = nxtNode;
            Info nxtInfo = new Info(left, right, start.nums, nxtNode);
            stack.push(nxtInfo);
            right = mid -1;
            pre = nxtNode;
        }
    }
}
