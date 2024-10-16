package com.app.Repository;

import com.app.Entity.account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface accountRepository extends JpaRepository<account, Integer> {
    Optional<account> findByUsernameAndPassword(String username, String password);
    Optional<account> findByUsername(String username);
    Optional<account>findByEmail(String email);


}
