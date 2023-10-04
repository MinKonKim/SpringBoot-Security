package com.cos.security1.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private String emil;
    private String role;

    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;
}
