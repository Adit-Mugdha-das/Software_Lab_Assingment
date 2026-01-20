# Database Relationships Guide

## Entity Relationship Diagram (ERD) Overview

```
┌─────────────────┐
│   Department    │
│─────────────────│
│ id (PK)         │
│ name            │
│ code            │
│ description     │
└────────┬────────┘
         │
         │ One-to-Many
         │
    ┌────┴─────┬──────────┬──────────┐
    │          │          │          │
┌───▼──────┐ ┌─▼────────┐ ┌──▼──────┐ ┌─────────┐
│ Student  │ │ Teacher  │ │ Course  │─│ Teacher │
│──────────│ │──────────│ │─────────│ │─────────│
│ id (PK)  │ │ id (PK)  │ │ id (PK) │ │ One-to- │
│ name     │ │ name     │ │ name    │ │ Many    │
│ email    │ │ email    │ │ code    │ └─────────┘
│ roll_no  │ │ emp_id   │ │ credits │
│ dept_id  │ │ dept_id  │ │ dept_id │
│ (FK)     │ │ (FK)     │ │ (FK)    │
└────┬─────┘ └──────────┘ │ teacher │
     │                    │ _id(FK) │
     │                    └────┬────┘
     │                         │
     │    Many-to-Many         │
     │    (via Enrollment)     │
     │                         │
     └────────┬────────────────┘
              │
        ┌─────▼─────┐
        │Enrollment │
        │───────────│
        │ id (PK)   │
        │student_id │
        │ (FK)      │
        │course_id  │
        │ (FK)      │
        │acad_year  │
        │ grade     │
        │ marks     │
        └───────────┘
```

## Detailed Relationship Breakdown

### 1. Department Entity (Parent)

**Table**: `departments`

**Relationships**:
- Has Many Students (One-to-Many)
- Has Many Teachers (One-to-Many)
- Has Many Courses (One-to-Many)

**SQL Schema**:
```sql
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    code VARCHAR(10) NOT NULL UNIQUE,
    description VARCHAR(1000),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

**Example Data**:
```sql
INSERT INTO departments (name, code, description) VALUES
('Computer Science', 'CS', 'Department of Computer Science and Engineering'),
('Electrical Engineering', 'EE', 'Department of Electrical Engineering'),
('Mechanical Engineering', 'ME', 'Department of Mechanical Engineering');
```

### 2. User Entity (Abstract Parent)

**Table**: `users`

**Type**: JOINED inheritance strategy

**Attributes**:
- id (Primary Key)
- name
- email (Unique)
- password
- phone
- role (STUDENT or TEACHER)
- status (PENDING, ACTIVE, SUSPENDED, REJECTED)
- created_at
- updated_at

**SQL Schema**:
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

### 3. Student Entity (Extends User)

**Table**: `students`

**Relationships**:
- Belongs to One Department (Many-to-One)
- Has Many Enrollments (One-to-Many)
- Enrolled in Many Courses (Many-to-Many via Enrollment)

**SQL Schema**:
```sql
CREATE TABLE students (
    id BIGINT PRIMARY KEY REFERENCES users(id),
    roll_number VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(255),
    date_of_birth VARCHAR(255),
    gender VARCHAR(50),
    semester INTEGER,
    guardian_name VARCHAR(255),
    guardian_contact VARCHAR(255),
    department_id BIGINT REFERENCES departments(id)
);
```

**Relationship Examples**:
```java
// In Student.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "department_id")
private Department department;

@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
private List<Enrollment> enrollments = new ArrayList<>();
```

**Query Examples**:
```sql
-- Get all students in a department
SELECT s.* FROM students s WHERE s.department_id = 1;

-- Get student with department details
SELECT s.*, d.name as dept_name 
FROM students s 
LEFT JOIN departments d ON s.department_id = d.id 
WHERE s.id = 1;
```

### 4. Teacher Entity (Extends User)

**Table**: `teachers`

**Relationships**:
- Belongs to One Department (Many-to-One)
- Teaches Many Courses (One-to-Many)

**SQL Schema**:
```sql
CREATE TABLE teachers (
    id BIGINT PRIMARY KEY REFERENCES users(id),
    employee_id VARCHAR(255) NOT NULL UNIQUE,
    specialization VARCHAR(255),
    qualification VARCHAR(255),
    experience INTEGER,
    office_room VARCHAR(255),
    department_id BIGINT REFERENCES departments(id)
);
```

**Relationship Examples**:
```java
// In Teacher.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "department_id")
private Department department;

@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
private List<Course> courses = new ArrayList<>();
```

**Query Examples**:
```sql
-- Get all teachers in a department
SELECT t.* FROM teachers t WHERE t.department_id = 1;

-- Get teacher with courses
SELECT t.name, c.name as course_name
FROM teachers t
LEFT JOIN courses c ON t.id = c.teacher_id
WHERE t.id = 1;
```

### 5. Course Entity

**Table**: `courses`

**Relationships**:
- Belongs to One Department (Many-to-One)
- Taught by One Teacher (Many-to-One)
- Has Many Enrollments (One-to-Many)
- Has Many Students enrolled (Many-to-Many via Enrollment)

**SQL Schema**:
```sql
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    course_code VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(1000),
    credits INTEGER,
    semester INTEGER,
    course_type VARCHAR(50),
    department_id BIGINT NOT NULL REFERENCES departments(id),
    teacher_id BIGINT REFERENCES teachers(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

**Relationship Examples**:
```java
// In Course.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "department_id", nullable = false)
private Department department;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "teacher_id")
private Teacher teacher;

@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
private List<Enrollment> enrollments = new ArrayList<>();
```

**Query Examples**:
```sql
-- Get all courses in a department
SELECT c.* FROM courses c WHERE c.department_id = 1;

-- Get courses taught by a teacher
SELECT c.* FROM courses c WHERE c.teacher_id = 1;

-- Get course with department and teacher details
SELECT c.*, d.name as dept_name, t.name as teacher_name
FROM courses c
LEFT JOIN departments d ON c.department_id = d.id
LEFT JOIN teachers t ON c.teacher_id = t.id
WHERE c.id = 1;
```

### 6. Enrollment Entity (Junction Table)

**Table**: `enrollments`

**Purpose**: Implements Many-to-Many relationship between Students and Courses

**Relationships**:
- Belongs to One Student (Many-to-One)
- Belongs to One Course (Many-to-One)

**SQL Schema**:
```sql
CREATE TABLE enrollments (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES students(id),
    course_id BIGINT NOT NULL REFERENCES courses(id),
    academic_year VARCHAR(255) NOT NULL,
    semester INTEGER,
    grade VARCHAR(255),
    marks DOUBLE PRECISION,
    status VARCHAR(50) NOT NULL,
    remarks VARCHAR(500),
    enrolled_at TIMESTAMP,
    updated_at TIMESTAMP,
    UNIQUE(student_id, course_id, academic_year, semester)
);
```

**Relationship Examples**:
```java
// In Enrollment.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "student_id", nullable = false)
private Student student;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "course_id", nullable = false)
private Course course;
```

**Query Examples**:
```sql
-- Get all enrollments for a student
SELECT e.*, c.name as course_name
FROM enrollments e
LEFT JOIN courses c ON e.course_id = c.id
WHERE e.student_id = 1;

-- Get all students enrolled in a course
SELECT e.*, s.name as student_name, s.roll_number
FROM enrollments e
LEFT JOIN students s ON e.student_id = s.id
WHERE e.course_id = 1;

-- Get student grades
SELECT s.name, s.roll_number, c.name as course, e.grade, e.marks
FROM enrollments e
JOIN students s ON e.student_id = s.id
JOIN courses c ON e.course_id = c.id
WHERE s.id = 1 AND e.status = 'COMPLETED';
```

## Cascade Operations

### Department Deletion
When a department is deleted:
```java
@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
```
- All students in that department are deleted
- All teachers in that department are deleted
- All courses in that department are deleted

**Be careful!** This is a powerful operation.

### Student Deletion
When a student is deleted:
```java
@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
```
- All enrollments for that student are deleted

### Course Deletion
When a course is deleted:
```java
@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
```
- All enrollments for that course are deleted

### Teacher Deletion
When a teacher is deleted:
```java
@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
```
- All courses taught by that teacher are deleted

## Fetch Strategies

### LAZY Loading (Default for Collections)
```java
@ManyToOne(fetch = FetchType.LAZY)
private Department department;
```
- Department is loaded only when accessed
- Prevents N+1 query problem
- Better performance

### JsonIgnore to Prevent Infinite Recursion
```java
@OneToMany(mappedBy = "department")
@JsonIgnore
private List<Student> students;
```
- Prevents infinite loop in JSON serialization
- Department → Student → Department → ...

## Common Queries

### 1. Get Department with Student Count
```sql
SELECT d.name, COUNT(s.id) as student_count
FROM departments d
LEFT JOIN students s ON d.id = s.department_id
GROUP BY d.id, d.name;
```

### 2. Get Student with All Courses
```sql
SELECT s.name, s.roll_number, c.name as course_name, e.grade
FROM students s
LEFT JOIN enrollments e ON s.id = e.student_id
LEFT JOIN courses c ON e.course_id = c.id
WHERE s.id = 1;
```

### 3. Get Course with Enrolled Students
```sql
SELECT c.name, COUNT(e.id) as enrollment_count
FROM courses c
LEFT JOIN enrollments e ON c.id = e.course_id
WHERE c.id = 1
GROUP BY c.id, c.name;
```

### 4. Get Teacher's Courses and Student Count
```sql
SELECT t.name as teacher, c.name as course, COUNT(e.student_id) as students
FROM teachers t
LEFT JOIN courses c ON t.id = c.teacher_id
LEFT JOIN enrollments e ON c.id = e.course_id
WHERE t.id = 1
GROUP BY t.name, c.name;
```

### 5. Department Report
```sql
SELECT 
    d.name as department,
    COUNT(DISTINCT s.id) as total_students,
    COUNT(DISTINCT t.id) as total_teachers,
    COUNT(DISTINCT c.id) as total_courses,
    COUNT(DISTINCT e.id) as total_enrollments
FROM departments d
LEFT JOIN students s ON d.id = s.department_id AND s.status = 'ACTIVE'
LEFT JOIN teachers t ON d.id = t.department_id
LEFT JOIN courses c ON d.id = c.department_id
LEFT JOIN enrollments e ON s.id = e.student_id
GROUP BY d.id, d.name;
```

## Indexes for Performance

```sql
-- Student indexes
CREATE INDEX idx_student_email ON students(email);
CREATE INDEX idx_student_roll ON students(roll_number);
CREATE INDEX idx_student_dept ON students(department_id);
CREATE INDEX idx_student_status ON students(status);

-- Teacher indexes
CREATE INDEX idx_teacher_email ON teachers(email);
CREATE INDEX idx_teacher_emp_id ON teachers(employee_id);
CREATE INDEX idx_teacher_dept ON teachers(department_id);

-- Course indexes
CREATE INDEX idx_course_code ON courses(course_code);
CREATE INDEX idx_course_dept ON courses(department_id);
CREATE INDEX idx_course_teacher ON courses(teacher_id);
CREATE INDEX idx_course_semester ON courses(semester);

-- Enrollment indexes
CREATE INDEX idx_enrollment_student ON enrollments(student_id);
CREATE INDEX idx_enrollment_course ON enrollments(course_id);
CREATE INDEX idx_enrollment_year ON enrollments(academic_year);
```

## Constraints and Business Rules

### Unique Constraints
```sql
-- Department
ALTER TABLE departments ADD CONSTRAINT uk_dept_name UNIQUE (name);
ALTER TABLE departments ADD CONSTRAINT uk_dept_code UNIQUE (code);

-- Student
ALTER TABLE students ADD CONSTRAINT uk_student_email UNIQUE (email);
ALTER TABLE students ADD CONSTRAINT uk_student_roll UNIQUE (roll_number);

-- Teacher
ALTER TABLE teachers ADD CONSTRAINT uk_teacher_email UNIQUE (email);
ALTER TABLE teachers ADD CONSTRAINT uk_teacher_emp_id UNIQUE (employee_id);

-- Course
ALTER TABLE courses ADD CONSTRAINT uk_course_code UNIQUE (course_code);

-- Enrollment (Composite Unique)
ALTER TABLE enrollments ADD CONSTRAINT uk_enrollment 
    UNIQUE (student_id, course_id, academic_year, semester);
```

## Data Integrity Examples

### Prevent Orphaned Records
```java
// In Enrollment entity
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "student_id", nullable = false)
private Student student;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "course_id", nullable = false)
private Course course;
```
- `nullable = false` ensures every enrollment has a student and course

### Orphan Removal
```java
@OneToMany(mappedBy = "student", orphanRemoval = true)
private List<Enrollment> enrollments;
```
- If enrollment is removed from student's collection, it's deleted from database

## Summary

### One-to-Many Relationships (3)
1. Department → Students
2. Department → Teachers
3. Department → Courses
4. Teacher → Courses

### Many-to-One Relationships (4)
1. Students → Department
2. Teachers → Department
3. Courses → Department
4. Courses → Teacher

### Many-to-Many Relationship (1)
1. Students ↔ Courses (via Enrollment junction table)

This design ensures:
- Data normalization
- Referential integrity
- Efficient queries
- Scalability
- Easy maintenance
