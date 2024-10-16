package com.app.service.Impl;

import com.app.Entity.account;
import com.app.Repository.accountRepository;
import com.app.service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class accountServiceImpl implements accountService {
    @Autowired
    accountRepository accountRepository;
    @Override
    public List<account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<account> findByUsernameAndPassword(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username,password);
    }

    @Override
    public Optional<account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public Page<account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public account save(account account) {
        return accountRepository.save(account);
    }

    @Override
    public void delete(account  account) {
         accountRepository.delete(account);
    }
}
