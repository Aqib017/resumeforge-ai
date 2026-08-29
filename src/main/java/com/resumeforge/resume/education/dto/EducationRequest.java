package com.resumeforge.resume.education.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EducationRequest {

    @NotBlank
    private String institution;

    @NotBlank
    private String degree;

    private String fieldOfStudy;
    private String location;
    private String startDate;
    private String endDate;
    private String grade;
}