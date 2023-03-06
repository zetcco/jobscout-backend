package com.zetcco.jobscoutserver.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    
    List<JobPost> findByTitleContainingIgnoreCase(String name);

    Optional<List<JobPost>> findByType(JobPostType type);

    Optional<List<JobPost>> findByStatus(JobPostStatus status);

    @Query(value = """
        SELECT 
            new JobPost
                (   jp.id, 
                    jp.timestamp,
                    jp.dueDate,
                    jp.title,
                    jp.description,
                    jp.type,
                    jp.urgent,
                    jp.status,
                    new Category(jp.category.id, jp.category.name, jp.category.description),
                    new JobCreator(
                        jp.jobCreator.id,
                        jp.jobCreator.email,
                        jp.jobCreator.role,
                        jp.jobCreator.displayPicture
                    )
                ) FROM JobPost jp
            """)
    Page<JobPost> getAll(Pageable page);

}