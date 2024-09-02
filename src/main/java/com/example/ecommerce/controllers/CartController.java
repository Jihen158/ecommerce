package com.example.ecommerce.controllers;


import com.example.ecommerce.entities.Cart;
import com.example.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }


    @PostMapping("/{id}/add")
    public Cart addToCart(@PathVariable Long id, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.addToCart(id,productId,quantity);
    }

    @PostMapping("/{id}/remove")
    public Cart removeFromCart(@PathVariable Long id, @RequestParam Long productId) {
        return cartService.removeFromCart(id,productId);
    }

    @PostMapping("/{id}/update")
    public Cart updateCartItem(@PathVariable Long id, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.updateCartItem(id,productId, quantity);
    }
}
