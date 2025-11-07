# Docker Setup Guide for Todo Application

This guide explains how to use Docker with your Todo application from scratch.

## What Files Were Created?

1. **Dockerfile** - Instructions to build your Spring Boot app into a container
2. **docker-compose.yml** - Orchestrates multiple containers (app + database + frontend)
3. **.dockerignore** - Tells Docker which files to ignore when building
4. **application-docker.properties** - Spring Boot config for Docker environment

---

## How to Use Docker

### Option 1: Keep Using H2 (Current Setup)

If you want to keep using H2 database for now:

```bash
# Build and start everything
docker-compose up

# Stop everything
docker-compose down
```

Your app will run on: http://localhost:8080

### Option 2: Switch to PostgreSQL (When Ready)

When you're ready to use Postgres:

1. **Add Postgres dependency to pom.xml:**

Add this inside the `<dependencies>` section:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

2. **Update docker-compose.yml:**

In the `app` service, change the environment section:
```yaml
environment:
  SPRING_PROFILES_ACTIVE: docker  # Change from 'dev' to 'docker'
```

3. **Start everything:**
```bash
docker-compose up
```

Now your app uses Postgres!

---

## Common Docker Commands

### Starting Your App

```bash
# Start in foreground (see logs)
docker-compose up

# Start in background (detached)
docker-compose up -d

# Rebuild images and start
docker-compose up --build
```

### Stopping Your App

```bash
# Stop containers (keeps data)
docker-compose down

# Stop and delete all data
docker-compose down -v
```

### Viewing Logs

```bash
# See all logs
docker-compose logs

# Follow logs (like tail -f)
docker-compose logs -f

# Logs for specific service
docker-compose logs app
docker-compose logs postgres
```

### Rebuilding After Code Changes

**IMPORTANT:** Docker doesn't auto-update when you change code!

```bash
# Stop containers
docker-compose down

# Rebuild and restart
docker-compose up --build
```

---

## Understanding Each Container

### Container 1: postgres
- **What:** PostgreSQL database
- **Port:** 5432
- **Credentials:**
  - Database: `tododb`
  - Username: `todouser`
  - Password: `todopass`
- **Data:** Stored in Docker volume (persists after restart)

### Container 2: app
- **What:** Your Spring Boot application
- **Port:** 8080
- **Built from:** Your source code using Dockerfile
- **Connects to:** postgres container

### Container 3: frontend (future)
- **What:** Your React frontend
- **Port:** 3000
- **Status:** Commented out, uncomment when ready

---

## Workflow: Development with Docker

### Daily Development (Recommended)

**Keep developing normally without Docker:**
```bash
mvn spring-boot:run
```

This is faster for quick testing!

### Testing with Docker (Occasionally)

**Test if everything works in production-like environment:**
```bash
# Build and run with Docker
docker-compose up --build

# Test your app
# Make changes to code
# Rebuild
docker-compose down
docker-compose up --build
```

---

## Troubleshooting

### Problem: Port already in use
```
Error: bind: address already in use
```

**Solution:** Stop your locally running app first
```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9
```

### Problem: Can't connect to database
```
Error: Connection refused
```

**Solution:** Make sure postgres is healthy
```bash
docker-compose ps
# Should show postgres as "healthy"
```

### Problem: Changes not showing up
```
I changed my code but nothing happened!
```

**Solution:** Rebuild the image
```bash
docker-compose down
docker-compose up --build
```

### Problem: Database is empty after restart
```
My data disappeared!
```

**Solution:** Check if you used `-v` flag
```bash
# This DELETES data
docker-compose down -v

# This KEEPS data
docker-compose down
```

---

## When to Use Docker vs Normal Development

| Scenario | Use This |
|----------|----------|
| Daily coding and testing | `mvn spring-boot:run` (normal) |
| Testing database integration | `docker-compose up` |
| Before deploying to production | `docker-compose up` |
| Sharing app with teammates | Docker |
| Running in production | Docker |

---

## Adding Your React Frontend (Future)

When your React app is ready:

1. **Create a Dockerfile in your React repo:**
```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]
```

2. **Update docker-compose.yml:**
Uncomment the `frontend` section and update the path:
```yaml
frontend:
  build:
    context: ../your-react-folder-name  # Update this path!
```

3. **Start everything:**
```bash
docker-compose up
```

Now you have:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- Database: postgres://localhost:5432

---

## Quick Reference

```bash
# Start everything
docker-compose up

# Start in background
docker-compose up -d

# Stop everything
docker-compose down

# Rebuild and start
docker-compose up --build

# View logs
docker-compose logs -f

# List running containers
docker ps

# Access database directly
docker exec -it todo-postgres psql -U todouser -d tododb
```

---

## Summary

- **Dockerfile** = Recipe for your Spring Boot app
- **docker-compose.yml** = Manages all containers together
- **You can still develop normally** - Docker is optional
- **Rebuild after code changes** - Docker doesn't auto-update
- **Start simple** - Use H2 first, then switch to Postgres when ready