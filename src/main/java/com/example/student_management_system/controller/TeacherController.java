package com.example.student_management_system.controller;

import com.example.student_management_system.entity.Teacher;
import com.example.student_management_system.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Tag(name = "Teachers", description = "Teacher management endpoints")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    @Operation(summary = "Get all teachers")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get teacher by ID")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get teachers by department")
    public ResponseEntity<List<Teacher>> getTeachersByDepartment(@PathVariable Long departmentId) {
        List<Teacher> teachers = teacherService.getTeachersByDepartment(departmentId);
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/search")
    @Operation(summary = "Search teachers by name or employee ID")
    public ResponseEntity<List<Teacher>> searchTeachers(@RequestParam String keyword) {
        List<Teacher> teachers = teacherService.searchTeachers(keyword);
        return ResponseEntity.ok(teachers);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update teacher profile")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacher) {
        Teacher updatedTeacher = teacherService.updateTeacher(id, teacher);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete teacher")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
