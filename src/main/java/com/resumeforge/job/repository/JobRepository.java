package com.resumeforge.job.repository;
import com.resumeforge.job.entity.Job;
import com.resumeforge.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByUser(User user);

    Optional<Job> findByIdAndUser(Long id, User user);
}