package com.frontend.financetracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByType(String type);



@Query("SELECT t FROM Transaction t WHERE " +
        "(:startDate IS NULL OR t.transactionDate >= :startDate) AND " +
        "(:endDate IS NULL OR t.transactionDate <= :endDate) AND " +
        "(:type IS NULL OR t.type = :type) AND " +
        "(:category IS NULL OR t.category.name = :category)")
List<Transaction> findFilteredTransactions(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("type") String type,
        @Param("category") String category);
    List<Transaction> findTop10ByOrderByTransactionDateDesc();
}

