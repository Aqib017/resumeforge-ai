package com.resumeforge.resume.skill.controller;
import com.resumeforge.common.response.ApiResponse;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.repository.ResumeRepository;
import com.resumeforge.resume.skill.dto.SkillRequest;
import com.resumeforge.resume.skill.entity.Skill;
import com.resumeforge.resume.skill.service.SkillService;
import com.resumeforge.user.entity.User;
import com.resumeforge.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resumes/{resumeId}/skills")
public class SkillController {

    private final SkillService skillService;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public SkillController(
            SkillService skillService,
            ResumeRepository resumeRepository,
            UserRepository userRepository) {

        this.skillService = skillService;
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
    public ApiResponse addSkill(
            @PathVariable Long resumeId,
            @Valid @RequestBody SkillRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Skill skill = skillService.addSkill(resume, request);

        return new ApiResponse(
                true,
                "Skill added successfully",
                skill
        );
    }

    @GetMapping
    public ApiResponse getSkills(
            @PathVariable Long resumeId,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        List<Skill> skills = skillService.getSkills(resume);

        return new ApiResponse(
                true,
                "Skills fetched successfully",
                skills
        );
    }

    @PutMapping("/{id}")
    public ApiResponse updateSkill(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            @Valid @RequestBody SkillRequest request,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        Skill skill = skillService.updateSkill(
                id, resume, request);

        return new ApiResponse(
                true,
                "Skill updated successfully",
                skill
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteSkill(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            Authentication authentication) {

        Resume resume = getResume(resumeId, authentication);

        skillService.deleteSkill(id, resume);

        return new ApiResponse(
                true,
                "Skill deleted successfully",
                null
        );
    }
}