package com.app.Repository;

import com.app.Entity.category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryRepository extends JpaRepository<category,Integer> {
    Page<category> findAll(Pageable pageable);
}
