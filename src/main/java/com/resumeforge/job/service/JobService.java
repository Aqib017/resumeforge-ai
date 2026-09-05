package com.resumeforge.job.service;
import com.resumeforge.job.dto.JobRequest;
import com.resumeforge.job.dto.JobResponse;
import com.resumeforge.job.entity.Job;
import com.resumeforge.job.repository.JobRepository;
import com.resumeforge.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    @Transactional
    public JobResponse createJob(JobRequest request, User user) {

        Job job = Job.builder()
                .title(request.getTitle())
                .companyName(request.getCompanyName())
                .location(request.getLocation())
                .description(request.getDescription())
                .sourceUrl(request.getSourceUrl())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    @Transactional(readOnly = true)
    public List<JobResponse> getUserJobs(User user) {

        return jobRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public JobResponse getJob(Long id, User user) {

        Job job = jobRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        return mapToResponse(job);
    }

    @Transactional
    public JobResponse updateJob(Long id, JobRequest request, User user) {

        Job job = jobRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setLocation(request.getLocation());
        job.setDescription(request.getDescription());
        job.setSourceUrl(request.getSourceUrl());

        return mapToResponse(job);
    }

    @Transactional
    public void deleteJob(Long id, User user) {

        Job job = jobRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        jobRepository.delete(job);
    }

    private JobResponse mapToResponse(Job job) {

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .companyName(job.getCompanyName())
                .location(job.getLocation())
                .description(job.getDescription())
                .sourceUrl(job.getSourceUrl())
                .createdAt(job.getCreatedAt())
                .build();
    }
}