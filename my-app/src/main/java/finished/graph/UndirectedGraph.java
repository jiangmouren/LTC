package finished.graph;

import java.util.*;
import java.util.function.Function;

/**
 * Created by eljian on 7/10/2017.
 * Properties:
 *      Vertex class,
 *      List<Vertex>,
 *      BFS return type,
 *      DFS return type,
 *      shortestDistance return type
 * Methods:
 * Should be careful, both DFS and BFS can get into cycles, which will result in infinite loop.
 * So unless explicitly specified, we must have logic to check for loops.
 * DFS(Node node),
 * DFS(),//DFS this whole graph
 * BFS(Node node),
 * BFS(Node node),//BFS this whole graph
 * shortestDistance(Node node1, Node node2)
 */
public class UndirectedGraph {
    private List<Node> graph;
    public UndirectedGraph(List<Node> nodeList){
        this.graph = nodeList;
    }

    //Because we are using an Function Interface as one of the argument here,
    //We can essentially implements the Function interface with a full fledged class that can do all the logic.
    //It can properties to track status and methods to manipulate.
    public BFS bFS(Node root, Function<Node, BFS> func){
        if(root==null){
            throw new IllegalArgumentException("Input cannot be null");
        }

        Queue<BNode> queue = new LinkedList<>();
        queue.add(new BNode(root, null));
        while(!queue.isEmpty()){
            BNode node = queue.remove();
            for(Node nbor : node.node.neighbors){
                nbor.neighbors.remove(node);
                queue.add(new BNode(nbor, node.node));
            }
            //Keep a parent pointer is how we restore the neighbors
            if(node.parentNode!=null){
                node.node.neighbors.add(node.parentNode);
            }
            return func.apply(node.node);
        }
        //if not returned within the BFS, then set up return here, we can return some properties in func object also.
        return new BFS();
    }


    public BFS bFS(Function<Node, BFS> func){
        //when we are traversal the whole graph, we need to keep track of what has been visited and what's not
        //we can either tag on the node itself or set up a separate structure to track this.
        //But I would say if the node is visited or not is not a info for the node, it is only something the traversal
        //concerns. And if you put a tag on the node, you will need to clean that tag after the traversal.

        //Let's use a HashSet to track what's visited.
        Set<Node> visitedSet = new HashSet<>();
        for(Node node : graph){
            if(!visitedSet.contains(node)){
                visitedSet.add(node);
                return this.bFS(node, func);
            }
        }

        //if not returned within the BFS, then set up return here, we can return some properties in func object also.
        return new BFS();
    }

    //DFS can be done with either recursion or iteration
    public DFS dFSRecursion(Node root){
        if(root==null){
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        /**
         * Logic before recursion:
         * Return as needed.
         * Normally logic here can be split into 2 types:
         * 1. logic for base case
         * 2. logic for normal case
         */

        //Then recurse
        for(Node nbor : root.neighbors){
            nbor.neighbors.remove(root);
            //Color the root as visited
            dFSRecursion(nbor);
            nbor.neighbors.add(root);
        }

        /**
         * Logic after recursion:
         * Now you can take data from children nodes
         */
        //return case in after recursion part
        return new DFS();
    }

    /**
     * The most commonly used Iterative DFS is tree traversal, general DFS can be tricky.
     * To do this, like the case in BFS, we cannot use the Stack<Node>
     * @param root
     * @return
     */
    public DFS dFSIteration(Node root){
        if(root==null){
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        //Initialize the stack
        Stack<DNode> stack = new Stack<>();
        Node ptr = root;
        while(!ptr.neighbors.isEmpty()){
            DNode tmp = new DNode(ptr);
            stack.add(tmp);
            ptr = tmp.visitNbor();
        }

        while(!stack.isEmpty()){
            DNode tmp = stack.peek();
            if(tmp.nborEmpty()){
                //Logic after all nbors visited
                //You want to put this current node logic only here, because every node in the stack will be visited
                //multiple times, you want to put the logic in place where it will only executed once.
                stack.pop();
                tmp.restore();
            }
            else{
                Node nxt = tmp.visitNbor();
                stack.add(new DNode(nxt));
            }
        }
        return new DFS();
    }

    public DFS dFS(){
        Set<Node> visitedSet = new HashSet<>();
        for(Node node : graph){
            if(!visitedSet.contains(node)){
                visitedSet.add(node);
                return this.dFSRecursion(node);
            }
        }

        //if not returned within the loop, then set up return here, we can return some properties in func object also.
        return new DFS();
    }

    //We use a wrapper Node to tag the distance
    public ShortestDistance shortestDistance(Node node1, Node node2){
        if(node1==null || node2==null){
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        Queue<BNode> queue = new LinkedList<>();
        BNode start = new BNode(node1, null);
        start.distance1 = 0;
        queue.add(start);
        while(!queue.isEmpty()){
            BNode current = queue.remove();
            int currDistance = current.distance1;
            List<Node> nbors = current.node.neighbors;
            if(current.parentNode!=null){
                current.node.neighbors.add(current.parentNode);
            }
            //check if match and return distance in case of yes
            if(current.node.equals(node2)){//I am assuming we are looking for a reference match, looking for exact one.
                return new ShortestDistance(currDistance);
            }
            for(Node tmp : nbors){
                BNode nxt = new BNode(tmp, current.node);
                nxt.distance1 = currDistance+1;
                queue.add(nxt);
            }
        }
        return new ShortestDistance(-1);//return -1 if can not find node2 from node1
    }

    /**
     * This is an implementation of bi-directional shortest distance.
     * The way we identify "crossing" is when current node is "visited by both". Not current1==current2!!!
     * @param node1
     * @param node2
     * @return
     */
    public ShortestDistance biDirShortest(Node node1, Node node2){
        if(node1==null || node2==null){
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        Queue<BNode> queue1 = new LinkedList<>();
        Queue<BNode> queue2 = new LinkedList<>();

        BNode start1 = new BNode(node1, null);
        BNode start2 = new BNode(node2, null);
        start1.distance1 = 0;
        start2.distance2 = 0;
        start1.visited1 = true;
        start2.visited2 = true;
        queue1.add(start1);
        queue2.add(start2);
        while(!queue1.isEmpty()&&!queue2.isEmpty()){
            BNode current1 = queue1.remove();
            BNode current2 = queue2.remove();
            int currDistance1 = current1.distance1;
            int currDistance2 = current2.distance2;
            List<Node> nbors1 = current1.node.neighbors;
            List<Node> nbors2 = current2.node.neighbors;

            //check if current1 visited by both
            if(current1.visited1 && current1.visited2){
                return new ShortestDistance(currDistance1+current1.distance2);
            }
            if(current2.visited1 && current2.visited2){
                return new ShortestDistance(currDistance2+current2.distance1);
            }

            for(Node tmp : nbors1){
                if(!(tmp==current1.parentNode)){
                    BNode nxt = new BNode(tmp, current1.node);
                    nxt.distance1 = currDistance1+1;
                    queue1.add(nxt);
                }
            }
            for(Node tmp : nbors2){
                if(!(tmp==current2.parentNode)){
                    BNode nxt = new BNode(tmp, current2.node);
                    nxt.distance2 = currDistance2+1;
                    queue2.add(nxt);
                }
            }
        }
        return new ShortestDistance(-1);//return -1 if no crossing
    }


    //Vertex class, it has to be public and static, because you are expecting List<Node> from outside
    //to instantiate a graph
    public static class Node{
        int lable;
        List<Node> neighbors;
        Node(int lable){
            this.lable = lable;
        }
    }

    /**
     * These are internal data type, should be private
     * BNode used in BFS Traversals.
     * We need to store the parent node, so we can avoid going back to its parent node
     */
    private class BNode{
        Node node;
        Node parentNode;
        int distance1;
        int distance2;
        boolean visited1;
        boolean visited2;
        BNode(Node node, Node parentNode){
            this.node = node;
            this.parentNode = parentNode;
        }
    }

    /**
     * DNode used in DFS Traversal.
     * We need to store what neighbors have been visited what haven't.
     */
    private class DNode{
        Node node;
        List<Node> visitedList;
        public DNode(Node node){
            this.node = node;
        }

        public Node visitNbor(){
            List<Node> nbors = this.node.neighbors;
            Node nxt = nbors.remove(nbors.size()-1);
            this.visitedList.add(nxt);
            return nxt;
        }

        public boolean nborEmpty(){
            return this.node.neighbors.isEmpty();
        }

        public void restore(){
            this.node.neighbors.addAll(visitedList);
        }
    }

    //BFS return type
    //Because you want to this to be your return type, it has to be public and static, otherwise, out of the scope
    //nobody will not what you are returning.
    public static class BFS{
    }
    //DFS return type
    public static class DFS{
    }
    //shortest distance return type
    public static class ShortestDistance{
        public ShortestDistance(int distance){

        }
    }
}
