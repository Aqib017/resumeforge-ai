package com.resumeforge.resume.experience.repository;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.experience.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    List<Experience> findByResume(Resume resume);

    Optional<Experience> findByIdAndResume(Long id, Resume resume);
}