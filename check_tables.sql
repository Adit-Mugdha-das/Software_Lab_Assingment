-- Simple Table Check
-- Run this in pgAdmin to see if tables exist

-- First, make sure you're in the right database
\c student_management_db

-- List all tables
\dt

-- If tables exist, this will show them
-- If no tables shown, tables don't exist yet

-- To manually check table existence:
SELECT EXISTS (
    SELECT FROM information_schema.tables
    WHERE table_schema = 'public'
    AND table_name = 'students'
) as students_table_exists;

SELECT EXISTS (
    SELECT FROM information_schema.tables
    WHERE table_schema = 'public'
    AND table_name = 'departments'
) as departments_table_exists;

SELECT EXISTS (
    SELECT FROM information_schema.tables
    WHERE table_schema = 'public'
    AND table_name = 'users'
) as users_table_exists;
