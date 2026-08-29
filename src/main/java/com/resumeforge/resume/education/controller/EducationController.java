package com.resumeforge.resume.education.controller;
import com.resumeforge.common.response.ApiResponse;
import com.resumeforge.resume.education.dto.EducationRequest;
import com.resumeforge.resume.education.entity.Education;
import com.resumeforge.resume.education.service.EducationService;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.repository.ResumeRepository;
import com.resumeforge.user.entity.User;
import com.resumeforge.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/{resumeId}/education")
public class EducationController {

    private final EducationService educationService;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public EducationController(
            EducationService educationService,
            ResumeRepository resumeRepository,
            UserRepository userRepository) {

        this.educationService = educationService;
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    private Resume getResume(
            Long resumeId,
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return resumeRepository.findByIdAndUser(resumeId, user)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addEducation(
            @PathVariable Long resumeId,
            @Valid @RequestBody EducationRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Education education =
                educationService.addEducation(resume, request);

        return new ApiResponse(
                true,
                "Education added successfully",
                education
        );
    }

    @GetMapping
    public ApiResponse getEducations(
            @PathVariable Long resumeId,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        List<Education> educations =
                educationService.getEducations(resume);

        return new ApiResponse(
                true,
                "Education fetched successfully",
                educations
        );
    }

    @PutMapping("/{id}")
    public ApiResponse updateEducation(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            @Valid @RequestBody EducationRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Education education =
                educationService.updateEducation(id, resume, request);

        return new ApiResponse(
                true,
                "Education updated successfully",
                education
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteEducation(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        educationService.deleteEducation(id, resume);

        return new ApiResponse(
                true,
                "Education deleted successfully",
                null
        );
    }
}