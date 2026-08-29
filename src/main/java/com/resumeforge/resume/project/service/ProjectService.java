package com.resumeforge.resume.project.service;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.resume.project.dto.ProjectRequest;
import com.resumeforge.resume.project.entity.Project;
import com.resumeforge.resume.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(
            Resume resume,
            ProjectRequest request) {

        Project project = new Project();

        mapRequest(project, request);
        project.setResume(resume);

        return projectRepository.save(project);
    }

    public List<Project> getProjects(Resume resume) {
        return projectRepository.findByResume(resume);
    }

    public Project updateProject(
            Long id,
            Resume resume,
            ProjectRequest request) {

        Project project = projectRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        mapRequest(project, request);

        return projectRepository.save(project);
    }

    public void deleteProject(Long id, Resume resume) {

        Project project = projectRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        projectRepository.delete(project);
    }

    private void mapRequest(
            Project project,
            ProjectRequest request) {

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setTechnologies(request.getTechnologies());
        project.setProjectUrl(request.getProjectUrl());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
    }
}