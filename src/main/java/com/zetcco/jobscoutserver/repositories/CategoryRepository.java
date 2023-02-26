package com.zetcco.jobscoutserver.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zetcco.jobscoutserver.domain.Category;

public interface CategoryRepository extends JpaRepository<Category , Long>{

        Optional<Category> findByNameIgnoreCase(String name); 
        
        List<Category> findByNameContainingIgnoreCase(String name);
}
