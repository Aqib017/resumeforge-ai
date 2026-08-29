package com.resumeforge.resume.project.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectRequest {

    @NotBlank
    private String name;

    private String description;

    private String technologies;

    private String projectUrl;

    private String startDate;

    private String endDate;
}