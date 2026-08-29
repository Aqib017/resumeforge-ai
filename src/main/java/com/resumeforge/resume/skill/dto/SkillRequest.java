package com.resumeforge.resume.skill.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SkillRequest {

    @NotBlank
    private String name;

    private String category;

    private String proficiency;
}