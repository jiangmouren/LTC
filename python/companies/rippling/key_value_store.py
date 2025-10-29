"""
#### Part 1 #####
Design and implement an in-memory key value datastore
This datastore should be able to support some basic operations such as Get, Set and Delete
For example:
db.set("key1", "val1")
print(db.get("key1")) # returns val1
db.delete("key1")
db.get("key1")

#### Part 2 #####
Add support for transactions - Begin, Commit and Rollback
A transaction is created with the Begin command and creates a context for the other operations to happen
Until the active transaction is committed using the Commit command, those operations do not persist
And, the Rollback command throws away any changes made by those operations in the context of the active transaction
Every Begin() will always come with a Commit() or Rollback()
For example:
Example 1
db.set("key0", "val0")
db.get("key0") # returns val0 which is set outside of a transaction
db.begin()
db.get("key0") # returns val0 which is set outside of a transaction
db.set("key1", "val1")
db.get("key1") # returns val1
db.commit()
db.get("key1") # returns val1
Example 2
db.begin()
db.set("key2", "val2")
db.get("key2") # returns val2
db.rollback()
db.get("key2") # error case as key2 is not set

#### Part 3 #####
Add support for nested transactions
The newly spawned transaction inherits the variables from its parent transaction
Changes made in the context of a child transaction will reflect in the parent transaction as well
Once commit is called on the parent transaction, all transactions will be persisted to the global storage
Note that commit on a child transaction does not affect global state. If the parent is rolled back, the global state should remain unchanged.
For example:
db.begin() # Create a parent transaction
db.set("key1", "val1")
db.set("key2", "val2")
db.begin() # Create a child transaction
db.get("key1") # returns val1 which is set from parent transaction
db.set("key1", "val1_child")
db.commit() # Commit child transaction
db.get("key1") # returns val1_child which is set from child transaction
db.get("key2") # returns val2 which is set from parent transaction
db.commit() # Commit parent transaction
"""

from abc import ABC, abstractmethod
from typing import Any, Optional, Dict, List
from enum import Enum
import copy


class KeyValueError(Exception):
    """Custom exception for key-value store operations."""
    pass


class OperationType(Enum):
    """Enumeration for different operation types."""
    SET = "SET"
    DELETE = "DELETE"


class Operation:
    """Represents a single operation in the key-value store."""
    
    def __init__(self, operation_type: OperationType, key: str, value: Any = None):
        self.operation_type = operation_type
        self.key = key
        self.value = value
        self.timestamp = id(self)  # Simple timestamp using object id
    
    def __repr__(self):
        return f"Operation({self.operation_type.value}, {self.key}, {self.value})"


class TransactionState(ABC):
    """Abstract base class for transaction states."""
    
    @abstractmethod
    def get(self, key: str) -> Any:
        pass
    
    @abstractmethod
    def set(self, key: str, value: Any) -> None:
        pass
    
    @abstractmethod
    def delete(self, key: str) -> None:
        pass


class ActiveTransactionState(TransactionState):
    """State when a transaction is active."""
    
    def __init__(self, transaction: 'Transaction'):
        self.transaction = transaction
    
    def get(self, key: str) -> Any:
        return self.transaction.get(key)
    
    def set(self, key: str, value: Any) -> None:
        self.transaction.set(key, value)
    
    def delete(self, key: str) -> None:
        self.transaction.delete(key)


class CommittedTransactionState(TransactionState):
    """State when a transaction is committed."""
    
    def get(self, key: str) -> Any:
        raise KeyValueError("Cannot perform operations on committed transaction")
    
    def set(self, key: str, value: Any) -> None:
        raise KeyValueError("Cannot perform operations on committed transaction")
    
    def delete(self, key: str) -> None:
        raise KeyValueError("Cannot perform operations on committed transaction")


class RolledBackTransactionState(TransactionState):
    """State when a transaction is rolled back."""
    
    def get(self, key: str) -> Any:
        raise KeyValueError("Cannot perform operations on rolled back transaction")
    
    def set(self, key: str, value: Any) -> None:
        raise KeyValueError("Cannot perform operations on rolled back transaction")
    
    def delete(self, key: str) -> None:
        raise KeyValueError("Cannot perform operations on rolled back transaction")


class Transaction:
    """Represents a transaction in the key-value store."""
    
    def __init__(self, parent: Optional['Transaction'] = None, global_store: Optional['KeyValueStore'] = None):
        self.parent = parent
        self.global_store = global_store
        self.state: TransactionState = ActiveTransactionState(self)
        self.local_data: Dict[str, Any] = {}
        self.operations: List[Operation] = []
        self.children: List['Transaction'] = []
        
        if parent:
            parent.children.append(self)
            # Inherit data from parent transaction
            self.local_data = copy.deepcopy(parent.local_data)
    
    def get(self, key: str) -> Any:
        """Get value for a key, checking local data first, then parent transactions, then global store."""
        if key in self.local_data:
            return self.local_data[key]
        
        if self.parent:
            return self.parent.get(key)
        
        # If no parent, this is the root transaction, check global store
        if self.global_store and key in self.global_store.data:
            return self.global_store.data[key]
        
        raise KeyValueError(f"Key '{key}' not found")
    
    def set(self, key: str, value: Any) -> None:
        """Set value for a key in this transaction."""
        if not isinstance(self.state, ActiveTransactionState):
            raise KeyValueError("Cannot perform operations on inactive transaction")
        
        self.local_data[key] = value
        self.operations.append(Operation(OperationType.SET, key, value))
    
    def delete(self, key: str) -> None:
        """Delete a key from this transaction."""
        if not isinstance(self.state, ActiveTransactionState):
            raise KeyValueError("Cannot perform operations on inactive transaction")
        
        if key in self.local_data:
            del self.local_data[key]
        else:
            # Mark for deletion even if key doesn't exist locally
            self.local_data[key] = None
        
        self.operations.append(Operation(OperationType.DELETE, key))
    
    def commit(self) -> None:
        """Commit this transaction and all its children."""
        if not isinstance(self.state, ActiveTransactionState):
            raise KeyValueError("Cannot commit inactive transaction")
        
        # First commit all active children
        for child in self.children:
            if isinstance(child.state, ActiveTransactionState):
                child.commit()
        
        # Apply operations to parent or global store
        if self.parent:
            # Apply to parent transaction
            for key, value in self.local_data.items():
                if value is None:  # Deleted key
                    if key in self.parent.local_data:
                        del self.parent.local_data[key]
                else:
                    self.parent.local_data[key] = value
        else:
            # Apply to global store
            if self.global_store:
                for key, value in self.local_data.items():
                    if value is None:  # Deleted key
                        if key in self.global_store.data:
                            del self.global_store.data[key]
                    else:
                        self.global_store.data[key] = value
        
        self.state = CommittedTransactionState()
    
    def rollback(self) -> None:
        """Rollback this transaction and all its children."""
        if not isinstance(self.state, ActiveTransactionState):
            raise KeyValueError("Cannot rollback inactive transaction")
        
        # First rollback all children
        for child in self.children:
            child.rollback()
        
        self.state = RolledBackTransactionState()
    
    def is_active(self) -> bool:
        """Check if transaction is active."""
        return isinstance(self.state, ActiveTransactionState)


class KeyValueStore:
    """Main key-value store implementation with transaction support."""
    
    def __init__(self):
        self.data: Dict[str, Any] = {}
        self.active_transactions: List[Transaction] = []
    
    def get(self, key: str) -> Any:
        """Get value for a key."""
        if self.active_transactions:
            # Use the most recent active transaction
            return self.active_transactions[-1].get(key)
        else:
            if key not in self.data:
                raise KeyValueError(f"Key '{key}' not found")
            return self.data[key]
    
    def set(self, key: str, value: Any) -> None:
        """Set value for a key."""
        if self.active_transactions:
            # Use the most recent active transaction
            self.active_transactions[-1].set(key, value)
        else:
            self.data[key] = value
    
    def delete(self, key: str) -> None:
        """Delete a key."""
        if self.active_transactions:
            # Use the most recent active transaction
            self.active_transactions[-1].delete(key)
        else:
            if key in self.data:
                del self.data[key]
            else:
                raise KeyValueError(f"Key '{key}' not found")
    
    def begin(self) -> Transaction:
        """Begin a new transaction."""
        parent = self.active_transactions[-1] if self.active_transactions else None
        transaction = Transaction(parent, self)
        self.active_transactions.append(transaction)
        return transaction
    
    def commit(self) -> None:
        """Commit the current transaction."""
        if not self.active_transactions:
            raise KeyValueError("No active transaction to commit")
        
        transaction = self.active_transactions.pop()
        transaction.commit()
    
    def rollback(self) -> None:
        """Rollback the current transaction."""
        if not self.active_transactions:
            raise KeyValueError("No active transaction to rollback")
        
        transaction = self.active_transactions.pop()
        transaction.rollback()
    
    def has_active_transaction(self) -> bool:
        """Check if there are any active transactions."""
        return len(self.active_transactions) > 0


# Example usage and test cases
def test_basic_operations():
    """Test basic key-value store operations."""
    print("=== Testing Basic Operations ===")
    db = KeyValueStore()
    
    # Test set and get
    db.set("key1", "val1")
    print(f"db.get('key1'): {db.get('key1')}")  # Should return val1
    
    # Test delete
    db.delete("key1")
    try:
        print(f"db.get('key1'): {db.get('key1')}")  # Should raise error
    except KeyValueError as e:
        print(f"Error: {e}")


def test_transactions():
    """Test transaction support."""
    print("\n=== Testing Transactions ===")
    db = KeyValueStore()
    
    # Example 1: Successful transaction
    print("Example 1: Successful transaction")
    db.set("key0", "val0")
    print(f"db.get('key0'): {db.get('key0')}")  # returns val0
    
    db.begin()
    print(f"db.get('key0'): {db.get('key0')}")  # returns val0
    db.set("key1", "val1")
    print(f"db.get('key1'): {db.get('key1')}")  # returns val1
    db.commit()
    print(f"db.get('key1'): {db.get('key1')}")  # returns val1
    
    # Example 2: Rollback transaction
    print("\nExample 2: Rollback transaction")
    db.begin()
    db.set("key2", "val2")
    print(f"db.get('key2'): {db.get('key2')}")  # returns val2
    db.rollback()
    try:
        print(f"db.get('key2'): {db.get('key2')}")  # error case
    except KeyValueError as e:
        print(f"Error: {e}")


def test_nested_transactions():
    """Test nested transactions."""
    print("\n=== Testing Nested Transactions ===")
    db = KeyValueStore()
    
    # Create parent transaction
    db.begin()
    db.set("key1", "val1")
    db.set("key2", "val2")
    print(f"Parent - db.get('key1'): {db.get('key1')}")  # returns val1
    print(f"Parent - db.get('key2'): {db.get('key2')}")  # returns val2
    
    # Create child transaction
    db.begin()
    print(f"Child - db.get('key1'): {db.get('key1')}")  # returns val1 (inherited)
    db.set("key1", "val1_child")
    print(f"Child - db.get('key1'): {db.get('key1')}")  # returns val1_child
    db.commit()  # Commit child transaction
    
    print(f"After child commit - db.get('key1'): {db.get('key1')}")  # returns val1_child
    print(f"After child commit - db.get('key2'): {db.get('key2')}")  # returns val2
    
    db.commit()  # Commit parent transaction
    print(f"After parent commit - db.get('key1'): {db.get('key1')}")  # returns val1_child
    print(f"After parent commit - db.get('key2'): {db.get('key2')}")  # returns val2


def test_error_cases():
    """Test error cases."""
    print("\n=== Testing Error Cases ===")
    db = KeyValueStore()
    
    # Test operations on committed transaction
    transaction = db.begin()
    db.set("test", "value")
    db.commit()
    
    try:
        transaction.set("test2", "value2")
    except KeyValueError as e:
        print(f"Error on committed transaction: {e}")
    
    # Test operations on rolled back transaction
    transaction = db.begin()
    db.set("test3", "value3")
    db.rollback()
    
    try:
        transaction.get("test3")
    except KeyValueError as e:
        print(f"Error on rolled back transaction: {e}")


if __name__ == "__main__":
    test_basic_operations()
    test_transactions()
    test_nested_transactions()
    test_error_cases()