package com.Finance_Planning_Application.service;





import com.Finance_Planning_Application.entity.Transaction;
import com.Finance_Planning_Application.entity.User;
import com.Finance_Planning_Application.repoistory.TransactionRepository;
import com.Finance_Planning_Application.repoistory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email already exists";
        }
        userRepository.save(user);
        return "Success";
    }


    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(User user) {
        return transactionRepository.findByUser(user);
    }


    public double getTotalIncome(User user) {
        return transactionRepository.getTotalIncomeByUser(user);
    }


    public double getTotalExpenses(User user) {
        return transactionRepository.getTotalExpensesByUser(user);
    }


    public double getBalance(User user) {
        return getTotalIncome(user) - getTotalExpenses(user);
    }
}
