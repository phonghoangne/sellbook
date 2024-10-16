package com.app.service.Impl;

import com.app.Entity.account;
import com.app.Entity.bill;
import com.app.Entity.book;
import com.app.Entity.cart;
import com.app.Repository.cartRepository;
import com.app.service.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class cartServiceImpl implements cartService {
    @Autowired
    cartRepository cartRepository;

    @Override
    public cart addItem(cart item) {
        cart cartFindId  = cartRepository.findByBookAndAccount(item.getBook(),item.getAccount());
        if(cartFindId == null ){
            return  cartRepository.save(item);
        }else {
            item.setId(cartFindId.getId());
            return cartRepository.save(item);
        }

    }

    @Override
    public List<cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public cart findByBook(book book) {
        return cartRepository.findByBook(book);
    }

    @Override
    public Optional<cart> findById(Integer id) {
        return cartRepository.findById(id);
    }

    @Override
    public cart findByBookAndAccount(book book, account account) {
        return cartRepository.findByBookAndAccount(book,account);
    }

    @Override
    public List<cart> findByAccount(account account) {
        return cartRepository.findByAccount(account);
    }

    @Override
    public Integer findMaxId() {
        return cartRepository.findMaxid();
    }
    @Transactional
    @Override
    public void remove(book bookId, account accountId) {
        cartRepository.deleteByBookAndAccount(bookId,accountId);
    }


}
