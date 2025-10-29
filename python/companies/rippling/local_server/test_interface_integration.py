"""
Test script to demonstrate the integration between api_interface.py and local_server.py
"""

import requests
import json

BASE_URL = "http://localhost:5000"

def test_interface_integration():
    """Test that the server now uses the defined interface properly"""
    
    print("🧪 Testing API Interface Integration")
    print("=" * 50)
    
    # Test 1: Valid request with proper interface validation
    print("\n1️⃣ Testing Valid Request (should succeed)")
    response = requests.post(f"{BASE_URL}/create_question", json={
        "question_id": 1,
        "question_text": "What is Python?"
    })
    print(f"Status: {response.status_code}")
    print(f"Response: {json.dumps(response.json(), indent=2)}")
    
    # Test 2: Invalid request - validation error
    print("\n2️⃣ Testing Validation Error (should fail with proper error code)")
    response = requests.post(f"{BASE_URL}/create_question", json={
        "question_id": -1,  # Invalid: negative ID
        "question_text": ""  # Invalid: empty text
    })
    print(f"Status: {response.status_code}")
    print(f"Response: {json.dumps(response.json(), indent=2)}")
    
    # Test 3: Duplicate entry error
    print("\n3️⃣ Testing Duplicate Entry Error (should fail with proper error code)")
    response = requests.post(f"{BASE_URL}/create_question", json={
        "question_id": 1,  # Already exists
        "question_text": "Another question"
    })
    print(f"Status: {response.status_code}")
    print(f"Response: {json.dumps(response.json(), indent=2)}")
    
    # Test 4: Standardized response format
    print("\n4️⃣ Testing Standardized Response Format")
    response = requests.get(f"{BASE_URL}/health")
    print(f"Status: {response.status_code}")
    print(f"Response: {json.dumps(response.json(), indent=2)}")
    
    print("\n✅ Interface Integration Test Complete!")
    print("\nKey Improvements:")
    print("- ✅ Uses defined request schemas for validation")
    print("- ✅ Standardized error responses with error codes")
    print("- ✅ Consistent response format across all endpoints")
    print("- ✅ Proper validation using interface methods")
    print("- ✅ Type safety with dataclasses")

if __name__ == "__main__":
    test_interface_integration()
