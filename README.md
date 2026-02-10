# Lost & Found Backend API

AI-powered lost and found platform backend built with Spring Boot.

## Features

- ✅ User Management
- ✅ Lost Items CRUD
- ✅ Found Items CRUD
- ✅ AI-Powered Search (regional filtering + semantic matching)
- ✅ Location-based queries
- ✅ Auto-generated database schema

## Tech Stack

- Java 17
- Spring Boot 3.2.1
- PostgreSQL / H2
- Hibernate JPA
- Auth0 (ready to integrate)
- OpenAI API (ready to integrate)
- Stripe (ready to integrate)

## API Endpoints

### Test
- `GET /api/users/test` - Health check

### Users
- `GET /api/users` - Get all users
- `POST /api/users` - Create user
- `GET /api/users/{id}` - Get user by ID

### Lost Items
- `GET /api/lost-items` - Get all lost items
- `POST /api/lost-items` - Create lost item
- `GET /api/lost-items/{id}` - Get lost item
- `GET /api/lost-items/category/{category}` - Filter by category
- `GET /api/lost-items/city/{city}` - Filter by city

### Found Items
- `GET /api/found-items` - Get all found items
- `POST /api/found-items` - Create found item
- `GET /api/found-items/{id}` - Get found item
- `GET /api/found-items/category/{category}` - Filter by category
- `GET /api/found-items/city/{city}` - Filter by city

### AI Search
- `POST /api/search` - AI-powered search
  ```json
  {
    "description": "Black iPhone 13",
    "category": "electronics",
    "country": "USA",
    "city": "New York",
    "latitude": 40.7128,
    "longitude": -74.0060,
    "radiusKm": 10
  }
  ```

## Local Development

```bash
./mvnw spring-boot:run
```

Access:
- API: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console

## Cloud Deployment

### Railway.app
1. Push to GitHub
2. Connect to Railway
3. Railway auto-detects Spring Boot
4. Add PostgreSQL database
5. Set environment variables
6. Deploy!

### Environment Variables
```
DATABASE_URL=postgresql://...
OPENAI_API_KEY=sk-...
STRIPE_API_KEY=sk_test_...
AUTH0_DOMAIN=xxx.auth0.com
AUTH0_CLIENT_ID=...
AUTH0_CLIENT_SECRET=...
```

## Database Schema

Tables auto-created by Hibernate:
- `users` - User accounts
- `lost_items` - Lost item posts
- `found_items` - Found item posts
- `item_images` - Item photos
- `matches` - AI-generated matches (future)
- `user_wallets` - User balances (future)
- `donations` - Payment transactions (future)

## Status

🚀 Core CRUD functionality: **COMPLETE**
🔍 AI Search: **COMPLETE** (basic text matching, OpenAI integration ready)
🔐 Auth0: **READY** (needs configuration)
💰 Stripe: **READY** (needs configuration)
📦 Redis: **READY** (needs configuration)
