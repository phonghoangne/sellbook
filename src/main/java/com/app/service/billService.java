package com.app.service;

import com.app.Entity.bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface billService {
    Page<bill> findAll(Pageable pageable);
    List<bill> findAll();
    bill save(bill bill);
    Integer findIdMax();
    bill findById(Integer id);

}
