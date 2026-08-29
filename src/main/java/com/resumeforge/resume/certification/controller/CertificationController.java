package com.resumeforge.resume.certification.controller;
import com.resumeforge.common.response.ApiResponse;
import com.resumeforge.resume.certification.dto.CertificationRequest;
import com.resumeforge.resume.certification.entity.Certification;
import com.resumeforge.resume.certification.service.CertificationService;
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
@RequestMapping("/api/resumes/{resumeId}/certifications")
public class CertificationController {

    private final CertificationService certificationService;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public CertificationController(
            CertificationService certificationService,
            ResumeRepository resumeRepository,
            UserRepository userRepository) {

        this.certificationService = certificationService;
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
    public ApiResponse addCertification(
            @PathVariable Long resumeId,
            @Valid @RequestBody CertificationRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Certification certification =
                certificationService.addCertification(resume, request);

        return new ApiResponse(
                true,
                "Certification added successfully",
                certification
        );
    }

    @GetMapping
    public ApiResponse getCertifications(
            @PathVariable Long resumeId,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        List<Certification> certifications =
                certificationService.getCertifications(resume);

        return new ApiResponse(
                true,
                "Certifications fetched successfully",
                certifications
        );
    }

    @PutMapping("/{id}")
    public ApiResponse updateCertification(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            @Valid @RequestBody CertificationRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Certification certification =
                certificationService.updateCertification(
                        id, resume, request);

        return new ApiResponse(
                true,
                "Certification updated successfully",
                certification
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteCertification(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        certificationService.deleteCertification(id, resume);

        return new ApiResponse(
                true,
                "Certification deleted successfully",
                null
        );
    }
}