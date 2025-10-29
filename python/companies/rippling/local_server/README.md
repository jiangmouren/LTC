# Quora-like API Server

A Flask-based API server that simulates a Quora-like question and answer platform with voting functionality.

## 🚀 Quick Start

### 1. Start the Server
```bash
python local_server.py
```
The server will run on `http://localhost:5000`

### 2. Run Tests
```bash
# Run comprehensive tests
python test_api.py

# Run interactive tests
python test_api.py interactive
```

## 📋 API Endpoints

### Core Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create_question` | Create a new question |
| POST | `/create_answer` | Create an answer for a question |
| POST | `/upvote_question` | Upvote a question |
| POST | `/downvote_question` | Downvote a question |
| POST | `/upvote_answer` | Upvote an answer |
| POST | `/downvote_answer` | Downvote an answer |
| GET | `/get_top_questions` | Get top N questions by votes |
| GET | `/get_top_answers` | Get top N answers by votes |

### Utility Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/health` | Health check |
| GET | `/questions` | Get all questions (debug) |
| GET | `/answers` | Get all answers (debug) |

## 📝 Request/Response Examples

### Create Question
```bash
curl -X POST http://localhost:5000/create_question \
  -H "Content-Type: application/json" \
  -d '{"question_id": 1, "question_text": "What is Python?"}'
```

**Response:**
```json
{
  "status": "success",
  "message": "Question 1 created successfully",
  "question_id": 1,
  "question_text": "What is Python?"
}
```

### Create Answer
```bash
curl -X POST http://localhost:5000/create_answer \
  -H "Content-Type: application/json" \
  -d '{"answer_id": 1, "answer_text": "Python is a programming language", "question_id": 1}'
```

### Upvote Question
```bash
curl -X POST http://localhost:5000/upvote_question \
  -H "Content-Type: application/json" \
  -d '{"question_id": 1}'
```

### Get Top Questions
```bash
curl http://localhost:5000/get_top_questions?n=5
```

**Response:**
```json
{
  "status": "success",
  "count": 5,
  "questions": [
    {
      "question_id": 1,
      "question_text": "What is Python?",
      "votes": 3
    }
  ]
}
```

## 🧪 Testing

The `test_api.py` script provides comprehensive testing:

### Automated Tests
- Health check
- Question and answer creation
- Voting functionality
- Top questions/answers retrieval
- Error handling scenarios

### Interactive Mode
Run `python test_api.py interactive` for manual testing with commands like:
- `create_question 1 "What is Python?"`
- `upvote_question 1`
- `top_questions 5`

## 🏗️ Architecture

### Data Structures
- `questions`: Dictionary storing question_id → question_text
- `answers`: Dictionary storing answer_id → (answer_text, question_id)
- `question_votes`: Dictionary storing question_id → vote_count
- `answer_votes`: Dictionary storing answer_id → vote_count

### Features
- ✅ In-memory storage (perfect for interviews)
- ✅ RESTful API design
- ✅ Comprehensive error handling
- ✅ Input validation
- ✅ JSON request/response format
- ✅ Efficient sorting for top results
- ✅ Type hints for code clarity

## 🎯 Interview-Ready Features

This implementation demonstrates:
- **Flask proficiency**: Clean server setup and routing
- **API design**: RESTful endpoints with proper HTTP methods
- **Error handling**: Comprehensive validation and meaningful error messages
- **Data structures**: Efficient in-memory storage and sorting
- **Code quality**: Type hints, clean structure, and documentation

## 🔧 Dependencies

```bash
pip install flask requests
```

## 📊 Sample Data Flow

1. Create questions with unique IDs
2. Create answers linked to existing questions
3. Users vote on questions and answers
4. Retrieve top-rated content by votes
5. System handles edge cases and errors gracefully

Perfect for technical interviews focusing on API design, Flask, and data manipulation!
