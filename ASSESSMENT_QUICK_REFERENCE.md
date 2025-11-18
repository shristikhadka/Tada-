# TurboVets Assessment - Quick Reference

## 🎯 Required Stack

### Backend:
- **Framework:** NestJS (TypeScript/Node.js)
- **ORM:** TypeORM
- **Database:** SQLite or PostgreSQL
- **Authentication:** JWT (Real JWT, not Basic Auth)

### Frontend:
- **Framework:** Angular (TypeScript)
- **Styling:** TailwindCSS
- **State Management:** Your choice (explain in README)

### Architecture:
- **Structure:** NX Monorepo (single repository, multiple apps)
- **Type:** Integrated monorepo

---

## 📋 Required Features

### 1. **Authentication & Authorization**
- ✅ JWT authentication (real JWT, not Basic Auth)
- ✅ User login
- ✅ User registration
- ✅ Session persistence
- ✅ Protected routes

### 2. **User Roles (RBAC)**
- ✅ Define user roles (e.g., Admin, User, Viewer)
- ✅ Service-layer RBAC enforcement
- ✅ Role-based access control
- ✅ Guards for role checking

### 3. **Organization Hierarchy**
- ✅ Organization entity
- ✅ User-Organization relationship
- ✅ Organization-level data scoping
- ✅ Data visibility based on organization

### 4. **Task Management**
- ✅ Create tasks
- ✅ Read/view tasks
- ✅ Update tasks
- ✅ Delete tasks
- ✅ Tasks scoped to organization

### 5. **Add User Flow**
- ✅ Add users to organization
- ✅ Assign roles to users
- ✅ User management

### 6. **Logging/Tracking**
- ✅ Log important system actions
- ✅ Track user actions
- ✅ Audit trail

### 7. **Frontend UI**
- ✅ Authentication UI (login/register)
- ✅ Task management UI
- ✅ Add User UI
- ✅ Responsive design
- ✅ Functional UI (polish is bonus)

---

## 🏗️ Architecture Requirements

### NX Monorepo Structure:
```
turbovets-assessment/
├── apps/
│   ├── api/          # NestJS backend
│   └── web/          # Angular frontend
├── libs/             # Shared libraries (optional)
├── nx.json           # NX configuration
├── package.json      # Root package.json
└── README.md         # Documentation
```

### Backend Structure (NestJS):
```
apps/api/
├── src/
│   ├── app/
│   │   ├── auth/         # Authentication module
│   │   ├── users/        # User module
│   │   ├── organizations/ # Organization module
│   │   ├── tasks/        # Task module
│   │   ├── guards/       # RBAC guards
│   │   ├── entities/     # TypeORM entities
│   │   └── common/       # Shared utilities
│   └── main.ts           # Entry point
```

### Frontend Structure (Angular):
```
apps/web/
├── src/
│   ├── app/
│   │   ├── auth/         # Authentication components
│   │   ├── tasks/        # Task components
│   │   ├── users/        # User components
│   │   ├── guards/       # Route guards
│   │   ├── services/     # API services
│   │   └── models/       # Data models
```

---

## 🔐 Security Requirements

### 1. **JWT Authentication**
- ✅ Real JWT tokens (not Basic Auth)
- ✅ Token generation on login
- ✅ Token validation on protected routes
- ✅ Token storage (localStorage or httpOnly cookies)

### 2. **Service-Layer RBAC**
- ✅ RBAC enforced in services (not just controllers)
- ✅ Service-level permission checks
- ✅ Guards for route protection
- ✅ Role-based data access

### 3. **Organization Scoping**
- ✅ Data visibility based on organization
- ✅ Users can only see their organization's data
- ✅ Tasks scoped to organization
- ✅ Users scoped to organization

### 4. **Access Control**
- ✅ Role restrictions (e.g., Viewer can't modify)
- ✅ Organization boundaries
- ✅ User permissions
- ✅ Secure data access

---

## 📊 Feature Breakdown

### Backend Features:

#### Authentication:
- [ ] JWT authentication
- [ ] Login endpoint
- [ ] Register endpoint
- [ ] Token validation
- [ ] Password encryption (bcrypt)

#### User Management:
- [ ] User entity
- [ ] User roles (Admin, User, Viewer)
- [ ] Add user to organization
- [ ] User CRUD operations
- [ ] User-organization relationship

#### Organization Management:
- [ ] Organization entity
- [ ] Organization creation
- [ ] Organization-user relationship
- [ ] Organization scoping

#### Task Management:
- [ ] Task entity
- [ ] Create task
- [ ] Read tasks (scoped to organization)
- [ ] Update task
- [ ] Delete task
- [ ] Task-organization relationship
- [ ] Task-user relationship

#### RBAC:
- [ ] Role definitions
- [ ] Service-layer RBAC
- [ ] Guards for routes
- [ ] Permission checks
- [ ] Role-based data access

#### Logging:
- [ ] Action logging
- [ ] User action tracking
- [ ] System event logging
- [ ] Audit trail

### Frontend Features:

#### Authentication:
- [ ] Login UI
- [ ] Register UI
- [ ] Token storage
- [ ] Session persistence
- [ ] Auth guards
- [ ] Logout functionality

#### Task Management:
- [ ] Task list UI
- [ ] Create task UI
- [ ] Update task UI
- [ ] Delete task UI
- [ ] Task filtering
- [ ] Task organization scoping

#### User Management:
- [ ] Add user UI
- [ ] User list UI
- [ ] Role assignment UI
- [ ] User-organization management

#### UI/UX:
- [ ] Responsive design
- [ ] TailwindCSS styling
- [ ] Functional UI
- [ ] Error handling
- [ ] Loading states

---

## 🎯 Key Requirements Summary

### Must Have:
1. ✅ **JWT Authentication** - Real JWT, not Basic Auth
2. ✅ **Service-Layer RBAC** - Enforced in services, not just controllers
3. ✅ **Organization Scoping** - Data visibility based on organization
4. ✅ **Task CRUD** - Create, Read, Update, Delete tasks
5. ✅ **Add User Flow** - Add users to organization
6. ✅ **Logging** - Track important actions
7. ✅ **Clean Code** - Maintainable NestJS + Angular structure
8. ✅ **Documentation** - README with setup and architecture

### Nice to Have (Bonus):
- ⚠️ UI polish (not required)
- ⚠️ Advanced features (not required)
- ⚠️ Extensive testing (not required)
- ⚠️ Perfect styling (not required)

---

## 📁 Repository Structure Question

### **Can you setup the repo inside the same workspace?**

**Answer: YES, but create a NEW separate project!**

### Option 1: Create in Same Workspace (Recommended)
```
/Users/shristikhadka/
├── Tada-/                    # Your current Spring Boot app
├── tada! frontend/           # Your current React app
└── turbovets-assessment/     # NEW NX monorepo for assessment
    ├── apps/
    │   ├── api/              # NestJS backend
    │   └── web/              # Angular frontend
    └── ...
```

**Pros:**
- ✅ Keep everything in one place
- ✅ Easy to find
- ✅ Separate from current projects
- ✅ Can reference your current app for concepts

**Cons:**
- ⚠️ Might be confusing (two different projects)

### Option 2: Create in Different Location (Also Good)
```
/Users/shristikhadka/
├── Projects/
│   ├── Tada-/                # Your current app
│   └── turbovets-assessment/ # NEW assessment project
```

**Pros:**
- ✅ Completely separate
- ✅ Clean organization
- ✅ No confusion

**Cons:**
- ⚠️ Need to navigate between folders

### **Recommendation: Create NEW project in same workspace**

**Why?**
- ✅ Easy to access
- ✅ Can reference your current app
- ✅ Separate git repository
- ✅ Clean structure

---

## 🚀 Setup Instructions

### Step 1: Create New Directory
```bash
cd /Users/shristikhadka
mkdir turbovets-assessment
cd turbovets-assessment
```

### Step 2: Create NX Monorepo
```bash
npx create-nx-workspace@latest .

# Choose:
# - Integrated monorepo
# - NestJS + Angular
# - TypeScript
```

### Step 3: Verify Structure
```bash
ls -la
# Should see: apps/, libs/, nx.json, package.json
```

---

## 📝 Submission Requirements

### 1. **GitHub Repository or ZIP**
- ✅ Complete codebase
- ✅ All source files
- ✅ Configuration files
- ✅ No sensitive information

### 2. **README.md**
- ✅ Setup instructions
- ✅ Architecture explanation
- ✅ Access control explanation
- ✅ Example requests
- ✅ Design decisions
- ✅ What to improve with more time

### 3. **Video Walkthrough (5-10 minutes)**
- ✅ Architecture explanation
- ✅ Authentication and RBAC
- ✅ Service-layer checks
- ✅ Role restrictions
- ✅ Design choices
- ✅ Improvements

---

## ✅ Checklist

### Backend:
- [ ] NestJS setup
- [ ] TypeORM configured
- [ ] SQLite/Postgres database
- [ ] JWT authentication
- [ ] User roles defined
- [ ] Organization entity
- [ ] Organization scoping
- [ ] Service-layer RBAC
- [ ] Task CRUD
- [ ] Add User flow
- [ ] Logging

### Frontend:
- [ ] Angular setup
- [ ] TailwindCSS configured
- [ ] Authentication UI
- [ ] Task management UI
- [ ] Add User UI
- [ ] Session persistence
- [ ] Responsive design

### Documentation:
- [ ] README with setup
- [ ] Architecture explanation
- [ ] Access control explanation
- [ ] Example requests
- [ ] Design decisions

### Submission:
- [ ] GitHub repository or ZIP
- [ ] README complete
- [ ] Video walkthrough
- [ ] No sensitive information
- [ ] All requirements met

---

## 🎯 Quick Answer to Your Questions

### 1. **Can I setup the repo inside the same workspace?**
**YES!** Create a NEW project in the same workspace:
```bash
cd /Users/shristikhadka
mkdir turbovets-assessment
cd turbovets-assessment
npx create-nx-workspace@latest .
```

### 2. **What features do they want?**
- JWT authentication
- User roles (RBAC)
- Organization hierarchy
- Task CRUD
- Add User flow
- Logging/tracking
- Responsive UI

### 3. **What stack do they want?**
- **Backend:** NestJS + TypeORM + SQLite/Postgres
- **Frontend:** Angular + TailwindCSS
- **Architecture:** NX Monorepo

---

## 🚀 Next Steps

1. **Create new project:**
   ```bash
   cd /Users/shristikhadka
   mkdir turbovets-assessment
   cd turbovets-assessment
   npx create-nx-workspace@latest .
   ```

2. **Set up NestJS backend:**
   ```bash
   npx nx generate @nx/nest:application api
   ```

3. **Set up Angular frontend:**
   ```bash
   npx nx generate @nx/angular:application web
   ```

4. **Start building:**
   - Authentication (JWT)
   - Organization scoping
   - RBAC
   - Task CRUD
   - Add User flow

---

**You've got this!** 🚀

