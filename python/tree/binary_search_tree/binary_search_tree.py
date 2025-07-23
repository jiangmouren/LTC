"""
binary search tree is a binary tree with the following properties:
1. The left subtree of a node contains only nodes with keys less than the node's key.
2. The right subtree of a node contains only nodes with keys greater than the node's key.
3. Both the left and right subtrees must also be binary search trees.
4. equality can be allowed, either on left of right side.

Operations:
1. Search
2. Find Min
3. Find Max
4. Find Successor/Predecessor
5. Insert
6. Delete

For a given array, there are more then one possible BST.
The actual BST depends on the insertion order.
"""

from typing import Optional, List, Tuple
# This tree node contains parent pointer.
# But all of the above operations can be done without parent pointer. 
# The exsistence of parent pointers makes traversal up easier.
# In practice, Red-Black trees typically has parent pointers while B-tree does not.
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None, parent: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right
        self.parent = parent

class BinarySearchTree:
    
    @staticmethod
    def search(root: Optional[TreeNode], target: int) -> Optional[TreeNode]:
        ptr = root
        while ptr:
            if ptr.val == target:
                break
            elif ptr.val > target:
                ptr = ptr.left
            else:
                ptr = ptr.right
        return ptr
    
    # assume self.root is not None
    @staticmethod
    def find_min(root: Optional[TreeNode]) -> Optional[TreeNode]:
        ptr = root
        while ptr and ptr.left:
            ptr = ptr.left
        return ptr
    
    @staticmethod
    def find_max(root: Optional[TreeNode]) -> Optional[TreeNode]:
        ptr = root
        while ptr and ptr.right:
            ptr = ptr.right
        return ptr

    # it's possible there is no successor, in this case, return None
    # leverage parent pointer
    @staticmethod
    def find_successor_with_parent_ptr(root: TreeNode, target: TreeNode) -> Optional[TreeNode]:
        if target.right:
            return BinarySearchTree.find_min(target.right)
        ptr_0 = target
        ptr_1 = target.parent
        while ptr_1 and ptr_1.right == ptr_0:
            ptr_0 = ptr_1
            ptr_1 = ptr_1.parent
        return ptr_1
            
    @staticmethod
    def _search_lowest_right_ancestor(root: TreeNode, target: TreeNode, res: List[Optional[TreeNode]]) -> None:
        ptr = root
        while ptr:
            if ptr.val == target.val:
                return
            elif ptr.val > target.val:
                res[0] = ptr
                ptr = ptr.left
            else:
                ptr = ptr.right
        return

    # assume there is no parent pointer
    @staticmethod 
    def find_successor_without_parent_ptr(root: TreeNode, target: TreeNode) -> Optional[TreeNode]:
        if target.right:
            return BinarySearchTree.find_min(target.right)
        res: List[Optional[TreeNode]] = [None]
        BinarySearchTree._search_lowest_right_ancestor(root, target, res)
        return res[0]

    @staticmethod
    def insert(root: Optional[TreeNode], node: TreeNode) -> None:
        ptr_0, ptr_1 = None, root
        while ptr_1:
            ptr_0 = ptr_1
            if ptr_1.val > node.val:
                ptr_1 = ptr_1.left
            else:
                ptr_1 = ptr_1.right
        if ptr_0 is None:
            root = node
        elif ptr_0.val > node.val:
            ptr_0.left = node
        else:
            ptr_0.right = node
        node.parent = ptr_0

    """
    Assume the node is in the BST
    Keeping a parent pointer makes delete operation easier.
    But it's not necessary.
    """
    # transplant replaces the subtree rooted at src with the subtree rooted at dst
    # Attention: it's a swap of the entire subtree
    @staticmethod
    def _transplant(root: Optional[TreeNode], src: TreeNode, dst: Optional[TreeNode]) -> None:
        # meaning src is the root
        if src.parent is None:
            root = dst
        elif src.parent.left is src:
            src.parent.left = dst
        else:
            src.parent.right = dst
        if dst:
            dst.parent = src.parent

    @staticmethod
    def delete_with_parent_ptr(root: TreeNode, node: TreeNode) -> None:
        if node.left is None:
            BinarySearchTree._transplant(root, node, node.right)
        elif node.right is None:
            BinarySearchTree._transplant(root, node, node.left)
        else:
            # find the successor of node, because we know it has right child, so it is the min of the right subtree
            suc = BinarySearchTree.find_min(node.right)
            assert suc is not None
            # now we have two cases: node.right is suc or not. The goal is to get the suc and the right subtree ready for transplant
            if node.right is not suc:
                BinarySearchTree._transplant(root, suc, suc.right)
                suc.right = node.right
                suc.right.parent = suc
            BinarySearchTree._transplant(root, node, suc)
            suc.left = node.left
            suc.left.parent = suc

    @staticmethod
    def _transplant_without_parent_ptr(root: Optional[TreeNode], src: TreeNode, src_parent: Optional[TreeNode], dst: Optional[TreeNode]) -> None:
        if src_parent is None:
            root = dst
        else:
            if src_parent.left is src:
                src_parent.left = dst
            else:
                src_parent.right = dst
    
    # returns (min_node, min_node_parent)
    @staticmethod
    def _find_min_and_parent(parent: TreeNode, node: TreeNode) -> Tuple[TreeNode, TreeNode]:
        ptr_0, ptr_1 = parent, node
        while ptr_1.left:
            ptr_0 = ptr_1
            ptr_1 = ptr_1.left
        return ptr_1, ptr_0
    """
    Because there is no parent pointer, we need to repeatly use tail pointers to locate parents
    """
    @staticmethod
    def delete_without_parent_ptr(root: Optional[TreeNode], node: TreeNode) -> None:
        # find node's parent
        ptr_0, ptr_1 = None, root
        while ptr_1 and ptr_1 is not node:
            ptr_0 = ptr_1
            if ptr_1.val > node.val:
                ptr_1 = ptr_1.left
            else:
                ptr_1 = ptr_1.right
        node_parent = ptr_0
        if node.left is None:
            BinarySearchTree._transplant_without_parent_ptr(root, node, node_parent, node.right)
        elif node.right is None:
            BinarySearchTree._transplant_without_parent_ptr(root, node, node_parent, node.left)
        else:
            # we need to find the successor of node on its right subtree.
            # But in the meantime, we need to locate the successor's parent.
            # So we need a modified version of the find_min
            suc, suc_parent = BinarySearchTree._find_min_and_parent(node, node.right)
            if suc_parent is not node:
                BinarySearchTree._transplant_without_parent_ptr(root, suc, suc_parent, suc.right)
                suc.right = node.right
            BinarySearchTree._transplant_without_parent_ptr(root, node, node_parent, suc)
            suc.left = node.left
"""
          4
        /   \
       2     5
     /   \  /  \
    1     3
"""
if __name__ == "__main__":
    # set up bst
    bst = BinarySearchTree()
    root = TreeNode(4)
    root.left = TreeNode(2)
    root.left.parent = root
    root.right = TreeNode(5)
    root.right.parent = root
    root.left.left = TreeNode(1)
    root.left.left.parent = root.left
    root.left.right = TreeNode(3)
    root.left.right.parent = root.left
    # test find_max
    max_node = BinarySearchTree.find_max(root)
    assert max_node is not None
    print("max: ", max_node.val)
    # test find_min
    min_node = BinarySearchTree.find_min(root)
    assert min_node is not None
    print("min: ", min_node.val)
    # test find_successor_without_parent_ptr
    suc = BinarySearchTree.find_successor_without_parent_ptr(root, root.left.right)
    assert suc is not None
    print(suc.val)
    suc = BinarySearchTree.find_successor_without_parent_ptr(root, root.right)
    print(suc)
    # test find_sucessor_with_parent_ptr
    suc = BinarySearchTree.find_successor_with_parent_ptr(root, root.left.right)
    assert suc is not None
    print(suc.val)
    suc = BinarySearchTree.find_successor_with_parent_ptr(root, root.right)
    print(suc)
    # test search
    loc = BinarySearchTree.search(root, 3)
    assert loc is not None
    print(loc.val)
    print(BinarySearchTree.search(root, 10))

    BinarySearchTree.insert(root, TreeNode(0))
    assert root.left.left.left is not None
    print(root.left.left.left.val)

    BinarySearchTree.delete_without_parent_ptr(root, root.left)
    print(root.left.val)
