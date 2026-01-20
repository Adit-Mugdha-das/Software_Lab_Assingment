package com.example.student_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequest {

    private Long studentId;

    private Long courseId;

    @NotBlank(message = "Academic year is required")
    private String academicYear;

    private Integer semester;
}
