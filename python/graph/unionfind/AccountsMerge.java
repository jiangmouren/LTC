package com.mycompany.app.graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/accounts-merge/
 * Given a list accounts, each element accounts[i] is a list of strings,
 * where the first element accounts[i][0] is a name,
 * and the rest of the elements are emails representing emails of the account.
 *
 * Now, we would like to merge these accounts.
 * Two accounts definitely belong to the same person if there is some email that is common to both accounts.
 * Note that even if two accounts have the same name,
 * they may belong to different people as people could have the same name.
 * A person can have any number of accounts initially, but all of their accounts definitely have the same name.
 *
 * After merging the accounts, return the accounts in the following format:
 * the first element of each account is the name, and the rest of the elements are emails in sorted order.
 * The accounts themselves can be returned in any order.
 *
 * Example 1:
 * Input:
 * accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"], ["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
 * Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  ["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]
 * Explanation:
 * The first and third John's are the same person as they have the common email "johnsmith@mail.com".
 * The second John and Mary are different people as none of their email addresses are used by other accounts.
 * We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'],
 * ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
 *
 * Note:
 * The length of accounts will be in the range [1, 1000].
 * The length of accounts[i] will be in the range [1, 10].
 * The length of accounts[i][j] will be in the range [1, 30].
 */


/**
 * 这个题既可以用DFS，也可以用UnionFind
 * 关键是要想清楚这里面的graph如何构建，核心就是PrimaryID是email，而不是name，per-email就是一个graphNode，name只是每个node的一个atrribute
 * 但是这个题目用DFS效率不如UnionFind，因为这里DFS构建graph,是O(n^2)的操作，这里n指的是每个account的email的数量，
 * 而且本质上是把已经Union到一起的email给拆散了，通过DFS再接起来，所以优先使用UnionFind
 * 具体写UnionFind又有两种思路，一种是去Union Accounts,另一种是直接去Union Emails,
 * 后者依然存在需要把已经Union在一个account下面的email在Union一遍的问题，所以前者效率更高
 */
public class AccountsMerge {
    class UnionFind{
        int[] parents;
        int[] ranks;
        public UnionFind(int n){
            this.parents = new int[n];
            this.ranks = new int[n];
            for(int i=0; i<n; i++){
                this.parents[i] = i;
                this.ranks[i] = 0;
            }
        }

        public int find(int x){
            if(x!=this.parents[x]){
                this.parents[x] = find(this.parents[x]);
            }
            return this.parents[x];
        }

        //return true if x and y successfully unioned; return false if x and y already unioned.
        public boolean union(int x, int y){
            int pX = find(x);
            int pY = find(y);
            if(pX==pY){
                return false;
            }
            else{
                if(this.ranks[pX]>this.ranks[pY]){
                    this.parents[pY] = pX;
                }
                else if(this.ranks[pX]<this.ranks[pY]){
                    this.parents[pX] = pY;
                }
                else{
                    this.parents[pY] = pX;
                    this.ranks[pX]++;
                }
                return true;
            }
        }
    }
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        UnionFind uf = new UnionFind(n);//每个account在list中的indx就是其id
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i<n; i++){
            for(int j=1; j<accounts.get(i).size(); j++){
                String email = accounts.get(i).get(j);
                if(map.containsKey(email)){
                    int preIdx = map.get(email);
                    uf.union(i, preIdx);
                }
                else{
                    map.put(email, i);
                }
            }
        }
        List<Set<String>> buf = new ArrayList<>();//buf email only
        for(int i=0; i<n; i++){
            buf.add(new HashSet<>());
        }
        for(int i=0; i<n; i++){
            int p = uf.find(i);
            for(int j=1; j<accounts.get(i).size(); j++){
                buf.get(p).add(accounts.get(i).get(j));
            }
        }
        List<List<String>> res = new ArrayList<>();
        for(int i=0; i<n; i++){
            Set<String> set = buf.get(i);
            String name = accounts.get(i).get(0);
            if(!set.isEmpty()){
                //TODO: Some very bad code in the following few lines
                List<String> temp = new ArrayList<>();
                temp.addAll(set);
                Collections.sort(temp);
                temp.add(0, name);
                res.add(temp);
            }
        }
        return res;
    }
}
