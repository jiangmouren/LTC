package com.mycompany.app;

import java.util.*;

/**
 * Analysis:
 * Should be careful, both DFS and BFS can get into cycles, which will result in infinite loop.
 * So unless explicitly specified, we must have logic to check for loops.
 *
 */

public class UndirectedGraph {
    /***************************************************************************************************************
     * Graph Definition
     ***************************************************************************************************************/
    //Node class.
    //Make it public static, because it is kind of utility property.
    public static class Node{
        int lable;
        List<Node> neighbors;
        Node(int lable){
            this.lable = lable;
        }
    }

    private List<Node> graph;
    public UndirectedGraph(List<Node> nodeList){
        this.graph = nodeList;
    }



    /***************************************************************************************************************
     * Graph Traversal
     * In general there two types of traversal: DFS and BFS
     * One good thing about BFS is that it is layer by layer, meaning close neighbors will always be visited before
     * distant neighbors. This makes it a nature fit for shortest path problems.
     * One good thing about DFS is that you will have the path from "root --> current", which is useful in many cases.
     * Like in backtracking problems, we are essentially interested in the path itself, not the final node.
     * Another good feature of DFS is that the return order will always be the reverse of a topological order, which
     * makes it a nature fit for topological sort.
     * Any time you are doing a graph traversal, remember to check for "cycle" conditions!!!
     *
     * Will use a search problem to demonstrate the general DFS and BFS.
     * And then move to specific algorithms like: shortest path.
     ***************************************************************************************************************/

    /**
     * @param root
     * @param target
     * @param visitedSet
     * @return
     */
    public boolean dFSSearchR(Node root, Node target, Set<Node> visitedSet){
        //base case
        if(root==null){
            return false;
        }

        //Recursive case
        if(root==target){
            return true;
        }
        visitedSet.add(root);
        for(Node nbor : root.neighbors){
            if(!visitedSet.contains(nbor)){
                //Bad practice in real production, multiple threads accessing the same node will have problem.
                nbor.neighbors.remove(root);
                if(dFSSearchR(nbor, target, visitedSet)){
                    return true;
                }
                nbor.neighbors.add(root);
            }
        }
        return false;
    }

    /**
     * Preferred!!!
     * @param root
     * @param target
     * @param parent
     * @param visitedSet
     * @return
     */
    public boolean dFSSearchR(Node root, Node target, Node parent, Set<Node> visitedSet){
        //base case
        if(root==null){
            return false;
        }

        //Recursive case
        if(root==target){
            return true;
        }
        visitedSet.add(root);
        for(Node nbor : root.neighbors){
            if(!visitedSet.contains(nbor) && nbor!=parent){
                if(dFSSearchR(nbor, target, root, visitedSet)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The most commonly used Iterative DFS is binary tree traversal, general DFS will need a wrapper node.
     * Basically, we need a scheme to mark visited children, so we know if a node is fully visited or not.
     * We need that info to decide whether we should remove this node from the stack or we should visit another child node.
     * Wrapper node is used for this purpose.
     * @param root
     * @return
     */
    public boolean dFSSearchI(Node root, Node target, Set<Node> visitedSet){
        if(root==null){
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        //Initialize the stack
        Stack<DNode> stack = new Stack<>();
        DNode rootD = new DNode(root, null);
        stack.add(rootD);
        DNode ptr = rootD;
        while(!ptr.nborEmpty()){
            Node tmp = ptr.visitNbor();
            DNode tmpD = new DNode(tmp, ptr.node);
            stack.add(tmpD);
            ptr = tmpD;
        }

        while(!stack.isEmpty()){
            DNode tmp = stack.peek();
            if(tmp.nborEmpty()){
                //Should only check here, so the check will only be executed once.
                if(tmp.node==target){
                    return true;
                }
                visitedSet.add(tmp.node);
                stack.pop();
            }
            else{
                Node nxt = tmp.visitNbor();
                if(nxt!=tmp.parentNOde && !visitedSet.contains(nxt)){
                    stack.add(new DNode(nxt, tmp.node));
                }
            }
        }
        return false;
    }

    /**
     * DNode used in Iterative DFS Traversal.
     * We need to store what neighbors have been visited what haven't.
     */
    private class DNode{
        Node node;
        Node parentNOde;
        int ptr = 0;
        public DNode(Node node, Node parent){
            this.node = node;
            this.parentNOde = parent;
        }

        public Node visitNbor(){
            List<Node> nbors = this.node.neighbors;
            Node nxt = nbors.get(ptr);
            ptr++;
            return nxt;
        }

        public boolean nborEmpty(){
            return ptr>=this.node.neighbors.size();
        }
    }

    public boolean dFS(Node target){
        Set<Node> visitedSet = new HashSet<>();
        for(Node node : graph){
            if(!visitedSet.contains(node)){
                if(dFSSearchR(node, target, null, visitedSet)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean bFSSearch(Node root, Node target, Set<Node> visitedSet){
        if(root==null){
            throw new IllegalArgumentException("Input cannot be null");
        }

        Queue<BNode> queue = new LinkedList<>();
        queue.add(new BNode(root, null));
        while(!queue.isEmpty()){
            BNode node = queue.remove();
            if(node.node==target){
                return true;
            }
            visitedSet.add(node.node);
            for(Node nbor : node.node.neighbors){
                if(nbor!=node.parentNode.node && !visitedSet.contains(nbor)){
                    queue.add(new BNode(nbor, node));
                }
            }
        }
        return false;
    }

    /**
     * BNode used in BFS Traversals.
     * We need to store the parent node, so we can avoid going back to its parent node
     * We will also need parentNode as parent pointer for us to construct the shortest path.
     */
    private class BNode{
        Node node;
        BNode parentNode;//ParentNode use Wrapper Node not the original node in order to create chain.
        int distance;
        BNode(Node node, BNode parentNode, int distance){
            this.node = node;
            this.parentNode = parentNode;
            this.distance = distance;
        }
        BNode(Node node, BNode parentNode){
            this.node = node;
            this.parentNode = parentNode;
        }
    }

    public boolean bFS(Node target){
        Set<Node> visitedSet = new HashSet<>();
        for(Node node : graph){
            if(!visitedSet.contains(node)){
                if(bFSSearch(node, target, visitedSet)){
                    return true;
                }
            }
        }

        return false;
    }

    /**************************************************************************************************************
     * Shortest Path Problems
     **************************************************************************************************************/

    /**
     * @param node1
     * @param node2
     * @return Return a Path object, which contains the shortest distance and the last node's wrapper node with
     * parent ptr. Return a null if there is no such path.
     */
    public Path findShorttest(Node node1, Node node2){
        if(node1==null || node2==null){
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        Set<Node> visitedSet = new HashSet<>();
        Queue<BNode> queue = new LinkedList<>();
        BNode start = new BNode(node1, null, 0);
        queue.add(start);
        while(!queue.isEmpty()){
            BNode current = queue.remove();
            int currDistance = current.distance;
            List<Node> nbors = current.node.neighbors;
            //check if match and return distance in case of yes
            if(current.node.equals(node2)){//I am assuming we are looking for a reference match, looking for exact one.
                return new Path(current);
            }
            visitedSet.add(current.node);
            for(Node tmp : nbors){
                if(tmp!=current.parentNode.node && !visitedSet.contains(tmp)){
                    BNode nxt = new BNode(tmp, current, currDistance+1);
                    queue.add(nxt);
                }
            }
        }
        return null;
    }

    private class Path{
        BNode pathPtr1;
        BNode pathPtr2;

        Path(BNode node1){
            this.pathPtr1 = node1;
        }

        Path(BNode node, BNode node2){
            this.pathPtr1 = node;
            this.pathPtr2 = node2;
        }
    }

    /**
     * @param node1
     * @param node2
     * @return return the shortest distance between node1 and node2
     */
    public int ShorttestDistance(Node node1, Node node2){
        return findShorttest(node1, node2).pathPtr1.distance;
    }

    /**
     * @param node1
     * @param node2
     * @return return the shortest path between node1 & node2; return null if there is no path.
     */
    public List<Node> shortestPath(Node node1, Node node2){
        Path path = findShorttest(node1, node2);
        if(path==null) return null;

        List<Node> res = new ArrayList<>();
        BNode ptr = path.pathPtr1;
        while(ptr!=null){
            res.add(ptr.node);
        }
        Collections.reverse(res);
        return res;
    }

    /**
     * This is an implementation of bi-directional shortest distance.
     * The way we identify "crossing" is when current node is "visited by both". Not current1==current2!!!
     * @param node1
     * @param node2
     * @return Return a Path object, which contains the shortest distance and the last node's wrapper node with
     * parent ptr. Return a null if there is no such path.
     */
    public Path biDirShortest(Node node1, Node node2){
        if(node1==null || node2==null){
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        Queue<BNode> queue1 = new LinkedList<>();
        Queue<BNode> queue2 = new LinkedList<>();
        Map<Node, BNode> visitedMap1 = new HashMap<>();
        Map<Node, BNode> visitedMap2 = new HashMap<>();

        BNode start1 = new BNode(node1, null, 0);
        BNode start2 = new BNode(node2, null, 0);
        queue1.add(start1);
        queue2.add(start2);

        while(!queue1.isEmpty()&&!queue2.isEmpty()){
            BNode current1 = queue1.remove();
            BNode current2 = queue2.remove();
            int currDistance1 = current1.distance;
            int currDistance2 = current2.distance;
            List<Node> nbors1 = current1.node.neighbors;
            List<Node> nbors2 = current2.node.neighbors;

            //check if current1 visited by both
            if(visitedMap1.containsKey(current2.node)){
                return new Path(visitedMap1.get(current2.node), current2);
            }
            else if(visitedMap2.containsKey(current1.node)){
                return new Path(visitedMap2.get(current1.node), current1);
            }
            else{
                for(Node tmp : nbors1){
                    if(tmp!=current1.parentNode.node && !visitedMap1.containsKey(tmp)){
                        BNode nxt = new BNode(tmp, current1, currDistance1+1);
                        queue1.add(nxt);
                    }
                }
                for(Node tmp : nbors2){
                    if(tmp!=current2.parentNode.node && !visitedMap2.containsKey(tmp)){
                        BNode nxt = new BNode(tmp, current2, currDistance2+1);
                        queue2.add(nxt);
                    }
                }
            }

        }
        return null;
    }

    /**************************************************************************************************************
     * Cycle detection && LongestDistance/Path() with DFS
     * Analysis:
     * Cycle detection problems in Undirected Graph is basically if for any inner DFS/BFS, if find visited node,
     * then you find a cycle in the graph. It is just a small change from the search logic to cycle detection logic.
     *
     * The "LongestDistance/Path" problem is not a very common one.
     * We first must understand the difference between: "a cycle in the current path" VS "a cycle in the graph".
     * Look at the following example:
     * 1--2--3--4
     * |  |
     * 5--6
     * I am looking for the longest path between 1&4.
     * Say the traversal happens in the following order:
     * 1->2->3->4 (1st time find 4, update the longest path)
     * (Backtracking)
     * 1->2->3
     * 1->2
     * 1->2->6->5->1 (1st time detected visited node, cycle detected!)
     * (Backtracking)
     * 1->2
     * 1
     * 1->5->6->2 (2nd time detected visited node)
     *
     * Now comes the question:
     * what is the difference between the 1st time visited detected vs the 2nd time visited detected?
     * The 1st time is a cycle in the current path; the 2nd time is a cycle in the graph not the current path!!!
     * "Cycle not in the current path" is an important feature for us, because what that essentially means is
     * that we have come to the same node from a different path.
     * Because for the "LongestPath" problem, what we essentially want is to explore all possible paths between
     * Node1 and Node4, "NOT visiting all nodes between Node1 and Node4".
     * So we only backtrack whenever there is cycle in the current path and will continue if the cycle is not in
     * the current path.
     * So will continue in the above 1->5->6->2 case.
     *
     * 1->5->6->2->1 (3rd time cycle detected, on the current path, backtracking)
     * (Backtracking)
     * 1->5->6->2
     * 1->5->6->2->3->4 (2nd time find 4, update the longest path)
     *
     * What is the termination condition(or topological order) for this algorithm?
     * Fully traverse all children nodes at every level.
     *
     * TODO:
     **************************************************************************************************************/
}
