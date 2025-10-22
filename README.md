# Todo Application

A modern, full-stack todo application built with React frontend and Spring Boot backend, featuring user authentication, task management, and real-time updates.

## 🚀 Features

- **Task Management**: Create, read, update, and delete tasks
- **User Authentication**: Register, login, and logout functionality
- **Task Filtering**: Filter tasks by completion status (All, Pending, Completed)
- **Batch Operations**: Clear completed tasks or all tasks (admin only)
- **Real-time Updates**: Instant UI updates with API synchronization
- **Responsive Design**: Modern, mobile-friendly interface
- **Role-based Access**: Admin privileges for the first registered user

## 🏗️ Architecture

This project follows a **frontend-backend separation** architecture:

```
┌─────────────────┐    HTTP/JSON    ┌─────────────────┐
│   React Frontend│ ◄─────────────► │ Spring Boot API │
│                 │                 │                 │
│ - Task Management│                 │ - REST Endpoints│
│ - User Auth      │                 │ - JWT Auth      │
│ - State Management│                │ - Data Validation│
└─────────────────┘                 └─────────────────┘
                                              │
                                              ▼
                                    ┌─────────────────┐
                                    │   MySQL Database│
                                    │                 │
                                    │ - users table   │
                                    │ - tasks table   │
                                    └─────────────────┘
```

## 🛠️ Tech Stack

### Frontend

- **React 18** - Modern React with hooks
- **TypeScript** - Type-safe JavaScript
- **Vite** - Fast build tool and dev server
- **Axios** - HTTP client for API calls
- **CSS3** - Modern styling with custom properties

### Backend

- **Java 17** - Latest LTS Java version
- **Spring Boot 3.0** - Enterprise-grade framework
- **Spring Data JPA** - Data persistence layer
- **MySQL 8.0** - Relational database
- **Lombok** - Boilerplate code reduction
- **Swagger/OpenAPI** - API documentation
- **JWT** - JSON Web Token authentication

## 📁 Project Structure

```
HA42/
├── frontend/                 # React frontend application
│   ├── src/
│   │   ├── components/       # React components
│   │   │   ├── AuthBar.tsx   # Authentication bar
│   │   │   ├── FilterBar.tsx # Task filtering
│   │   │   ├── TaskForm.tsx  # Task creation form
│   │   │   ├── TaskItem.tsx  # Individual task item
│   │   │   └── TaskList.tsx  # Task list container
│   │   ├── hooks/            # Custom React hooks
│   │   │   ├── useAuth.ts    # Authentication logic
│   │   │   └── useTasks.ts   # Task management logic
│   │   ├── services/         # API services
│   │   │   └── api.ts        # HTTP client configuration
│   │   ├── types/            # TypeScript type definitions
│   │   │   └── task.ts       # Task-related types
│   │   ├── App.tsx           # Main application component
│   │   ├── main.tsx          # Application entry point
│   │   └── styles.css        # Global styles
│   ├── package.json          # Frontend dependencies
│   └── vite.config.ts        # Vite configuration
├── backend/                  # Spring Boot backend application
│   ├── src/main/java/com/example/todoapp/
│   │   ├── config/           # Configuration classes
│   │   │   └── CorsConfig.java
│   │   ├── controller/       # REST controllers
│   │   │   ├── AuthController.java
│   │   │   ├── HealthController.java
│   │   │   └── TaskController.java
│   │   ├── model/            # Data models/entities
│   │   │   ├── Task.java
│   │   │   └── User.java
│   │   ├── repository/       # Data access layer
│   │   │   ├── TaskRepository.java
│   │   │   └── UserRepository.java
│   │   ├── service/          # Business logic layer
│   │   │   ├── TaskService.java
│   │   │   ├── UserService.java
│   │   │   └── impl/
│   │   │       ├── TaskServiceImpl.java
│   │   │       └── UserServiceImpl.java
│   │   └── TodoAppApplication.java
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   ├── schema.sql        # Database schema
│   │   └── user_schema.sql   # User table schema
│   └── pom.xml               # Maven dependencies
└── README.md                 # This file
```

## 🚀 Quick Start

### Prerequisites

- **Node.js** 18+ and npm
- **Java** 17+
- **Maven** 3.6+
- **MySQL** 8.0+

### 1. Database Setup

Create a MySQL database and configure the connection:

```sql
CREATE DATABASE todoapp;
```

Update the database configuration in `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todoapp?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8000`

### 3. Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

The frontend will start on `http://localhost:3000`

### 4. Access the Application

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8000
- **API Documentation**: http://localhost:8000/swagger-ui.html
- **Health Check**: http://localhost:8000/api/v1/health

## 📚 API Documentation

### Authentication Endpoints

| Method | Endpoint                | Description           |
| ------ | ----------------------- | --------------------- |
| POST   | `/api/v1/auth/register` | Register a new user   |
| POST   | `/api/v1/auth/login`    | Login user            |
| GET    | `/api/v1/auth/me`       | Get current user info |

### Task Management Endpoints

| Method | Endpoint                    | Description                    |
| ------ | --------------------------- | ------------------------------ |
| GET    | `/api/v1/todos`             | Get all tasks (with filtering) |
| POST   | `/api/v1/todos`             | Create a new task              |
| PUT    | `/api/v1/todos/{id}`        | Update a task                  |
| DELETE | `/api/v1/todos/{id}`        | Delete a task                  |
| PATCH  | `/api/v1/todos/{id}/toggle` | Toggle task completion         |
| DELETE | `/api/v1/todos/completed`   | Delete all completed tasks     |
| DELETE | `/api/v1/todos/all`         | Delete all tasks (admin only)  |

### Example API Usage

**Create a task:**

```bash
curl -X POST http://localhost:8000/api/v1/todos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"title": "Learn React"}'
```

**Get all tasks:**

```bash
curl -X GET http://localhost:8000/api/v1/todos \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🧪 Testing

### Backend Tests

```bash
cd backend
mvn test
```

### Frontend Tests

```bash
cd frontend
npm run lint
npm run format
```

## 🔧 Development

### Frontend Development

```bash
# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Lint code
npm run lint

# Format code
npm run format
```

### Backend Development

```bash
# Compile and run tests
mvn clean test

# Run application
mvn spring-boot:run

# Build JAR file
mvn clean package

# Run JAR file
java -jar target/todoapp-1.0.0.jar
```

## 🌟 Key Features Explained

### User Authentication

- JWT-based authentication system
- First registered user automatically becomes admin
- Role-based access control for sensitive operations

### Task Management

- Full CRUD operations for tasks
- Real-time status updates
- Batch operations for efficiency

### Modern UI/UX

- Responsive design that works on all devices
- Smooth animations and transitions
- Intuitive user interface
- Loading states and error handling

## 🔒 Security Features

- JWT token-based authentication
- CORS configuration for cross-origin requests
- Input validation and sanitization
- Role-based access control
- Secure password handling

## 📱 Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

If you encounter any issues or have questions:

1. Check the [API Documentation](http://localhost:8000/swagger-ui.html)
2. Review the existing issues in the repository
3. Create a new issue with detailed information about your problem

## 🎯 Roadmap

- [ ] Task categories and tags
- [ ] Due dates and reminders
- [ ] File attachments
- [ ] Collaborative features
- [ ] Mobile app (React Native)
- [ ] Dark mode theme
- [ ] Export/import functionality

---

**Happy coding! 🚀**
