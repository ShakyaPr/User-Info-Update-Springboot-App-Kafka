package com.shakya.userinfochange.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    @Id
//    @Column(name = "id")
    private int id;
//    @Column(name = "name")
    private String name;
//    @Column(name = "email")
    private String email;
//    @Autowired
//    @OneToOne(cascade = CascadeType.ALL)
//    private Products product;
//
//    public Products getProduct() {
//        return product;
//    }
//
//    public void setProduct(Products product) {
//        this.product = product;
//    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Entity
    public static class Products {
//        @Column(name ="product_id")
        @Id
        private int productId;
//        @Column(name ="product_name")
        private String productName;

        public int getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }
    }

}
