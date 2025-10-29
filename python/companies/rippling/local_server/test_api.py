"""
Test script for the Quora-like API server.

This script demonstrates all the API endpoints and their functionality.
Run this after starting the Flask server with: python local_server.py
"""

import requests
import json
import time

# Base URL for the API server
BASE_URL = "http://localhost:5000"

def test_api_endpoint(method, endpoint, data=None, params=None):
    """Helper function to test API endpoints and print results"""
    url = f"{BASE_URL}{endpoint}"
    
    try:
        if method == "GET":
            response = requests.get(url, params=params)
        elif method == "POST":
            response = requests.post(url, json=data)
        else:
            print(f"Unsupported method: {method}")
            return None
            
        print(f"\n{method} {endpoint}")
        if data:
            print(f"Request data: {json.dumps(data, indent=2)}")
        if params:
            print(f"Request params: {params}")
        print(f"Status Code: {response.status_code}")
        
        try:
            response_data = response.json()
            print(f"Response: {json.dumps(response_data, indent=2)}")
        except:
            print(f"Response: {response.text}")
            
        return response
        
    except requests.exceptions.ConnectionError:
        print(f"❌ Connection Error: Make sure the Flask server is running on {BASE_URL}")
        return None
    except Exception as e:
        print(f"❌ Error: {e}")
        return None

def main():
    """Main test function"""
    print("🧪 Testing Quora-like API Server")
    print("=" * 50)
    
    # Test 1: Health Check
    print("\n1️⃣ Testing Health Check")
    test_api_endpoint("GET", "/health")
    
    # Test 2: Create Questions
    print("\n2️⃣ Creating Questions")
    questions_data = [
        {"question_id": 1, "question_text": "What is Python?"},
        {"question_id": 2, "question_text": "How does machine learning work?"},
        {"question_id": 3, "question_text": "What are the benefits of microservices?"},
        {"question_id": 4, "question_text": "How to optimize database queries?"},
        {"question_id": 5, "question_text": "What is the difference between REST and GraphQL?"}
    ]
    
    for q_data in questions_data:
        test_api_endpoint("POST", "/create_question", q_data)
    
    # Test 3: Create Answers
    print("\n3️⃣ Creating Answers")
    answers_data = [
        {"answer_id": 1, "answer_text": "Python is a high-level programming language known for its simplicity and readability.", "question_id": 1},
        {"answer_id": 2, "answer_text": "Machine learning uses algorithms to learn patterns from data and make predictions.", "question_id": 2},
        {"answer_id": 3, "answer_text": "Microservices offer better scalability, maintainability, and technology diversity.", "question_id": 3},
        {"answer_id": 4, "answer_text": "Database optimization involves indexing, query rewriting, and proper schema design.", "question_id": 4},
        {"answer_id": 5, "answer_text": "REST uses HTTP methods, while GraphQL provides a single endpoint with flexible queries.", "question_id": 5},
        {"answer_id": 6, "answer_text": "Python is also great for data science and web development.", "question_id": 1},
        {"answer_id": 7, "answer_text": "ML includes supervised, unsupervised, and reinforcement learning approaches.", "question_id": 2}
    ]
    
    for a_data in answers_data:
        test_api_endpoint("POST", "/create_answer", a_data)
    
    # Test 4: Voting on Questions
    print("\n4️⃣ Voting on Questions")
    
    # Upvote some questions multiple times
    for question_id in [1, 2, 3]:
        for _ in range(3):  # Upvote 3 times
            test_api_endpoint("POST", "/upvote_question", {"question_id": question_id})
    
    for question_id in [4, 5]:
        for _ in range(2):  # Upvote 2 times
            test_api_endpoint("POST", "/upvote_question", {"question_id": question_id})
    
    # Downvote question 4 once
    test_api_endpoint("POST", "/downvote_question", {"question_id": 4})
    
    # Test 5: Voting on Answers
    print("\n5️⃣ Voting on Answers")
    
    # Upvote answers
    for answer_id in [1, 2, 3]:
        for _ in range(4):  # Upvote 4 times
            test_api_endpoint("POST", "/upvote_answer", {"answer_id": answer_id})
    
    for answer_id in [4, 5]:
        for _ in range(2):  # Upvote 2 times
            test_api_endpoint("POST", "/upvote_answer", {"answer_id": answer_id})
    
    # Downvote answer 4 once
    test_api_endpoint("POST", "/downvote_answer", {"answer_id": 4})
    
    # Test 6: Get Top Questions
    print("\n6️⃣ Getting Top Questions")
    test_api_endpoint("GET", "/get_top_questions", params={"n": 3})
    test_api_endpoint("GET", "/get_top_questions", params={"n": 5})
    
    # Test 7: Get Top Answers
    print("\n7️⃣ Getting Top Answers")
    test_api_endpoint("GET", "/get_top_answers", params={"n": 3})
    test_api_endpoint("GET", "/get_top_answers", params={"n": 5})
    
    # Test 8: Error Handling
    print("\n8️⃣ Testing Error Handling")
    
    # Try to create duplicate question
    test_api_endpoint("POST", "/create_question", {"question_id": 1, "question_text": "Duplicate question"})
    
    # Try to create answer for non-existent question
    test_api_endpoint("POST", "/create_answer", {"answer_id": 99, "answer_text": "Answer text", "question_id": 999})
    
    # Try to vote on non-existent question
    test_api_endpoint("POST", "/upvote_question", {"question_id": 999})
    
    # Try invalid parameters
    test_api_endpoint("GET", "/get_top_questions", params={"n": 0})
    test_api_endpoint("GET", "/get_top_questions")  # Missing n parameter
    
    # Test 9: View All Data (Debug endpoints)
    print("\n9️⃣ Viewing All Data")
    test_api_endpoint("GET", "/questions")
    test_api_endpoint("GET", "/answers")
    
    print("\n✅ All tests completed!")
    print("\n💡 Tips:")
    print("- Questions with most votes: 1, 2, 3 (each has 3 votes)")
    print("- Question 4 has 1 vote (2 upvotes - 1 downvote)")
    print("- Question 5 has 2 votes")
    print("- Answers with most votes: 1, 2, 3 (each has 4 votes)")
    print("- Answer 4 has 1 vote (2 upvotes - 1 downvote)")
    print("- Answer 5 has 2 votes")

def interactive_test():
    """Interactive test mode for manual testing"""
    print("\n🎮 Interactive Test Mode")
    print("Available commands:")
    print("1. create_question <id> <text>")
    print("2. create_answer <answer_id> <text> <question_id>")
    print("3. upvote_question <id>")
    print("4. downvote_question <id>")
    print("5. upvote_answer <id>")
    print("6. downvote_answer <id>")
    print("7. top_questions <n>")
    print("8. top_answers <n>")
    print("9. quit")
    
    while True:
        try:
            command = input("\nEnter command: ").strip().split()
            if not command:
                continue
                
            cmd = command[0]
            
            if cmd == "quit":
                break
            elif cmd == "create_question" and len(command) >= 3:
                question_id = int(command[1])
                question_text = " ".join(command[2:])
                test_api_endpoint("POST", "/create_question", {"question_id": question_id, "question_text": question_text})
            elif cmd == "create_answer" and len(command) >= 4:
                answer_id = int(command[1])
                answer_text = command[2]
                question_id = int(command[3])
                test_api_endpoint("POST", "/create_answer", {"answer_id": answer_id, "answer_text": answer_text, "question_id": question_id})
            elif cmd == "upvote_question" and len(command) >= 2:
                question_id = int(command[1])
                test_api_endpoint("POST", "/upvote_question", {"question_id": question_id})
            elif cmd == "downvote_question" and len(command) >= 2:
                question_id = int(command[1])
                test_api_endpoint("POST", "/downvote_question", {"question_id": question_id})
            elif cmd == "upvote_answer" and len(command) >= 2:
                answer_id = int(command[1])
                test_api_endpoint("POST", "/upvote_answer", {"answer_id": answer_id})
            elif cmd == "downvote_answer" and len(command) >= 2:
                answer_id = int(command[1])
                test_api_endpoint("POST", "/downvote_answer", {"answer_id": answer_id})
            elif cmd == "top_questions" and len(command) >= 2:
                n = int(command[1])
                test_api_endpoint("GET", "/get_top_questions", params={"n": n})
            elif cmd == "top_answers" and len(command) >= 2:
                n = int(command[1])
                test_api_endpoint("GET", "/get_top_answers", params={"n": n})
            else:
                print("❌ Invalid command. Please check the format.")
                
        except ValueError:
            print("❌ Invalid number format.")
        except KeyboardInterrupt:
            print("\n👋 Goodbye!")
            break
        except Exception as e:
            print(f"❌ Error: {e}")

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) > 1 and sys.argv[1] == "interactive":
        interactive_test()
    else:
        main()
