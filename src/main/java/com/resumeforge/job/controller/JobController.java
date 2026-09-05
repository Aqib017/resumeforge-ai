package com.resumeforge.job.controller;
import com.resumeforge.common.response.ApiResponse;
import com.resumeforge.job.dto.JobRequest;
import com.resumeforge.job.dto.JobResponse;
import com.resumeforge.job.service.JobService;
import com.resumeforge.user.entity.User;
import com.resumeforge.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createJob(
            @Valid @RequestBody JobRequest request,
            Authentication authentication) {

        User user = userService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobResponse response = jobService.createJob(request, user);

        return new ApiResponse(
                true,
                "Job created successfully",
                response
        );
    }

    @GetMapping
    public ApiResponse getUserJobs(
            Authentication authentication) {

        User user = userService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<JobResponse> jobs = jobService.getUserJobs(user);

        return new ApiResponse(
                true,
                "Jobs fetched successfully",
                jobs
        );
    }

    @GetMapping("/{id}")
    public ApiResponse getJob(
            @PathVariable Long id,
            Authentication authentication) {

        User user = userService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobResponse response = jobService.getJob(id, user);

        return new ApiResponse(
                true,
                "Job fetched successfully",
                response
        );
    }

    @PutMapping("/{id}")
    public ApiResponse updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request,
            Authentication authentication) {

        User user = userService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobResponse response = jobService.updateJob(id, request, user);

        return new ApiResponse(
                true,
                "Job updated successfully",
                response
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteJob(
            @PathVariable Long id,
            Authentication authentication) {

        User user = userService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        jobService.deleteJob(id, user);

        return new ApiResponse(
                true,
                "Job deleted successfully",
                null
        );
    }
}