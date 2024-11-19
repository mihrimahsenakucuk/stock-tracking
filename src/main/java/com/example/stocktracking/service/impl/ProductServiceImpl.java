package com.example.stocktracking.service.impl;

import com.example.stocktracking.entity.Product;
import com.example.stocktracking.repository.ProductRepository;
import com.example.stocktracking.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int id, Product updateProduct) {
        Product productControl= productRepository.findById(id).orElseThrow(() -> new RuntimeException("Urun bulunamadı"));
        productControl.setName(updateProduct.getName());
        productControl.setPrice(updateProduct.getPrice());
        productControl.setStockQuantity(updateProduct.getStockQuantity());

        return productRepository.save(productControl);
    }

    @Override
    public void deleteProduct(int productId) {
        Product product= productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Urun bulunamadı"));
        productRepository.delete(product);
    }
}
