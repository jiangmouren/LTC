"""
Expense System - Corporate Credit Card Rules Engine

A flexible rules engine for evaluating corporate expense policies.
Supports both individual expense rules and trip-level aggregate rules.
Q1:
We plan to offer a corporate credit card to companies that employees can use 
for business expenses. Managers can set policies on the cards so that employees 
do not misuse the card or exceed allowances. We're going to be building the rules 
engine that supports this product. 

You are encouraged to ask product-oriented questions and to share your assumptions.

We will use "expenses" as our model and the policies will be rules governing whether 
submitted expenses should be reviewed by an expense manager.

Let's start with some basic rules and we'll add some requirements, 
so please think about the flexibility of your design.

The rules to start with:

* No expense at a restaurant can exceed $75
* No airfare expenses
* No entertainment expenses
* No expenses over $250

Let's look at an example of some expenses:

    expenses.add(new HashMap<String, String>() {
      {
        put("expense_id", "001");
        put("trip_id", "001");
        put("amount_usd", "49.99");
        put("expense_type", "supplies");
        put("vendor_type", "restaurant");
        put("vendor_name", "Outback Roadhouse");
      }
    });
    expenses.add(new HashMap<String, String>() {
      {
        put("expense_id", "002");
        put("trip_id", "001");
        put("amount_usd", "125.00");
        put("expense_type", "supplies");
        put("vendor_type", "retailer");
        put("vendor_name", "Staples");
      }
    });
    expenses.add(new HashMap<String, String>() {
      {
        put("expense_id", "003");
        put("trip_id", "002");
        put("amount_usd", "153.00");
        put("expense_type", "meals");
        put("vendor_type", "restaurant");
        put("vendor_name", "Olive Yurt");
      }
    });
    expenses.add(new HashMap<String, String>() {
      {
        put("expense_id", "004");
        put("trip_id", "002");
        put("amount_usd", "1996.00");
        put("expense_type", "airfare");
        put("vendor_type", "transportation");
        put("vendor_name", "Southeast Airlines");
      }
    });
    expenses.add(new HashMap<String, String>() {
      {
        put("expense_id", "005");
        put("trip_id", "002");
        put("amount_usd", "34.68");
        put("expense_type", "meals");
        put("vendor_type", "restaurant");
        put("vendor_name", "The Great Grill");
      }
    });
    expenses.add(new HashMap<String, String>() {
      {
        put("expense_id", "006");
        put("trip_id", "002");
        put("amount_usd", "22.40");
        put("expense_type", "meals");
        put("vendor_type", "restaurant");
        put("vendor_name", "The Great Grill");
      }
    });
    expenses.add(new HashMap<String, String>() {
      {
        put("expense_id", "007");
        put("trip_id", "003");
        put("amount_usd", "59.50");
        put("expense_type", "entertainment");
        put("vendor_type", "theater");
        put("vendor_name", "Silver Screen");
      }
    });

Expense "003" is a meal at a restaurant that cost over $75 so should be reviewed.
Expense "004" is both an airfare, and over $250 so should be reviewed.
Expense "007" is entertainment so should be reviewed.

Let's design a system that can handle these rules and flag expenses that violate them.
You should implement the following function:

```
evaluateRules(rules: list<rule>, expenses: list<expense>) -> ?
```

Expenses will be provided to you as a list of hashmap/dictionary objects 
with `string` keys and values.

For the four rules above, we must pass a list of four rules to the ``rules`` argument.
The types of those four rules are up to you.

The return type of evaluateRules is up to you.

This system will enable managers to choose the rules they want to apply to their team's expenses.
A company may have over a hundred rules for a manager to choose from.

In the future we will add more rules and allow rule creation via an API.

Feel free to ask any questions which may help you understand the requirements,
and to share any thoughts on how you would approach the problem in production.

Before you get started coding, let's discuss the return function's return type.

Q2:
Our return value is being used as a response in our expenses API.
This API is being used by a large number of companies and we are adding more rules to the system.
As well as reviewing expenses that violate rules, 
managers now need to review trips that violate rules.

Let's add functionality to evaluate rules such as:

* A trip cannot exceed $2000 in total expenses.

or

* Total meal expenses cannot exceed $200 per trip.

In our set of expenses, trip ``002`` violates both rules.

Implement a way to handle more complex rules like these ones, 
in addition to the rules already covered.

The output should show trips that violate these new rules, 
in addition to individual expenses from the previous question.
"""

from typing import Dict, List, Any, Union
from collections import defaultdict
from decimal import Decimal, InvalidOperation
from abc import ABC, abstractmethod
from enum import Enum
import re


class ExpenseValidationError(Exception):
    """Raised when expense data doesn't match required schema"""
    pass


class RuleValidationError(Exception):
    """Raised when rule configuration is invalid"""
    pass


class ExpenseType(Enum):
    """Enumeration of valid expense types"""
    SUPPLIES = "supplies"
    MEALS = "meals"
    AIRFARE = "airfare"
    ENTERTAINMENT = "entertainment"
    TRANSPORTATION = "transportation"
    LODGING = "lodging"
    PARKING = "parking"
    FUEL = "fuel"
    COMMUNICATION = "communication"
    OFFICE_SUPPLIES = "office_supplies"


class VendorType(Enum):
    """Enumeration of valid vendor types"""
    RESTAURANT = "restaurant"
    RETAILER = "retailer"
    TRANSPORTATION = "transportation"
    THEATER = "theater"
    HOTEL = "hotel"
    AIRLINE = "airline"
    GAS_STATION = "gas_station"
    PARKING = "parking"
    OFFICE_STORE = "office_store"
    TELECOM = "telecom"


class RuleValidator:
    """Validates rule configurations against expense schema and constraints"""
    
    # Valid operators for different field types
    NUMERIC_OPERATORS = [">", "<", ">=", "<=", "==", "!="]
    STRING_OPERATORS = ["==", "!=", "contains", "regex", "in_list"]
    AGGREGATION_TYPES = ["sum", "count", "avg", "max", "min"]
    
    # Field type mapping from expense schema
    FIELD_TYPES = {
        "expense_id": str,
        "trip_id": str,
        "amount_usd": Decimal,
        "expense_type": ExpenseType,
        "vendor_type": VendorType,
        "vendor_name": str,
    }
    
    @classmethod
    def validate_numeric_rule(cls, rule_id: str, field: str, operator: str, value: Union[float, Decimal]) -> None:
        """
        Validate NumericRule configuration
        
        Args:
            rule_id: Unique identifier for the rule
            field: Field name to validate
            operator: Operator to validate
            value: Value to validate
            
        Raises:
            RuleValidationError: If configuration is invalid
        """
        cls._validate_rule_id(rule_id)
        cls._validate_field_exists(field)
        cls._validate_numeric_field(field)
        cls._validate_numeric_operator(operator)
        cls._validate_numeric_value(value)
    
    @classmethod
    def validate_string_rule(cls, rule_id: str, field: str, operator: str, value: str) -> None:
        """
        Validate StringRule configuration
        
        Args:
            rule_id: Unique identifier for the rule
            field: Field name to validate
            operator: Operator to validate
            value: Value to validate
            
        Raises:
            RuleValidationError: If configuration is invalid
        """
        cls._validate_rule_id(rule_id)
        cls._validate_field_exists(field)
        cls._validate_string_field(field)
        cls._validate_string_operator(operator)
        cls._validate_string_value(field, value)
    
    @classmethod
    def validate_composite_rule(cls, rule_id: str, rules: List['Rule'], operator: str) -> None:
        """
        Validate CompositeRule configuration
        
        Args:
            rule_id: Unique identifier for the rule
            rules: List of Rule objects to combine
            operator: Combination operator ("and" or "or")
            
        Raises:
            RuleValidationError: If configuration is invalid
        """
        cls._validate_rule_id(rule_id)
        cls._validate_composite_operator(operator)
        cls._validate_composite_rules(rules)
    
    @classmethod
    def validate_aggregate_rule(cls, rule_id: str, field: str, operator: str, value: Union[float, Decimal],
                               aggregation: str, filter_field: str = None, filter_value: str = None) -> None:
        """
        Validate AggregateRule configuration
        
        Args:
            rule_id: Unique identifier for the rule
            field: Field name to aggregate
            operator: Operator to validate
            value: Value to validate
            aggregation: Aggregation type
            filter_field: Optional filter field
            filter_value: Optional filter value
            
        Raises:
            RuleValidationError: If configuration is invalid
        """
        cls._validate_rule_id(rule_id)
        cls._validate_field_exists(field)
        cls._validate_aggregatable_field(field, aggregation)
        cls._validate_numeric_operator(operator)
        cls._validate_numeric_value(value)
        cls._validate_aggregation_type(aggregation)
        
        if filter_field:
            cls._validate_field_exists(filter_field)
            cls._validate_string_value(filter_field, filter_value)
    
    @classmethod
    def _validate_rule_id(cls, rule_id: str) -> None:
        """Validate rule ID is not empty"""
        if not rule_id or not isinstance(rule_id, str) or not rule_id.strip():
            raise RuleValidationError("Rule ID must be a non-empty string")
    
    @classmethod
    def _validate_field_exists(cls, field: str) -> None:
        """Validate field exists in expense schema"""
        if field not in cls.FIELD_TYPES:
            valid_fields = list(cls.FIELD_TYPES.keys())
            raise RuleValidationError(f"Field '{field}' not found in expense schema. Valid fields: {valid_fields}")
    
    @classmethod
    def _validate_numeric_field(cls, field: str) -> None:
        """Validate field supports numeric operations"""
        field_type = cls.FIELD_TYPES[field]
        if field_type not in [Decimal, int, float]:
            raise RuleValidationError(f"Field '{field}' (type {field_type.__name__}) does not support numeric operations. Only numeric fields support comparison operators.")
    
    @classmethod
    def _validate_string_field(cls, field: str) -> None:
        """Validate field supports string operations"""
        field_type = cls.FIELD_TYPES[field]
        if field_type not in [str, ExpenseType, VendorType]:
            raise RuleValidationError(f"Field '{field}' (type {field_type.__name__}) does not support string operations. Only string/enum fields support string operators.")
    
    @classmethod
    def _validate_aggregatable_field(cls, field: str, aggregation: str) -> None:
        """Validate field supports aggregation"""
        field_type = cls.FIELD_TYPES[field]
        
        # Count aggregation works on any field
        if aggregation == "count":
            return
        
        # Other aggregations require numeric fields
        if field_type not in [Decimal, int, float]:
            raise RuleValidationError(f"Field '{field}' (type {field_type.__name__}) does not support '{aggregation}' aggregation. Only numeric fields support sum/avg/max/min operations.")
    
    @classmethod
    def _validate_numeric_operator(cls, operator: str) -> None:
        """Validate numeric operator"""
        if operator not in cls.NUMERIC_OPERATORS:
            raise RuleValidationError(f"Invalid numeric operator: '{operator}'. Valid operators: {cls.NUMERIC_OPERATORS}")
    
    @classmethod
    def _validate_string_operator(cls, operator: str) -> None:
        """Validate string operator"""
        if operator not in cls.STRING_OPERATORS:
            raise RuleValidationError(f"Invalid string operator: '{operator}'. Valid operators: {cls.STRING_OPERATORS}")
    
    @classmethod
    def _validate_aggregation_type(cls, aggregation: str) -> None:
        """Validate aggregation type"""
        if aggregation not in cls.AGGREGATION_TYPES:
            raise RuleValidationError(f"Invalid aggregation type: '{aggregation}'. Valid types: {cls.AGGREGATION_TYPES}")
    
    @classmethod
    def _validate_composite_operator(cls, operator: str) -> None:
        """Validate composite operator"""
        if operator not in ["and", "or"]:
            raise RuleValidationError(f"Invalid composite operator: '{operator}'. Valid operators: ['and', 'or']")
    
    @classmethod
    def _validate_composite_rules(cls, rules: List['Rule']) -> None:
        """Validate composite rule list"""
        if not rules:
            raise RuleValidationError("Composite rule must contain at least one rule")
        
        if len(rules) < 2:
            raise RuleValidationError("Composite rule must contain at least 2 rules to combine")
        
        # Check that all rules are valid Rule instances
        for i, rule in enumerate(rules):
            if not hasattr(rule, 'evaluate_expense') or not hasattr(rule, 'evaluate_trip'):
                raise RuleValidationError(f"Rule at index {i} is not a valid Rule instance")
    
    @classmethod
    def _validate_numeric_value(cls, value: Union[float, Decimal]) -> None:
        """Validate numeric value"""
        try:
            Decimal(str(value))
        except (ValueError, TypeError, InvalidOperation):
            raise RuleValidationError(f"Invalid numeric value: '{value}'. Must be a valid number.")
    
    @classmethod
    def _validate_string_value(cls, field: str, value: str) -> None:
        """Validate string value against field constraints"""
        if not isinstance(value, str):
            raise RuleValidationError(f"Value for field '{field}' must be a string, got {type(value).__name__}")
        
        field_type = cls.FIELD_TYPES[field]
        
        # Validate enum fields
        if field_type == ExpenseType:
            try:
                ExpenseType(value)
            except ValueError:
                valid_values = [e.value for e in ExpenseType]
                raise RuleValidationError(f"Invalid expense_type: '{value}'. Valid values: {valid_values}")
        
        elif field_type == VendorType:
            try:
                VendorType(value)
            except ValueError:
                valid_values = [e.value for e in VendorType]
                raise RuleValidationError(f"Invalid vendor_type: '{value}'. Valid values: {valid_values}")
        
        # For other string fields, just check they're not empty
        elif not value.strip():
            raise RuleValidationError(f"Value for field '{field}' cannot be empty")


# Expense Schema Definition
EXPENSE_SCHEMA = {
    "expense_id": str,           # Required
    "trip_id": str,              # Required  
    "amount_usd": Decimal,       # Required - using Decimal for precise money calculations
    "expense_type": ExpenseType, # Required - enum for type safety
    "vendor_type": VendorType,   # Required - enum for type safety
    "vendor_name": str,          # Required
}


class ExpenseValidator:
    """Validates expense data against schema"""
    
    @staticmethod
    def validate_expense(expense: Dict[str, Any]) -> None:
        """
        Validate expense against schema, raise ExpenseValidationError if invalid
        
        Args:
            expense: Dictionary containing expense data with required fields:
                - expense_id (str): Unique identifier for the expense
                - trip_id (str): Identifier of the trip this expense belongs to
                - amount_usd (Decimal|str|float): Expense amount in USD
                - expense_type (ExpenseType|str): Type of expense (enum or string)
                - vendor_type (VendorType|str): Type of vendor (enum or string)
                - vendor_name (str): Name of the vendor
        
        Raises:
            ExpenseValidationError: If expense data is invalid or missing required fields
        """
        # Check required fields
        for field, field_type in EXPENSE_SCHEMA.items():
            if field not in expense:
                raise ExpenseValidationError(f"Missing required field: {field}")
            
            # Type validation
            if field == "amount_usd":
                try:
                    # Convert to Decimal for precise money calculations
                    if isinstance(expense[field], (int, float, str)):
                        Decimal(str(expense[field]))  # Convert via string to avoid float precision issues
                    elif isinstance(expense[field], Decimal):
                        pass  # Already a Decimal
                    else:
                        raise ValueError(f"Cannot convert {type(expense[field])} to Decimal")
                except (ValueError, TypeError, InvalidOperation):
                    raise ExpenseValidationError(f"Invalid amount_usd: {expense[field]}")
            elif field == "expense_type":
                # Validate expense_type enum
                if isinstance(expense[field], ExpenseType):
                    pass  # Already an enum
                elif isinstance(expense[field], str):
                    try:
                        ExpenseType(expense[field])  # Validate string value exists in enum
                    except ValueError:
                        valid_values = [e.value for e in ExpenseType]
                        raise ExpenseValidationError(f"Invalid expense_type: {expense[field]}. Valid values: {valid_values}")
                else:
                    raise ExpenseValidationError(f"Invalid type for {field}: expected {field_type.__name__} or str")
            elif field == "vendor_type":
                # Validate vendor_type enum
                if isinstance(expense[field], VendorType):
                    pass  # Already an enum
                elif isinstance(expense[field], str):
                    try:
                        VendorType(expense[field])  # Validate string value exists in enum
                    except ValueError:
                        valid_values = [e.value for e in VendorType]
                        raise ExpenseValidationError(f"Invalid vendor_type: {expense[field]}. Valid values: {valid_values}")
                else:
                    raise ExpenseValidationError(f"Invalid type for {field}: expected {field_type.__name__} or str")
            elif not isinstance(expense[field], field_type):
                raise ExpenseValidationError(f"Invalid type for {field}: expected {field_type.__name__}")


class Rule(ABC):
    """Abstract base class for expense rules"""
    
    def __init__(self, rule_id: str):
        """
        Initialize a rule with a unique identifier
        
        Args:
            rule_id: Unique string identifier for this rule
        """
        self.rule_id = rule_id
    
    @abstractmethod
    def evaluate_expense(self, expense: Dict[str, Any]) -> bool:
        """
        Evaluate rule against a single expense
        
        Args:
            expense: Dictionary containing expense data with required fields:
                - expense_id (str): Unique identifier for the expense
                - trip_id (str): Identifier of the trip this expense belongs to
                - amount_usd (Decimal): Expense amount in USD
                - expense_type (ExpenseType): Type of expense
                - vendor_type (VendorType): Type of vendor
                - vendor_name (str): Name of the vendor
        
        Returns:
            bool: True if the expense violates this rule, False otherwise
        """
        pass
    
    @abstractmethod
    def evaluate_trip(self, expenses: List[Dict[str, Any]]) -> bool:
        """
        Evaluate rule against aggregated expenses from a single trip
        
        Args:
            expenses: List of expense dictionaries, ALL belonging to the SAME trip.
                     Each expense has the same structure as evaluate_expense parameter.
                     This represents all expenses for a specific trip_id.
        
        Returns:
            bool: True if the trip violates this rule, False otherwise
        """
        pass
    
    def get_rule_id(self) -> str:
        """
        Get the unique identifier for this rule
        
        Returns:
            str: The unique rule identifier
        """
        return self.rule_id


class NumericRule(Rule):
    """Rule for numeric field comparisons (e.g., amount_usd > 100)"""
    
    def __init__(self, rule_id: str, field: str, operator: str, value: Union[float, Decimal]):
        """
        Initialize a numeric comparison rule
        
        Args:
            rule_id: Unique identifier for this rule
            field: Name of the field to compare (e.g., "amount_usd")
            operator: Comparison operator (">", "<", ">=", "<=", "==", "!=")
            value: Value to compare against (will be converted to Decimal)
        
        Raises:
            RuleValidationError: If configuration is invalid
        """
        # Validate rule configuration before initialization
        RuleValidator.validate_numeric_rule(rule_id, field, operator, value)
        
        super().__init__(rule_id)
        self.field = field
        self.operator = operator
        self.value = Decimal(str(value))  # Convert to Decimal for precise comparison
    
    def evaluate_expense(self, expense: Dict[str, Any]) -> bool:
        """
        Evaluate numeric rule against a single expense
        
        Args:
            expense: Dictionary containing expense data (see Rule.evaluate_expense for structure)
        
        Returns:
            bool: True if the expense violates this rule (e.g., amount > limit), False otherwise
        """
        if self.field not in expense:
            return False  # Skip if field missing
        
        try:
            # Convert to Decimal for precise comparison
            expense_value = Decimal(str(expense[self.field]))
        except (ValueError, TypeError, InvalidOperation):
            return False  # Skip if can't convert to Decimal
        
        return self._compare(expense_value, self.value)
    
    def _compare(self, a: Decimal, b: Decimal) -> bool:
        """Compare two values based on operator"""
        if self.operator == ">":
            return a > b
        elif self.operator == "<":
            return a < b
        elif self.operator == ">=":
            return a >= b
        elif self.operator == "<=":
            return a <= b
        elif self.operator == "==":
            return a == b
        elif self.operator == "!=":
            return a != b
        return False
    
    def evaluate_trip(self, expenses: List[Dict[str, Any]]) -> bool:
        """
        NumericRule doesn't apply to trip-level evaluation
        
        Args:
            expenses: List of expense dictionaries from the same trip (see Rule.evaluate_trip for structure)
        
        Returns:
            bool: Always returns False since NumericRule only applies to individual expenses
        """
        return False  # NumericRule only applies to individual expenses


class StringRule(Rule):
    """Rule for string/enum field comparisons (e.g., expense_type == "airfare")"""
    
    def __init__(self, rule_id: str, field: str, operator: str, value: str):
        """
        Initialize a string comparison rule
        
        Args:
            rule_id: Unique identifier for this rule
            field: Name of the field to compare (e.g., "expense_type", "vendor_type")
            operator: Comparison operator ("==", "!=", "contains", "regex", "in_list")
            value: String value to compare against
        
        Raises:
            RuleValidationError: If configuration is invalid
        """
        # Validate rule configuration before initialization
        RuleValidator.validate_string_rule(rule_id, field, operator, value)
        
        super().__init__(rule_id)
        self.field = field
        self.operator = operator
        self.value = value
    
    def evaluate_expense(self, expense: Dict[str, Any]) -> bool:
        """
        Evaluate string rule against a single expense
        
        Args:
            expense: Dictionary containing expense data (see Rule.evaluate_expense for structure)
        
        Returns:
            bool: True if the expense violates this rule (e.g., expense_type == "airfare"), False otherwise
        """
        if self.field not in expense:
            return False
        
        # Handle enum values by converting to their string value
        expense_value = expense[self.field]
        if hasattr(expense_value, 'value'):  # It's an enum
            expense_value = expense_value.value
        else:
            expense_value = str(expense_value)
        
        return self._compare(expense_value, self.value)
    
    def _compare(self, a: str, b: str) -> bool:
        """Compare two strings based on operator"""
        if self.operator == "==":
            return a == b
        elif self.operator == "!=":
            return a != b
        elif self.operator == "contains":
            return b.lower() in a.lower()
        elif self.operator == "regex":
            return bool(re.search(b, a, re.IGNORECASE))
        elif self.operator == "in_list":
            return a in [item.strip() for item in b.split(",")]
        return False
    
    def evaluate_trip(self, expenses: List[Dict[str, Any]]) -> bool:
        """
        StringRule doesn't apply to trip-level evaluation
        
        Args:
            expenses: List of expense dictionaries from the same trip (see Rule.evaluate_trip for structure)
        
        Returns:
            bool: Always returns False since StringRule only applies to individual expenses
        """
        return False  # StringRule only applies to individual expenses


class AggregateRule(Rule):
    """Rule for trip-level aggregate calculations (e.g., sum of all meal expenses > $200)"""
    
    def __init__(self, rule_id: str, field: str, operator: str, value: Union[float, Decimal], 
                 aggregation: str, filter_field: str = None, filter_value: str = None):
        """
        Initialize an aggregate rule for trip-level calculations
        
        Args:
            rule_id: Unique identifier for this rule
            field: Name of the field to aggregate (e.g., "amount_usd")
            operator: Comparison operator (">", "<", ">=", "<=", "==", "!=")
            value: Value to compare against (will be converted to Decimal)
            aggregation: Type of aggregation ("sum", "count", "avg", "max", "min")
            filter_field: Optional field to filter expenses before aggregation (e.g., "expense_type")
            filter_value: Optional value to filter by (e.g., "meals")
        
        Raises:
            RuleValidationError: If configuration is invalid
        """
        # Validate rule configuration before initialization
        RuleValidator.validate_aggregate_rule(rule_id, field, operator, value, aggregation, filter_field, filter_value)
        
        super().__init__(rule_id)
        self.field = field
        self.operator = operator
        self.value = Decimal(str(value))  # Convert to Decimal for precise comparison
        self.aggregation = aggregation
        self.filter_field = filter_field  # Optional: filter expenses before aggregation
        self.filter_value = filter_value
    
    def evaluate_expense(self, expense: Dict[str, Any]) -> bool:
        """
        AggregateRule doesn't apply to individual expense evaluation
        
        Args:
            expense: Dictionary containing expense data (see Rule.evaluate_expense for structure)
        
        Returns:
            bool: Always returns False since AggregateRule only applies to trip-level evaluation
        """
        return False  # AggregateRule only applies to trip-level evaluation
    
    def evaluate_trip(self, expenses: List[Dict[str, Any]]) -> bool:
        """
        Evaluate aggregate rule against all expenses from a single trip
        
        Args:
            expenses: List of expense dictionaries, ALL belonging to the SAME trip.
                     Each expense has the same structure as evaluate_expense parameter.
                     This represents all expenses for a specific trip_id.
        
        Returns:
            bool: True if the trip violates this rule (e.g., total expenses > $2000), False otherwise
        """
        # Filter expenses if filter criteria specified
        filtered_expenses = expenses
        if self.filter_field and self.filter_value:
            filtered_expenses = []
            for exp in expenses:
                exp_value = exp.get(self.filter_field)
                # Handle enum values by converting to their string value
                if hasattr(exp_value, 'value'):  # It's an enum
                    exp_value = exp_value.value
                if exp_value == self.filter_value:
                    filtered_expenses.append(exp)
        
        # Calculate aggregate using Decimal for precision
        try:
            if self.aggregation == "sum":
                total = sum(Decimal(str(exp[self.field])) for exp in filtered_expenses 
                           if self.field in exp and exp[self.field] is not None)
            elif self.aggregation == "count":
                total = Decimal(len(filtered_expenses))
            elif self.aggregation == "avg":
                values = [Decimal(str(exp[self.field])) for exp in filtered_expenses 
                         if self.field in exp and exp[self.field] is not None]
                total = sum(values) / len(values) if values else Decimal('0')
            elif self.aggregation == "max":
                values = [Decimal(str(exp[self.field])) for exp in filtered_expenses 
                         if self.field in exp and exp[self.field] is not None]
                total = max(values) if values else Decimal('0')
            elif self.aggregation == "min":
                values = [Decimal(str(exp[self.field])) for exp in filtered_expenses 
                         if self.field in exp and exp[self.field] is not None]
                total = min(values) if values else Decimal('0')
        except (ValueError, TypeError, InvalidOperation):
            return False
        
        # Compare using the same logic as NumericRule
        return self._compare(total, self.value)
    
    def _compare(self, a: Decimal, b: Decimal) -> bool:
        """Compare two values based on operator"""
        if self.operator == ">":
            return a > b
        elif self.operator == "<":
            return a < b
        elif self.operator == ">=":
            return a >= b
        elif self.operator == "<=":
            return a <= b
        elif self.operator == "==":
            return a == b
        elif self.operator == "!=":
            return a != b
        return False


class CompositeRule(Rule):
    """
    Rule that combines multiple rules with AND or OR logic
    
    Current limitations:
    - Only supports single-level combinations (all rules use the same operator)
    - Cannot mix AND with OR in the same composite rule (e.g., (A AND B) OR (C AND D)), user can create nested composite rules to achieve this
    - No parentheses support yet
    
    Future extensibility:
    - Parentheses evaluation for complex expressions
    - Mixed operator support within single composite rule
    - Additional operators (xor, nand, etc.)
    """
    
    def __init__(self, rule_id: str, rules: List[Rule], operator: str = "and"):
        """
        Initialize a composite rule
        
        Args:
            rule_id: Unique identifier for this rule
            rules: List of Rule objects to combine (all must use the same operator)
            operator: Combination operator ("and" or "or") - applies to ALL rules in the list
        
        Note:
            Currently all rules in the list must use the same operator.
            Cannot mix AND/OR in the same composite rule (e.g., (A AND B) OR (C AND D)).
            This is a future enhancement.
        
        Raises:
            RuleValidationError: If configuration is invalid
        """
        # Validate rule configuration before initialization
        RuleValidator.validate_composite_rule(rule_id, rules, operator)
        
        super().__init__(rule_id)
        self.rules = rules
        self.operator = operator
    
    def evaluate_expense(self, expense: Dict[str, Any]) -> bool:
        """
        Evaluate composite rule against a single expense
        
        Args:
            expense: Dictionary containing expense data (see Rule.evaluate_expense for structure)
        
        Returns:
            bool: True if the expense violates this composite rule, False otherwise
            
        Note:
            Uses simple AND/OR logic - all rules must use the same operator.
            For complex expressions like (A AND B) OR (C AND D), create nested CompositeRules.
        """
        if self.operator == "and":
            # All rules must be violated for the composite rule to be violated
            return all(rule.evaluate_expense(expense) for rule in self.rules)
        elif self.operator == "or":
            # Any rule violation means the composite rule is violated
            return any(rule.evaluate_expense(expense) for rule in self.rules)
        return False
    
    def evaluate_trip(self, expenses: List[Dict[str, Any]]) -> bool:
        """
        Evaluate composite rule against all expenses from a single trip
        
        Args:
            expenses: List of expense dictionaries, ALL belonging to the SAME trip.
                     Each expense has the same structure as evaluate_expense parameter.
                     This represents all expenses for a specific trip_id.
        
        Returns:
            bool: True if the trip violates this composite rule, False otherwise
            
        Note:
            Uses simple AND/OR logic - all rules must use the same operator.
            For complex expressions like (A AND B) OR (C AND D), create nested CompositeRules.
        """
        if self.operator == "and":
            # All rules must be violated for the composite rule to be violated
            return all(rule.evaluate_trip(expenses) for rule in self.rules)
        elif self.operator == "or":
            # Any rule violation means the composite rule is violated
            return any(rule.evaluate_trip(expenses) for rule in self.rules)
        return False


class RuleEngine:
    """Main engine for evaluating rules against expenses"""
    
    def __init__(self):
        """Initialize the rule engine with expense validator"""
        self.validator = ExpenseValidator()
    
    def evaluate_rules(self, rules: List[Rule], expenses: List[Dict[str, Any]], 
                      strict_mode: bool = False) -> Dict[str, List[Dict]]:
        """
        Evaluate rules against expenses and return violations
        
        Args:
            rules: List of Rule objects to evaluate against expenses
            expenses: List of expense dictionaries from potentially multiple trips.
                     Each expense contains:
                     - expense_id (str): Unique identifier for the expense
                     - trip_id (str): Identifier of the trip this expense belongs to
                     - amount_usd (Decimal): Expense amount in USD
                     - expense_type (ExpenseType): Type of expense
                     - vendor_type (VendorType): Type of vendor
                     - vendor_name (str): Name of the vendor
            strict_mode: If True, raises exceptions on rule evaluation failures.
                        If False (default), logs warnings and continues processing.
        
        Returns:
            Dict[str, List[Dict]]: Dictionary containing violation results:
                - expense_violations: List of dictionaries with expense-level violations:
                    - expense_id (str): ID of violating expense
                    - violated_rules (List[str]): List of rule IDs that were violated
                    - details (str): Human-readable description of violations
                - trip_violations: List of dictionaries with trip-level violations:
                    - trip_id (str): ID of violating trip
                    - violated_rules (List[str]): List of rule IDs that were violated
                    - details (str): Human-readable description of violations
        
        Raises:
            ExpenseValidationError: If any expense data is invalid
        """
        # Validate all expenses first
        for expense in expenses:
            self.validator.validate_expense(expense)
        
        # Group expenses by trip_id for optimization
        trips = defaultdict(list)
        for expense in expenses:
            trips[expense["trip_id"]].append(expense)
        
        expense_violations = []
        trip_violations = []
        
        # Evaluate expense-level rules
        for expense in expenses:
            violated_rules = []
            for rule in rules:
                try:
                    if rule.evaluate_expense(expense):
                        violated_rules.append(rule.get_rule_id())
                except Exception as e:
                    if strict_mode:
                        # In strict mode, propagate exceptions to caller
                        raise Exception(f"Rule '{rule.get_rule_id()}' failed for expense '{expense['expense_id']}': {e}") from e
                    else:
                        # Log rule evaluation failures for debugging while maintaining resilience
                        # In production, this would use proper logging (e.g., logger.warning)
                        print(f"Warning: Rule '{rule.get_rule_id()}' failed for expense '{expense['expense_id']}': {e}")
                        # Skip this rule but continue with others
                        continue
            
            if violated_rules:
                expense_violations.append({
                    "expense_id": expense["expense_id"],
                    "violated_rules": violated_rules,
                    "details": f"Expense violates rules: {', '.join(violated_rules)}"
                })
        
        # Evaluate trip-level rules
        for trip_id, trip_expenses in trips.items():
            violated_rules = []
            for rule in rules:
                try:
                    if rule.evaluate_trip(trip_expenses):
                        violated_rules.append(rule.get_rule_id())
                except Exception as e:
                    if strict_mode:
                        # In strict mode, propagate exceptions to caller
                        raise Exception(f"Rule '{rule.get_rule_id()}' failed for trip '{trip_id}': {e}") from e
                    else:
                        # Log rule evaluation failures for debugging while maintaining resilience
                        # In production, this would use proper logging (e.g., logger.warning)
                        print(f"Warning: Rule '{rule.get_rule_id()}' failed for trip '{trip_id}': {e}")
                        # Skip this rule but continue with others
                        continue
            
            if violated_rules:
                trip_violations.append({
                    "trip_id": trip_id,
                    "violated_rules": violated_rules,
                    "details": f"Trip violates rules: {', '.join(violated_rules)}"
                })
        
        return {
            "expense_violations": expense_violations,
            "trip_violations": trip_violations
        }


def create_restaurant_limit_rule(rule_id: str = "restaurant_limit") -> CompositeRule:
    """
    Create a restaurant limit rule using composite rule pattern
    
    This demonstrates how to create a composite rule that combines:
    - StringRule: vendor_type == "restaurant" 
    - NumericRule: amount_usd > 75.0
    
    Args:
        rule_id: Unique identifier for this rule
        
    Returns:
        CompositeRule: A composite rule that combines restaurant check and amount limit
    """
    restaurant_check = StringRule("temp_restaurant", "vendor_type", "==", VendorType.RESTAURANT.value)
    amount_limit = NumericRule("temp_amount", "amount_usd", ">", 75.0)
    
    return CompositeRule(rule_id, [restaurant_check, amount_limit], "and")


def create_initial_rules() -> List[Rule]:
    """
    Create the 4 initial rules plus 2 new trip-level rules as specified in the problem
    
    Returns:
        List[Rule]: List containing:
            - CompositeRule: No restaurant expense > $75 (vendor_type == "restaurant" AND amount_usd > 75)
            - StringRule: No airfare expenses
            - StringRule: No entertainment expenses  
            - NumericRule: No expenses > $250
            - AggregateRule: Trip total expenses <= $2000
            - AggregateRule: Trip meal expenses <= $200
    """
    rules = [
        # Original rules
        create_restaurant_limit_rule("restaurant_limit"),
        StringRule("no_airfare", "expense_type", "==", ExpenseType.AIRFARE.value),
        StringRule("no_entertainment", "expense_type", "==", ExpenseType.ENTERTAINMENT.value),
        NumericRule("expense_limit", "amount_usd", ">", 250.0),
        
        # New trip-level rules
        AggregateRule("trip_total_limit", "amount_usd", ">", 2000.0, "sum"),
        AggregateRule("trip_meal_limit", "amount_usd", ">", 200.0, "sum", "expense_type", ExpenseType.MEALS.value),
    ]
    
    return rules


def create_expense(expense_id: str, trip_id: str, amount_usd: Union[str, float, Decimal], 
                  expense_type: Union[ExpenseType, str], vendor_type: Union[VendorType, str], 
                  vendor_name: str) -> Dict[str, Any]:
    """
    Helper function to create expenses with proper enum handling and validation
    
    Args:
        expense_id: Unique identifier for the expense
        trip_id: Trip identifier this expense belongs to
        amount_usd: Expense amount (will be converted to Decimal for precise calculations)
        expense_type: Type of expense (ExpenseType enum or string value)
        vendor_type: Type of vendor (VendorType enum or string value)  
        vendor_name: Name of the vendor
        
    Returns:
        Dict[str, Any]: Dictionary representing the expense with proper types:
            - expense_id (str): Unique identifier
            - trip_id (str): Trip identifier
            - amount_usd (Decimal): Expense amount with precise decimal arithmetic
            - expense_type (ExpenseType): Type of expense as enum
            - vendor_type (VendorType): Type of vendor as enum
            - vendor_name (str): Vendor name
    
    Raises:
        ValueError: If expense_type or vendor_type string values are invalid
    """
    # Convert string expense_type to enum if needed
    if isinstance(expense_type, str):
        expense_type = ExpenseType(expense_type)
    
    # Convert string vendor_type to enum if needed
    if isinstance(vendor_type, str):
        vendor_type = VendorType(vendor_type)
    
    return {
        "expense_id": expense_id,
        "trip_id": trip_id,
        "amount_usd": Decimal(str(amount_usd)),
        "expense_type": expense_type,
        "vendor_type": vendor_type,
        "vendor_name": vendor_name
    }


def evaluate_rules(rules: List[Rule], expenses: List[Dict[str, Any]], strict_mode: bool = False) -> Dict[str, List[Dict]]:
    """
    Main function to evaluate rules against expenses
    
    Args:
        rules: List of Rule objects to evaluate against expenses
        expenses: List of expense dictionaries, each containing:
            - expense_id (str): Unique identifier for the expense
            - trip_id (str): Identifier of the trip this expense belongs to
            - amount_usd (Decimal): Expense amount in USD
            - expense_type (ExpenseType): Type of expense
            - vendor_type (VendorType): Type of vendor
            - vendor_name (str): Name of the vendor
        strict_mode: If True, raises exceptions on rule evaluation failures.
                    If False (default), logs warnings and continues processing.
    
    Returns:
        Dict[str, List[Dict]]: Dictionary containing violation results:
            - expense_violations: List of dictionaries with expense-level violations:
                - expense_id (str): ID of violating expense
                - violated_rules (List[str]): List of rule IDs that were violated
                - details (str): Human-readable description of violations
            - trip_violations: List of dictionaries with trip-level violations:
                - trip_id (str): ID of violating trip
                - violated_rules (List[str]): List of rule IDs that were violated
                - details (str): Human-readable description of violations
    
    Raises:
        ExpenseValidationError: If any expense data is invalid
    """
    engine = RuleEngine()
    return engine.evaluate_rules(rules, expenses, strict_mode)


# Example usage and test
if __name__ == "__main__":
    # Sample expenses from the problem - using helper function for type safety
    # These expenses represent data from multiple trips that will be grouped by trip_id
    sample_expenses = [
        create_expense("001", "001", "49.99", ExpenseType.SUPPLIES, VendorType.RESTAURANT, "Outback Roadhouse"),
        create_expense("002", "001", "125.00", ExpenseType.SUPPLIES, VendorType.RETAILER, "Staples"),
        create_expense("003", "002", "153.00", ExpenseType.MEALS, VendorType.RESTAURANT, "Olive Yurt"),
        create_expense("004", "002", "1996.00", ExpenseType.AIRFARE, VendorType.TRANSPORTATION, "Southeast Airlines"),
        create_expense("005", "002", "34.68", ExpenseType.MEALS, VendorType.RESTAURANT, "The Great Grill"),
        create_expense("006", "002", "22.40", ExpenseType.MEALS, VendorType.RESTAURANT, "The Great Grill"),
        create_expense("007", "003", "59.50", ExpenseType.ENTERTAINMENT, VendorType.THEATER, "Silver Screen")
    ]
    
    # Create rules and evaluate
    rules = create_initial_rules()
    results = evaluate_rules(rules, sample_expenses)
    
    print("=== EXPENSE VIOLATIONS ===")
    for violation in results["expense_violations"]:
        print(f"Expense {violation['expense_id']}: {violation['details']}")
    
    print("\n=== TRIP VIOLATIONS ===") 
    for violation in results["trip_violations"]:
        print(f"Trip {violation['trip_id']}: {violation['details']}")
