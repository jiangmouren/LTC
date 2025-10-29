"""
Spin up a a local api server for a quora like application.

It supports the following operations: 
* create_question(question_id: int, question_text: str)
* create_answer(answer_id: int, answer_text: str, question_id: int)
* upvote_question(question_id: int)
* downvote_question(question_id: int)
* upvote_answer(answer_id: int)
* downvote_answer(answer_id: int)
* get_top_questions(n: int)
* get_top_answers(n: int)
"""

from flask import Flask, request, jsonify
from collections import defaultdict
import heapq
from typing import Dict, List, Tuple
from api_interface import (
    CreateQuestionRequest, CreateAnswerRequest, VoteRequest, TopItemsRequest,
    APIResponse, ErrorCode, Question, Answer
)

app = Flask(__name__)

# In-memory data structures for the application
questions: Dict[int, str] = {}
answers: Dict[int, Tuple[str, int]] = {}  # answer_id -> (answer_text, question_id)
question_votes: Dict[int, int] = defaultdict(int)
answer_votes: Dict[int, int] = defaultdict(int)

# Counter for auto-generating IDs (optional enhancement)
next_question_id = 1
next_answer_id = 1

# Helper functions for standardized responses
def create_success_response(message: str, data: Dict = None) -> tuple:
    """Create standardized success response"""
    response_data = {"status": "success", "message": message}
    if data:
        response_data.update(data)
    return jsonify(response_data), 200

def create_error_response(message: str, error_code: ErrorCode, status_code: int = 400, errors: List[str] = None) -> tuple:
    """Create standardized error response"""
    response_data = {
        "status": "error",
        "message": message,
        "error_code": error_code.value
    }
    if errors:
        response_data["data"] = {"errors": errors}
    return jsonify(response_data), status_code


@app.route('/health', methods=['GET'])
def health_check():
    """Simple health check endpoint"""
    return create_success_response("Quora-like API server is running", {"health": "healthy"})


@app.route('/create_question', methods=['POST'])
def create_question():
    """Create a new question"""
    try:
        data = request.get_json()
        
        if not data:
            return create_error_response(
                "No JSON data provided", 
                ErrorCode.VALIDATION_ERROR, 
                400
            )
        
        # Create request object and validate
        try:
            req = CreateQuestionRequest(
                question_id=data.get('question_id'),
                question_text=data.get('question_text')
            )
        except TypeError:
            return create_error_response(
                "Invalid request format", 
                ErrorCode.VALIDATION_ERROR, 
                400
            )
        
        # Validate the request
        validation_errors = req.validate()
        if validation_errors:
            return create_error_response(
                "Validation failed", 
                ErrorCode.VALIDATION_ERROR, 
                400, 
                validation_errors
            )
            
        if req.question_id in questions:
            return create_error_response(
                f"Question with id {req.question_id} already exists", 
                ErrorCode.DUPLICATE_ENTRY, 
                400
            )
            
        questions[req.question_id] = req.question_text
        question_votes[req.question_id] = 0  # Initialize votes to 0
        
        return create_success_response(
            f"Question {req.question_id} created successfully",
            {
                "question_id": req.question_id,
                "question_text": req.question_text,
                "votes": 0
            }
        ), 201
        
    except Exception as e:
        return create_error_response(
            f"Internal server error: {str(e)}", 
            ErrorCode.INTERNAL_ERROR, 
            500
        )


@app.route('/create_answer', methods=['POST'])
def create_answer():
    """Create a new answer for a question"""
    try:
        data = request.get_json()
        
        if not data:
            return create_error_response(
                "No JSON data provided", 
                ErrorCode.VALIDATION_ERROR, 
                400
            )
        
        # Create request object and validate
        try:
            req = CreateAnswerRequest(
                answer_id=data.get('answer_id'),
                answer_text=data.get('answer_text'),
                question_id=data.get('question_id')
            )
        except TypeError:
            return create_error_response(
                "Invalid request format", 
                ErrorCode.VALIDATION_ERROR, 
                400
            )
        
        # Validate the request
        validation_errors = req.validate()
        if validation_errors:
            return create_error_response(
                "Validation failed", 
                ErrorCode.VALIDATION_ERROR, 
                400, 
                validation_errors
            )
            
        if req.answer_id in answers:
            return create_error_response(
                f"Answer with id {req.answer_id} already exists", 
                ErrorCode.DUPLICATE_ENTRY, 
                400
            )
            
        if req.question_id not in questions:
            return create_error_response(
                f"Question with id {req.question_id} does not exist", 
                ErrorCode.NOT_FOUND, 
                400
            )
            
        answers[req.answer_id] = (req.answer_text, req.question_id)
        answer_votes[req.answer_id] = 0  # Initialize votes to 0
        
        return create_success_response(
            f"Answer {req.answer_id} created successfully",
            {
                "answer_id": req.answer_id,
                "answer_text": req.answer_text,
                "question_id": req.question_id,
                "votes": 0
            }
        ), 201
        
    except Exception as e:
        return create_error_response(
            f"Internal server error: {str(e)}", 
            ErrorCode.INTERNAL_ERROR, 
            500
        )


@app.route('/upvote_question', methods=['POST'])
def upvote_question():
    """Upvote a question"""
    try:
        data = request.get_json()
        
        if not data:
            return create_error_response(
                "No JSON data provided", 
                ErrorCode.VALIDATION_ERROR, 
                400
            )
        
        # Create request object and validate
        try:
            req = VoteRequest(question_id=data.get('question_id'))
        except TypeError:
            return create_error_response(
                "Invalid request format", 
                ErrorCode.VALIDATION_ERROR, 
                400
            )
        
        # Validate the request
        validation_errors = req.validate()
        if validation_errors:
            return create_error_response(
                "Validation failed", 
                ErrorCode.VALIDATION_ERROR, 
                400, 
                validation_errors
            )
            
        if req.question_id not in questions:
            return create_error_response(
                f"Question with id {req.question_id} does not exist", 
                ErrorCode.NOT_FOUND, 
                400
            )
            
        question_votes[req.question_id] += 1
        
        return create_success_response(
            f"Question {req.question_id} upvoted",
            {
                "question_id": req.question_id,
                "votes": question_votes[req.question_id]
            }
        )
        
    except Exception as e:
        return create_error_response(
            f"Internal server error: {str(e)}", 
            ErrorCode.INTERNAL_ERROR, 
            500
        )


@app.route('/downvote_question', methods=['POST'])
def downvote_question():
    """Downvote a question"""
    try:
        data = request.get_json()
        
        if not data:
            return jsonify({"error": "No JSON data provided"}), 400
            
        question_id = data.get('question_id')
        
        if question_id is None:
            return jsonify({"error": "question_id is required"}), 400
            
        if question_id not in questions:
            return jsonify({"error": f"Question with id {question_id} does not exist"}), 400
            
        question_votes[question_id] -= 1
        
        return jsonify({
            "status": "success",
            "message": f"Question {question_id} downvoted",
            "question_id": question_id,
            "votes": question_votes[question_id]
        }), 200
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route('/upvote_answer', methods=['POST'])
def upvote_answer():
    """Upvote an answer"""
    try:
        data = request.get_json()
        
        if not data:
            return jsonify({"error": "No JSON data provided"}), 400
            
        answer_id = data.get('answer_id')
        
        if answer_id is None:
            return jsonify({"error": "answer_id is required"}), 400
            
        if answer_id not in answers:
            return jsonify({"error": f"Answer with id {answer_id} does not exist"}), 400
            
        answer_votes[answer_id] += 1
        
        return jsonify({
            "status": "success",
            "message": f"Answer {answer_id} upvoted",
            "answer_id": answer_id,
            "votes": answer_votes[answer_id]
        }), 200
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route('/downvote_answer', methods=['POST'])
def downvote_answer():
    """Downvote an answer"""
    try:
        data = request.get_json()
        
        if not data:
            return jsonify({"error": "No JSON data provided"}), 400
            
        answer_id = data.get('answer_id')
        
        if answer_id is None:
            return jsonify({"error": "answer_id is required"}), 400
            
        if answer_id not in answers:
            return jsonify({"error": f"Answer with id {answer_id} does not exist"}), 400
            
        answer_votes[answer_id] -= 1
        
        return jsonify({
            "status": "success",
            "message": f"Answer {answer_id} downvoted",
            "answer_id": answer_id,
            "votes": answer_votes[answer_id]
        }), 200
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route('/get_top_questions', methods=['GET'])
def get_top_questions():
    """Get top N questions by votes"""
    try:
        n = request.args.get('n', type=int)
        
        # Create request object and validate
        try:
            req = TopItemsRequest(n=n if n is not None else 0)
        except TypeError:
            return create_error_response(
                "Invalid request format", 
                ErrorCode.VALIDATION_ERROR, 
                400
            )
        
        # Validate the request
        validation_errors = req.validate()
        if validation_errors:
            return create_error_response(
                "Validation failed", 
                ErrorCode.VALIDATION_ERROR, 
                400, 
                validation_errors
            )
            
        if not questions:
            return create_success_response(
                "No questions available", 
                {"count": 0, "questions": []}
            )
            
        # Create list of (votes, question_id, question_text) and sort by votes (descending)
        question_list = []
        for q_id, q_text in questions.items():
            votes = question_votes[q_id]
            question_list.append((votes, q_id, q_text))
            
        # Sort by votes descending, then by question_id ascending (for tie-breaking)
        question_list.sort(key=lambda x: (-x[0], x[1]))
        
        # Take top n questions
        top_questions = question_list[:req.n]
        
        result = []
        for votes, q_id, q_text in top_questions:
            result.append({
                "question_id": q_id,
                "question_text": q_text,
                "votes": votes
            })
            
        return create_success_response(
            "Top questions retrieved successfully",
            {
                "count": len(result),
                "questions": result
            }
        )
        
    except Exception as e:
        return create_error_response(
            f"Internal server error: {str(e)}", 
            ErrorCode.INTERNAL_ERROR, 
            500
        )


@app.route('/get_top_answers', methods=['GET'])
def get_top_answers():
    """Get top N answers by votes"""
    try:
        n = request.args.get('n', type=int)
        
        if n is None:
            return jsonify({"error": "Parameter 'n' is required"}), 400
            
        if n <= 0:
            return jsonify({"error": "Parameter 'n' must be a positive integer"}), 400
            
        if not answers:
            return jsonify({"message": "No answers available", "answers": []}), 200
            
        # Create list of (votes, answer_id, answer_text, question_id) and sort by votes
        answer_list = []
        for a_id, (a_text, q_id) in answers.items():
            votes = answer_votes[a_id]
            answer_list.append((votes, a_id, a_text, q_id))
            
        # Sort by votes descending, then by answer_id ascending (for tie-breaking)
        answer_list.sort(key=lambda x: (-x[0], x[1]))
        
        # Take top n answers
        top_answers = answer_list[:n]
        
        result = []
        for votes, a_id, a_text, q_id in top_answers:
            result.append({
                "answer_id": a_id,
                "answer_text": a_text,
                "question_id": q_id,
                "votes": votes
            })
            
        return jsonify({
            "status": "success",
            "count": len(result),
            "answers": result
        }), 200
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route('/questions', methods=['GET'])
def get_all_questions():
    """Get all questions (useful for debugging/testing)"""
    result = []
    for q_id, q_text in questions.items():
        result.append({
            "question_id": q_id,
            "question_text": q_text,
            "votes": question_votes[q_id]
        })
    
    return create_success_response(
        "All questions retrieved successfully",
        {
            "count": len(result),
            "questions": result
        }
    )


@app.route('/answers', methods=['GET'])
def get_all_answers():
    """Get all answers (useful for debugging/testing)"""
    result = []
    for a_id, (a_text, q_id) in answers.items():
        result.append({
            "answer_id": a_id,
            "answer_text": a_text,
            "question_id": q_id,
            "votes": answer_votes[a_id]
        })
    
    return create_success_response(
        "All answers retrieved successfully",
        {
            "count": len(result),
            "answers": result
        }
    )


if __name__ == '__main__':
    print("Starting Quora-like API server...")
    print("Available endpoints:")
    print("  POST /create_question")
    print("  POST /create_answer")
    print("  POST /upvote_question")
    print("  POST /downvote_question")
    print("  POST /upvote_answer")
    print("  POST /downvote_answer")
    print("  GET /get_top_questions?n=<number>")
    print("  GET /get_top_answers?n=<number>")
    print("  GET /questions (debug)")
    print("  GET /answers (debug)")
    print("  GET /health")
    print("\nServer will run on http://localhost:5000")
    
    app.run(debug=True, host='0.0.0.0', port=5000)