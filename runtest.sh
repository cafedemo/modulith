#!/bin/bash

# Set the URL based on the environment
if [ "$ENV" = "local" ]; then
  url="http://localhost:8080/api"
elif [ "$ENV" = "dev" ]; then
  url="http://dev.test"
elif [ "$ENV" = "prod" ]; then
  url="http://prod.test"
else
  echo "Please provide an environment"
  exit 1
fi

# Function to run a specific test based on the test number
run_test() {
  case $1 in
    1)
      echo "curl -v -X GET "$url/shop/" -H "Accept: application/json" | jq ."
      curl -v -X GET "$url/shop/" -H "Accept: application/json" | jq .
      ;;
    2)
      echo "curl -v -X GET "$url/shop/4" -H "Accept: application/json" | jq ."
      curl -v -X GET "$url/shop/4" -H "Accept: application/json" | jq .
      ;;
    3)
      echo "curl -s -X POST "$url/shop" -H "Content-Type: application/json" -d '{
        "name": "John Does1",
        "address": "1241 Main St",
        "phone": "566-555-5555",
        "email": "testjohn4@test.com"
      }' | jq ."
      curl -s -X POST "$url/shop" -H "Content-Type: application/json" -d '{
        "name": "John Does1",
        "address": "1241 Main St",
        "phone": "566-555-5555",
        "email": "testjohn4@test.com"
      }' | jq .
      ;;
    4)
      echo "curl -v -X PUT "$url/shop" -H \"Content-Type: application/json\" -H \"Accept: application/json\" -d '{
        \"id\": 9,
        \"name\": \"John Does2\",
        \"address\": \"1241 Main St\",
        \"phone\": \"566-555-5555\",
        \"email\": \"testjohn4@test.com\"
      }'"
      curl -v -X PUT "$url/shop" -H "Content-Type: application/json" -H "Accept: application/json" -d '{
        "id": 9,
        "name": "John Does2",
        "address": "1241 Main St",
        "phone": "566-555-5555",
        "email": "testjohn5@test.com"
      }' | jq .
      ;;
    5)
      curl -v -X DELETE "$url/shop/9" | jq .
      ;;
    6)
      curl -v -X GET "$url/barista/" -H "Accept: application/json" | jq .
      ;;
    7)
      curl -v -X GET "$url/barista/shop/4" -H "Accept: application/json" | jq .
      ;;
    8)
      curl -v -X POST "$url/barista" -H "Content-Type: application/json" -H "Accept: application/json" -d '{
        "name": "John Doe",
        "email": "testjohn@test.com",
        "shop_id": 4
      }' | jq .
      ;;
    9)
      curl -v -X PUT "$url/barista" -H "Content-Type: application/json" -H "Accept: application/json" -d '{
        "id": 4,
        "name": "John Does",
        "email": "baristajohn@test.com",
        "shop_id": 3
      }' | jq .
      ;;
    10)
      curl -v -X DELETE "$url/barista/9" | jq .
      ;;
    *)
      echo "Invalid test number"
      ;;
  esac
}

# Check if a test number is provided
if [ -z "$1" ]; then
  echo "Please provide a test number"
  exit 1
fi

# Run the test
run_test $1
