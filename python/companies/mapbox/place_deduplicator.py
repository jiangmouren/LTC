"""
At Mapbox we process many sources of place data to create a single place dataset. Occasionally our ingestion system will not correctly conflate a place to an already existing place in our dataset and we end up with duplicate places. To handle these cases we want to build a system to manually deduplicate places.

For the purposes of this question we will represent places just by their non-negative integer id and the system will be a class. When the system is constructed it is given a list of all possible ids and you can initialize whatever data structures you wish. The class must implement 3 functions that satisfy the 3 requirements: 1. Must be able to mark a place as a duplicate of another place 2. Must be able to check whether two places are duplicates 3. Must be able to get the new effective id of a place if it’s been marked as a duplicate. This is the lowest id of all duplicates of that place.

Example in Python: Let’s assume there are 5 places in our current dataset: [0, 8, 2, 1, 15] Here are a list of functions run (in order) and the expected result of each:

pd = PlaceDeduplicator([0, 8, 2, 1, 15]) -> PlaceDeduplicator
pd.get_effective_id(1) -> 1
pd.are_duplicates(0, 1) -> False
pd.mark_duplicates(1, 0) -> None
pd.get_effective_id(1) -> 0
pd.are_duplicates(0, 1) -> True
pd.mark_duplicates(1, 2) -> None
pd.get_effective_id(0) -> 0
pd.are_duplicates(0, 2) -> True
pd.mark_duplicates(8, 15) -> None
pd.are_duplicates(2, 8) -> False
pd.get_effective_id(15) -> 8
"""

from typing import List
import math

class UnionFind:
    def __init__(self, n):
        # parent[i] stores the parent of element i. 
        # Initially, each element is its own parent (representative of its own set).
        self.parent = list(range(n))
        # rank[i] stores the rank (approximate height) of the tree rooted at i.
        # Used for union by rank optimization.
        self.rank = [0] * n

    def find(self, i):
        # Path Compression: If i is not the root of its set, 
        # recursively find the root and set it as the direct parent of i.
        if self.parent[i] == i:
            return i
        self.parent[i] = self.find(self.parent[i])
        return self.parent[i]

    def union(self, i, j):
        # Find the representatives (roots) of the sets containing i and j.
        root_i = self.find(i)
        root_j = self.find(j)

        # If they are already in the same set, do nothing.
        if root_i != root_j:
            # Union by Rank: Attach the smaller rank tree under the root of the larger rank tree.
            # This helps to keep the trees relatively flat.
            if self.rank[root_i] < self.rank[root_j]:
                self.parent[root_i] = root_j
            elif self.rank[root_i] > self.rank[root_j]:
                self.parent[root_j] = root_i
            else:
                # If ranks are equal, attach one to the other and increment the rank of the new root.
                self.parent[root_j] = root_i
                # Note the rank list is only updated when the ranks of both roots are the same. 
                # When they are not equal, attaching the short one to the long one, will not change the rank of the long one
                # Neithe will the rank of the short one change. 
                self.rank[root_i] += 1
            return True  # Union performed
        return False # Already in the same set

    def connected(self, i, j):
        # Check if two elements are in the same set by comparing their roots.
        return self.find(i) == self.find(j)


class PlaceDeduplicator:

    def __init__(self, places: List[int]) -> None:
        """Construct a new PlaceDeduplicator.

        Args: places is a list of all existing place ids

        IMPLEMENT
        """
        self.id_map = {}
        for i, place in enumerate(places):
            self.id_map[place] = i
        self.uf = UnionFind(len(places))
        self.places = places

    def mark_duplicates(self, place_id_1: int, place_id_2: int) -> None:
        """Mark two places as duplicates.

        IMPLEMENT
        """
        self.uf.union(self.id_map[place_id_1], self.id_map[place_id_2])

    def are_duplicates(self, place_id_1: int, place_id_2: int) -> bool:
        """Return whether or not two places are duplicates.

        IMPLEMENT
        """
        return self.uf.connected(self.id_map[place_id_1], self.id_map[place_id_2])

    def get_effective_id(self, place_id: int) -> int:
        """Return the lowest id among all places it's a duplicate of.

        This is the new "effective" id for the duplicate places.

        IMPLEMENT
        """
        effective_id = place_id
        parent_id = self.uf.find(self.id_map[place_id])
        for place in self.places:
            if parent_id == self.uf.find(self.id_map[place]):
                effective_id = min(effective_id, place)
        return effective_id


# example
pd = PlaceDeduplicator([0, 8, 2, 1, 15])
assert pd.get_effective_id(1) == 1
pd.mark_duplicates(0, 1)
assert pd.get_effective_id(1) == 0
assert pd.are_duplicates(0, 1) is True
pd.mark_duplicates(1, 2)
assert pd.get_effective_id(0) == 0
assert pd.are_duplicates(0, 2) is True
pd.mark_duplicates(8, 15)
assert pd.are_duplicates(2, 8) is False
assert pd.get_effective_id(15) == 8


