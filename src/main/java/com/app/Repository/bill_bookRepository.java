package com.app.Repository;

import com.app.Entity.bill;
import com.app.Entity.bill_detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface bill_bookRepository extends JpaRepository<bill_detail, Integer> {
    List<bill_detail> findAllByBill(bill bill);
}
