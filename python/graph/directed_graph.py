"""
Analysis:
When you are doing cycle detection, instead of unvisited/visited, you will have one more state as visiting. 
Basically, not all connected vertices will be visited because of the directions.

Another detail is becuase of the direciton, not all nodes will be in the graph.keys(), as some nodes will only have incoming edges.

What does it mean for shortest distance in case of a directed graph?
In the undirected graph, that is a 2-way distance, but in directed graph, it is a 1-way distance.
Meaning the path from v1-->v2, and the one from v2-->v1 are different.

So in this case, you cannot do a 2-way BFS. And if there do exit both paths from v1-->v2 and v2-->v1,
that means there is a cycle containing v1 and v2.
A derived question from the above analysis could be:
In a given directed graph, what is the smallest cycle that contains both v1 and v2?
What you do is do shortest path for both v1-->v2 and v2-->v1 and then combine them.

Attention when doing DFS or BFS, unless told graph is a DAG, otherwise has to be careful for cycles.

BFS, DFS, CircleDetection
topologicalSort()
"""
from typing import List, Hashable, Dict
from collections import deque
def bfs(graph: {Hashable: List[Hashable]}, start: Hashable) -> List[Hashable]:
    res = []
    visited = set()
    queue = deque()
    queue.append(start)
    visited.add(start)
    while queue:
        node = queue.popleft()
        res.append(node)
        for nbor in graph.get(node, []):
            if nbor not in visited:
                visited.add(nbor)
                queue.append(nbor)
    return res

def _dfs_helper(graph: {Hashable: List[Hashable]}, node: Hashable, visited: set, res: List[Hashable]) -> None:
    visited.add(node)
    res.append(node)
    for nbor in graph.get(node, []):
        if nbor not in visited: 
            _dfs_helper(graph, nbor, visited, res)
    
def dfs(graph: {Hashable: List[Hashable]}) -> List[List[Hashable]]:
    visited = set()
    res = []
    nodes = set(graph.keys())
    # collect all nodes, some might only have incoming edges
    for vs in graph.values():
        nodes.update(vs)
    for node in nodes:
        if node not in visited:
            partial_res = []
            _dfs_helper(graph, node, visited, partial_res)
            res.append(partial_res)
    return res

# Assuming graph is a DAG
# Must use post-order dfs for topological sort: Only the finish time, not the discovery time guarantees the order
def _topological_sort_helper(graph: {Hashable: List[Hashable]}, node: Hashable, visited: set, res: List[Hashable]) -> None:
    visited.add(node)
    for nbor in graph.get(node, []):
        if nbor not in visited:
            _topological_sort_helper(graph, nbor, visited, res)
    res.append(node)

def topological_sort(graph: {Hashable: List[Hashable]}) -> List[Hashable]:
    visited = set()
    res = []
    # collect all nodes, some might only have incoming edges
    nodes = set(graph.keys())
    for vs in graph.values():
        nodes.update(vs)
    for node in nodes:
        if node not in visited:
            _topological_sort_helper(graph, node, visited, res)
    res.reverse()
    return res

# color: 0: white, 1: gray, 2: black
def _is_acyclic_helper(graph: {Hashable: List[Hashable]}, node: Hashable, color: Dict[Hashable, int]) -> bool:
    color[node] = 1
    for nbor in graph.get(node, []):
        if color[nbor] == 1:
            return False
        if color[nbor] == 0:
            if not _is_acyclic_helper(graph, nbor, color):
                return False
    color[node] = 2
    return True

def is_acyclic(graph: {Hashable: List[Hashable]}) -> bool:
    # collect all nodes, some might only have incoming edges
    nodes = set(graph.keys())
    for vs in graph.values():
        nodes.update(vs)
    color = {node: 0 for node in nodes}
    for node in nodes:
        if color[node] == 0:
            if not _is_acyclic_helper(graph, node, color):
                return False
    return True

if __name__ == "__main__":
    graph = {
        "A": ["B", "C"],
        "B": ["D"],
        "C": ["D"],
        "E": ["D"]
    }
    print(bfs(graph, "A"))
    print(dfs(graph))
    print(topological_sort(graph))
    print(is_acyclic(graph))
    
    graph = {
        "A": ["B", "C"],
        "B": ["D"],
        "C": ["D"],
        "D": ["A"],
        "E": ["D"]
    }
    print(bfs(graph, "A"))
    print(dfs(graph))
    print(is_acyclic(graph))