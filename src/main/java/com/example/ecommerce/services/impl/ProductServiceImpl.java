package com.example.ecommerce.services.impl;

import com.example.ecommerce.entities.Category;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.repositories.CategoryRepository;
import com.example.ecommerce.repositories.ProductRepository;
import com.example.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        return productRepository.findById(id).map(value -> productRepository.save(value.toBuilder()
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory()).build())).orElse(null);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product linkProductToCategory(Long productId, Long categoryId) {
        return productRepository.findById(productId).flatMap(value -> categoryRepository.findById(categoryId).map(category1 -> productRepository.save(value.toBuilder()
                .category(category1).build()))).orElse(null);
    }

    @Override
    public Product unlinkProductToCategory(Long productId) {
        return productRepository.findById(productId).map(value ->
                productRepository.save(value.toBuilder().category(null).build())).orElse(null);
    }
}
