package com.example.stocktracking.service;

import com.example.stocktracking.entity.Product;

public interface ProductService {

    Product getProduct(int productId);

    Product createProduct(Product product);

    Product updateProduct(int productId, Product updateProduct);

    void deleteProduct(int productId);

}
