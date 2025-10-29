# refer to: my-app\src\main\resources\BellmanFord.docx 
def bellman_ford(vertices, edges, src):
    """
    vertices: number of vertices
    edges: list of (u, v, w) tuples for edge u->v with weight w
    src: starting vertex
    """
    # step 1: initialize distances
    dist = [float("inf")] * vertices
    dist[src] = 0

    # step 2: relax all edges V-1 times
    for i in range(vertices - 1):
        for u, v, w in edges: 
            if dist[u] != float("inf") and dist[u] + w < dist[v]:
                dist[v] = dist[u] + w
    
    # step 3: check for negative weight cycles
    for u, v, w in edges:
        if dist[u] != float("inf") and dist[u] + w < dist[u]:
            raise ValueError("Negative weight cycle detected")
    
    return dist