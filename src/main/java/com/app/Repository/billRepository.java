package com.app.Repository;

import com.app.Entity.bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface billRepository extends JpaRepository<bill,Integer> {
    Page<bill>findAll(Pageable pageable);
    @Query("select max(o.Id) from bill  o ")
    Integer findByMaxId();
    Optional<bill> findById(Integer id);
}
