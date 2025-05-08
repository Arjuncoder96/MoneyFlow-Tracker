package com.Finance_Planning_Application.repoistory;

import com.Finance_Planning_Application.entity.Transaction;
import com.Finance_Planning_Application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = 'Income'")
    double getTotalIncomeByUser(@Param("user") User user);


    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = 'Expense'")
    double getTotalExpensesByUser(@Param("user") User user);
}
