# Mini Docto+ 🏥

A mini application for connecting patients with healthcare professionals, featuring appointment booking and availability management.

## 🏗️ Architecture

This project consists of three main components:
- **Mobile App (Flutter)**: Patient interface for browsing professionals and managing appointments
- **Web App (React)**: Professional interface for managing availability and appointments
- **Backend (Spring Boot)**: REST API with JWT authentication and MongoDB database

## 📱 Features

### For Patients (Mobile App)
- ✅ User authentication (register/login)
- ✅ Browse available professionals (sorted by score)
- ✅ Book appointment slots
- ✅ View personal appointments
- ✅ Modify/cancel own appointments

### For Professionals (Web App)
- ✅ Professional authentication (register/login)
- ✅ Add/remove availability slots
- ✅ View booked appointments from patients
- ✅ Professional ranking system (0-100 score)

### Backend Features
- ✅ JWT-based authentication
- ✅ Role-based access control (PATIENT/PRO)
- ✅ RESTful API endpoints
- ✅ MongoDB integration
- ✅ Professional scoring and ranking
- ✅ API Documentation with Swagger: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| Mobile | Flutter with Provider state management |
| Web | React with Zustand state management |
| Backend | Spring Boot + MongoDB |
| Database | MongoDB Atlas |
| Authentication | JWT tokens |

## 📋 Prerequisites

Before running this project, make sure you have:

- **Flutter SDK** (>= 3.0.0)
- **Node.js** (>= 18.0.0)
- **Java** (>= 17)
- **Maven** (>= 3.8.0)
- **MongoDB Atlas account** (or local MongoDB instance)

## ⚙️ Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/SOUHAILBENBRIK/doctoplus.git
cd doctoplus
```

### 2. Backend Setup (Spring Boot)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

#### Environment Configuration

Create a `.env` file in the backend root directory:

```env
SERVER_PORT=8080
SPRING_DATA_MONGODB_URI=mongodb+srv://souhail:MyStrongPass123@cluster0.mongodb.net/mini-docto?retryWrites=true&w=majority
APP_JWT_EXPIRATION_MS=86400000
APP_JWT_SECRET=your-super-secret-jwt-key-here-make-it-long-and-secure
```

> ⚠️ **Important**: Replace the MongoDB URI with your actual MongoDB Atlas connection string

#### Run the Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend will be available at `http://localhost:8080`  
Swagger UI is available at [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

### 3. Mobile App Setup (Flutter)

```bash
cd mobile
flutter pub get
flutter run
```

#### Key Dependencies
- `provider: ^6.1.5+1` - State management
- `shared_preferences: ^2.5.3` - Local storage
- `dio: ^5.9.0` - HTTP client

### 4. Web App Setup (React)

```bash
cd web
npm install
npm run dev
```

The web app will be available at `http://localhost:5173`

#### Key Dependencies
- React Router for navigation
- Zustand for state management
- Protected routes for role-based access

## 🔐 Authentication & Security

### Security Measures Implemented

1. **JWT Authentication**
   - Secure token-based authentication
   - Configurable token expiration
   - Role-based access control (USER/PRO)

2. **Password Security**
   - Passwords are hashed using BCrypt
   - Strong password validation

3. **API Security**
   - CORS configuration for cross-origin requests
   - Request validation and sanitization
   - Protected endpoints with role verification

4. **Frontend Security**
   - Token storage in secure local storage
   - Automatic token cleanup on logout
   - Protected routes with role validation

### Environment Variables

Keep sensitive information in environment variables:
- Database connection strings
- JWT secrets
- API endpoints

## ⚡ Performance Optimizations

### Backend Performance
- **Database Indexing**: MongoDB indexes on frequently queried fields
- **Connection Pooling**: Efficient database connection management
- **Caching Strategy**: Professional scores cached for faster ranking

### Frontend Performance
- **State Management**: Efficient state updates with Provider/Zustand
- **Lazy Loading**: Components loaded on demand
- **API Optimization**: Minimal API calls with smart caching

### Mobile Performance
- **Provider Pattern**: Optimal state management for Flutter
- **HTTP Caching**: Dio interceptors for response caching
- **Memory Management**: Proper disposal of resources

## 🐛 Troubleshooting

### Common Issues

1. **MongoDB Connection Error**
   - Verify your MongoDB Atlas connection string
   - Check network access settings in MongoDB Atlas
   - Ensure your IP is whitelisted

2. **CORS Issues**
   - Verify CORS configuration in Spring Boot
   - Check API endpoints are correctly configured

---

**Mini Docto+** - Connecting patients with healthcare professionals efficiently and securely. 🏥✨
