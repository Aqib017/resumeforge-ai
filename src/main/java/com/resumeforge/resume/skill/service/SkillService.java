package com.resumeforge.resume.skill.service;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.skill.dto.SkillRequest;
import com.resumeforge.resume.skill.entity.Skill;
import com.resumeforge.resume.skill.repository.SkillRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill addSkill(Resume resume, SkillRequest request) {

        Skill skill = new Skill();

        mapRequest(skill, request);
        skill.setResume(resume);

        return skillRepository.save(skill);
    }

    public List<Skill> getSkills(Resume resume) {
        return skillRepository.findByResume(resume);
    }

    public Skill updateSkill(
            Long id,
            Resume resume,
            SkillRequest request) {

        Skill skill = skillRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found"));

        mapRequest(skill, request);

        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id, Resume resume) {

        Skill skill = skillRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found"));

        skillRepository.delete(skill);
    }

    private void mapRequest(
            Skill skill,
            SkillRequest request) {

        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        skill.setProficiency(request.getProficiency());
    }
}