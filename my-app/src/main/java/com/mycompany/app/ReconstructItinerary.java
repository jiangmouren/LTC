package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/reconstruct-itinerary/
 * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to],
 * reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK.
 * Thus, the itinerary must begin with JFK.
 *
 * Note:
 *
 * If there are multiple valid itineraries, you should return the itinerary that has the smallest
 * lexical order when read as a single string. For example, the itinerary ["JFK", "LGA"]
 * has a smaller lexical order than ["JFK", "LGB"].
 * All airports are represented by three capital letters (IATA code).
 * You may assume all tickets form at least one valid itinerary.
 * One must use all the tickets once and only once.
 *
 * Example 1:
 * Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
 * Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]
 *
 * Example 2:
 * Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"].
 *              But it is larger in lexical order.
 */

/**
 * 这个问题涉及了NonSimple graph, 具体说是multiGraph，还是挺偏的。
 * 然后算法书上，甚至是网上对multigraph的内容都是很少的。
 * 具体这个问题是下面“欧拉一笔画”问题的一个变种：
 * https://en.wikipedia.org/wiki/Eulerian_path
 * 下面中文讲得更清楚：
 * https://zh.wikipedia.org/wiki/%E4%B8%80%E7%AC%94%E7%94%BB%E9%97%AE%E9%A2%98
 */
public class ReconstructItinerary {
    public static void main(String[] args){
        String[][] array = {{"AXA","EZE"},{"EZE","AUA"},{"ADL","JFK"},{"ADL","TIA"},{"AUA","AXA"},{"EZE","TIA"},{"EZE","TIA"},{"AXA","EZE"},{"EZE","ADL"},{"ANU","EZE"},{"TIA","EZE"},{"JFK","ADL"},{"AUA","JFK"},{"JFK","EZE"},{"EZE","ANU"},{"ADL","AUA"},{"ANU","AXA"},{"AXA","ADL"},{"AUA","JFK"},{"EZE","ADL"},{"ANU","TIA"},{"AUA","JFK"},{"TIA","JFK"},{"EZE","AUA"},{"AXA","EZE"},{"AUA","ANU"},{"ADL","AXA"},{"EZE","ADL"},{"AUA","ANU"},{"AXA","EZE"},{"TIA","AUA"},{"AXA","EZE"},{"AUA","SYD"},{"ADL","JFK"},{"EZE","AUA"},{"ADL","ANU"},{"AUA","TIA"},{"ADL","EZE"},{"TIA","JFK"},{"AXA","ANU"},{"JFK","AXA"},{"JFK","ADL"},{"ADL","EZE"},{"AXA","TIA"},{"JFK","AUA"},{"ADL","EZE"},{"JFK","ADL"},{"ADL","AXA"},{"TIA","AUA"},{"AXA","JFK"},{"ADL","AUA"},{"TIA","JFK"},{"JFK","ADL"},{"JFK","ADL"},{"ANU","AXA"},{"TIA","AXA"},{"EZE","JFK"},{"EZE","AXA"},{"ADL","TIA"},{"JFK","AUA"},{"TIA","EZE"},{"EZE","ADL"},{"JFK","ANU"},{"TIA","AUA"},{"EZE","ADL"},{"ADL","JFK"},{"ANU","AXA"},{"AUA","AXA"},{"ANU","EZE"},{"ADL","AXA"},{"ANU","AXA"},{"TIA","ADL"},{"JFK","ADL"},{"JFK","TIA"},{"AUA","ADL"},{"AUA","TIA"},{"TIA","JFK"},{"EZE","JFK"},{"AUA","ADL"},{"ADL","AUA"},{"EZE","ANU"},{"ADL","ANU"},{"AUA","AXA"},{"AXA","TIA"},{"AXA","TIA"},{"ADL","AXA"},{"EZE","AXA"},{"AXA","JFK"},{"JFK","AUA"},{"ANU","ADL"},{"AXA","TIA"},{"ANU","AUA"},{"JFK","EZE"},{"AXA","ADL"},{"TIA","EZE"},{"JFK","AXA"},{"AXA","ADL"},{"EZE","AUA"},{"AXA","ANU"},{"ADL","EZE"},{"AUA","EZE"}};
        List<List<String>> list = new ArrayList<>();
        for(String[] ticket : array){
            List<String> temp = Arrays.asList(ticket);
            list.add(temp);
        }
        ReconstructItinerary instance = new ReconstructItinerary();
        System.out.println(instance.findItinerary(list));
    }

    /**
     * 这个题的第三种解法，其实恰恰就是我的第一感觉topological sort，只不过这跟普通的topo sort有变化的。
     * 这种写法，构造解是从edge node到root，我上面的写法都是从root到edge Node，所以我前面要把删掉的edge再补回去。
     * 但是这种写法的正确性却并不是很直观，证明如下：
     * src\main\resources\ReconstructItineraryTopoSort.jpg
     */
    public List<String> findItineraryTopoSort(List<List<String>> tickets) {
        if(tickets == null || tickets.size() == 0) {
            return new ArrayList<String>();
        }
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for(List<String> ticket : tickets) {
            String ticket0 = ticket.get(0);
            if(!graph.containsKey(ticket0)) {
                graph.put(ticket0, new PriorityQueue<String>());
            }
            graph.get(ticket0).offer(ticket.get(1));
        }
        List<String> res = new ArrayList<>();
        dfs(graph, res, "JFK");
        Collections.reverse(res);//注意记得reverse
        return res;
    }

    private void dfs(Map<String, PriorityQueue<String>> graph, List<String> res, String city) {
        PriorityQueue<String> ticket = graph.get(city);
        while(ticket != null && !ticket.isEmpty()) {
            String nextCity = ticket.poll();
            dfs(graph, res, nextCity);
        }
        res.add(0, city);
    }


    /**
     * 基于下面第二种解法的分析，不能把所有的解都找出来，事实上配合greedy思路，可以只把最优解找出来。
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, List<String>> graph = buildGraph(tickets);

        List<String> buf = new ArrayList<>();
        Map<String, Set<String>> visited = new HashMap<>();
        for(Map.Entry<String,List<String>> entry : graph.entrySet()){
            Set<String> set = new HashSet<>();
            visited.put(entry.getKey(), set);
            Collections.sort(entry.getValue());
            //System.out.println("start: " + entry.getKey() + ", list: " + entry.getValue());
        }
        dfs(graph, "JFK", buf, tickets.size(), visited);
        return buf;
    }

    private Map<String, List<String>> buildGraph(List<List<String>> tickets){
        Map<String, List<String>> graph = new HashMap<>();
        for(List<String> ticket : tickets){
            String start = ticket.get(0);
            String end = ticket.get(1);
            //注意这个graph只是给start建了entry，没有给end建entry，所以也就是说这个map里面并不包含所有的node
            //在做graph walking的时候要注意这一点，因为对于edge node，graph.get(node)会是Null
            //要么在做graph walking的时候，注意排查上面的问题，要么就索性在下面给end node也开一个entry，里面放个empty list就是了
            if(graph.containsKey(start)){
                List<String> list = graph.get(start);
                list.add(end);
                graph.put(start, list);
            }
            else{
                List<String> list = new ArrayList<>();
                list.add(end);
                graph.put(start, list);
            }
        }
        return graph;
    }

    private boolean dfs(Map<String, List<String>> graph, String root, List<String> buf, int total, Map<String, Set<String>> visited){
        buf.add(root);
        //termination
        if(!graph.containsKey(root) || graph.get(root).size()==visited.get(root).size()){
            if(buf.size()-1==total){
                return true;
            }
            buf.remove(buf.size()-1);
            return false;
        }

        for(int i=0; i<graph.get(root).size(); i++){
            String child = graph.get(root).get(i);
            //下面的构造dest的操作看起来很诡异，其实是为了解决从A到B有多条edge的情况，比如下面test中：
            //[["EZE","AXA"],["TIA","ANU"],["ANU","JFK"],["JFK","ANU"],["ANU","EZE"],["TIA","ANU"],["AXA","TIA"],["TIA","JFK"],["ANU","TIA"],["JFK","TIA"]]
            //从TIA到ANU有两张机票，那么这两张机票就要做区分，区分办法就是用“目的地”加上index
            String dest = child+i;
            if(!visited.get(root).contains(dest)){
                visited.get(root).add(dest);
                if(dfs(graph, child, buf, total, visited)){
                    return true;
                }
                //这里要注意要把这个visit给revert,这里形式上跟普通的directed graph dfs是一样的
                //但是所表达的意义却是不同的，普通的directed graph dfs用的visted是一个大set
                //而这里是每个Node一个自己的set，所以前者的目的是为了避免同一个node在visiting path上出现两次
                //后者其实允许这种情况，其目的是为了避免同一条edge在visiting path上出现两次
                //也就是说像A->C->D->A->B这种visiting path对普通的dfs是不允许的，但是这里是允许的
                //这里所不允许的是:A->B->C->A->B这种情况，注意(A->B)这条edge出现了两次
                visited.get(root).remove(dest);
            }
        }
        buf.remove(buf.size()-1);
        return false;
    }

    /**
     * 下面是我最开始的想法: 用dfs把所有的解都找到，然后做sort
     * 这种解法逻辑上是真确的，但是会timeout，因为问题的解空间太大。
     * 比如上述main函数里面的input, 生成的graph如下：
     * start: EZE, list: [ADL, ADL, ADL, ADL, ADL, ANU, ANU, AUA, AUA, AUA, AUA, AXA, AXA, JFK, JFK, TIA, TIA]
     * start: ANU, list: [ADL, AUA, AXA, AXA, AXA, AXA, EZE, EZE, TIA]
     * start: ADL, list: [ANU, ANU, AUA, AUA, AUA, AXA, AXA, AXA, AXA, EZE, EZE, EZE, EZE, JFK, JFK, JFK, TIA, TIA]
     * start: AXA, list: [ADL, ADL, ADL, ANU, ANU, EZE, EZE, EZE, EZE, EZE, JFK, JFK, TIA, TIA, TIA, TIA]
     * start: AUA, list: [ADL, ADL, ANU, ANU, AXA, AXA, AXA, EZE, JFK, JFK, JFK, SYD, TIA, TIA]
     * start: TIA, list: [ADL, AUA, AUA, AUA, AXA, EZE, EZE, EZE, JFK, JFK, JFK, JFK]
     * start: JFK, list: [ADL, ADL, ADL, ADL, ADL, ADL, ANU, AUA, AUA, AUA, AXA, AXA, EZE, EZE, TIA]
     * 这个问题的复杂度：
     * src\main\resources\ReconstructItineraryComplexity.PNG
     * 所以我们知道问题的复杂度可以近似用d^E去估计，然后从上面的graph可以看出d=18, E就更大了，那怕也取18
     * 18^18也是个巨大无比的天文数字
     */
    public List<String> findItineraryNoGreedy(List<List<String>> tickets) {
        //第一感觉很像topological sort，但是实际不是，实际是要找到一条路径走完所有的edge
        //然后还要这条路径是smallest lexical order
        //而且这里面有是有Loop,而且允许loop，所以就明显的不是topological sort
        //思路如下：首先build graph，然后做backtracking，过程中因为允许looping,又要避免一条edge走多次，每走完一条edge，就把那条edge删掉，回头backtracking的时候还要补回来
        //就是实现上，不是去删edge，因为从list里删东西效率不高，如果换成set，又不能简单的边Iterate边删，所以选择给每个Node单独建一个visitedSet
        //每一次当dfs进行不下去的时候，就检查已经走过的edge数量是否等于全部edge数量
        //最后还要再把所有满足条件的Itinerary sort一下，选出最小的一个
        Map<String, List<String>> graph = buildGraph(tickets);

        List<List<String>> res = new ArrayList<>();
        List<String> buf = new ArrayList<>();
        Map<String, Set<String>> visited = new HashMap<>();
        for(Map.Entry<String,List<String>> entry : graph.entrySet()){
            //System.out.println("start: " + entry.getKey() + ", list: " + entry.getValue());
            Set<String> set = new HashSet<>();
            visited.put(entry.getKey(), set);
        }
        dfs(graph, "JFK", res, buf, tickets.size(), visited);
        if(res.size()==0){
            return buf;
        }
        Collections.sort(res, (a, b)->{
            int cmp  = 0;
            for(int i=0; i<a.size(); i++){
                cmp = a.get(i).compareTo(b.get(i));
                if(cmp!=0){
                    break;
                }
            }
            return cmp;
        });
        //System.out.println("size: "+res.size());
        return res.get(0);
    }

    private void dfs(Map<String, List<String>> graph, String root, List<List<String>> res, List<String> buf, int total, Map<String, Set<String>> visited){
        buf.add(root);
        //termination
        if(!graph.containsKey(root) || graph.get(root).size()==visited.get(root).size()){
            if(buf.size()-1==total){
                List<String> temp = new ArrayList<>();
                temp.addAll(buf);
                res.add(temp);
            }
            buf.remove(buf.size()-1);
            return;
        }

        for(int i=0; i<graph.get(root).size(); i++){
            String child = graph.get(root).get(i);
            String key = child+i;
            if(!visited.get(root).contains(key)){
                visited.get(root).add(key);
                dfs(graph, child, res, buf, total, visited);
                //这里要注意要把这个visit给revert,这里形式上跟普通的directed graph dfs是一样的
                //但是所表达的意义却是不同的，普通的directed graph dfs用的visted是一个大set
                //而这里是每个Node一个自己的set，所以前者的目的是为了避免同一个node在visiting path上出现两次
                //后者其实允许这种情况，其目的是为了避免同一条edge在visiting path上出现两次
                //也就是说像A->C->D->A->B这种visiting path对普通的dfs是不允许的，但是这里是允许的
                //这里所不允许的是:A->B->C->A->B这种情况，注意(A->B)这条edge出现了两次
                visited.get(root).remove(key);
            }
        }
        buf.remove(buf.size()-1);
    }
}
