package com.resumeforge.resume.certification.repository;

import com.resumeforge.resume.certification.entity.Certification;
import com.resumeforge.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificationRepository
        extends JpaRepository<Certification, Long> {

    List<Certification> findByResume(Resume resume);

    Optional<Certification> findByIdAndResume(
            Long id,
            Resume resume);
}