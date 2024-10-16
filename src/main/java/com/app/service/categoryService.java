package com.app.service;

import com.app.Entity.cart;
import com.app.Entity.category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface categoryService {
    List<category> findAll();

    Optional<category> findById(Integer id);

    category save(category category);

//    List<cart> findByBill()
}
