package com.resumeforge.resume.controller;
import com.resumeforge.common.response.ApiResponse;
import com.resumeforge.resume.dto.ResumeRequest;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createResume(@Valid @RequestBody ResumeRequest request) {

        Resume resume = new Resume();
        resume.setTitle(request.getTitle());
        resume.setFullName(request.getFullName());
        resume.setEmail(request.getEmail());
        resume.setPhone(request.getPhone());
        resume.setSummary(request.getSummary());

        Resume savedResume = resumeService.createResume(resume);

        return new ApiResponse(
                true,
                "Resume created successfully",
                savedResume
        );
    }

    @GetMapping
    public ApiResponse getAllResumes() {

        List<Resume> resumes = resumeService.getAllResumes();

        return new ApiResponse(
                true,
                "Resumes fetched successfully",
                resumes
        );
    }

    @GetMapping("/{id}")
    public ApiResponse getResumeById(@PathVariable Long id) {

        Resume resume = resumeService.getResumeById(id)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found with id: " + id));

        return new ApiResponse(
                true,
                "Resume fetched successfully",
                resume
        );
    }

    @PutMapping("/{id}")
    public ApiResponse updateResume(
            @PathVariable Long id,
            @Valid @RequestBody ResumeRequest request) {

        Resume updatedResume = new Resume();
        updatedResume.setTitle(request.getTitle());
        updatedResume.setFullName(request.getFullName());
        updatedResume.setEmail(request.getEmail());
        updatedResume.setPhone(request.getPhone());
        updatedResume.setSummary(request.getSummary());

        Resume resume = resumeService.updateResume(id, updatedResume);

        return new ApiResponse(
                true,
                "Resume updated successfully",
                resume
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteResume(@PathVariable Long id) {

        resumeService.deleteResume(id);

        return new ApiResponse(
                true,
                "Resume deleted successfully",
                null
        );
    }
}