"""
Union-Find is a data structure for disjoint-set operations, such as finding the representative of a set, merging two sets, and checking if two elements are in the same set.
With Path Compression and Union by Rank, the amortized time complexity is O(1) for each operation.
"""
class UnionFind:
    def __init__(self, n):
        # parent[i] stores the parent of element i.
        # Initially, each element is its own parent (representative of its own set).
        self.parent = list(range(n))
        # rank[i] stores the rank (approximate height) of the tree root at i.
        # Used for union by rank optimization. 
        self.rank = [0] * n

    def find(self, i):
        # Path Compression: If i is not the root of its set
        # recursively find the root and set it as the direct parent of i.
        if self.parent[i] == i:
            return i
        self.parent[i] = self.find(self.parent[i])
        return self.parent[i]

    def union(self, i, j):
        # Find the representatives (roots) of the sets containing i and j
        root_i = self.find(i)
        root_j = self.find(j)

        # if they are already in the same set, do nothing.
        if root_i != root_j:
            # Union by rank: Attach the smaller rank tree under the root of the larger rank tree.
            # This helps to keep the trees flat.
            if self.rank[root_i] < self.rank[root_j]:
                self.parent[root_i] = root_j
            elif self.rank[root_i] > self.rank[root_j]:
                self.parent[root_j] = root_i
            else:
                # if they are the same, attach one to another and increment the rank of the new root by one.
                # rank is only updated when when both are equal, otherwise, there will by no rank changes.
                self.parent[root_j] = root_i
                self.rank[root_i] += 1
            return True # union performed
        return False # nothing is done

    def connected(self, i, j):
        return self.find(i) == self.find(j)