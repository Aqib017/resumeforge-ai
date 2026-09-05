package com.resumeforge.job.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class JobResponse {

    private Long id;
    private String title;
    private String companyName;
    private String location;
    private String description;
    private String sourceUrl;
    private LocalDateTime createdAt;
}