package com.example.student_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeUpdateRequest {

    private String grade;
    private Double marks;
    private String status;
    private String remarks;
}
