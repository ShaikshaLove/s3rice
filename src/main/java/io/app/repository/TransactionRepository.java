package io.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.app.dto.Transaction;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
