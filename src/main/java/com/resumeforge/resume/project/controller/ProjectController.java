package com.resumeforge.resume.project.controller;
import com.resumeforge.common.response.ApiResponse;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.project.dto.ProjectRequest;
import com.resumeforge.resume.project.entity.Project;
import com.resumeforge.resume.project.service.ProjectService;
import com.resumeforge.resume.repository.ResumeRepository;
import com.resumeforge.user.entity.User;
import com.resumeforge.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resumes/{resumeId}/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ProjectController(
            ProjectService projectService,
            ResumeRepository resumeRepository,
            UserRepository userRepository) {

        this.projectService = projectService;
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
    public ApiResponse addProject(
            @PathVariable Long resumeId,
            @Valid @RequestBody ProjectRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Project project = projectService.addProject(resume, request);

        return new ApiResponse(
                true,
                "Project added successfully",
                project
        );
    }

    @GetMapping
    public ApiResponse getProjects(
            @PathVariable Long resumeId,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        List<Project> projects = projectService.getProjects(resume);

        return new ApiResponse(
                true,
                "Projects fetched successfully",
                projects
        );
    }

    @PutMapping("/{id}")
    public ApiResponse updateProject(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Project project = projectService.updateProject(
                id, resume, request);

        return new ApiResponse(
                true,
                "Project updated successfully",
                project
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteProject(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        projectService.deleteProject(id, resume);

        return new ApiResponse(
                true,
                "Project deleted successfully",
                null
        );
    }
}