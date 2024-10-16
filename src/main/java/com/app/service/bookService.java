package com.app.service;

import com.app.Entity.book;
import com.app.Entity.category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface bookService {
    book save(book book);
    Optional<book> findById(Integer id);
    List<book> findByName(String name);
    Page<book>findAll(Pageable pageable);
    List<book>findAll();

    Page<book>findByCategory(Pageable pageable, category categoryId);

    Page<book>findBysearchName(Pageable pageable,String searchKey);

    void delete(book book);
}
