package com.example.student_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {

    private String name;
    private String phone;
    private String address;
    private String dateOfBirth;
    private String gender;
    private Integer semester;
    private String guardianName;
    private String guardianContact;
    private Long departmentId;
}
