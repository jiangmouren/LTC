"""
Refer to: my-app\src\main\resources\Strongly Connected Components (SCC) and Bi-Connected Components (BCC).docx
Biconnected Components using Tarjan's algorithm
"""

from typing import Hashable, Dict, List, Set, Tuple
from collections import deque

# In terms of when to use class and when to next everything in a function,
# If everything stored is really only for the function, then use function.
def tarjan_bcc(graph: Dict[Hashable, List[Hashable]]):
    """
    Extract biconnected components, articulation points, and bridges in an undirected graph.
    
    Returns:
        bccs: List of biconnected components (each BCC is a list of edges)
        articulation_points: Set of articulation points
        bridges: List of bridges (edges that disconnect the graph)
    """

    time = [0]
    disc = {}         # Discovery time
    """
    low[v] is the lowest discovery time that can be reached from vertex v using:
        Tree edges (DFS edges)
        At most one back edge
    """
    low = {}          # Lowest reachable ancestor
    parent = {}       # DFS parent, you need this to avoid going back to the parent node and locating a root node
    edge_stack = deque()   # Stack of edges
    bccs = []         # List of BCCs
    articulation_points: Set[Hashable] = set()
    bridges: List[Tuple[Hashable, Hashable]] = []

    def extract_bcc(u: Hashable, v: Hashable) -> None:
        """Extract a BCC by popping edges from stack until finding edge (u,v) or (v,u)"""
        bcc = []
        while edge_stack:
            e = edge_stack.pop()
            bcc.append(e)
            if e == (u, v) or e == (v, u):
                break
        bccs.append(bcc)

    def dfs(u):
        disc[u] = low[u] = time[0]
        time[0] += 1
        child_count = 0
        is_root = parent.get(u) is None
        root_children = deque()  # Track children of root for BCC extraction

        for v in graph[u]:
            if v not in disc:  # Tree edge
                parent[v] = u
                edge_stack.append((u, v))
                child_count += 1
                
                # Track children of root for BCC extraction
                if is_root:
                    root_children.append(v)
                
                dfs(v)
                low[u] = min(low[u], low[v])

                # Bridge condition
                if low[v] > disc[u]:
                    bridges.append((u, v))

                # Non-root articulation point condition
                if not is_root and low[v] >= disc[u]:
                    # u is an articulation point — pop BCC
                    articulation_points.add(u)
                    extract_bcc(u, v)

            elif v != parent.get(u):  # Back edge
                if disc[v] < disc[u]:  # Avoid double-pushing
                    edge_stack.append((u, v))
                    """
                    When disc[v]>=disc[u], you are reversely traversing a back-edge that has been previously traversed. 
                    We know for sure low[u]<=disc[u], and if we further know disc[v]>=disc[u], then it's guaranteed that disc[v]>=low[u]. 
                    So no point of doing low[u] = min(low[u], disc[v]).
                    """
                    # here we use disc[v] instead of low[v]
                    # low indidate lowest reachable ancestor, which is disc[v], not low[v]
                    # low[v] might counted an disc time of a ancestor of v, which is not rechable following dfs for u.
                    low[u] = min(low[u], disc[v])
        
        # Root articulation point condition - check after all children are processed
        if is_root and child_count > 1:
            articulation_points.add(u)
            # Extract BCCs for each child subtree using edge-based approach
            while root_children:
                child = root_children.pop()
                extract_bcc(u, child)

    for u in graph:
        if u not in disc:
            dfs(u)

            # If edges remain in stack, they form a BCC
            # In the code above, we are only extracting BCCs if we find an articulation point.
            # However, if there is no articulation point, then the graph is still biconnected.
            if edge_stack:
                bcc = []
                while edge_stack:
                    bcc.append(edge_stack.pop())
                bccs.append(bcc)

    return bccs, articulation_points, bridges


def print_bcc_analysis(graph, bccs, articulation_points, bridges):
    """Helper function to print the analysis results"""
    print("Graph:", graph)
    print("\nBiconnected Components:")
    for i, bcc in enumerate(bccs):
        print(f"BCC {i+1}: {bcc}")
    
    print(f"\nArticulation Points: {articulation_points}")
    print(f"Bridges: {bridges}")
    
    # Print BCCs as vertex sets for easier understanding
    print("\nBCCs as vertex sets:")
    for i, bcc in enumerate(bccs):
        vertices = set()
        for u, v in bcc:
            vertices.add(u)
            vertices.add(v)
        print(f"BCC {i+1} vertices: {vertices}")


if __name__ == "__main__":
    # Test case 1: Simple graph with articulation point
    graph1 = {
        'A': ['B', 'C'],
        'B': ['A', 'C'],
        'C': ['A', 'B', 'D'],
        'D': ['C', 'E', 'F'],
        'E': ['D', 'F'],
        'F': ['D', 'E']
    }

    print("=== Test Case 1 ===")
    bccs1, articulation_points1, bridges1 = tarjan_bcc(graph1)
    print_bcc_analysis(graph1, bccs1, articulation_points1, bridges1)
    
    # Test case 2: Graph with multiple articulation points
    graph2 = {
        'A': ['B'],
        'B': ['A', 'C', 'D'],
        'C': ['B'],
        'D': ['B', 'E'],
        'E': ['D', 'F'],
        'F': ['E']
    }
    
    print("\n=== Test Case 2 ===")
    bccs2, articulation_points2, bridges2 = tarjan_bcc(graph2)
    print_bcc_analysis(graph2, bccs2, articulation_points2, bridges2)

    # Test case 3: Root articulation point with multiple children
    graph3 = {
        'R': ['A', 'B', 'C'],
        'A': ['R', 'D', 'E'],
        'B': ['R', 'F'],
        'C': ['R', 'G'],
        'D': ['A'],
        'E': ['A'],
        'F': ['B'],
        'G': ['C']
    }
    
    print("\n=== Test Case 3 (Root Articulation Point) ===")
    bccs3, articulation_points3, bridges3 = tarjan_bcc(graph3)
    print_bcc_analysis(graph3, bccs3, articulation_points3, bridges3)

    # Test case 4: Simple root articulation point
    graph4 = {
        'R': ['A', 'B'],
        'A': ['R'],
        'B': ['R']
    }
    
    print("\n=== Test Case 4 (Simple Root Articulation Point) ===")
    bccs4, articulation_points4, bridges4 = tarjan_bcc(graph4)
    print_bcc_analysis(graph4, bccs4, articulation_points4, bridges4)
