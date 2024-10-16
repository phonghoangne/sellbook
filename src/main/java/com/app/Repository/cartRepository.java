package com.app.Repository;

import com.app.Entity.account;
import com.app.Entity.bill;
import com.app.Entity.book;
import com.app.Entity.cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface cartRepository extends JpaRepository<cart,Integer> {
    Page<cart> findAll(Pageable pageable);
    cart findByBook(book book);

    cart findByBookAndAccount(book book, account account);
    Optional<cart> findById(Integer id);

    List<cart> findByAccount(account account);

    @Query("select max(o.Id) from cart o ")
    Integer findMaxid();

    Integer deleteByBookAndAccount(book bookId, account accountId);

}
