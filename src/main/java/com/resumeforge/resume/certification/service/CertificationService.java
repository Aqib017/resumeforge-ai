package com.resumeforge.resume.certification.service;
import com.resumeforge.resume.certification.dto.CertificationRequest;
import com.resumeforge.resume.certification.entity.Certification;
import com.resumeforge.resume.certification.repository.CertificationRepository;
import com.resumeforge.resume.entity.Resume;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;

    public CertificationService(
            CertificationRepository certificationRepository) {
        this.certificationRepository = certificationRepository;
    }

    public Certification addCertification(
            Resume resume,
            CertificationRequest request) {

        Certification certification = new Certification();

        mapRequest(certification, request);
        certification.setResume(resume);

        return certificationRepository.save(certification);
    }

    public List<Certification> getCertifications(Resume resume) {
        return certificationRepository.findByResume(resume);
    }

    public Certification updateCertification(
            Long id,
            Resume resume,
            CertificationRequest request) {

        Certification certification = certificationRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Certification not found"));

        mapRequest(certification, request);

        return certificationRepository.save(certification);
    }

    public void deleteCertification(Long id, Resume resume) {

        Certification certification = certificationRepository
                .findByIdAndResume(id, resume)
                .orElseThrow(() ->
                        new RuntimeException("Certification not found"));

        certificationRepository.delete(certification);
    }

    private void mapRequest(
            Certification certification,
            CertificationRequest request) {

        certification.setName(request.getName());
        certification.setIssuingOrganization(
                request.getIssuingOrganization());
        certification.setIssueDate(request.getIssueDate());
        certification.setCredentialId(request.getCredentialId());
        certification.setCredentialUrl(request.getCredentialUrl());
    }
}