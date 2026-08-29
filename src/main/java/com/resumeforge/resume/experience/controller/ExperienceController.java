package com.resumeforge.resume.experience.controller;
import com.resumeforge.common.response.ApiResponse;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.experience.dto.ExperienceRequest;
import com.resumeforge.resume.experience.entity.Experience;
import com.resumeforge.resume.experience.service.ExperienceService;
import com.resumeforge.resume.repository.ResumeRepository;
import com.resumeforge.user.entity.User;
import com.resumeforge.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/{resumeId}/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ExperienceController(
            ExperienceService experienceService,
            ResumeRepository resumeRepository,
            UserRepository userRepository) {

        this.experienceService = experienceService;
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    private Resume getResume(Long resumeId, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return resumeRepository.findByIdAndUser(resumeId, user)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addExperience(
            @PathVariable Long resumeId,
            @Valid @RequestBody ExperienceRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Experience experience =
                experienceService.addExperience(resume, request);

        return new ApiResponse(
                true,
                "Experience added successfully",
                experience
        );
    }

    @GetMapping
    public ApiResponse getExperiences(
            @PathVariable Long resumeId,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        List<Experience> experiences =
                experienceService.getExperiences(resume);

        return new ApiResponse(
                true,
                "Experiences fetched successfully",
                experiences
        );
    }

    @PutMapping("/{id}")
    public ApiResponse updateExperience(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            @Valid @RequestBody ExperienceRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Experience experience =
                experienceService.updateExperience(id, resume, request);

        return new ApiResponse(
                true,
                "Experience updated successfully",
                experience
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteExperience(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        experienceService.deleteExperience(id, resume);

        return new ApiResponse(
                true,
                "Experience deleted successfully",
                null
        );
    }
}