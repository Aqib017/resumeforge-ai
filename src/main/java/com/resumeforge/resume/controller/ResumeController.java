package com.resumeforge.resume.controller;
import com.resumeforge.common.response.ApiResponse;
import com.resumeforge.resume.dto.ResumeRequest;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.service.ResumeService;
import com.resumeforge.user.entity.User;
import com.resumeforge.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;
    private final UserRepository userRepository;

    public ResumeController(
            ResumeService resumeService, UserRepository userRepository) {
        this.resumeService = resumeService;
        this.userRepository = userRepository;
    }

    private User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createResume(
            @Valid @RequestBody ResumeRequest request,
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        Resume resume = new Resume();
        resume.setTitle(request.getTitle());
        resume.setFullName(request.getFullName());
        resume.setEmail(request.getEmail());
        resume.setPhone(request.getPhone());
        resume.setSummary(request.getSummary());

        Resume savedResume = resumeService.createResume(resume, user);

        return new ApiResponse(
                true,
                "Resume created successfully",
                savedResume
        );
    }

    @GetMapping
    public ApiResponse getAllResumes(Authentication authentication) {

        User user = getCurrentUser(authentication);

        List<Resume> resumes = resumeService.getAllResumes(user);

        return new ApiResponse(
                true,
                "Resumes fetched successfully",
                resumes
        );
    }

    @GetMapping("/{id}")
    public ApiResponse getResumeById(
            @PathVariable Long id,
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        Resume resume = resumeService.getResumeById(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));

        return new ApiResponse(
                true,
                "Resume fetched successfully",
                resume
        );
    }

    @PutMapping("/{id}")
    public ApiResponse updateResume(
            @PathVariable Long id,
            @Valid @RequestBody ResumeRequest request,
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        Resume updatedResume = new Resume();
        updatedResume.setTitle(request.getTitle());
        updatedResume.setFullName(request.getFullName());
        updatedResume.setEmail(request.getEmail());
        updatedResume.setPhone(request.getPhone());
        updatedResume.setSummary(request.getSummary());

        Resume resume = resumeService.updateResume(
                id,
                updatedResume,
                user
        );

        return new ApiResponse(
                true,
                "Resume updated successfully",
                resume
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteResume(
            @PathVariable Long id,
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        resumeService.deleteResume(id, user);

        return new ApiResponse(
                true,
                "Resume deleted successfully",
                null
        );
    }
}