package com.resumeforge.resume.certification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CertificationRequest {

    @NotBlank
    private String name;

    private String issuingOrganization;
    private String issueDate;
    private String credentialId;
    private String credentialUrl;
}