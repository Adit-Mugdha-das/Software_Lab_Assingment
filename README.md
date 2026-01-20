# Student Management System

A comprehensive Spring Boot application for managing students, teachers, departments, and courses with role-based access control and approval workflows.

## Features

### Core Functionality
- **Two User Roles**: Student and Teacher with distinct permissions
- **Four Main Entities**: Student, Teacher, Department, and Course
- **Database Relationships**:
  - One-to-Many: Department → Students, Department → Courses, Department → Teachers
  - Many-to-One: Students → Department, Courses → Department, Teachers → Department
  - Many-to-Many: Students ↔ Courses (via Enrollment entity)
  - One-to-Many: Teacher → Courses

### Authentication & Authorization
- JWT-based authentication
- Role-based access control using Spring Security
- **Student Registration**: Requires teacher approval (PENDING → ACTIVE)
- **Teacher Registration**: Auto-approved
- Students can only update their own profile (except roll number)
- Teachers have full CRUD access

### Additional Features
1. **Enrollment Management**: Students can enroll in courses
2. **Grade Management**: Teachers can assign grades to enrolled students
3. **Search Functionality**: Search students, teachers, courses, and departments
4. **Department-wise Reporting**: View students and courses by department
5. **Status Management**: Track account status (PENDING, ACTIVE, SUSPENDED, REJECTED)
6. **Audit Timestamps**: Automatic creation and update timestamps
7. **API Documentation**: Swagger UI for easy API testing

## Technology Stack

- **Framework**: Spring Boot 4.0.1
- **Java Version**: 25
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA (Hibernate)
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Maven
- **Additional**: Lombok, Validation

## Prerequisites

1. **Java 25** or higher
2. **PostgreSQL** installed and running
3. **Maven** (or use the included Maven wrapper)

## Database Setup

1. Install PostgreSQL and create a database:
```sql
CREATE DATABASE student_management_db;
```

2. Update the database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/student_management_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

## Installation & Running

1. **Clone the repository** (or navigate to project directory):
```bash
cd C:\Users\mugdha\IdeaProjects\Student_Management_System
```

2. **Build the project**:
```bash
mvnw clean install
```

3. **Run the application**:
```bash
mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## Sample Data

The application automatically loads sample data on first startup:

### Teacher Accounts
- **Email**: john.smith@university.edu | **Password**: teacher123
- **Email**: sarah.johnson@university.edu | **Password**: teacher123

### Student Accounts
- **Email**: alice.brown@student.edu | **Password**: student123 (Active)
- **Email**: bob.wilson@student.edu | **Password**: student123 (Pending Approval)

### Departments
- Computer Science (CS)
- Electrical Engineering (EE)
- Mechanical Engineering (ME)

### Courses
- CS101: Data Structures and Algorithms
- CS201: Database Management Systems
- CS301: Machine Learning
- CS102L: Web Development Lab

## API Documentation

Access Swagger UI at: **http://localhost:8080/swagger-ui.html**

### Authentication Endpoints

#### Register Student
```http
POST /api/auth/register/student
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@student.edu",
  "password": "password123",
  "phone": "1234567890",
  "rollNumber": "CS2024003",
  "address": "123 Street",
  "dateOfBirth": "2004-01-01",
  "gender": "MALE",
  "semester": 4,
  "guardianName": "Jane Doe",
  "guardianContact": "0987654321",
  "departmentId": 1
}
```

#### Register Teacher
```http
POST /api/auth/register/teacher
Content-Type: application/json

{
  "name": "Dr. Jane Smith",
  "email": "jane.smith@university.edu",
  "password": "password123",
  "phone": "1234567890",
  "employeeId": "T003",
  "specialization": "Software Engineering",
  "qualification": "Ph.D.",
  "experience": 5,
  "officeRoom": "CS-303",
  "departmentId": 1
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john.smith@university.edu",
  "password": "teacher123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "id": 1,
  "email": "john.smith@university.edu",
  "name": "Dr. John Smith",
  "role": "TEACHER",
  "status": "ACTIVE"
}
```

### Teacher-Only Endpoints

#### Get Pending Students
```http
GET /api/auth/pending-students
Authorization: Bearer {token}
```

#### Approve Student
```http
POST /api/auth/approve-student/{studentId}
Authorization: Bearer {token}
```

#### Reject Student
```http
POST /api/auth/reject-student/{studentId}
Authorization: Bearer {token}
```

### Student Endpoints

#### Get All Students (Teacher Only)
```http
GET /api/students
Authorization: Bearer {token}
```

#### Get Student by ID
```http
GET /api/students/{id}
Authorization: Bearer {token}
```

#### Search Students
```http
GET /api/students/search?keyword=alice
Authorization: Bearer {token}
```

#### Update Student Profile
```http
PUT /api/students/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Updated Name",
  "phone": "9999999999",
  "semester": 5
}
```
**Note**: Roll number cannot be changed!

### Department Endpoints

#### Get All Departments (Public)
```http
GET /api/departments/all
```

#### Create Department (Teacher Only)
```http
POST /api/departments
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Civil Engineering",
  "code": "CE",
  "description": "Department of Civil Engineering"
}
```

### Course Endpoints

#### Get All Courses
```http
GET /api/courses
Authorization: Bearer {token}
```

#### Get Courses by Department
```http
GET /api/courses/department/{departmentId}
Authorization: Bearer {token}
```

#### Create Course (Teacher Only)
```http
POST /api/courses
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Advanced Java",
  "courseCode": "CS401",
  "description": "Advanced Java programming",
  "credits": 4,
  "semester": 7,
  "courseType": "ELECTIVE",
  "department": {"id": 1},
  "teacher": {"id": 1}
}
```

### Enrollment Endpoints

#### Enroll in Course
```http
POST /api/enrollments
Authorization: Bearer {token}
Content-Type: application/json

{
  "studentId": 1,
  "courseId": 1,
  "academicYear": "2024-2025",
  "semester": 4
}
```

#### Get Student Enrollments
```http
GET /api/enrollments/student/{studentId}
Authorization: Bearer {token}
```

#### Update Grade (Teacher Only)
```http
PUT /api/enrollments/{enrollmentId}/grade
Authorization: Bearer {token}
Content-Type: application/json

{
  "grade": "A",
  "marks": 85.5,
  "status": "COMPLETED",
  "remarks": "Excellent performance"
}
```

## Database Relationships Explained

### One-to-Many Relationships
1. **Department → Students**: One department has many students
2. **Department → Courses**: One department offers many courses
3. **Department → Teachers**: One department has many teachers
4. **Teacher → Courses**: One teacher teaches many courses

### Many-to-One Relationships
1. **Students → Department**: Many students belong to one department
2. **Courses → Department**: Many courses belong to one department
3. **Teachers → Department**: Many teachers belong to one department
4. **Courses → Teacher**: Many courses are taught by one teacher

### Many-to-Many Relationship
1. **Students ↔ Courses**: Implemented via the `Enrollment` entity (junction table)
   - A student can enroll in many courses
   - A course can have many students enrolled
   - The Enrollment entity stores additional data: grade, marks, academic year, semester

## Project Structure

```
src/main/java/com/example/student_management_system/
├── config/               # Security, JWT, and application configuration
│   ├── ApplicationConfig.java
│   ├── DataLoader.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtUtil.java
│   └── SecurityConfig.java
├── controller/           # REST API endpoints
│   ├── AuthController.java
│   ├── CourseController.java
│   ├── DepartmentController.java
│   ├── EnrollmentController.java
│   ├── StudentController.java
│   └── TeacherController.java
├── dto/                  # Data Transfer Objects
│   ├── AuthResponse.java
│   ├── EnrollmentRequest.java
│   ├── GradeUpdateRequest.java
│   ├── LoginRequest.java
│   ├── StudentRegistrationRequest.java
│   ├── StudentUpdateRequest.java
│   └── TeacherRegistrationRequest.java
├── entity/               # JPA Entities
│   ├── Course.java
│   ├── Department.java
│   ├── Enrollment.java
│   ├── Student.java
│   ├── Teacher.java
│   └── User.java
├── exception/            # Exception handling
│   ├── DuplicateResourceException.java
│   ├── ErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── UnauthorizedException.java
├── repository/           # Data access layer
│   ├── CourseRepository.java
│   ├── DepartmentRepository.java
│   ├── EnrollmentRepository.java
│   ├── StudentRepository.java
│   └── TeacherRepository.java
└── service/              # Business logic
    ├── AuthService.java
    ├── CourseService.java
    ├── DepartmentService.java
    ├── EnrollmentService.java
    ├── StudentService.java
    └── TeacherService.java
```

## Key Business Rules

1. **Student Registration**: 
   - Students register with status PENDING
   - Teachers must approve before students can login
   
2. **Roll Number Immutability**: 
   - Once assigned, student roll numbers cannot be changed
   - Enforced in the service layer
   
3. **Teacher Auto-Approval**: 
   - Teachers are automatically ACTIVE upon registration
   
4. **Profile Updates**:
   - Students can only update their own profiles
   - Teachers can update any profile
   
5. **Enrollment Rules**:
   - Students cannot enroll in the same course twice in the same academic year
   - Teachers can assign grades only after enrollment

## Additional Functionalities

1. **Search and Filter**: Search across all entities by keywords
2. **Department Reports**: View statistics by department
3. **Grade Management**: Complete grade tracking system
4. **Audit Trail**: Automatic timestamps for all entities
5. **Validation**: Comprehensive input validation
6. **Error Handling**: Global exception handling with meaningful messages
7. **API Documentation**: Interactive Swagger UI

## Future Enhancements (Ideas)

- Attendance tracking system
- Timetable management
- Fee management module
- Student performance analytics
- Email notifications for approvals
- File upload for documents
- Report generation (PDF)
- Dashboard with statistics
- Mobile app integration
- Parent portal

## Testing

### Using Swagger UI
1. Go to http://localhost:8080/swagger-ui.html
2. Use the "Authorize" button to add your JWT token
3. Test all endpoints interactively

### Using Postman
1. Import the API endpoints from Swagger
2. Create an environment variable for the token
3. Test the complete workflow

## Troubleshooting

### Database Connection Issues
- Ensure PostgreSQL is running
- Verify database credentials in application.properties
- Check if the database exists

### JWT Token Issues
- Token expires after 24 hours (configurable in application.properties)
- Always include "Bearer " prefix in Authorization header

### Port Already in Use
Change the port in application.properties:
```properties
server.port=8081
```

## License

This project is created for educational purposes.

## Contact

For questions or support, please contact the development team.
