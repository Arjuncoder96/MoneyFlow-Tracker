package com.Finance_Planning_Application.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private String source;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

