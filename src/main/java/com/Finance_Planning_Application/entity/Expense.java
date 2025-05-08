package com.Finance_Planning_Application.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

