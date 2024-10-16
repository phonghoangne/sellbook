package com.app.service;

import com.app.Entity.account;
import com.app.Entity.bill;
import com.app.Entity.book;
import com.app.Entity.cart;

import java.util.List;
import java.util.Optional;

public interface cartService {
    cart addItem(cart item);
    List<cart> findAll();
    cart findByBook(book book);
    Optional<cart> findById(Integer id);
    cart findByBookAndAccount(book book, account account);
    List<cart>findByAccount(account account);
    Integer findMaxId( );
    void remove(book bookId, account accountId);
}
