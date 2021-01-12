package com.mycompany.app;

import java.util.*;
import java.util.function.Function;

/**
 * You are given a list of projects and a list of dependencies (which is a list of pair of projects, where the second project is dependent on the first project).
 * All of a project's dependencies must be built before the project is. Find a build order taht will allow the projects to be built. If there is no valid build
 * order, return an error.
 * Example:
 * input:
 * project: a, b, c, d, e, f
 * dependencies: (a, d), (f, b), (b, d), (f, a), (d, c)
 * output: f, e, a, b, d, c
 */

// 2 common ways implementing topological sort: "incoming edges based", "DFS based".
public class TopologicalSort {
    String[] inBondBasedTopoSort(String[] projects, String[][]dependencies){
        Graph<NodeWithDependencies> graph = buildGraph(projects, dependencies, NodeWithDependencies::new);
        ArrayList<GraphNodeInterface> orderList = orderGraphByInBond(graph);
        String[] orderListNames = dumpNames(orderList);
        return orderListNames;
    }

    String[] dfsBasedTopoSort(String[] projects, String[][]dependencies){
        Graph<NodeWithState> graph = buildGraph(projects, dependencies, NodeWithState::new);
        ArrayList<GraphNodeInterface> orderList = orderGraphByDfs(graph);
        String[] orderListNames = dumpNames(orderList);
        return orderListNames;
    }

    private String[] dumpNames(ArrayList<GraphNodeInterface> orderList){
        if(orderList==null){
            return null;
        }
        String[] orderListNames = new String[orderList.size()];
        for(int i = 0; i<orderList.size(); i++){
            orderListNames[i] = orderList.get(i).getName();
        }
        return orderListNames;
    }

    private static interface GraphNodeInterface{
        public String getName();
        public ArrayList<GraphNodeInterface> getChildren();
        public boolean addNeighbor(GraphNodeInterface node);
    }

    private static interface GraphInterface<T extends GraphNodeInterface>{
        public ArrayList<T> getNodeList();
        public void addNode(T node);
        public T getNode(String name);
    }

    private static abstract class AbstractGraphNode implements GraphNodeInterface{
        private ArrayList<GraphNodeInterface> children = new ArrayList<>();
        protected HashMap<String, GraphNodeInterface> map = new HashMap<>();
        private String name;
        //one good reason to use accessor is to separate write and read access.
        //like here for the "name" field, only read allowed, no write allowed,
        //once the object created. Also interface will need accessors to expose
        //internal states.
        public String getName(){
            return this.name;
        }

        public AbstractGraphNode(String name){
            this.name = name;
        }

        public ArrayList<GraphNodeInterface> getChildren(){
            return children;
        }
    }

    private static class NodeWithDependencies extends AbstractGraphNode{
        private int dependencies = 0;

        public NodeWithDependencies(String name) {
            super(name);
        }


        public int getDependencies() {
            return this.dependencies;
        }

        public boolean addNeighbor(GraphNodeInterface node){
            if(node instanceof NodeWithDependencies){
                NodeWithDependencies neighbor = (NodeWithDependencies) node;
                if(!map.containsKey(neighbor.getName())){
                    getChildren().add(neighbor);
                    map.put(neighbor.getName(), neighbor);
                    neighbor.incrementDependencies();
                }
                return true;
            }
            else{
                return false;
            }
        }

        public void incrementDependencies(){
            dependencies ++;
        }

        public void decrementDependencies(){
            dependencies --;
        }
    }

    private static class NodeWithState extends AbstractGraphNode{

        public enum State {UNVISITED, VISITING, VISITED}
        private State state = State.UNVISITED;

        public State getState(){
            return this.state;
        }

        public void setState(State state){
            this.state = state;
        }
        public NodeWithState(String name) {
            super(name);
        }

        public boolean addNeighbor(GraphNodeInterface node){
            if(node instanceof NodeWithState){
                NodeWithState neighbor = (NodeWithState) node;
                if(!map.containsKey(neighbor.getName())){
                    getChildren().add(neighbor);
                    map.put(neighbor.getName(), neighbor);
                }
                return true;
            }
            else{
                return false;
            }
        }
    }

    private static class Graph<T extends GraphNodeInterface> implements GraphInterface<T>{
        private ArrayList<T> nodeList = new ArrayList<>();
        private HashMap<String, T> map = new HashMap<>();

        public ArrayList<T> getNodeList(){
            return nodeList;
        }

        public void addNode(T node){
            if(!map.containsKey(node.getName())){
                map.put(node.getName(), (T)node);
                nodeList.add((T)node);
            }
        }

        public T getNode(String name){
            if(!map.containsKey(name)){
                return null;
            }
            return map.get(name);
        }
    }

    //Build graph of concrete type
    private <T extends GraphNodeInterface> Graph<T> buildGraph(String[] projects, String[][]dependencies, Function<String, T> constructor){
        Graph<T> graph = new Graph<>();
        for(String name : projects) {
            // https://stackoverflow.com/questions/299998/instantiating-object-of-type-parameter
            T node = constructor.apply(name);
            graph.addNode(node);
        }

        for(String[] pair : dependencies){
            String sourceName = pair[0];
            String destinationName = pair[1];
            T sourceNode = graph.getNode(sourceName);
            T destinationNode = graph.getNode(destinationName);
            sourceNode.addNeighbor(destinationNode);
        }
        return graph;
    }

    private ArrayList<GraphNodeInterface> orderGraphByInBond(Graph<NodeWithDependencies> graph){
        //Do not use generic with array in java
        ArrayList<NodeWithDependencies> projects = graph.getNodeList();
        ArrayList<GraphNodeInterface> orderList = new ArrayList<>();
        int endOfList = 0;
        int toBeProcessed = 0;
        endOfList = addNoneDependentNodes(orderList, projects, endOfList);
        while(toBeProcessed<projects.size()){
            //meaning there are loop, cannot find non-dependent node anymore
            if(toBeProcessed>=orderList.size()){
                return null;
            }
            NodeWithDependencies current = (NodeWithDependencies) orderList.get(toBeProcessed);
            ArrayList<NodeWithDependencies> children = new ArrayList<>();
            for(GraphNodeInterface child : current.getChildren()){
                NodeWithDependencies node = (NodeWithDependencies) child;
                children.add(node);
                node.decrementDependencies();
            }
            endOfList = addNoneDependentNodes(orderList, children, endOfList);
            toBeProcessed++;
        }
        return orderList;
    }

    private int addNoneDependentNodes(List<GraphNodeInterface> orderList, List<NodeWithDependencies> candidates, int endOfList){
        for(NodeWithDependencies candidate : candidates){
            if(candidate.getDependencies()==0){
                orderList.add(candidate);
                endOfList++;
            }
        }
        return endOfList;
    }

    public ArrayList<GraphNodeInterface> orderGraphByDfs(Graph<NodeWithState> graph){
        ArrayList<NodeWithState> nodeList = graph.getNodeList();
        ArrayList<GraphNodeInterface> orderList = new ArrayList<>();
        for(NodeWithState node : nodeList){
            if(node.state== NodeWithState.State.UNVISITED){
                //cycle detected.
                if(!dfs(orderList, node)){
                    return null;
                }
            }
        }
        Collections.reverse(orderList);
        return orderList;
    }

    private boolean dfs(ArrayList<GraphNodeInterface> orderList, NodeWithState current){
        current.state = NodeWithState.State.VISITING;
        for(GraphNodeInterface child : current.getChildren()){
            NodeWithState childWithState = (NodeWithState) child;
            //cycle detected.
            if(childWithState.state== NodeWithState.State.VISITING){
                return false;
            }
            if(childWithState.state== NodeWithState.State.UNVISITED){
                if(!dfs(orderList, childWithState)){
                    return false;
                }
            }
        }
        current.state = NodeWithState.State.VISITED;
        orderList.add(current);
        return true;
    }

}
