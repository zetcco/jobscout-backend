package com.zetcco.jobscoutserver.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zetcco.jobscoutserver.domain.BlogPost;
import com.zetcco.jobscoutserver.domain.support.User;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    Optional<User> findByUser(String user);
    List<BlogPost> findByTimeStampBetween(Date startDate, Date endDate);

    @Query("SELECT bp FROM BlogPost bp LEFT JOIN bp.upvotedUsers u WHERE bp.timeStamp BETWEEN :startDate AND :endDate GROUP BY bp ORDER BY SIZE(bp.upvotedUsers) DESC")
    List<BlogPost> findByTimeStampBetweenAndOrderByUpvotedUsers(Date startDate, Date endDate);
}
