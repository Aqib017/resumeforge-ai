package com.resumeforge.resume.education.repository;

import com.resumeforge.resume.education.entity.Education;
import com.resumeforge.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findByResume(Resume resume);

    Optional<Education> findByIdAndResume(Long id, Resume resume);
}