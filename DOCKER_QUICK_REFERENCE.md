# 🐳 Docker Quick Reference

## 🚀 Windows - One-Click Scripts

Just double-click these files:

| Script | Purpose |
|--------|---------|
| `docker-start.bat` | 🟢 Start everything (builds + runs) |
| `docker-stop.bat` | 🟡 Stop containers |
| `docker-logs.bat` | 📋 View live logs |
| `docker-clean.bat` | 🔴 Remove everything (fresh start) |

---

## ⚡ Quick Commands (PowerShell/CMD)

### Essential Commands
```bash
# Start (first time or after changes)
docker-compose up --build -d

# Start (existing containers)
docker-compose start

# Stop
docker-compose stop

# Remove everything
docker-compose down -v

# View logs
docker-compose logs -f
```

### Status & Info
```bash
# Check status
docker-compose ps

# View all containers
docker ps -a

# Check health
docker inspect student-management-app | findstr Health
```

---

## 🌐 Access URLs

| Service | URL |
|---------|-----|
| 🎓 Main App | http://localhost:8081 |
| 📚 Swagger API | http://localhost:8081/swagger-ui/index.html |
| ❤️ Health Check | http://localhost:8081/actuator/health |
| 🗄️ Database | localhost:5432 |

**Default Login:**
- Email: See your registered users
- Password: Use registration or existing credentials

---

## 🔧 Common Tasks

### After Code Changes
```bash
docker-compose down
docker-compose up --build
```

### Database Backup
```bash
docker exec student-management-db pg_dump -U postgres student_management_db > backup.sql
```

### Database Restore
```bash
type backup.sql | docker exec -i student-management-db psql -U postgres student_management_db
```

### Access Database Shell
```bash
docker exec -it student-management-db psql -U postgres -d student_management_db
```

### View Application Logs Only
```bash
docker-compose logs -f spring-app
```

### View Database Logs Only
```bash
docker-compose logs -f postgres-db
```

---

## 🐛 Troubleshooting

### "Port already in use"
```bash
# Find what's using the port
netstat -ano | findstr :8081

# Kill the process (replace PID)
taskkill /PID <PID> /F

# Or change port in docker-compose.yml
```

### "Cannot connect to database"
```bash
# Check database is running
docker-compose ps

# Restart database
docker-compose restart postgres-db

# Check logs
docker-compose logs postgres-db
```

### "Out of disk space"
```bash
# Clean unused resources
docker system prune -a

# Remove unused volumes
docker volume prune
```

### "Container won't start"
```bash
# Check logs for errors
docker-compose logs

# Rebuild from scratch
docker-compose down -v
docker-compose build --no-cache
docker-compose up
```

### "Health check failing"
```bash
# Wait longer (can take 60+ seconds first time)
docker-compose logs -f spring-app

# Check health endpoint directly
curl http://localhost:8081/actuator/health
```

---

## 📦 Container Management

### Restart Containers
```bash
# Restart all
docker-compose restart

# Restart specific service
docker-compose restart spring-app
```

### Rebuild After Changes
```bash
# Full rebuild
docker-compose build --no-cache
docker-compose up -d
```

### Scale Services (if needed)
```bash
# Run multiple app instances
docker-compose up -d --scale spring-app=2
```

---

## 💾 Data Management

### Persistent Data Location
- Database data: Docker volume `postgres-data`
- Application logs: Inside containers

### Clear All Data (Fresh Start)
```bash
docker-compose down -v
docker-compose up --build
```

### Export Database
```bash
docker exec student-management-db pg_dump -U postgres student_management_db > backup-$(date +%Y%m%d).sql
```

---

## 🔍 Monitoring

### Container Resource Usage
```bash
docker stats
```

### Disk Usage
```bash
docker system df
```

### Network Inspection
```bash
docker network inspect student-management-network
```

### Volume Inspection
```bash
docker volume inspect postgres-data
```

---

## 🎯 Development Workflow

1. **Start Development**
   ```bash
   docker-start.bat
   ```

2. **Make Code Changes**
   - Edit Java, HTML, CSS files
   - Save changes

3. **Test Changes**
   ```bash
   docker-compose down
   docker-compose up --build
   ```

4. **View Logs**
   ```bash
   docker-logs.bat
   ```

5. **Stop for the Day**
   ```bash
   docker-stop.bat
   ```

---

## 📊 Health Checks

### Application Health
```bash
curl http://localhost:8081/actuator/health
```

**Response:**
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    },
    "diskSpace": {
      "status": "UP"
    }
  }
}
```

### Database Health
```bash
docker exec student-management-db pg_isready -U postgres
```

---

## 🌟 Pro Tips

1. **First Run Takes Time**
   - Initial build: 3-5 minutes
   - Subsequent runs: 30-60 seconds

2. **Keep Docker Desktop Running**
   - Containers need Docker daemon

3. **Check Logs When Issues Occur**
   - Logs show exact errors
   - Use `docker-compose logs -f`

4. **Use Volume for Development**
   - Mount code as volume for hot reload (advanced)

5. **Resource Allocation**
   - Give Docker Desktop enough RAM (4GB+)
   - Configure in Docker Desktop settings

---

## 📚 File Structure

```
Student_Management_System/
├── docker-start.bat          ← Start everything
├── docker-stop.bat           ← Stop containers
├── docker-clean.bat          ← Clean reset
├── docker-logs.bat           ← View logs
├── Dockerfile                ← Spring Boot image
├── docker-compose.yml        ← Orchestration
├── .dockerignore            ← Build exclusions
└── src/main/resources/
    └── application-docker.properties  ← Docker config
```

---

## ✅ Verification Checklist

Before running Docker:
- [ ] Docker Desktop installed and running
- [ ] Ports 8081 and 5432 available
- [ ] At least 2GB free disk space
- [ ] Internet connection (first build only)

After starting:
- [ ] Containers show "healthy" status
- [ ] http://localhost:8081 loads
- [ ] No errors in logs
- [ ] Can access database

---

## 🆘 Getting Help

1. **Check This Guide**
2. **View Logs**: `docker-compose logs -f`
3. **Check DOCKER_DEPLOYMENT.md** for detailed docs
4. **Docker Status**: `docker-compose ps`
5. **Health Check**: `curl http://localhost:8081/actuator/health`

---

## 🎉 Success Indicators

✅ Both containers running  
✅ Status shows "healthy"  
✅ Application accessible at localhost:8081  
✅ No error messages in logs  
✅ Database queries working  

---

**Happy Dockerizing! 🐳**
