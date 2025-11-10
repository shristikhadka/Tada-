# Tada! - Todo Application

A full-featured Todo application built with Spring Boot (backend) and React (frontend), featuring user authentication, role-based access control, and an admin dashboard.

## 🚀 Features

### User Features
- ✅ User registration and authentication (Basic Auth)
- ✅ Create, read, update, and delete todos
- ✅ Mark todos as completed/incomplete
- ✅ Filter todos (All/Active/Completed)
- ✅ Search todos by title
- ✅ Todo statistics (completed/active/total)
- ✅ User profile management
- ✅ Change password
- ✅ Update username
- ✅ Delete account

### Admin Features
- ✅ View all users
- ✅ Delete users
- ✅ Role-based access control (USER/ADMIN)

### Technical Features
- ✅ RESTful API
- ✅ Spring Security with Basic Authentication
- ✅ H2 database with Flyway migrations
- ✅ CORS configuration
- ✅ API documentation (Swagger/OpenAPI)
- ✅ Responsive UI design
- ✅ Modern gradient animations

## 🛠️ Tech Stack

### Backend
- **Spring Boot 3.5.6**
- **Spring Security** (Basic Authentication)
- **Spring Data JPA** (Hibernate)
- **H2 Database** (file-based)
- **Flyway** (Database migrations)
- **Lombok**
- **Swagger/OpenAPI** (API documentation)

### Frontend
- **React 18**
- **React Router** (Navigation)
- **Axios** (HTTP client)
- **Vite** (Build tool)
- **CSS3** (Styling with animations)

## 📋 Prerequisites

- **Java 17+**
- **Node.js 16+** and **npm**
- **Maven** (or use Maven Wrapper `mvnw`)

## 🚀 Getting Started

### Environment Variables Setup

#### Backend Environment Variables

**Option 1: Use Defaults (Recommended for Development)**
- The application works out of the box with default values in `application.properties`
- No setup required!

**Option 2: Set Environment Variables**

Spring Boot reads from system environment variables. You can set them in several ways:

**Linux/Mac:**
```bash
export DATABASE_URL="jdbc:h2:file:./data/tada-db;AUTO_SERVER=TRUE;AUTO_RECONNECT=TRUE"
export SERVER_PORT=8080
export FRONTEND_URL="http://localhost:5173"
./mvnw spring-boot:run
```

**Windows (PowerShell):**
```powershell
$env:DATABASE_URL="jdbc:h2:file:./data/tada-db;AUTO_SERVER=TRUE;AUTO_RECONNECT=TRUE"
$env:SERVER_PORT="8080"
$env:FRONTEND_URL="http://localhost:5173"
./mvnw spring-boot:run
```

**Using .env file with dotenv-cli (Optional):**
```bash
# Install dotenv-cli
npm install -g dotenv-cli

# Create .env file from ENV_EXAMPLE.txt
cp ENV_EXAMPLE.txt .env

# Run with dotenv-cli
dotenv -e .env -- ./mvnw spring-boot:run
```

**Note:** The `ENV_EXAMPLE.txt` file is just a reference. Spring Boot doesn't automatically read `.env` files - it reads from system environment variables. The defaults in `application.properties` will be used if environment variables are not set.

#### Frontend Environment Variables

**Option 1: Use Defaults (Recommended for Development)**
- The frontend defaults to `http://localhost:8080` if no environment variable is set
- No setup required!

**Option 2: Create .env file**

1. **Copy the example file:**
```bash
cd "tada! frontend"
cp ENV_EXAMPLE.txt .env
```

2. **Edit `.env` file** (optional):
```bash
# API Base URL (Backend server)
VITE_API_BASE_URL=http://localhost:8080
```

**Note:** 
- Vite automatically reads `.env` files (unlike Spring Boot)
- Environment variables must start with `VITE_` prefix to be exposed to the frontend
- After changing `.env`, restart the dev server

### Backend Setup

1. **Navigate to backend directory:**
```bash
cd Tada-
```

2. **Run the application:**
```bash
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080` (or the port specified in `SERVER_PORT`)

3. **Access H2 Console:**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: Use the value from `DATABASE_URL` (default: `jdbc:h2:file:./data/tada-db`)
- Username: `sa` (or value from `DATABASE_USERNAME`)
- Password: (leave empty or use value from `DATABASE_PASSWORD`)

4. **Access Swagger UI:**
- URL: `http://localhost:8080/swagger-ui.html`

### Frontend Setup

1. **Navigate to frontend directory:**
```bash
cd "tada! frontend"
```

2. **Install dependencies:**
```bash
npm install
```

3. **Run the development server:**
```bash
npm run dev
```

The frontend will start on `http://localhost:5173`

### Create Admin User

To create an admin user for testing:

1. **Register a user** via the frontend or API
2. **Access H2 Console** at `http://localhost:8080/h2-console`
   - JDBC URL: Check your `DATABASE_URL` environment variable or use the default from `application.properties`
3. **Run this SQL** (replace `1` with your user's ID):
```sql
INSERT INTO USER_ROLES (USER_ID, ROLES) VALUES (1, 'ADMIN');
```

Alternatively, you can create an admin user directly via SQL:
```sql
-- Insert user (password will be hashed by Spring Security on first login attempt)
INSERT INTO USERS (USER_NAME, PASSWORD) VALUES ('admin', '$2a$10$...');
-- Add ADMIN role
INSERT INTO USER_ROLES (USER_ID, ROLES) VALUES (1, 'USER');
INSERT INTO USER_ROLES (USER_ID, ROLES) VALUES (1, 'ADMIN');
```

## ⚙️ Configuration

### Environment Variables

The application uses environment variables for configuration. All values have sensible defaults, so the application will work without a `.env` file.

#### Backend Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DATABASE_URL` | H2 database JDBC URL | `jdbc:h2:file:./data/tada-db;AUTO_SERVER=TRUE;AUTO_RECONNECT=TRUE` |
| `DATABASE_USERNAME` | Database username | `sa` |
| `DATABASE_PASSWORD` | Database password | (empty) |
| `SERVER_PORT` | Server port | `8080` |
| `FRONTEND_URL` | Frontend URL for CORS | `http://localhost:5173` |
| `H2_CONSOLE_ENABLED` | Enable H2 console | `true` |
| `JPA_SHOW_SQL` | Show SQL queries in logs | `true` |

#### Frontend Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `VITE_API_BASE_URL` | Backend API URL | `http://localhost:8080` |

### Production Configuration

For production deployment:

1. **Set environment variables:**
   - `DATABASE_URL`: Use PostgreSQL or MySQL connection string
   - `FRONTEND_URL`: Your production frontend URL
   - `H2_CONSOLE_ENABLED`: Set to `false`
   - `JPA_SHOW_SQL`: Set to `false`
   - `VITE_API_BASE_URL`: Your production backend URL

2. **Security:**
   - Never commit `.env` files to version control
   - Use secure passwords for database
   - Enable HTTPS in production
   - Configure proper CORS origins

## 📖 API Documentation

Once the backend is running, access the API documentation at:
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

### Main Endpoints

#### Public Endpoints
- `POST /public/register` - Register a new user
- `GET /public/health-check` - Health check

#### User Endpoints (Requires Authentication)
- `GET /user/me` - Get current user profile
- `PUT /user/me` - Update user profile
- `DELETE /user/me` - Delete user account
- `PUT /user/change-password` - Change password
- `GET /user/todos` - Get all todos
- `POST /user/todos` - Create a todo
- `PUT /user/todos/{id}` - Update a todo
- `DELETE /user/todos/{id}` - Delete a todo

#### Admin Endpoints (Requires ADMIN role)
- `GET /admin/users` - Get all users
- `GET /admin/users/{id}` - Get user by ID
- `POST /admin/users` - Create user with roles
- `PUT /admin/users/{id}/roles` - Update user roles
- `PUT /admin/users/{id}/password` - Reset user password
- `DELETE /admin/users/{id}` - Delete user

## 🗄️ Database

The application uses **H2 file-based database** located at:
```
/Users/shristikhadka/tada-db
```

### Database Schema

- **users** - User accounts
- **user_roles** - User roles (USER, ADMIN)
- **todo** - Todo items

### Flyway Migrations

Database migrations are managed by Flyway:
- Migration files: `src/main/resources/db/migration/`
- Current migration: `V1__Create_users_and_todos_tables.sql`

## 🔐 Authentication

The application uses **HTTP Basic Authentication**:
- Username and password are Base64 encoded
- Credentials are stored in `localStorage` on the frontend
- All authenticated endpoints require valid credentials

## 🎨 Frontend Routes

- `/login` - Login page
- `/register` - Registration page
- `/dashboard` - Todo dashboard (protected)
- `/profile` - User profile (protected)
- `/admin` - Admin dashboard (protected, ADMIN only)

## 🧪 Testing

### Backend Tests
```bash
./mvnw test
```

### Frontend Tests
```bash
cd "tada! frontend"
npm test
```

## 📁 Project Structure

```
Tada-/
├── src/
│   ├── main/
│   │   ├── java/com/shristikhadka/demo/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── entity/          # JPA entities
│   │   │   ├── repo/            # JPA repositories
│   │   │   ├── service/         # Business logic
│   │   │   └── dto/             # Data Transfer Objects
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/    # Flyway migrations
│   └── test/                    # Test files
└── pom.xml

tada! frontend/
├── src/
│   ├── components/              # React components
│   ├── pages/                   # Page components
│   ├── utils/                   # Utility functions
│   ├── App.jsx                  # Main app component
│   └── main.jsx                 # Entry point
└── package.json
```

## 🚢 Deployment

See [README-DOCKER.md](./README-DOCKER.md) for Docker deployment instructions.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the MIT License.

## 👤 Author

**Shristi Khadka**
- GitHub: [@shristikhadka](https://github.com/shristikhadka)

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- React team for the UI library
- All open-source contributors

---

**Note:** This is a learning project. Some features may not be production-ready.

