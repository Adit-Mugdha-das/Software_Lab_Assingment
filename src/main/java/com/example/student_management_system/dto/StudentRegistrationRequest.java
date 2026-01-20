package com.example.student_management_system.dto;

import com.example.student_management_system.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegistrationRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Roll number is required")
    private String rollNumber;

    private String address;

    private String dateOfBirth;

    private String gender;

    private Integer semester;

    private String guardianName;

    private String guardianContact;

    private Long departmentId;
}
