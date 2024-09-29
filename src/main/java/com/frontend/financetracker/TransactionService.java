package com.frontend.financetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getFilteredTransactions(LocalDate startDate, LocalDate endDate, String type, String category) {
        return transactionRepository.findFilteredTransactions(startDate, endDate, type, category);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public BigDecimal calculateTotalByType(String type) {
        return transactionRepository.findByType(type)
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Get a transaction by ID for editing
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction Id: " + id));
    }

    // Update an existing transaction
    public void updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction Id: " + id));

        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setDescription(updatedTransaction.getDescription());
        existingTransaction.setCategory(updatedTransaction.getCategory());
        existingTransaction.setTransactionDate(updatedTransaction.getTransactionDate());

        transactionRepository.save(existingTransaction); // Save the updated transaction
    }

    // Delete a transaction by ID
    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getLastTenTransactions() {
        return transactionRepository.findTop10ByOrderByTransactionDateDesc();
    }
}






