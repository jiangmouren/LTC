package com.mycompany.app; /**
Given a nested list of integers, implement an iterator to flatten it.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Example 1:
Given the list [[1,1],2,[1,1]],

By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].

Example 2:
Given the list [1,[4,[6]]],

By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,4,6].

 *
 */

import java.util.*;

public class FlattenNestedListIterator implements Iterator<Integer> {
    // This is the interface that allows for creating nested lists.
    // You should not implement it, or speculate about its implementation
    interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    /**
     * 下面的这种解法是最简单直接的，就是nestedList进来直接摊平，后面access都是O(1)
     * 唯一的弊端就是constructor的complexity会比较高，O(N+L)
     * 这里面L是一共有多少个List，包含nested； N是一共哟多少个Integer，摊开之后。
     * 因为每个list都要call一次flattenList，这个call本身就是一个O(1)
     */
    private List<Integer> list;
    private int pos = 0;

    public FlattenNestedListIterator(List<NestedInteger> nestedList) {
        this.list = new ArrayList<>();
        flattenList(this.list, nestedList);
    }

    private void flattenList(List<Integer> res, List<NestedInteger> nestedList){
        for(NestedInteger token : nestedList){
            if(token.isInteger()){
                res.add(token.getInteger());
            }
            else{
                flattenList(res, token.getList());
            }
        }
    }

    @Override
    public Integer next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        else{
            return this.list.get(pos++);
        }
    }

    @Override
    public boolean hasNext() {
        return pos<this.list.size();
    }

    /**
     * 下面这种解法，是为了降低constructor的运算量，否则并没有什么优势。实际leetcode跑出来的时间也比上面差不少。
     * 这种解法里面的逻辑写起来还是有点绕的，详见：
     * src\main\resources\FlattenNestedListIterator.docx
     */
    private Deque<List<NestedInteger>> listStack = new ArrayDeque<>();
    private Deque<Integer> indexStack = new ArrayDeque<>();

    //added fake argument to make signature different.
    public FlattenNestedListIterator(List<NestedInteger> nestedList, int fake) {
        listStack.addFirst(nestedList);
        indexStack.addFirst(0);
    }

    public Integer next2() {
        if (!hasNext()) throw new NoSuchElementException();
        int currentPosition = indexStack.removeFirst();
        indexStack.addFirst(currentPosition + 1);
        return listStack.peekFirst().get(currentPosition).getInteger();
    }

    public boolean hasNext2() {
        makeStackTopAnInteger();
        return !indexStack.isEmpty();
    }

    private void makeStackTopAnInteger() {

        while (!indexStack.isEmpty()) {

            // If the top list is used up, pop it and its index.
            if (indexStack.peekFirst() >= listStack.peekFirst().size()) {
                indexStack.removeFirst();
                listStack.removeFirst();
                continue;
            }

            // Otherwise, if it's already an integer, we don't need to do anything.
            if (listStack.peekFirst().get(indexStack.peekFirst()).isInteger()) {
                break;
            }

            // Otherwise, it must be a list. We need to update the previous index
            // and then add the new list with an index of 0.
            listStack.addFirst(listStack.peekFirst().get(indexStack.peekFirst()).getList());
            indexStack.addFirst(indexStack.removeFirst() + 1);
            indexStack.addFirst(0);
        }
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
