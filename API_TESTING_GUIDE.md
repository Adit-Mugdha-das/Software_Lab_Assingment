# API Testing Guide - Student Management System

## Quick Start Testing Workflow

### Step 1: Start the Application

1. Ensure PostgreSQL is running
2. Run the application:
```bash
mvnw spring-boot:run
```

3. The application will create sample data automatically

### Step 2: Access Swagger UI

Open your browser and go to:
```
http://localhost:8080/swagger-ui.html
```

## Complete Testing Workflow

### Scenario 1: Teacher Login and Student Approval

#### 1. Login as Teacher
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.smith@university.edu",
    "password": "teacher123"
  }'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWI...",
  "type": "Bearer",
  "id": 1,
  "email": "john.smith@university.edu",
  "name": "Dr. John Smith",
  "role": "TEACHER",
  "status": "ACTIVE"
}
```

**Save the token for subsequent requests!**

#### 2. View Pending Students
```bash
curl -X GET http://localhost:8080/api/auth/pending-students \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

#### 3. Approve a Student
```bash
curl -X POST http://localhost:8080/api/auth/approve-student/2 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### Scenario 2: Student Registration and Login

#### 1. Register New Student
```bash
curl -X POST http://localhost:8080/api/auth/register/student \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Emma Watson",
    "email": "emma.watson@student.edu",
    "password": "student123",
    "phone": "9876543220",
    "rollNumber": "CS2024005",
    "address": "789 Pine Road",
    "dateOfBirth": "2004-04-15",
    "gender": "FEMALE",
    "semester": 4,
    "guardianName": "Chris Watson",
    "guardianContact": "9876543221",
    "departmentId": 1
  }'
```

Response:
```json
{
  "message": "Registration successful. Waiting for teacher approval.",
  "id": 3,
  "email": "emma.watson@student.edu",
  "name": "Emma Watson",
  "role": "STUDENT",
  "status": "PENDING"
}
```

#### 2. Try to Login (Will Fail - Pending Approval)
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "emma.watson@student.edu",
    "password": "student123"
  }'
```

Error Response:
```json
{
  "timestamp": "2026-01-20T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Account is pending approval"
}
```

#### 3. Teacher Approves the Student
```bash
curl -X POST http://localhost:8080/api/auth/approve-student/3 \
  -H "Authorization: Bearer TEACHER_TOKEN"
```

#### 4. Student Login (Now Works)
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "emma.watson@student.edu",
    "password": "student123"
  }'
```

### Scenario 3: CRUD Operations on Departments

#### 1. View All Departments (Public - No Auth Required)
```bash
curl -X GET http://localhost:8080/api/departments/all
```

#### 2. Create New Department (Teacher Only)
```bash
curl -X POST http://localhost:8080/api/departments \
  -H "Authorization: Bearer TEACHER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Information Technology",
    "code": "IT",
    "description": "Department of Information Technology"
  }'
```

#### 3. Update Department
```bash
curl -X PUT http://localhost:8080/api/departments/4 \
  -H "Authorization: Bearer TEACHER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Updated: Department of Information Technology and Systems"
  }'
```

#### 4. Search Departments
```bash
curl -X GET "http://localhost:8080/api/departments/search?keyword=computer" \
  -H "Authorization: Bearer TEACHER_TOKEN"
```

### Scenario 4: Course Management

#### 1. View All Courses
```bash
curl -X GET http://localhost:8080/api/courses \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

#### 2. Create New Course (Teacher Only)
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Authorization: Bearer TEACHER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Cloud Computing",
    "courseCode": "CS402",
    "description": "Introduction to cloud platforms and services",
    "credits": 3,
    "semester": 7,
    "courseType": "ELECTIVE",
    "department": {"id": 1},
    "teacher": {"id": 1}
  }'
```

#### 3. Get Courses by Department
```bash
curl -X GET http://localhost:8080/api/courses/department/1 \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

#### 4. Get Courses by Semester
```bash
curl -X GET http://localhost:8080/api/courses/semester/4 \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

#### 5. Get Courses by Teacher
```bash
curl -X GET http://localhost:8080/api/courses/teacher/1 \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

### Scenario 5: Student Enrollment and Grading

#### 1. Student Enrolls in a Course
```bash
curl -X POST http://localhost:8080/api/enrollments \
  -H "Authorization: Bearer STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "courseId": 1,
    "academicYear": "2024-2025",
    "semester": 4
  }'
```

#### 2. View Student's Enrollments
```bash
curl -X GET http://localhost:8080/api/enrollments/student/1 \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

#### 3. View Course Enrollments (Teacher)
```bash
curl -X GET http://localhost:8080/api/enrollments/course/1 \
  -H "Authorization: Bearer TEACHER_TOKEN"
```

#### 4. Teacher Updates Grade
```bash
curl -X PUT http://localhost:8080/api/enrollments/1/grade \
  -H "Authorization: Bearer TEACHER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "grade": "A",
    "marks": 88.5,
    "status": "COMPLETED",
    "remarks": "Excellent work on final project"
  }'
```

### Scenario 6: Student Profile Management

#### 1. Student Views Own Profile
```bash
curl -X GET http://localhost:8080/api/students/1 \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

#### 2. Student Updates Own Profile
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Authorization: Bearer STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Brown-Smith",
    "phone": "9999999999",
    "address": "456 New Address",
    "semester": 5
  }'
```

**Note**: Roll number cannot be changed!

#### 3. Try to Update Another Student's Profile (Will Fail)
```bash
curl -X PUT http://localhost:8080/api/students/2 \
  -H "Authorization: Bearer STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Hacker Name"
  }'
```

Error Response:
```json
{
  "timestamp": "2026-01-20T10:45:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "You can only update your own profile"
}
```

### Scenario 7: Search Functionality

#### 1. Search Students
```bash
curl -X GET "http://localhost:8080/api/students/search?keyword=alice" \
  -H "Authorization: Bearer TEACHER_TOKEN"
```

#### 2. Search by Roll Number
```bash
curl -X GET "http://localhost:8080/api/students/search?keyword=CS2024" \
  -H "Authorization: Bearer TEACHER_TOKEN"
```

#### 3. Search Teachers
```bash
curl -X GET "http://localhost:8080/api/teachers/search?keyword=john" \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

#### 4. Search Courses
```bash
curl -X GET "http://localhost:8080/api/courses/search?keyword=database" \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

## Testing with Postman

### Setup

1. Create a new Postman Collection: "Student Management System"
2. Create two environment variables:
   - `teacher_token`: Store teacher's JWT token
   - `student_token`: Store student's JWT token
   - `base_url`: http://localhost:8080

### Postman Test Collection

#### Collection 1: Authentication
- POST Login Teacher
- POST Login Student
- POST Register Student
- POST Register Teacher
- GET Pending Students
- POST Approve Student
- POST Reject Student

#### Collection 2: Departments
- GET All Departments (Public)
- GET Department by ID
- POST Create Department
- PUT Update Department
- DELETE Delete Department
- GET Search Departments

#### Collection 3: Courses
- GET All Courses
- GET Course by ID
- GET Courses by Department
- GET Courses by Teacher
- GET Courses by Semester
- POST Create Course
- PUT Update Course
- DELETE Delete Course

#### Collection 4: Students
- GET All Students
- GET Student by ID
- GET Students by Department
- PUT Update Student
- DELETE Delete Student
- GET Search Students

#### Collection 5: Enrollments
- POST Enroll Student
- GET Student Enrollments
- GET Course Enrollments
- PUT Update Grade
- DELETE Drop Enrollment

## Common Testing Scenarios

### Test Case 1: Roll Number Immutability
**Expected**: Student cannot change their roll number

```bash
# Try to update roll number
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Authorization: Bearer STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "rollNumber": "CS2024999"
  }'
```

**Result**: Roll number field is not in the update DTO, so it's ignored.

### Test Case 2: Duplicate Enrollment Prevention
**Expected**: Student cannot enroll in same course twice in same year

```bash
# First enrollment - Success
curl -X POST http://localhost:8080/api/enrollments \
  -H "Authorization: Bearer STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "courseId": 2,
    "academicYear": "2024-2025",
    "semester": 4
  }'

# Second enrollment - Should Fail
curl -X POST http://localhost:8080/api/enrollments \
  -H "Authorization: Bearer STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "courseId": 2,
    "academicYear": "2024-2025",
    "semester": 4
  }'
```

Error:
```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Student is already enrolled in this course for the academic year"
}
```

### Test Case 3: Access Control
**Expected**: Students cannot approve other students

```bash
curl -X POST http://localhost:8080/api/auth/approve-student/2 \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

Error:
```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "You don't have permission to access this resource"
}
```

## Database Verification

### Check Data in PostgreSQL

```sql
-- View all students
SELECT id, name, email, roll_number, status, department_id 
FROM students;

-- View all teachers
SELECT id, name, email, employee_id, status, department_id 
FROM teachers;

-- View enrollments with details
SELECT 
    e.id,
    s.name as student_name,
    c.name as course_name,
    e.academic_year,
    e.grade,
    e.marks,
    e.status
FROM enrollments e
JOIN students s ON e.student_id = s.id
JOIN courses c ON e.course_id = c.id;

-- Department statistics
SELECT 
    d.name as department,
    COUNT(DISTINCT s.id) as student_count,
    COUNT(DISTINCT t.id) as teacher_count,
    COUNT(DISTINCT c.id) as course_count
FROM departments d
LEFT JOIN students s ON d.id = s.department_id
LEFT JOIN teachers t ON d.id = t.department_id
LEFT JOIN courses c ON d.id = c.department_id
GROUP BY d.name;
```

## Performance Testing

### Load Testing with Apache Bench

```bash
# Test login endpoint
ab -n 100 -c 10 -p login.json -T application/json \
  http://localhost:8080/api/auth/login

# Test get all courses (with auth header)
ab -n 1000 -c 50 -H "Authorization: Bearer YOUR_TOKEN" \
  http://localhost:8080/api/courses
```

## Troubleshooting Common Issues

### Issue 1: 401 Unauthorized
**Solution**: Check if token is valid and not expired (24 hours)

### Issue 2: 403 Forbidden
**Solution**: Verify user role has permission for the endpoint

### Issue 3: 409 Conflict
**Solution**: Check for duplicate entries (email, roll number, course code)

### Issue 4: 404 Not Found
**Solution**: Verify the resource ID exists in database

## Next Steps for Testing

1. **Integration Tests**: Write JUnit tests for services
2. **Security Tests**: Test with @WithMockUser
3. **Repository Tests**: Use @DataJpaTest
4. **Load Testing**: Test with multiple concurrent users
5. **Edge Cases**: Test with null values, invalid data, etc.

## API Response Examples

### Successful Response
```json
{
  "id": 1,
  "name": "Alice Brown",
  "email": "alice.brown@student.edu",
  "rollNumber": "CS2024001",
  "status": "ACTIVE"
}
```

### Error Response
```json
{
  "timestamp": "2026-01-20T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id: 999",
  "path": "/api/students/999"
}
```

### Validation Error Response
```json
{
  "timestamp": "2026-01-20T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Input validation error",
  "path": "/api/auth/register/student",
  "validationErrors": {
    "email": "Invalid email format",
    "rollNumber": "Roll number is required"
  }
}
```

Happy Testing! 🚀
