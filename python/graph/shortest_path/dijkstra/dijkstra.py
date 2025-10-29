"""
Dijkstra's Algorithm, assumes there is no negative edge
It is a greedy algorithm for a special case of single sorce shortest path problem. 
"""

import heapq
from typing import List, Tuple, Dict

def dijkstra(graph: List[List[Tuple[int, int]]], src: int, dest: int) -> Tuple[List[int], float]:
    """
    Args:
        graph: Adjacency list where the tuple contains (neighbor, weight)
        src: source node
        dest: destination node

    Returns:
        Tuple of (path, total_cost). Returns ([], float('inf')) if there is no path exits.    
    """

    # Map to store (distance, node_id, parent) - serves as both visited set and distance storage
    visited_map = {} # Maps node_id to (distance, node_id, parent), essentially what's in the priority queue
    pq = [(0, src, src)] # priority queue: (distance, node_id, parent)
    found = False

    while pq:
        distance, current, parent = heapq.heappop(pq)

        # mark as visited and store the distance and parent
        if current not in visited_map:
            visited_map[current] = (distance, current, parent)

        # early termination when dest is reached
        if current == dest:
            found = True
            break

        # explore neighbors
        for nbor, weight in graph[current]:
            if nbor not in visited_map:
                heapq.heappush(pq, (distance + weight, nbor, current))
    
    # recosntruct the path
    if not found:
        return [], float('inf')
    
    path = []
    node = dest
    while node!= src:
        path.append(node)
        node = visited_map[node][2]
    path.append(src)
    path.reverse()

    return path, visited_map[dest][0]

def dijkstra_all_dests(graph: List[List[Tuple[int,int]]], src: int) -> Tuple[List[float],List[int]]:
    """
    Find the shortest path from src to all other nodes.
    Returns: Tuple of (distances, parents)
    """
    visited_map = {} # Maps node_id to (distance, node_id, parent)
    pq = [(0, src, src)] # priority queue: (distance, node_id, parent)

    while pq:
        distance, current, parent = heapq.heappop(pq)

        # mark as visited and store the distance and parent
        # We need to do this check to avoid duplicated entries update the map again. 
        # The first entry that surfaced out has the shortes path. 
        # Other ones are only there because python priority queue has no decrease key method, so we have to these duplicated entries. 
        if current not in visited_map:
            visited_map[current] = (distance, current, parent)

        # explore neighbors
        for nbor, weight in graph[current]:
            if nbor not in visited_map:
                heapq.heappush(pq, (distance + weight, nbor, current))

    # convert to arrays for compatibility
    n = len(graph)
    parents = [-1] * n
    distances = [float('inf')] * n

    for node, (dist, _, par) in visited_map.items():
        parents[node] = par
        distances[node] = dist

    return distances, parents


if __name__ == "__main__":
    graph = [
        [(1, 4), (2, 1)], # Node 0
        [(3, 1)], # Node 1
        [(1, 2)], # Node 2
        [] # Node 3
    ]

    # Test 1: Find path from 0 to 3
    path, cost = dijkstra(graph, 0, 3)
    print(f"Shortest path from 0 to 3: {path}, cost: {cost}")

    # Test 2: Find all shortest paths from node 0
    distances, parents = dijkstra_all_dests(graph, 0)
    print(f"Distances from 0: {distances}")
    print(f"Parents: {parents}")

    # Test 3: No path exists
    graph_no_path = [
        [(1, 1)], # Node 0
        [], # Node 1
        [] # Node 2
    ]
    path, cost = dijkstra(graph_no_path, 0, 2)
    print(f"Path from 0 to 2 (no path): {path}, cost: {cost}")
    
    # Test 4: Pure cycle without destination (would cause infinite loop in old version)
    graph_cycle = [
        [(1, 1)], # Node 0
        [(2, 1)], # Node 1
        [(0, 1)], # Node 2
        [] # Node 3
    ]
    path, cost = dijkstra(graph_cycle, 0, 3)
    print(f"Path from 0 to 3 (no path): {path}, cost: {cost}")
    print(f"Expected: [] (no path)")

    # Test 5: Cycle with unreachable destination
    graph_unreachable = [
        [(1, 1)], # Node 0
        [(0, 1)], # Node 1
        [(3, 1)], # Node 2
        [] # Node 3
    ]
    path, cost = dijkstra(graph_unreachable, 0, 2)
    print(f"Path from 0 to 2 (unreachable): {path}, cost: {cost}")
    print(f"Expected: [] (no path)")