package com.app.service.Impl;

import com.app.Entity.bill;
import com.app.Entity.bill_detail;
import com.app.Repository.bill_bookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class bill_bookServiceImpl {
    @Autowired
    bill_bookRepository bill_bookRepository;

    public List<bill_detail> findAllByBill(bill bill){
        return  bill_bookRepository.findAllByBill(bill);
    }
    public bill_detail save(bill_detail bill_detail){
        return  bill_bookRepository.save(bill_detail);
    }
}
