package com.app.service;

import com.app.Entity.account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface accountService {
    List<account>findAll();
    Optional<account> findByUsernameAndPassword(String username, String password);
    Optional<account> findByUsername(String username);
    Optional<account>findByEmail(String email);
    Optional<account>findById(Integer id);
    Page<account> findAll(Pageable pageable);

    account save(account account);

    void delete (account account);
}
