package com.example.ecommerce.services;

import com.example.ecommerce.entities.Cart;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CartService {

    Cart getCartById(Long id);

    Cart addToCart(Long id,Long productId, int quantity);

    Cart removeFromCart(Long id, Long productId);

    Cart updateCartItem(Long id, Long productId, int quantity);


}

