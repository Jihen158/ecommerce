package com.example.ecommerce.services.impl;

import com.example.ecommerce.entities.Cart;
import com.example.ecommerce.entities.CartItem;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.repositories.CartItemRepository;
import com.example.ecommerce.repositories.CartRepository;
import com.example.ecommerce.services.CartService;
import com.example.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart addToCart(Long id, Long productId, int quantity) {
        Cart cart = getCartById(id);
        if (Objects.isNull(cart)) {
            cartRepository.save(Cart.builder().total(Double.valueOf(0)).build());
        }
        Product product = productService.getProductById(productId);
        if (Objects.nonNull(product) && product.getQuantity() >= quantity) {
            CartItem cartItem = cartItemRepository.save(CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .price(product.getPrice() * quantity).build());

            cart = cartRepository.save(cart.toBuilder().total(cart.getTotal() + cartItem.getPrice()).build());
        }

        return cart;
    }

    @Override
    public Cart removeFromCart(Long id, Long productId) {
        Cart cart = getCartById(id);
        if (Objects.nonNull(cart)) {
            List<CartItem> cartItems = cart.getCartItems();
            cartItems.removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));

            cart = cartRepository.save(cart.toBuilder().cartItems(cartItems).total(cartItems.stream().mapToDouble(CartItem::getPrice).sum()).build());
        }

        return cart;
    }

    @Override
    public Cart updateCartItem(Long id, Long productId, int quantity) {
        Cart cart = getCartById(id);
        if (Objects.nonNull(cart)) {
            cart.getCartItems().stream().filter(cartItem -> cartItem.getProduct().getId().equals(productId)).
                    forEach(cartItem -> {
                        cartItemRepository.save(cartItem.toBuilder().quantity(quantity).price(cartItem.getProduct().getPrice() * quantity).build());
                    });
            cart = cartRepository.save(cart.toBuilder().total(cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum()).build());
        }

        return cart;
    }
}
