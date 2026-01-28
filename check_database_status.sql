-- Check if database tables have data
-- Run this in pgAdmin to verify current database state

SELECT 'Departments' as table_name, COUNT(*) as row_count FROM departments
UNION ALL
SELECT 'Users', COUNT(*) FROM users
UNION ALL
SELECT 'Teachers', COUNT(*) FROM teachers
UNION ALL
SELECT 'Students', COUNT(*) FROM students
UNION ALL
SELECT 'Courses', COUNT(*) FROM courses
UNION ALL
SELECT 'Enrollments', COUNT(*) FROM enrollments
ORDER BY table_name;

-- Check for unassigned courses
SELECT
    'Unassigned Courses' as info,
    COUNT(*) as count
FROM courses
WHERE teacher_id IS NULL;

-- View all courses with teacher assignment status
SELECT
    c.name as course_name,
    c.course_code,
    c.course_type,
    c.semester,
    CASE
        WHEN c.teacher_id IS NULL THEN 'UNASSIGNED'
        ELSE t.name
    END as teacher_status
FROM courses c
LEFT JOIN teachers t ON c.teacher_id = t.id
ORDER BY c.course_code;
