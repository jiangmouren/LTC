def bubble_sort(arr):
    """
    Sorts a list in-place using bubble sort.
    :param arr: List of comparable elements
    """
    n = len(arr)
    for i in range(n):
        # Last i elements are already sorted
        for j in range(n-1-i):
            # Swap if the element is greater than the next element
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]

if __name__ == "__main__":
    data = [64, 34, 88, 12, 0, 90, -1]
    print("Input data: ", data)
    bubble_sort(data)
    print("Sorted: ", data)
