package com.example.student_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/courses")
    public String courses() {
        return "courses";
    }

    @GetMapping("/my-courses")
    public String myCourses() {
        return "my-courses";
    }

    @GetMapping("/debug/students")
    public String debugStudents() {
        return "debug-students";
    }

    @GetMapping("/teacher-dashboard")
    public String teacherDashboard() {
        return "teacher-dashboard";
    }

    @GetMapping("/grade-management")
    public String gradeManagement() {
        return "grade-management";
    }

    @GetMapping("/student-approvals")
    public String studentApprovals() {
        return "student-approvals";
    }
}
