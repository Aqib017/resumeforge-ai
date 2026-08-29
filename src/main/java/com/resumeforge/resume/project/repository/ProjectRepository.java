package com.resumeforge.resume.project.repository;

import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findByResume(Resume resume);

    Optional<Project> findByIdAndResume(Long id, Resume resume);
}
