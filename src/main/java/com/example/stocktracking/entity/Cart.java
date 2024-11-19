package com.example.stocktracking.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart extends BaseEntity{


     private double totalPrice;

     @ManyToOne
     @JoinColumn(name = "customer_id")
     private Customer customer;

     @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Product> products = new ArrayList<>();

     public double getTotalPrice() {
          return totalPrice;
     }

     public void setTotalPrice(double totalPrice) {
          this.totalPrice = totalPrice;
     }

     public Customer getCustomer() {
          return customer;
     }

     public void setCustomer(Customer customer) {
          this.customer = customer;
     }

     public List<Product> getProducts() {
          return products;
     }

     public void setProducts(List<Product> products) {
          this.products = products;
     }
}
