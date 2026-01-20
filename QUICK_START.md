# Quick Start Guide - Student Management System

## 🚀 Getting Started in 5 Minutes

### Prerequisites Check
- ✅ Java 25 installed
- ✅ PostgreSQL installed and running
- ✅ Maven installed (or use included wrapper)

### Step 1: Database Setup (2 minutes)

1. Open PostgreSQL command line or pgAdmin
2. Create the database:
```sql
CREATE DATABASE student_management_db;
```

3. Update credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.password=your_postgres_password
```

### Step 2: Build & Run (2 minutes)

Open terminal in project directory:

**Windows (PowerShell)**:
```powershell
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

**Mac/Linux**:
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### Step 3: Verify Setup (1 minute)

1. Check console for:
```
Sample data loaded successfully!
===========================================
Teacher Login: john.smith@university.edu / teacher123
Student Login (Active): alice.brown@student.edu / student123
===========================================
```

2. Open browser: http://localhost:8080/swagger-ui.html

## 🎯 Quick Test Workflow

### Test 1: Login as Teacher (30 seconds)

1. In Swagger UI, expand **Authentication → POST /api/auth/login**
2. Click "Try it out"
3. Enter:
```json
{
  "email": "john.smith@university.edu",
  "password": "teacher123"
}
```
4. Click "Execute"
5. Copy the `token` from response

### Test 2: Authorize in Swagger (10 seconds)

1. Click the green **"Authorize"** button at top
2. Enter: `Bearer your_token_here`
3. Click "Authorize" then "Close"

### Test 3: View Pending Students (10 seconds)

1. Expand **Authentication → GET /api/auth/pending-students**
2. Click "Try it out" then "Execute"
3. You should see Bob Wilson (PENDING status)

### Test 4: Approve Student (10 seconds)

1. Expand **Authentication → POST /api/auth/approve-student/{studentId}**
2. Enter studentId: `2`
3. Click "Execute"
4. Status should change to ACTIVE

### Test 5: Student Login (10 seconds)

1. Logout (click Authorize → Logout)
2. Go to **POST /api/auth/login**
3. Login as Bob:
```json
{
  "email": "bob.wilson@student.edu",
  "password": "student123"
}
```
4. Should now work! (Previously would fail)

## 📊 Explore the System

### View Departments
**GET /api/departments/all** (No auth needed)

### View All Courses
**GET /api/courses** (Auth required)

### Enroll in Course
**POST /api/enrollments**
```json
{
  "studentId": 1,
  "courseId": 1,
  "academicYear": "2024-2025",
  "semester": 4
}
```

### Add Grade (Teacher Only)
**PUT /api/enrollments/{id}/grade**
```json
{
  "grade": "A",
  "marks": 90.0,
  "status": "COMPLETED",
  "remarks": "Excellent work"
}
```

## 🔑 Default Credentials

### Teachers (Auto-Approved)
| Email | Password | Department |
|-------|----------|------------|
| john.smith@university.edu | teacher123 | Computer Science |
| sarah.johnson@university.edu | teacher123 | Computer Science |

### Students
| Email | Password | Status | Roll Number |
|-------|----------|--------|-------------|
| alice.brown@student.edu | student123 | ACTIVE | CS2024001 |
| bob.wilson@student.edu | student123 | PENDING* | CS2024002 |

*Approve Bob using teacher account to test approval workflow

## 📝 Sample Departments

1. **Computer Science** (CS)
2. **Electrical Engineering** (EE)
3. **Mechanical Engineering** (ME)

## 📚 Sample Courses

| Code | Name | Semester | Credits | Type |
|------|------|----------|---------|------|
| CS101 | Data Structures | 3 | 4 | CORE |
| CS201 | Database Systems | 4 | 4 | CORE |
| CS301 | Machine Learning | 6 | 3 | ELECTIVE |
| CS102L | Web Dev Lab | 4 | 2 | LAB |

## 🧪 Testing Checklist

- [ ] Teacher login works
- [ ] Student login works (after approval)
- [ ] View pending students
- [ ] Approve/reject students
- [ ] View departments and courses
- [ ] Enroll student in course
- [ ] Update grades
- [ ] Search functionality
- [ ] Update student profile (cannot change roll number)
- [ ] Access control (student cannot approve students)

## 🛠️ Common Issues & Solutions

### Issue: Port 8080 already in use
**Solution**: Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Database connection failed
**Solution**: 
1. Check PostgreSQL is running
2. Verify database exists
3. Check username/password

### Issue: JWT token expired
**Solution**: Login again to get new token (expires in 24 hours)

### Issue: Student login fails with "Account pending approval"
**Solution**: Login as teacher and approve the student first

### Issue: Cannot update roll number
**Solution**: This is by design! Roll numbers are immutable.

## 📖 Next Steps

1. **Read Full Documentation**: Check `README.md`
2. **API Testing Guide**: See `API_TESTING_GUIDE.md`
3. **Database Details**: Read `DATABASE_RELATIONSHIPS.md`
4. **Customize**: Add your own features!

## 🎓 Learning Resources

### Understand the Relationships

**One-to-Many Examples**:
- One Department → Many Students
- One Department → Many Courses
- One Teacher → Many Courses

**Many-to-One Examples**:
- Many Students → One Department
- Many Courses → One Department

**Many-to-Many**:
- Students ↔ Courses (via Enrollment table)

### Key Features Demonstrated

1. **JWT Authentication**: Secure, stateless authentication
2. **Role-Based Access**: Different permissions for students/teachers
3. **Approval Workflow**: Teacher approval for student registration
4. **CRUD Operations**: Create, Read, Update, Delete for all entities
5. **Search & Filter**: Find records quickly
6. **Grade Management**: Track student performance
7. **Audit Trail**: Automatic timestamps

## 📞 Need Help?

### Check the logs
```bash
# In terminal where app is running
# Look for errors or warnings
```

### Database queries
```sql
-- Check if data was loaded
SELECT COUNT(*) FROM departments;
SELECT COUNT(*) FROM students;
SELECT COUNT(*) FROM teachers;
SELECT COUNT(*) FROM courses;
```

### Reset database
```sql
-- Drop and recreate
DROP DATABASE student_management_db;
CREATE DATABASE student_management_db;
-- Then restart the application
```

## 🎉 Success Indicators

You'll know everything is working when:

1. ✅ Application starts without errors
2. ✅ Sample data loading message appears
3. ✅ Swagger UI opens successfully
4. ✅ Teacher login returns a JWT token
5. ✅ Can view pending students
6. ✅ Can approve a student
7. ✅ Student can login after approval
8. ✅ Can enroll in courses
9. ✅ Teacher can update grades

## 🚀 Advanced Features to Try

1. **Create New Department**
2. **Register New Teacher** (auto-approved)
3. **Register New Student** (needs approval)
4. **Create New Course**
5. **Assign Course to Teacher**
6. **Student Enrolls in Multiple Courses**
7. **View Department Statistics**
8. **Search Students by Name**
9. **Search Courses by Code**
10. **Generate Grade Reports**

## 💡 Tips for Development

1. **Use Swagger UI** for quick testing
2. **Check PostgreSQL** for data verification
3. **Monitor Logs** for debugging
4. **Test Edge Cases** (duplicate emails, invalid IDs, etc.)
5. **Understand Relationships** before modifying entities

## 🔄 Quick Restart

If you make changes:

```bash
# Stop the application (Ctrl+C)
# Rebuild and run
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

## 📈 Performance Tips

1. Database indexes are auto-created for email, roll number, etc.
2. LAZY loading prevents N+1 query issues
3. @JsonIgnore prevents circular references
4. Connection pooling is configured automatically

---

**Happy Coding! 🎓**

Your Student Management System is now ready for development, testing, and enhancement!
