# API Design Guide for Interviews

## 🎯 **Why Clear Interface Definition Matters**

For API design interviews, having a clearly defined interface demonstrates:

- **Professional thinking** - Shows you understand enterprise-level API development
- **Attention to detail** - Proves you consider edge cases and validation
- **Communication skills** - Makes it easy for others to understand and use your API
- **Scalability mindset** - Indicates you think about future maintenance and expansion

## 📋 **Essential Components of API Interface**

### 1. **Request/Response Schemas**
```python
@dataclass
class CreateQuestionRequest:
    question_id: int
    question_text: str
    
    def validate(self) -> List[str]:
        errors = []
        if not isinstance(self.question_id, int) or self.question_id <= 0:
            errors.append("question_id must be a positive integer")
        # ... more validation
        return errors
```

### 2. **Standardized Response Format**
```json
{
  "status": "success|error",
  "message": "Human-readable message",
  "data": { /* response data */ },
  "error_code": "VALIDATION_ERROR" // if error
}
```

### 3. **Error Code Definitions**
```python
class ErrorCode(Enum):
    SUCCESS = "SUCCESS"
    VALIDATION_ERROR = "VALIDATION_ERROR"
    NOT_FOUND = "NOT_FOUND"
    DUPLICATE_ENTRY = "DUPLICATE_ENTRY"
    INTERNAL_ERROR = "INTERNAL_ERROR"
```

### 4. **Complete Endpoint Documentation**
- HTTP method and path
- Request schema with validation rules
- Response schema with examples
- Error scenarios and codes
- Query parameters (for GET requests)

## 🏗️ **API Design Best Practices**

### **1. RESTful Design**
```
POST /create_question     # Create resource
GET  /get_top_questions   # Retrieve resources
POST /upvote_question     # Action on resource
```

### **2. Consistent Naming**
- Use nouns for resources: `/questions`, `/answers`
- Use verbs for actions: `/upvote`, `/downvote`
- Consistent field naming: `question_id`, `answer_id`

### **3. Input Validation**
- **Type validation**: Ensure correct data types
- **Range validation**: Check min/max values
- **Format validation**: Validate string formats
- **Business logic validation**: Check business rules

### **4. Error Handling**
- **HTTP status codes**: 200, 400, 404, 500
- **Error messages**: Clear, actionable messages
- **Error codes**: Standardized error identifiers
- **Validation errors**: Detailed field-level errors

### **5. Response Consistency**
- **Standard format**: Same structure for all responses
- **Metadata**: Include useful metadata (count, pagination)
- **Data nesting**: Logical grouping of response data

## 📊 **Interview-Ready Features**

### **Data Models**
```python
@dataclass
class Question:
    question_id: int
    question_text: str
    votes: int = 0
    created_at: Optional[str] = None
```

### **Validation Layer**
```python
def validate(self) -> List[str]:
    """Return list of validation errors"""
    errors = []
    # Validation logic here
    return errors
```

### **Type Safety**
- Use type hints throughout
- Dataclasses for structured data
- Optional types for nullable fields

## 🎯 **Interview Discussion Points**

### **1. API Versioning**
```
/api/v1/questions
/api/v2/questions  # Future version
```

### **2. Pagination**
```python
GET /questions?page=1&limit=20&sort=votes_desc
```

### **3. Filtering & Search**
```python
GET /questions?search=python&category=programming
```

### **4. Rate Limiting**
```
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 99
```

### **5. Authentication**
```
Authorization: Bearer <token>
```

## 📝 **Documentation Standards**

### **Endpoint Documentation**
```python
{
    "method": "POST",
    "path": "/create_question",
    "description": "Create a new question",
    "request_schema": CreateQuestionRequest,
    "response_schema": APIResponse,
    "example_request": {...},
    "example_response": {...}
}
```

### **Error Documentation**
```python
ERROR_EXAMPLES = {
    "VALIDATION_ERROR": {
        "status": "error",
        "message": "Validation failed",
        "error_code": "VALIDATION_ERROR",
        "data": {"errors": ["question_id must be positive"]}
    }
}
```

## 🚀 **Implementation Checklist**

- [ ] Define data models with validation
- [ ] Create standardized response format
- [ ] Implement comprehensive error handling
- [ ] Document all endpoints with examples
- [ ] Add input validation for all parameters
- [ ] Use appropriate HTTP status codes
- [ ] Include error codes and messages
- [ ] Test edge cases and error scenarios

## 💡 **Interview Tips**

1. **Start with the interface** - Define schemas before implementation
2. **Think about edge cases** - What happens with invalid data?
3. **Consider scalability** - How would this work with millions of questions?
4. **Show validation thinking** - Demonstrate input sanitization
5. **Explain design decisions** - Why did you choose this structure?

This approach shows you understand production-ready API development, not just basic CRUD operations!
