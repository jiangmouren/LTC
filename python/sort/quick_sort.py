def quick_sort(arr):
    if arr==None:
        raise TypeError("arr cannot be None")
    _quick_sort_helper(arr, 0, len(arr)-1)

def _quick_sort_helper(arr, start, end):
    pivot = _partition(arr, start, end)
    if pivot-1>start:
        _quick_sort_helper(arr, start, pivot-1)
    if pivot+1<end:
        _quick_sort_helper(arr, pivot+1, end)

def _partition(arr, start, end):
    # take arr[start] as the pivot point, right > and left <=
    ptr1, ptr2 = start+1, end
    while ptr1<=ptr2:
        while ptr2>start and arr[ptr2]>arr[start]: # not point checking ptr2==start
            ptr2 -= 1
        while ptr1<=end and arr[ptr1]<=arr[start]:
            ptr1 += 1
        if(ptr1<ptr2):
            arr[ptr1], arr[ptr2] = arr[ptr2], arr[ptr1]
    arr[start], arr[ptr2] = arr[ptr2], arr[start]
    return ptr2

"""
The iterative way to write this is look at the recursive partition operations as
a tree traversal. And we need to process the root node first. Meaning it's either
a bfs or a pre-order dfs. 
"""
from collections import deque
def quick_sort_iter(arr):
    q = deque()
    root = (0, len(arr)-1)
    q.append(root)
    while q:
        root = q.popleft()
        pivot = _partition(arr, root[0], root[1])
        if pivot-1>root[0]:
            left = (root[0], pivot-1)
            q.append(left)
        if pivot+1<root[1]:
            right = (pivot+1, root[1])
            q.append(right)

if __name__ == "__main__":
    test_1 = [0]
    test_2 = [10, 2, 21, -1]
    test_3 = [1, 2, 3]
    print("test_1: ", test_1)
    quick_sort_iter(test_1)
    print("sorted test_1: ", test_1)

    print("test_2: ", test_2)
    quick_sort_iter(test_2)
    print("sorted test_2: ", test_2)

    print("test_3: ", test_3)
    quick_sort_iter(test_3)
    print("sorted test_3: ", test_3)