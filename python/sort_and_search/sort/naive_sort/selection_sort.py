def selection_sort(arr):
    """
    Sorts a list in-place using selection sort
    :param arr: List of comparable elements
    """
    n = len(arr)
    # The last swapping target is at "n-2"
    for i in range(n-1):
        min_pos = i
        for j in range(i, n):
            if arr[j] < arr[min_pos]:
                min_pos = j
        arr[i], arr[min_pos] = arr[min_pos], arr[i]

if __name__ == "__main__":
    test_1 = [10, 9, 23, -3, 66]
    test_2 = [10, 11]
    test_3 = [9]
    print("test_1: ", test_1)
    # selection_sort does not create new array, instead it sort in-place, the following code, you get None.
    # test_1 = selection_sort(test_1)
    selection_sort(test_1)
    print("sorted test_1: ", test_1)
    print("test_2: ", test_2)
    selection_sort(test_2)
    print("sorted test_2: ", test_2)
    print("test_3: ", test_3)
    selection_sort(test_3)
    print("sorted test_3: ", test_3)

            
