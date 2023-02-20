package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zetcco.jobscoutserver.domain.Category;

public interface CategoryRepository extends JpaRepository<Category , Long>{
    
}
