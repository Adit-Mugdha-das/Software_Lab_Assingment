# COMPLETE SETUP GUIDE - Student Management System

## 🔴 IMPORTANT: What You Need to Do BEFORE Running the Application

### Issue Summary
Your `application.properties` currently has:
```properties
spring.datasource.password=postgres
```

**This will only work if your PostgreSQL password is actually "postgres"**

You need to:
1. ✅ Create the PostgreSQL database
2. ✅ Update the password to YOUR actual PostgreSQL password

---

## Step 1: Find Your PostgreSQL Password

**Your PostgreSQL password is the one you set when you installed PostgreSQL.**

If you don't remember it:
- **Windows**: You can reset it through pgAdmin or command line
- **The default username is usually**: `postgres`
- **The password**: Whatever YOU chose during installation

---

## Step 2: Create the Database

### Option A: Using Command Line (Recommended)

1. Open **Command Prompt** as Administrator

2. Navigate to PostgreSQL bin folder (usually):
```bash
cd C:\Program Files\PostgreSQL\[version]\bin
```

3. Login to PostgreSQL:
```bash
psql -U postgres
```

4. Enter YOUR PostgreSQL password when prompted

5. Create the database:
```sql
CREATE DATABASE student_management_db;
```

6. Verify:
```sql
\l
```
You should see `student_management_db` in the list

7. Exit:
```sql
\q
```

### Option B: Using pgAdmin (GUI Method)

1. Open **pgAdmin**
2. Enter your master password
3. Expand "Servers" → "PostgreSQL [version]"
4. Right-click on **"Databases"**
5. Select **"Create"** → **"Database..."**
6. In the dialog:
   - **Database name**: `student_management_db`
   - **Owner**: postgres
   - Click **Save**

---

## Step 3: Update application.properties with YOUR Password

Open: `src/main/resources/application.properties`

**Find this line:**
```properties
spring.datasource.password=postgres
```

**Change "postgres" to YOUR actual PostgreSQL password:**
```properties
spring.datasource.password=YourActualPassword123
```

For example, if your PostgreSQL password is "admin123":
```properties
spring.datasource.password=admin123
```

---

## Step 4: Download Maven Dependencies

The dependencies need to be downloaded. Run this command in the project directory:

```bash
.\mvnw.cmd clean install -U
```

This will:
- Download all required dependencies
- Compile the project
- Run tests

**This may take 5-10 minutes the first time!**

---

## Step 5: Verify Setup

### Check 1: PostgreSQL is Running
**Windows:**
- Press `Win + R`
- Type `services.msc`
- Look for "postgresql-x64-[version]"
- Status should be "Running"

If not running, right-click → Start

### Check 2: Database Exists
Run in psql:
```sql
\l
```
You should see `student_management_db`

### Check 3: Connection Test
Run in psql:
```bash
psql -U postgres -d student_management_db
```
If it connects successfully, you're good!

---

## Step 6: Run the Application

```bash
.\mvnw.cmd spring-boot:run
```

### What Should Happen:
1. Application starts
2. Connects to PostgreSQL
3. Creates tables automatically
4. Loads sample data
5. You'll see:
```
Sample data loaded successfully!
===========================================
Teacher Login: john.smith@university.edu / teacher123
Student Login (Active): alice.brown@student.edu / student123
===========================================
```

---

## 🔴 Common Errors & Solutions

### Error 1: "password authentication failed for user postgres"
**Cause**: Wrong password in application.properties

**Solution**: 
1. Find your actual PostgreSQL password
2. Update `spring.datasource.password=YOUR_REAL_PASSWORD`

### Error 2: "database student_management_db does not exist"
**Cause**: Database not created

**Solution**: Follow Step 2 to create the database

### Error 3: "Connection refused"
**Cause**: PostgreSQL service not running

**Solution**: 
```bash
# Windows - Run as Administrator
net start postgresql-x64-16
```

### Error 4: "Dependency not found" in pom.xml
**Cause**: Maven dependencies not downloaded

**Solution**:
```bash
.\mvnw.cmd clean install -U
```

### Error 5: "Port 8080 already in use"
**Cause**: Another application using port 8080

**Solution**: Change port in application.properties:
```properties
server.port=8081
```

---

## 🎯 Quick Checklist

Before running the application:

- [ ] PostgreSQL is installed
- [ ] PostgreSQL service is running
- [ ] Database `student_management_db` is created
- [ ] Password in application.properties matches your PostgreSQL password
- [ ] Maven dependencies downloaded (`mvnw clean install`)
- [ ] Port 8080 is available

---

## 📝 Your Current Configuration

**From your application.properties:**

```properties
Database Name: student_management_db
Username: postgres
Password: postgres  ⚠️ UPDATE THIS TO YOUR ACTUAL PASSWORD!
Host: localhost
Port: 5432
```

---

## 🧪 Test Database Connection

Create a simple test file to verify connection:

```bash
psql -U postgres -d student_management_db -c "SELECT version();"
```

If this works, your database connection is ready!

---

## 📞 Still Having Issues?

### Check PostgreSQL Version
```bash
psql --version
```

### Check if PostgreSQL is listening
```bash
netstat -an | findstr 5432
```

### View PostgreSQL logs
Usually located at:
```
C:\Program Files\PostgreSQL\[version]\data\log\
```

---

## ✅ Success Indicators

When everything is working, you'll see:

1. **In Console:**
```
Tomcat started on port(s): 8080 (http)
Sample data loaded successfully!
```

2. **In Browser:**
- http://localhost:8080/swagger-ui.html opens successfully

3. **In Database:**
```sql
\c student_management_db
\dt
```
Shows tables: users, students, teachers, departments, courses, enrollments

---

## 🚀 Next Steps After Successful Setup

1. Open Swagger UI: http://localhost:8080/swagger-ui.html
2. Test login with teacher credentials
3. View sample data
4. Start building features!

---

## 💡 Pro Tip

Save your PostgreSQL password in a secure location. You'll need it every time you:
- Connect to the database
- Update application.properties
- Use database management tools

---

**Need Help?** Check the `DATABASE_SETUP.md` file for more details!
