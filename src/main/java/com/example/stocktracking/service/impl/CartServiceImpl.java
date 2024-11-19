package com.example.stocktracking.service.impl;

import com.example.stocktracking.entity.Cart;
import com.example.stocktracking.entity.Customer;
import com.example.stocktracking.entity.Product;
import com.example.stocktracking.repository.CartRepository;
import com.example.stocktracking.repository.ProductRepository;
import com.example.stocktracking.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private  final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart getCart(int cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));
    }

    @Override
    public Cart updateCart(int cartId, Cart updateCart) {
        Cart cart= cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("sepet bulunamadı"));
        cart.setTotalPrice(updateCart.getTotalPrice());
        cart.setProducts(updateCart.getProducts());
        return cartRepository.save(updateCart);

    }

    @Override
    public void emptyCart(int cartId) {
        Cart cart= cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("sepet bulunamadı"));
        cart.getProducts().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart addProductToCart(int cartId, Product product, Customer customer) {
        Cart cart = cartRepository.findById(cartId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setId(cartId);
            newCart.setCustomer(customer);
            return cartRepository.save(newCart);
        });

        if (product.getStockQuantity() <= 0) {
            throw new RuntimeException("Stokta bu ürün yok");
        }
        if (product.getCreatedDate() == null) {
            product.setCreatedDate(LocalDateTime.now());
        }

        product.setCart(cart);
        cart.getProducts().add(product);

        double newTotalPrice = cart.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
        cart.setTotalPrice(newTotalPrice);

        cartRepository.save(cart);
        productRepository.save(product);
        return cart;
    }

    @Override
    public Cart removeProductFromCart(int cartId, Product product) {
        Cart cart= cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("sepet bulunamadı"));
        cart.getProducts().remove(product);
        cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
        return cartRepository.save(cart);

    }
}
