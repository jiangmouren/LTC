When coding in python for leetcode, pay attention to the following:

1. You don't always need classes, if it is just to implement some algorithm, you can simplly put done a bunch of methods, that way no need to worry about 'self' in every method, no need to worry about instantiation, etc. Focus purely on algorithm. 

2. You do not add type hints, that's best practice, but waste time for interview. You can always come back to add those after you are done with everything, but not doing that upfront which might slow you down. 

3. When you do need to do Object Oriented Design, make sure don't forget to include 'self' for instance method. 

4. Pay attention to the difference between @staticmethod and @classmethod. Refer to the following example:

```python
class Date:
    def __init__(self, year, month, day):
        self.year = year
        self.month = month
        self.day = day
    
    @staticmethod
    def from_timestamp(timestamp):
        import datetime
        dt = datetime.datetime.fromtimestamp(timestamp)
        return Date(dt.year, dt.month, dt.day)  # Hardcoded to Date
    
    @classmethod
    def from_timestamp_classmethod(cls, timestamp):
        import datetime
        dt = datetime.datetime.fromtimestamp(timestamp)
        return cls(dt.year, dt.month, dt.day)  # Creates instance of calling class

class DateTime(Date):
    def __init__(self, year, month, day, hour=0, minute=0, second=0):
        super().__init__(year, month, day)
        self.hour = hour
        self.minute = minute
        self.second = second

# The problem:
timestamp = 1640995200  # Jan 1, 2022

# Using @staticmethod - creates Date instance even when called on DateTime
dt1 = DateTime.from_timestamp(timestamp)
print(type(dt1))  # <class '__main__.Date'> - WRONG!
# dt1.hour  # AttributeError! Date doesn't have hour attribute

# Using @classmethod - creates DateTime instance
dt2 = DateTime.from_timestamp_classmethod(timestamp)
print(type(dt2))  # <class '__main__.DateTime'> - CORRECT!
print(dt2.hour)  # 0 - Works fine!
```

5. When practicing Leetcode, you should just turn off Cursor Tab. 

6. Same as the No. 5 above, you need to familiarize yourself with python syntax. Cursor can make stupid false syntax recommendations like asking you to do:
```python
for i, num in enumerate[int](nums):
```
You CANNOT annotate like "enumerate[int]"!

7. 我手写代码的时候，脑子里默认出来的代码还是java，要熟练两者常见语法的异同

8. common building blocks:
Just like you should familiar with java.util.*, in python you should read the following: 
https://docs.python.org/3.12/library/index.html

Sort
```python
a = [5, 2, 3, 1, 4]
a.sort() # will sort a in place
sorted(a) # will create a new sorted list out of a, no need to import anything
```

Heap & Priority Queue
| Operation                      | Description              | Time       |
| ------------------------------ | ------------------------ | ---------- |
| `heapq.heappush(heap, x)`      | Push `x` into min-heap   | O(log n)   |
| `heapq.heappop(heap)`          | Pop smallest element     | O(log n)   |
| `heapq.heapify(list)`          | Transform list into heap | O(n)       |
| `heapq.nlargest(k, iterable)`  | k largest elements       | O(n log k) |
| `heapq.nsmallest(k, iterable)` | k smallest elements      | O(n log k) |

Since python 3.14, heapq.heappush_max, heapq.heappop_max and heapq.heapify_max are added. Before that, you have to use negate the values when pushing to build
max_heap. And because of that, you cannot direclty heapify a list into max heap.
You will have to create/wrap a seperate list with negated values first before
you can heaify it to a max_heap. 

Max & Min
```python
# no need to import anything, max & min are built-in methods like sorted
a = [5, 2, 3, 1, 4]
max(a) # returns 5
max(1, 4) # returns 4
max(a, key=lambda x:-x) # returns 1
min(a) # returns 1
```

List
```python
a = [5, 2, 3, 1, 4]
a.append(0) # append 0 to the end of the list, there is no 'add' method for list
b = [0, 6]
a.extend(b) # append b to a
a.insert(0, -1) # insert -1 at the index 0
```

Set
```python
hash_set = set()
hash_set.add("something") # set has 'add' method
```

Dict
```python
map = {}
val = map.get("key_0", []) # use get with default value is recommend, so you don't need to check if key in the dict first
val.append("val_0")
map[key] = val

keys = map.keys()
vals = map.values()
# the keys and values above are iterable, but they are NOT lists, they are dict_keys and dict_values
# to convert them into lists
keys_list = list(keys)
values_list = list(values)
```

Loop with index
In java you have:
```java
for (int i = 0; i < nums.length; i++){
    // you want to loop through with index
}
```
The way you do this in python is with built-in enumerate method:
```python
for idx, num in enumerate(nums):
    # you loop through with index and element
```
# TODO:
bisect

SortedDict as TreeMap
