"""
Implement DFS and BFS and Circle Detection.

Note: there is no forward and cross edges in undirected graph.
"""
from typing import Hashable, List
from collections import deque

"""
# one way to represent graph is using a set of list of nodes like below.
class Node:
    def __init__(self, val:int, neighbors:List['Node'] = []):
        self.val = val
        self.neighbors = neighbors
    
    def __hash__(self):
        return hash(self.val)

    def __eq__(self, other):
        if not isinstance(other, 'Node'):
            return False
        return self.val == other.val
"""
# Another way is to present a graph as {Hashable: List{Hashable}}
# DFS will need to consider disconnected subgraphs. 
# BUT BFS usually only consider the travasel from a single source. 
# There is not a particular reason for this, but rather a common practice.
def _dfs_helper(graph: {Hashable: List[Hashable]}, node: Hashable, visited: set, res: List[Hashable]):
    visited.add(node)
    res.append(node)
    for neighbor in graph.get(node, []):
        if neighbor not in visited:
            _dfs_helper(graph, neighbor, visited, res)

def dfs(graph: {Hashable: List[Hashable]}) -> List[List[Hashable]]:
    visited = set()
    res = []
    for node in graph.keys():
        if node not in visited: 
            partial_res = []
            _dfs_helper(graph, node, visited, partial_res) 
            res.append(partial_res)
    return res

def bfs(graph: {Hashable: List[Hashable]}, source: Hashable) -> List[Hashable]:
    visited = set()
    queue = deque()
    res = []
    queue.append(source)
    visited.add(source)
    while queue:
        node = queue.popleft()
        res.append(node)
        for neighbor in graph.get(node, []):
            if neighbor not in visited:
                visited.add(neighbor)
                queue.append(neighbor)
    return res

def _circle_detection_helper(graph: {Hashable: List[Hashable]}, node: Hashable, visited: set, parent: Hashable) -> bool:
    visited.add(node)
    for neighbor in graph.get(node, []):
        if neighbor in visited and neighbor is not parent:
            return True
        elif neighbor not in visited:
            _circle_detection_helper(graph, neighbor, visited, node)
    return False

def circle_detection(graph: {Hashable: List[Hashable]}) -> bool:
    visited = set()
    for node in graph.keys():
        if node not in visited: 
            if _circle_detection_helper(graph, node, visited, None):
                return True
    return False

if __name__ == "__main__":
    graph = {
        'A': ['B', 'C'],
        'B': ['A', 'D'],
        'C': ['A', 'D'],
        'D': ['B', 'C'],
        'E': ['F'],
        'F': ['E']
    }
    print(dfs(graph))
    print(bfs(graph, 'A'))
    print(circle_detection(graph))
