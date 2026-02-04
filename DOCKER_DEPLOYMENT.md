# 🐳 Docker Deployment Guide

## 📋 Overview

This Student Management System is fully dockerized with two main containers:
1. **PostgreSQL Database** - Data persistence layer
2. **Spring Boot Application** - Backend API and web interface

## 🚀 Quick Start

### Prerequisites
- Docker Desktop installed ([Download here](https://www.docker.com/products/docker-desktop))
- Docker Compose (included with Docker Desktop)

### 🎯 One-Command Deployment

```bash
docker-compose up --build
```

That's it! Your application will be available at: **http://localhost:8081**

---

## 📦 What Gets Created

### Containers:
- `student-management-app` - Spring Boot application (Port 8081)
- `student-management-db` - PostgreSQL database (Port 5432)

### Volumes:
- `postgres-data` - Persistent storage for database

### Network:
- `student-management-network` - Bridge network for container communication

---

## 🛠️ Detailed Commands

### 1️⃣ Build and Start (First Time)
```bash
docker-compose up --build -d
```
- `--build` forces rebuild of the Spring Boot image
- `-d` runs in detached mode (background)

### 2️⃣ Start Existing Containers
```bash
docker-compose start
```

### 3️⃣ Stop Containers
```bash
docker-compose stop
```

### 4️⃣ Stop and Remove Containers
```bash
docker-compose down
```

### 5️⃣ Stop and Remove Everything (including volumes)
```bash
docker-compose down -v
```
⚠️ **Warning**: This deletes all database data!

### 6️⃣ View Logs
```bash
# All services
docker-compose logs -f

# Only Spring Boot app
docker-compose logs -f spring-app

# Only PostgreSQL
docker-compose logs -f postgres-db
```

### 7️⃣ Rebuild After Code Changes
```bash
docker-compose down
docker-compose up --build
```

---

## 🔍 Monitoring & Debugging

### Check Container Status
```bash
docker-compose ps
```

### Access Spring Boot Container Shell
```bash
docker exec -it student-management-app sh
```

### Access PostgreSQL Container
```bash
docker exec -it student-management-db psql -U postgres -d student_management_db
```

### View Real-time Logs
```bash
docker-compose logs -f spring-app
```

### Check Health Status
```bash
docker inspect student-management-app | grep Health -A 10
```

---

## 🌐 Access Points

| Service | URL | Description |
|---------|-----|-------------|
| Web Application | http://localhost:8081 | Main application |
| API Documentation | http://localhost:8081/swagger-ui/index.html | Swagger UI |
| Database | localhost:5432 | PostgreSQL (from host) |
| Health Check | http://localhost:8081/actuator/health | Spring Boot health |

---

## 🔧 Configuration

### Environment Variables (docker-compose.yml)

**Database:**
- `POSTGRES_DB` - Database name
- `POSTGRES_USER` - Database username
- `POSTGRES_PASSWORD` - Database password

**Spring Boot:**
- `SPRING_PROFILES_ACTIVE` - Active Spring profile (docker)
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SERVER_PORT` - Application port

### Modify Configuration
Edit `docker-compose.yml` to change:
- Port mappings
- Environment variables
- Memory limits
- Volume configurations

---

## 📊 Database Management

### Backup Database
```bash
docker exec student-management-db pg_dump -U postgres student_management_db > backup.sql
```

### Restore Database
```bash
cat backup.sql | docker exec -i student-management-db psql -U postgres -d student_management_db
```

### Clear All Data (Fresh Start)
```bash
docker-compose down -v
docker-compose up --build
```

---

## 🐛 Troubleshooting

### Issue: "Port already in use"
```bash
# Change port in docker-compose.yml
ports:
  - "8082:8081"  # Changed from 8081:8081
```

### Issue: "Database connection refused"
```bash
# Check database is running
docker-compose ps

# Check database logs
docker-compose logs postgres-db

# Restart containers
docker-compose restart
```

### Issue: "Application won't start"
```bash
# Check application logs
docker-compose logs spring-app

# Rebuild from scratch
docker-compose down
docker-compose build --no-cache
docker-compose up
```

### Issue: "Out of disk space"
```bash
# Clean up unused Docker resources
docker system prune -a

# Remove unused volumes
docker volume prune
```

---

## 🔄 Development Workflow

### 1. Make Code Changes
Edit your Java files, HTML templates, etc.

### 2. Rebuild and Deploy
```bash
docker-compose down
docker-compose up --build
```

### 3. Test
Access http://localhost:8081 and verify changes

### 4. Check Logs
```bash
docker-compose logs -f spring-app
```

---

## 🚢 Production Deployment

### For Production, Update:

**1. docker-compose.yml**
```yaml
environment:
  SPRING_PROFILES_ACTIVE: prod
  SPRING_JPA_HIBERNATE_DDL_AUTO: validate  # Don't auto-update schema
```

**2. Create application-prod.properties**
- Use strong passwords
- Disable debug logging
- Configure production database

**3. Use Docker Secrets**
```yaml
secrets:
  db_password:
    file: ./db_password.txt
```

---

## 📈 Performance Optimization

### Increase Memory for Spring Boot
```yaml
spring-app:
  environment:
    JAVA_OPTS: "-Xmx1024m -Xms512m"  # Increased from 512/256
```

### Database Connection Pool
Already configured in `application-docker.properties`:
- Maximum pool size: 10
- Minimum idle: 5

---

## 🔐 Security Notes

### For Production:
1. ✅ Change default database password
2. ✅ Use environment variables for secrets
3. ✅ Enable HTTPS/SSL
4. ✅ Implement proper authentication
5. ✅ Use Docker secrets or external secret management
6. ✅ Regular security updates

```yaml
# Example with environment file
services:
  postgres-db:
    env_file:
      - .env.production
```

---

## 📝 Common Use Cases

### Scenario 1: Fresh Development Setup
```bash
docker-compose up --build -d
# Wait 30 seconds for initialization
# Access http://localhost:8081
```

### Scenario 2: Daily Development
```bash
docker-compose start
# ... make changes ...
docker-compose down
docker-compose up --build
```

### Scenario 3: Demo/Presentation
```bash
docker-compose up --build -d
docker-compose logs -f spring-app
# Show application at http://localhost:8081
```

### Scenario 4: Clean Slate
```bash
docker-compose down -v
docker-compose up --build
# Fresh database with DataLoader initialization
```

---

## 🎓 Learning Resources

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Spring Boot with Docker](https://spring.io/guides/topicals/spring-boot-docker/)
- [PostgreSQL Docker Image](https://hub.docker.com/_/postgres)

---

## ✅ Checklist

Before running:
- [ ] Docker Desktop is running
- [ ] Port 8081 is available
- [ ] Port 5432 is available
- [ ] You have internet connection (for first build)
- [ ] At least 2GB free disk space

---

## 🆘 Support

If you encounter issues:
1. Check logs: `docker-compose logs -f`
2. Verify services: `docker-compose ps`
3. Check health: `docker ps` (look for "healthy" status)
4. Restart: `docker-compose restart`
5. Clean rebuild: `docker-compose down && docker-compose up --build`

---

## 📄 File Structure

```
Student_Management_System/
├── Dockerfile                      # Spring Boot container definition
├── docker-compose.yml              # Multi-container orchestration
├── .dockerignore                   # Files to exclude from build
├── src/
│   └── main/
│       └── resources/
│           └── application-docker.properties  # Docker-specific config
├── pom.xml                         # Maven dependencies
└── DOCKER_DEPLOYMENT.md           # This file
```

---

**🎉 You're all set! Happy Dockerizing! 🐳**
