"""
A Binary Heap is a complete binary tree that satisfies the heap property:
    - For a max heap, the parent node is greater than or equal to its children
    - For a min heap, the parent node is less than or equal to its children
The actual implementation is a list, where the parent and children are at index i, 2i+1(left), 2i+2(right) respectively.
"""

# max heap
class BinaryHeap:
    def __init__(self):
        self.heap = []
    
    """
    return the parent index of the given index
    """
    @staticmethod
    def _parent(idx):
        return (idx-1)//2

    """
    return index of the left child of the given index
    """
    @staticmethod
    def _left_child(idx):
        return 2*idx + 1
    
    """
    return index of the right child of the given index
    """
    @staticmethod
    def _right_child(idx):
        return 2*idx + 2
    
    """
    Assuming the left and right subtrees are already max heaps, heapify the given index
    bubble down: O(lgn)
    """
    @staticmethod
    def _max_heapify(arr, idx, heap_size):
        idx_left = BinaryHeap._left_child(idx)
        idx_right = BinaryHeap._right_child(idx)
        idx_largest = idx if idx_left < heap_size and arr[idx] > arr[idx_left] else idx_left
        idx_largest = idx_right if idx_right < heap_size and arr[idx_right] > arr[idx_largest] else idx_largest
        if idx_largest != idx: 
            arr[idx], arr[idx_largest] = arr[idx_largest], arr[idx]
            BinaryHeap._max_heapify(arr, idx_largest, heap_size)
    
    """
    build a max heap from the given array
    the last element is at len(arr)-1 or l-1
    the parent of the last element is at (l-1-1)//2 = l//2 -1
    anything after that is a leaf node, meaning we don't need to heapify them.
    So all we need to do is to heapify from 1//2 -1 to 0
    NOTE: we need to heapify from the last non-leaf node to the root, 
    this way the left or right subtrees are already max heaps.
    O(n)
    """
    @staticmethod
    def build_max_heap(arr):
        for i in reversed(range(len(arr)//2)):
            BinaryHeap._max_heapify(arr, i, len(arr))

    """
    sort the given array in ascending order
    O(nlgn)
    """
    @staticmethod
    def heap_sort(arr):
        BinaryHeap.build_max_heap(arr)
        heap_size = len(arr)
        for i in reversed(range(1, len(arr))):
            arr[0], arr[i] = arr[i], arr[0]
            heap_size -= 1
            BinaryHeap._max_heapify(arr, 0, heap_size)

    def _bubble_up(self, idx):
        i = idx
        while i>0 and self.heap[i] > self.heap[BinaryHeap._parent(i)]:
            parent_idx = BinaryHeap._parent(i)
            self.heap[i], self.heap[parent_idx] = self.heap[parent_idx], self.heap[i]
            i = parent_idx
    
    """
    insert the given value into the heap
    O(lgn)
    """
    def insert(self, val):
        self.heap.append(val)
        self._bubble_up(len(self.heap)-1)

    """
    extract the max value from the heap
    O(lgn)
    """
    def extract_max(self):
        max = self.heap[0]
        self.heap[0] = self.heap[len(self.heap)-1]
        self.heap.pop()
        BinaryHeap._max_heapify(self.heap, 0, len(self.heap))
        return max

    """
    return the max value of the heap
    O(1)
    """
    def get_max(self):
        return self.heap[0]

    """
    update the key at the given index
    O(lgn)
    """
    def update_key(self, idx, val):
        self.heap[idx] = val
        if val < self.heap[idx]:
            BinaryHeap._max_heapify(self.heap, idx, len(self.heap))
        else:
            self._bubble_up(idx)

if __name__ == "__main__":
    binary_heap = BinaryHeap()
    binary_heap.insert(1)
    binary_heap.insert(2)
    binary_heap.insert(3)
    binary_heap.insert(4)
    binary_heap.insert(5)
    print(binary_heap.heap)
    print(binary_heap.extract_max())
    print(binary_heap.heap)
    print(binary_heap.get_max())
    binary_heap.update_key(2, 6)
    print(binary_heap.heap)