package com.zetcco.jobscoutserver.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.zetcco.jobscoutserver.domain.Category;

public interface CategoryRepository extends JpaRepository<Category , Long>{

        Category findByNameIgnoreCase(String name); 
        
        List<Category> findByNameContainingIgnoreCase(String name);
}
