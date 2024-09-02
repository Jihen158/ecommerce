package com.example.ecommerce.services;

import com.example.ecommerce.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category saveCategory(Category category);

    Category updateCategory(Category category, Long id );

    void deleteCategoryById(Long id);
}
