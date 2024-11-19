package com.example.stocktracking.service;

import com.example.stocktracking.entity.Cart;
import com.example.stocktracking.entity.Customer;
import com.example.stocktracking.entity.Product;

public interface CartService {

    Cart getCart(int cartId);
    Cart updateCart(int cartId, Cart updateCart);
    void emptyCart(int cartId);
    Cart addProductToCart(int cartId, Product product, Customer customer);
    Cart removeProductFromCart(int cartId, Product product);

}
