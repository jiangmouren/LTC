"""
/**
 * https://leetcode.com/problems/critical-connections-in-a-network/
 * There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming a network
 * where connections[i] = [a, b] represents a connection between servers a and b.
 * Any server can reach any other server directly or indirectly through the network.
 * A critical connection is a connection that, if removed, will make some server unable to reach some other server.
 * Return all critical connections in the network in any order.
 *
 * Example 1:
 * Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
 * Output: [[1,3]]
 * Explanation: [[3,1]] is also accepted.
 *
 * Constraints:
 * 1 <= n <= 10^5
 * n-1 <= connections.length <= 10^5
 * connections[i][0] != connections[i][1]
 * There are no repeated connections.
 */
"""
from typing import List, Tuple, Dict

def find_bridges(n: int, connections: List[Tuple[int, int]]) -> List[Tuple[int, int]]:
    # Build graph
    graph: Dict[int, List[int]] = {i: [] for i in range(n)}
    for u, v in connections: 
        graph.get(u).append(v)
        graph.get(v).append(u)
    
    # Tarjan's algorithm
    time = [0]
    disc = {} # Discovery time
    low = {} # Lowest reachable ancestor
    parent = {} # DFS parent
    bridges = [] # List of bridges

    def dfs(u: int) -> None:
        disc[u] = low[u] = time[0]
        time[0] += 1
        
        for v in graph.get(u):
            if v not in disc:
                parent[v] = u
                dfs(v)
                low[u] = min(low[u], low[v])

                # Bridge condition
                if low[v] > disc[u]:
                    bridges.append((u, v))
            elif v != parent.get(u): # Back edge
                if disc[v] < disc[u]: # only when v is an ancestor of u
                    low[u] = min(low[u], disc[v])
    
    for i in range(n):
        if i not in disc:
            dfs(i)
    
    return bridges


if __name__ == "__main__":
    n = 4
    connections = [[0,1],[1,2],[2,0],[1,3]]
    print(find_bridges(n, connections))
    connections = [[0,1],[1,2],[2,0],[1,3],[3,2]]
    print(find_bridges(n, connections))
    n = 6
    connections = [[0,1],[1,2],[2,0],[1,3],[3,4],[4,5],[5,3]]
    print(find_bridges(n, connections))