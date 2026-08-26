package com.resumeforge.resume.service;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume createResume(Resume resume) {
        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    public Optional<Resume> getResumeById(Long id) {
        return resumeRepository.findById(id);
    }

    public Resume updateResume(Long id, Resume updatedResume) {
        Resume existingResume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));

        existingResume.setTitle(updatedResume.getTitle());
        existingResume.setFullName(updatedResume.getFullName());
        existingResume.setEmail(updatedResume.getEmail());
        existingResume.setPhone(updatedResume.getPhone());
        existingResume.setSummary(updatedResume.getSummary());

        return resumeRepository.save(existingResume);
    }

    public void deleteResume(Long id) {
        resumeRepository.deleteById(id);
    }
}