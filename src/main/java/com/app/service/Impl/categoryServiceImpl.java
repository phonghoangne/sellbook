package com.app.service.Impl;

import com.app.Entity.category;
import com.app.Repository.categoryRepository;
import com.app.service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class categoryServiceImpl implements categoryService {
    @Autowired
    categoryRepository categoryRepository;

    @Override
    public List<category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public category save(category category) {
        return categoryRepository.save(category);
    }
}
