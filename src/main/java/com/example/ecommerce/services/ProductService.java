package com.example.ecommerce.services;

import com.example.ecommerce.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product);

    Product updateProduct(Product product, Long id );

    void deleteProductById(Long id);

    Product linkProductToCategory(Long productId ,Long categoryId);
    Product unlinkProductToCategory(Long productId);
}

