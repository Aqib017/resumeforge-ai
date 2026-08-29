package com.resumeforge.resume.experience.service;

import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.experience.dto.ExperienceRequest;
import com.resumeforge.resume.experience.entity.Experience;
import com.resumeforge.resume.experience.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public Experience addExperience(Resume resume, ExperienceRequest request) {

        Experience experience = new Experience();

        experience.setCompany(request.getCompany());
        experience.setJobTitle(request.getJobTitle());
        experience.setLocation(request.getLocation());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCurrentlyWorking(request.isCurrentlyWorking());
        experience.setDescription(request.getDescription());
        experience.setResume(resume);

        return experienceRepository.save(experience);
    }

    public List<Experience> getExperiences(Resume resume) {
        return experienceRepository.findByResume(resume);
    }

    public Experience updateExperience(
            Long id,
            Resume resume,
            ExperienceRequest request) {

        Experience experience = experienceRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Experience not found"));

        experience.setCompany(request.getCompany());
        experience.setJobTitle(request.getJobTitle());
        experience.setLocation(request.getLocation());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCurrentlyWorking(request.isCurrentlyWorking());
        experience.setDescription(request.getDescription());

        return experienceRepository.save(experience);
    }

    public void deleteExperience(Long id, Resume resume) {

        Experience experience = experienceRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Experience not found"));

        experienceRepository.delete(experience);
    }
}