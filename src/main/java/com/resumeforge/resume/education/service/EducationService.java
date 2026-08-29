package com.resumeforge.resume.education.service;
import com.resumeforge.resume.education.dto.EducationRequest;
import com.resumeforge.resume.education.entity.Education;
import com.resumeforge.resume.education.repository.EducationRepository;
import com.resumeforge.resume.entity.Resume;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public Education addEducation(
            Resume resume,
            EducationRequest request) {

        Education education = new Education();

        mapRequest(education, request);
        education.setResume(resume);

        return educationRepository.save(education);
    }

    public List<Education> getEducations(Resume resume) {
        return educationRepository.findByResume(resume);
    }

    public Education updateEducation(
            Long id,
            Resume resume,
            EducationRequest request) {

        Education education = educationRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Education not found"));

        mapRequest(education, request);

        return educationRepository.save(education);
    }

    public void deleteEducation(Long id, Resume resume) {

        Education education = educationRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Education not found"));

        educationRepository.delete(education);
    }

    private void mapRequest(
            Education education,
            EducationRequest request) {

        education.setInstitution(request.getInstitution());
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setLocation(request.getLocation());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setGrade(request.getGrade());
    }
}