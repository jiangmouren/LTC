"""
Question: https://leetcode.com/problems/two-sum-iii-data-structure-design/
Design a data structure that accepts a stream of integers and checks
if it has a pair of integers that sum up to a particular value.

Implement the TwoSum class:
TwoSum() Initializes the TwoSum object, with an empty array initially.
void add(int number) Adds number to the data structure.
boolean find(int value) Returns true if there exists any pair of numbers 
whose sum is equal to value, otherwise, it returns false.

Example 1:
Input
["TwoSum", "add", "add", "add", "find", "find"]
[[], [1], [3], [5], [4], [7]]
Output
[null, null, null, null, true, false]
Explanation
TwoSum twoSum = new TwoSum();
twoSum.add(1);   // [] --> [1]
twoSum.add(3);   // [1] --> [1,3]
twoSum.add(5);   // [1,3] --> [1,3,5]
twoSum.find(4);  // 1 + 3 = 4, return true
twoSum.find(7);  // No two integers sum up to 7, return false

Constraints:
-105 <= number <= 105
-231 <= value <= 231 - 1
At most 5 * 104 calls will be made to add and find.

三种思路:
一种是每次进来一个数，就跟之前所有的数，计算可能的sum，然后存下所有的可能的sum
这样每次add()是O(n),但是每次find()是O(1)

另外一种是把所有进来的数，存在一个python SortedList里面，
类似于一个self balanced binary search tree的结构这样每次add()是O(lgn),
每次find()是O(n).  

第三种思路就是把所有进来数存在一个hashMap里，key是数本身，value是cnt
这样每次add()是O(1),但是每次find()是O(n)，因为需要把hashMap entry过一遍，
看对应每个key能不能找到需要的另一半

综合看第三种最优。
"""

class TwoSum:
    def __init__(self):
        self.dict = {}

    def add(self, num):
        cnt = self.dict.get(num, 0)
        self.dict[num] = cnt + 1

    def find(self, sum):
        for key in self.dict.keys():
            target = sum - key
            if target == key:
                return self.dict[key] > 1
            elif target in self.dict:
                return True
        return False

if __name__ == "__main__":
    two_sum = TwoSum()
    two_sum.add(1)
    two_sum.add(1)
    two_sum.add(3)
    two_sum.add(5)
    #two_sum.add(-1)
    print(two_sum.find(4))
    print(two_sum.find(2))
    print(two_sum.find(0))
    print(two_sum.find(7))
