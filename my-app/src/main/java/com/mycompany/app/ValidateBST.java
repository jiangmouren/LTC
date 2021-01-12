package com.mycompany.app;

import com.sun.org.apache.xpath.internal.objects.XNull;

public class ValidateBST {
    public static class Node{
        Node left;
        Node right;
        int value;
        public Node(int val){
            this.value = val;
        }
    }
    //You can use a int[] to do this, but not as clean.
    //Should code the same way you would do for real.
    private static class IntHelper{
        int value;
        boolean valid ;
        public IntHelper(int value, boolean valid){
            this.value = value;
            this.valid = valid;
        }
    }

    public boolean validateInOrder(Node root){
        //Want to give preVal global access, so must create outside
        IntHelper preVal = new IntHelper(0, false);
        return inOrder(root, preVal);
    }

    public boolean validateInOrderImproved(Node root){
        IntHelper preVal = new IntHelper(0, false);
        return inOrderImproved(root, preVal);
    }

    public boolean validatePreOrder(Node root){
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        return preOrder(root, min, max);
    }

    public boolean validatePostOrder(Node root){
        IntHelper min = new IntHelper(0, false);
        IntHelper max = new IntHelper(0, false);
        return postOrder(root, min, max);
    }

    private boolean inOrder(Node root, IntHelper preVal){
        //base case
        if(root==null){
            return true;
        }

        boolean leftResult = inOrder(root.left, preVal);
        //Big limitation here, this current sln cannot diff the following two cases:
        //   20           20    //
        //     \         /
        //      20     20       //
        //Basically cannot handle duplicated value in BST, thus force: root.val>preVal
        if(!leftResult || preVal.valid && root.value<=preVal.value){
            return false;
        }
        preVal.value = root.value;
        preVal.valid = true;
        boolean rightResult = inOrder(root.right, preVal);
        if(!rightResult){
            return false;
        }
        return true;
    }

    //Add handling logic for duplicated values.
    private boolean inOrderImproved(Node root, IntHelper preVal){
        //base case
        if(root==null){
            return true;
        }

        boolean leftResult = inOrderImproved(root.left, preVal);
        if(!leftResult){
             return false;
        }
        //The preVal coming from the predecessor, is either from the max of the left subtree
        //or the first left side accessor. The first case preVal<=root.value.
        //The second case, preVal<root.value.
        if(preVal.valid){
            //preVal coming from left subtree
            if(root.left!=null){
                if(root.value<preVal.value){
                    return false;
                }
            }
            //preVal coming from some left side ancestor
            if(root.left==null){
                if(root.value<=preVal.value){
                    return false;
                }
            }
        }
        preVal.value = root.value;
        preVal.valid = true;
        boolean rightResult = inOrderImproved(root.right, preVal);
        if(!rightResult){
            return false;
        }
        return true;
    }

    // We are only passing in(not out) value for min and max, so no reference type needed
    private boolean preOrder(Node root, int min, int max){
        //base case
        if(root==null){
            return true;
        }
        //To be valid BST: min(from left side parent)<root<=max(from right side parent)
        if(root.value<=min || root.value>max){
            return false;
        }
        boolean leftResult = preOrder(root.left, min, root.value);
        if(!leftResult){
            return false;
        }
        boolean rightResult = preOrder(root.right, root.value, max);
        if(!rightResult){
            return false;
        }
        return true;
    }

    private boolean postOrder(Node root, IntHelper min, IntHelper max){
        //base case
        if(root==null){
            return true;
        }
        //We can reuse the min and max, but need to invalidate the corresponding
        //value before reused. Not as straight forward as passing in new instance.
        //To invalidate just invalidate min and max at the beginning of this method.
        //They used to pass out values not pass in.
        IntHelper minLeft = new IntHelper(0, false);
        IntHelper maxLeft = new IntHelper(0, false);
        boolean leftResult = postOrder(root.left, minLeft, maxLeft);
        if(!leftResult || maxLeft.valid && root.value<maxLeft.value){
            return false;
        }
        IntHelper minRight = new IntHelper(0, false);
        IntHelper maxRight = new IntHelper(0, false);
        boolean rightResult = postOrder(root.right, minRight, maxRight);
        if(!rightResult || minRight.valid && root.value>=minRight.value){
            return false;
        }
        min.value = minLeft.valid ? Math.min(root.value, minLeft.value): root.value;
        //min.value = minRight.valid ? Math.min(root.value, minRight.value): root.value;
        //max.value = maxLeft.valid ? Math.max(root.value, maxLeft.value): root.value;
        max.value = maxRight.valid ? Math.max(root.value, maxRight.value): root.value;

        min.valid = true;
        max.valid = true;
        return true;
    }
}
