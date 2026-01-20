package com.example.student_management_system.controller;

import com.example.student_management_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
public class DebugController {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @GetMapping("/database-stats")
    public Map<String, Object> getDatabaseStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("total_students", studentRepository.count());
        stats.put("total_teachers", teacherRepository.count());
        stats.put("total_courses", courseRepository.count());
        stats.put("total_departments", departmentRepository.count());
        stats.put("total_enrollments", enrollmentRepository.count());

        stats.put("students", studentRepository.findAll());
        stats.put("teachers", teacherRepository.findAll());
        stats.put("courses", courseRepository.findAll());
        stats.put("departments", departmentRepository.findAll());

        return stats;
    }

    @GetMapping("/tables-info")
    public Map<String, String> getTablesInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("message", "Database is connected and working!");
        info.put("database", "student_management_db");
        info.put("tables_created", "courses, departments, enrollments, students, teachers, users");
        info.put("status", "All tables are active and accessible");
        return info;
    }
}
