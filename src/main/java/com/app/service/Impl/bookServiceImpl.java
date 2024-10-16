package com.app.service.Impl;

import com.app.Entity.book;
import com.app.Entity.category;
import com.app.Repository.bookRepository;
import com.app.service.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class bookServiceImpl implements bookService {

    @Autowired
    bookRepository bookRepository;

    @Override
    public book save(book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<book> findByName(String name) {
        return bookRepository.findAllByName(name);
    }

    @Override
    public Page<book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public List<book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<book> findByCategory(Pageable pageable, category categoryId) {
        return bookRepository.findByCategory(pageable,categoryId);
    }

    @Override
    public Page<book> findBysearchName(Pageable pageable, String searchKey) {
        return bookRepository.findByNameStartingWith(pageable,searchKey);
    }

    @Override
    public void delete(book book) {
        bookRepository.delete(book);
    }
}
