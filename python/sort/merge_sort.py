"""
Analysis:
The property of Merge sort:
1. Worst, Average, Best all have the same run time complexity: NlgN.(Or say NlgN guaranteed.)
2. Stable.
3. Not in place. O(N) space complexity.

In common comparison sort, this is the only NlgN guaranteed algorithm.
Plus it is also stable, the other stable sort is insertion sort.
Only drawback is the extra array needed.
"""
def merge_sort(arr):
    if arr == None: 
        raise TypeError("input cannot be None")
    buf = []
    _merge_sort_helper(arr, buf, 0, len(arr)-1)

def _merge_sort_helper(arr, buf, start, end):
    # Termination case
    if start >= end:
        return
    
    # Recursive case
    mid = (start + end) // 2 # use floor division to round down to int
    _merge_sort_helper(arr, buf, start, mid)
    _merge_sort_helper(arr, buf, mid+1, end)
    _merge(arr, buf, start, mid, end)

def _merge(arr, buf, start, mid, end):
    buf[start: end+1] = arr[start: end+1]
    ptr, ptr1, ptr2 = start, start, mid+1
    while ptr1<=mid and ptr2<=end:
        if buf[ptr1]<=buf[ptr2]:
            arr[ptr] = buf[ptr1]
            ptr1 += 1
        else:
            arr[ptr] = buf[ptr2]
            ptr2 += 1
        ptr += 1

    # processing leftovers
    # if reminder is from left, copy it over
    # if reminder is from the right, they are already in arr
    if ptr1<=mid:
        arr[ptr: end+1] = buf[ptr1: mid+1]
"""
先2个一组merge，从左到右做一遍
再4个一组merge, 从左到右做一遍
直到merge的size大于等于len(arr)
"""
def merge_sort_iter(arr):
    l = 1
    buf = []
    while(l<len(arr)):
        start = 0
        while(start<len(arr)):
            # 注意尾部不足一组的时候的溢出
            end = start + 2*l -1 if start + 2*l -1 < len(arr) else len(arr)-1
            mid = (start+end)//2
            _merge(arr, buf, start, mid, end)
            start = end + 1
        l *= 2

     
if __name__ == "__main__":
    test_1 = [0]
    test_2 = [10, 2, 21, -1]
    test_3 = [1, 2, 3]
    print("test_1: ", test_1)
    merge_sort_iter(test_1)
    print("sorted test_1: ", test_1)

    print("test_2: ", test_2)
    merge_sort_iter(test_2)
    print("sorted test_2: ", test_2)

    print("test_3: ", test_3)
    merge_sort_iter(test_3)
    print("sorted test_3: ", test_3)