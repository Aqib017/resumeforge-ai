package com.resumeforge.resume.skill.repository;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill,Long> {

    List<Skill> findByResume(Resume resume);

    Optional<Skill> findByIdAndResume(Long id, Resume resume);
}
