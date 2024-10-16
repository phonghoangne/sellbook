package com.app.Repository;

import com.app.Entity.book;
import com.app.Entity.category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface bookRepository extends JpaRepository<book,Integer> {
    Page<book> findAll(Pageable pageable);
    @Query(value = "select o from book o where o.name like % :name %",nativeQuery = true)
    List<book>findAllByName(@Param("name")String name);

    Page<book> findByCategory(Pageable pageable, category id);

    Page<book> findByNameStartingWith(Pageable pageable,String searName);



}
