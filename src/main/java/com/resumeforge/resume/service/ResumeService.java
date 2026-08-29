package com.resumeforge.resume.service;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.repository.ResumeRepository;
import com.resumeforge.user.entity.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume createResume(Resume resume, User user) {
        resume.setUser(user);
        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResumes() {

        return resumeRepository.findAll();
    }

    public Optional<Resume> getResumeById(Long id) {

        return resumeRepository.findById(id);
    }

    public Resume updateResume(Long id, Resume updatedResume, User user) {

        Resume existingResume = resumeRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));

        existingResume.setTitle(updatedResume.getTitle());
        existingResume.setFullName(updatedResume.getFullName());
        existingResume.setEmail(updatedResume.getEmail());
        existingResume.setPhone(updatedResume.getPhone());
        existingResume.setSummary(updatedResume.getSummary());

        return resumeRepository.save(existingResume);
    }

    public void deleteResume(Long id, User user) {

        Resume resume = resumeRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));

        resumeRepository.delete(resume);
    }

    public List<Resume> getAllResumes(User user) {
        return resumeRepository.findByUser(user);
    }

    public Optional<Resume> getResumeById(Long id, User user) {
        return resumeRepository.findByIdAndUser(id, user);
    }
}