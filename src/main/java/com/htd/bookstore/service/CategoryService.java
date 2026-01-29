package com.htd.bookstore.service;

import com.htd.bookstore.model.Category;
import com.htd.bookstore.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    @Transactional
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }
    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }
}

