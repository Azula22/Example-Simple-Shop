**This is a dummy version of online shop.**

To start the app run ./run.sh script

This program has few endpoints:

**1. Create User**

_Request:_ 

``POST /user``
```json
{
    "email": "email",
    "bankAccount": "account"
}
```
_Response:_ 

```json
{
     "id": "61e06f06-2a2e-42b6-817f-7f7f31d14c0a",
     "email": "email",
     "bankAccount": "account"
}
```
**2. Get User**

_Request:_ 

``GET /user/:email``

_Response:_
 
```json
{
     "id": "61e06f06-2a2e-42b6-817f-7f7f31d14c0a",
     "email": "email",
     "bankAccount": "account"
}
```

**3. Create product**

_Request:_

``POST /product``
```json
{
    "price": 23.0,
    "description": "Very goo chair",
    "amount": 12
}
```

_Response:_

```json
{
    "id": "a8d823b-4ddf-40b9-8501-5fd37d30e92e",
    "price": 23.0,
    "description": "Very goo chair",
    "amount": 12
}
```

**4. Get products**

_Request:_

``GET /product?limit=10&offset=1``

_Response:_ 

```json
[
  {
      "id": "a8d823b-4ddf-40b9-8501-5fd37d30e92e",
      "price": 23.0,
      "description": "Very goo chair",
      "amount": 12
  },
  {
       "id": "20d06407-5547-4388-9b49-08ee6063211d",
       "price": 3.0,
       "description": "Very goo brush",
       "amount": 2
  }
]
```

**5. Put a product in a cart**

_Request:_

``PUT /product``
```json
{
    "userId": "61e06f06-2a2e-42b6-817f-7f7f31d14c0a",
    "itemId": "a8d823b-4ddf-40b9-8501-5fd37d30e92e",
    "amount": 2 
}
```

_Response is empty_

**6. Get a final cart list**

_Request:_

``GET "/product/:userId``

_Response:_

```json
[
  {
      "id": "a8d823b-4ddf-40b9-8501-5fd37d30e92e",
      "price": 23.0,
      "description": "Very goo chair",
      "amount": 2
  }
]
```

**7. Buy everything from a cart**

_Request:_

``PUT /product/buy``
```json
{
    "userId": "61e06f06-2a2e-42b6-817f-7f7f31d14c0a"
}
```

_Response: purchase id_

```json
{
    "id":  "511970e5-8969-41cc-bd24-86267600e5ef"
}
``` 

Enjoy :eyes: