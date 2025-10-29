"""
Refer to: my-app\src\main\resources\Strongly Connected Components (SCC) and Bi-Connected Components (BCC).docx
Definition of Strongly Connected Components:
A strongly connected component (SCC) is a subset of nodes in a directed graph such that for any two nodes in the subset, 
there is a path from one to the other.
This means: any two nodes in the subset can form a cycle.
If you see this from a dependency graph perspective, then components in the same subset are all mutually dependent.

Tarjan's algorithm is a linear time algorithm to find strongly connected components in a directed graph.
Uses a single DFS traversal
Maintains a lowlink value to detect cycles and SCC boundaries
Runs in O(V + E) time (linear!)

Kosaraju's algorithm is a linear time algorithm to find strongly connected components in a directed graph.
1. Compute the finish order of the nodes in the original graph.
2. Compute the transpose graph.
3. DFS on the transpose graph in reverse finish order.

Among these two algorithms, Tarjan's algorithm is used more often as it works for both SCC and BCC.
"""

# --- Tarjan's algorithm ---
from typing import Dict, List, Hashable

def tarjan_scc(graph: Dict[Hashable, List[Hashable]]) -> List[List[Hashable]]:
    """
    Return list of SCCs; each SCC is a list of nodes.
    `graph` is an adjacency list: {node: [neighbors...]}.
    Nodes can be hashable types (int, str, etc).
    """
    index = {}           # node -> discovery index
    lowlink = {}         # node -> lowlink value
    on_stack = set()     # nodes currently on the stack
    stack = []           # DFS stack of nodes
    result = []          # list of SCCs
    next_index = 0

    def strongconnect(v):
        nonlocal next_index
        index[v] = next_index
        lowlink[v] = next_index
        next_index += 1
        stack.append(v)
        on_stack.add(v)

        for w in graph.get(v, []):
            if w not in index:
                # Successor w has not yet been visited; recurse on it.
                strongconnect(w)
                lowlink[v] = min(lowlink[v], lowlink[w])
            elif w in on_stack:
                # Successor w is in stack and hence in the current SCC.
                lowlink[v] = min(lowlink[v], index[w])

        # If v is a root node, pop the stack and generate an SCC
        if lowlink[v] == index[v]:
            scc = []
            while True:
                w = stack.pop()
                on_stack.discard(w)
                scc.append(w)
                if w == v:
                    break
            result.append(scc)

    # Ensure all nodes (even isolated ones) are visited
    nodes = set(graph.keys())
    for vs in graph.values():
        nodes.update(vs)
    for v in nodes:
        if v not in index:
            strongconnect(v)
    return result



def kosaraju_scc(graph: Dict[Hashable, List[Hashable]]) -> List[List[Hashable]]:
    # Collect all nodes (including those that appear only as neighbors)
    nodes = set(graph.keys())
    for vs in graph.values():
        nodes.update(vs)

    # Step 1: DFS order on original graph
    visited = set()
    order = []

    def dfs1(v):
        visited.add(v)
        for w in graph.get(v, []):
            if w not in visited:
                dfs1(w)
        order.append(v)  # postorder push

    for v in nodes:
        if v not in visited:
            dfs1(v)

    # Step 2: Build transpose graph
    gt: Dict[Hashable, List[Hashable]] = {v: [] for v in nodes}
    for u in graph:
        for w in graph[u]:
            gt[w].append(u)

    # Step 3: DFS on transpose in reverse finish order
    visited.clear()
    sccs: List[List[Hashable]] = []

    def dfs2(v, comp):
        visited.add(v)
        comp.append(v)
        for w in gt.get(v, []):
            if w not in visited:
                dfs2(w, comp)

    # Visit nodes in reverse finish order / "topological order"
    for v in reversed(order):
        if v not in visited:
            comp = []
            dfs2(v, comp)
            sccs.append(comp)
    return sccs

# --- Example usage ---
if __name__ == "__main__":
    g = {
        'A': ['B'],
        'B': ['C', 'E', 'F'],
        'C': ['D', 'G'],
        'D': ['C', 'H'],
        'E': ['A', 'F'],
        'F': ['G'],
        'G': ['F'],
        'H': ['D', 'G']
    }
    print(kosaraju_scc(g))
    # Output (order may vary): [['A', 'E', 'B'], ['C', 'D', 'H'], ['G', 'F']]
