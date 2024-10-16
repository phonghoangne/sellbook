package com.app.service.Impl;

import com.app.Entity.bill;
import com.app.Repository.billRepository;
import com.app.service.billService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class billServiceImpl implements billService {
    @Autowired
    billRepository billRepository;

    @Override
    public Page<bill> findAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    @Override
    public List<bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public bill save(bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Integer findIdMax() {
        return billRepository.findByMaxId();
    }

    @Override
    public bill findById(Integer id) {
        return billRepository.findById(id).get();
    }
}
