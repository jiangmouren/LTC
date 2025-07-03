def insertion_sort(arr):
    """
    Sorts a list in-place using insertion sort
    :param arr: list of comparable elements
    """
    n = len(arr)
    for i in range(1, n):
        val = arr[i]
        j = i - 1
        while j>=0 and arr[j]>val:
            arr[j+1] = arr[j]
            j -= 1
        arr[j+1] = val

if __name__ == "__main__":
    test_1 = [0]
    test_2 = [10, 2, 21, -1]
    test_3 = [1, 2, 3]
    print("test_1: ", test_1)
    insertion_sort(test_1)
    print("sorted test_1: ", test_1)

    print("test_2: ", test_2)
    insertion_sort(test_2)
    print("sorted test_2: ", test_2)

    print("test_3: ", test_3)
    insertion_sort(test_3)
    print("sorted test_3: ", test_3)