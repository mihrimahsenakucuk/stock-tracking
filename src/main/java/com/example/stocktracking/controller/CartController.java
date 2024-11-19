package com.example.stocktracking.controller;

import com.example.stocktracking.entity.Cart;
import com.example.stocktracking.entity.Customer;
import com.example.stocktracking.entity.Product;
import com.example.stocktracking.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public Cart getToCart(@PathVariable int id){
       return cartService.getCart(id);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable int id, @RequestBody Cart cart){
        return cartService.updateCart(id,cart);
    }

    @DeleteMapping("/deleteAll/{id}")
    public void  deleteCart(@PathVariable int id){
         cartService.emptyCart(id);
    }

    @PostMapping("/addProductToCarts/{id}") //id olmamalı burada
    public ResponseEntity<Cart> addProductToCart(
            @PathVariable int id,
            @RequestBody Map<String, Object> request) {
        Product product = new ObjectMapper().convertValue(request.get("product"), Product.class);
        Customer customer = new ObjectMapper().convertValue(request.get("customer"), Customer.class);
        Cart addProductToCart = cartService.addProductToCart(id, product, customer);

        return ResponseEntity.ok(addProductToCart);
    }


    @DeleteMapping("/delete/{id}")
    public void removeProductFromCart(@PathVariable int id,@RequestBody Product product){
        cartService.removeProductFromCart(id,product);
    }
}
