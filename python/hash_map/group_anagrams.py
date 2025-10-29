"""
https://leetcode.com/problems/group-anagrams/
Given an array of strings strs, group the anagrams together. You can return the answer in any order.
An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
typically using all the original letters exactly once.

Example 1:
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]

Example 2:
Input: strs = [""]
Output: [[""]]

Example 3:
Input: strs = ["a"]
Output: [["a"]]

Constraints:
1 <= strs.length <= 104
0 <= strs[i].length <= 100
strs[i] consists of lower-case English letters.
"""

def group_anagram(strs):
    dict = {}
    for str in strs:
        key = ''.join(sorted(str))
        val = dict.get(key, [])
        val.append(str)
        dict[key] = val
    return list(dict.values())

if __name__ == "__main__":
    strs = ["eat","tea","tan","ate","nat","bat"]
    print(group_anagram(strs))
    strs = [""]
    print(group_anagram(strs))
    strs = ["a"]
    print(group_anagram(strs))
