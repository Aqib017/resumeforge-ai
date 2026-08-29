package com.resumeforge.resume.experience.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExperienceRequest {

    @NotBlank
    private String company;

    @NotBlank
    private String jobTitle;

    private String location;

    @NotBlank
    private String startDate;

    private String endDate;

    private boolean currentlyWorking;

    private String description;
}