"""
API Interface Definition for Quora-like Application

This module defines the complete API interface with:
- Request/Response schemas
- Data models
- Validation rules
- Error codes
"""

from dataclasses import dataclass
from typing import List, Optional, Dict, Any
from enum import Enum

class ErrorCode(Enum):
    """Standardized error codes for the API"""
    SUCCESS = "SUCCESS"
    VALIDATION_ERROR = "VALIDATION_ERROR"
    NOT_FOUND = "NOT_FOUND"
    DUPLICATE_ENTRY = "DUPLICATE_ENTRY"
    INTERNAL_ERROR = "INTERNAL_ERROR"

@dataclass
class APIResponse:
    """Standard API response format"""
    status: str
    message: str
    data: Optional[Dict[str, Any]] = None
    error_code: Optional[ErrorCode] = None

@dataclass
class Question:
    """Question data model"""
    question_id: int
    question_text: str
    votes: int = 0
    created_at: Optional[str] = None

@dataclass
class Answer:
    """Answer data model"""
    answer_id: int
    answer_text: str
    question_id: int
    votes: int = 0
    created_at: Optional[str] = None

@dataclass
class CreateQuestionRequest:
    """Request schema for creating a question"""
    question_id: int
    question_text: str
    
    def validate(self) -> List[str]:
        """Validate request data"""
        errors = []
        if not isinstance(self.question_id, int) or self.question_id <= 0:
            errors.append("question_id must be a positive integer")
        if not isinstance(self.question_text, str) or not self.question_text.strip():
            errors.append("question_text must be a non-empty string")
        if len(self.question_text) > 1000:
            errors.append("question_text must be less than 1000 characters")
        return errors

@dataclass
class CreateAnswerRequest:
    """Request schema for creating an answer"""
    answer_id: int
    answer_text: str
    question_id: int
    
    def validate(self) -> List[str]:
        """Validate request data"""
        errors = []
        if not isinstance(self.answer_id, int) or self.answer_id <= 0:
            errors.append("answer_id must be a positive integer")
        if not isinstance(self.answer_text, str) or not self.answer_text.strip():
            errors.append("answer_text must be a non-empty string")
        if not isinstance(self.question_id, int) or self.question_id <= 0:
            errors.append("question_id must be a positive integer")
        if len(self.answer_text) > 2000:
            errors.append("answer_text must be less than 2000 characters")
        return errors

@dataclass
class VoteRequest:
    """Request schema for voting operations"""
    question_id: Optional[int] = None
    answer_id: Optional[int] = None
    
    def validate(self) -> List[str]:
        """Validate request data"""
        errors = []
        if self.question_id is None and self.answer_id is None:
            errors.append("Either question_id or answer_id must be provided")
        if self.question_id is not None and self.answer_id is not None:
            errors.append("Cannot provide both question_id and answer_id")
        if self.question_id is not None and (not isinstance(self.question_id, int) or self.question_id <= 0):
            errors.append("question_id must be a positive integer")
        if self.answer_id is not None and (not isinstance(self.answer_id, int) or self.answer_id <= 0):
            errors.append("answer_id must be a positive integer")
        return errors

@dataclass
class TopItemsRequest:
    """Request schema for getting top questions/answers"""
    n: int
    
    def validate(self) -> List[str]:
        """Validate request data"""
        errors = []
        if not isinstance(self.n, int) or self.n <= 0:
            errors.append("n must be a positive integer")
        if self.n > 100:
            errors.append("n must be less than or equal to 100")
        return errors

# API Endpoint Definitions
API_ENDPOINTS = {
    "create_question": {
        "method": "POST",
        "path": "/create_question",
        "description": "Create a new question",
        "request_schema": CreateQuestionRequest,
        "response_schema": APIResponse,
        "example_request": {
            "question_id": 1,
            "question_text": "What is Python?"
        },
        "example_response": {
            "status": "success",
            "message": "Question 1 created successfully",
            "data": {
                "question_id": 1,
                "question_text": "What is Python?",
                "votes": 0
            }
        }
    },
    
    "create_answer": {
        "method": "POST",
        "path": "/create_answer",
        "description": "Create a new answer for a question",
        "request_schema": CreateAnswerRequest,
        "response_schema": APIResponse,
        "example_request": {
            "answer_id": 1,
            "answer_text": "Python is a high-level programming language",
            "question_id": 1
        },
        "example_response": {
            "status": "success",
            "message": "Answer 1 created successfully",
            "data": {
                "answer_id": 1,
                "answer_text": "Python is a high-level programming language",
                "question_id": 1,
                "votes": 0
            }
        }
    },
    
    "upvote_question": {
        "method": "POST",
        "path": "/upvote_question",
        "description": "Upvote a question",
        "request_schema": VoteRequest,
        "response_schema": APIResponse,
        "example_request": {
            "question_id": 1
        },
        "example_response": {
            "status": "success",
            "message": "Question 1 upvoted",
            "data": {
                "question_id": 1,
                "votes": 1
            }
        }
    },
    
    "downvote_question": {
        "method": "POST",
        "path": "/downvote_question",
        "description": "Downvote a question",
        "request_schema": VoteRequest,
        "response_schema": APIResponse,
        "example_request": {
            "question_id": 1
        },
        "example_response": {
            "status": "success",
            "message": "Question 1 downvoted",
            "data": {
                "question_id": 1,
                "votes": -1
            }
        }
    },
    
    "upvote_answer": {
        "method": "POST",
        "path": "/upvote_answer",
        "description": "Upvote an answer",
        "request_schema": VoteRequest,
        "response_schema": APIResponse,
        "example_request": {
            "answer_id": 1
        },
        "example_response": {
            "status": "success",
            "message": "Answer 1 upvoted",
            "data": {
                "answer_id": 1,
                "votes": 1
            }
        }
    },
    
    "downvote_answer": {
        "method": "POST",
        "path": "/downvote_answer",
        "description": "Downvote an answer",
        "request_schema": VoteRequest,
        "response_schema": APIResponse,
        "example_request": {
            "answer_id": 1
        },
        "example_response": {
            "status": "success",
            "message": "Answer 1 downvoted",
            "data": {
                "answer_id": 1,
                "votes": -1
            }
        }
    },
    
    "get_top_questions": {
        "method": "GET",
        "path": "/get_top_questions",
        "description": "Get top N questions by votes",
        "request_schema": TopItemsRequest,
        "response_schema": APIResponse,
        "query_parameters": {
            "n": {
                "type": "integer",
                "required": True,
                "description": "Number of top questions to return",
                "minimum": 1,
                "maximum": 100
            }
        },
        "example_request": "GET /get_top_questions?n=5",
        "example_response": {
            "status": "success",
            "message": "Top questions retrieved successfully",
            "data": {
                "count": 5,
                "questions": [
                    {
                        "question_id": 1,
                        "question_text": "What is Python?",
                        "votes": 10
                    }
                ]
            }
        }
    },
    
    "get_top_answers": {
        "method": "GET",
        "path": "/get_top_answers",
        "description": "Get top N answers by votes",
        "request_schema": TopItemsRequest,
        "response_schema": APIResponse,
        "query_parameters": {
            "n": {
                "type": "integer",
                "required": True,
                "description": "Number of top answers to return",
                "minimum": 1,
                "maximum": 100
            }
        },
        "example_request": "GET /get_top_answers?n=5",
        "example_response": {
            "status": "success",
            "message": "Top answers retrieved successfully",
            "data": {
                "count": 5,
                "answers": [
                    {
                        "answer_id": 1,
                        "answer_text": "Python is a high-level programming language",
                        "question_id": 1,
                        "votes": 8
                    }
                ]
            }
        }
    }
}

# Error Response Examples
ERROR_EXAMPLES = {
    "VALIDATION_ERROR": {
        "status": "error",
        "message": "Validation failed",
        "error_code": "VALIDATION_ERROR",
        "data": {
            "errors": ["question_id must be a positive integer"]
        }
    },
    "NOT_FOUND": {
        "status": "error",
        "message": "Question with id 999 does not exist",
        "error_code": "NOT_FOUND"
    },
    "DUPLICATE_ENTRY": {
        "status": "error",
        "message": "Question with id 1 already exists",
        "error_code": "DUPLICATE_ENTRY"
    },
    "INTERNAL_ERROR": {
        "status": "error",
        "message": "Internal server error",
        "error_code": "INTERNAL_ERROR"
    }
}

# API Design Principles
API_DESIGN_PRINCIPLES = """
API Design Principles for Quora-like Application:

1. **RESTful Design**:
   - Use appropriate HTTP methods (GET, POST)
   - Consistent URL patterns
   - Resource-based URLs

2. **Standardized Responses**:
   - Consistent response format across all endpoints
   - Clear success/error indicators
   - Meaningful error messages

3. **Input Validation**:
   - Validate all input parameters
   - Provide clear validation error messages
   - Set reasonable limits on input size

4. **Error Handling**:
   - Use standard HTTP status codes
   - Provide detailed error information
   - Handle edge cases gracefully

5. **Data Models**:
   - Clear data structures
   - Type safety with validation
   - Consistent field naming

6. **Documentation**:
   - Complete endpoint documentation
   - Request/response examples
   - Error code definitions
"""

def get_api_spec() -> Dict[str, Any]:
    """Return complete API specification"""
    return {
        "title": "Quora-like API",
        "version": "1.0.0",
        "description": "A simple Q&A platform with voting functionality",
        "base_url": "http://localhost:5000",
        "endpoints": API_ENDPOINTS,
        "error_examples": ERROR_EXAMPLES,
        "data_models": {
            "Question": Question,
            "Answer": Answer,
            "APIResponse": APIResponse
        },
        "validation_rules": {
            "max_question_length": 1000,
            "max_answer_length": 2000,
            "max_top_items": 100,
            "min_id": 1
        }
    }

if __name__ == "__main__":
    import json
    spec = get_api_spec()
    print(json.dumps(spec, indent=2, default=str))
