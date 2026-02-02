package com.htd.bookstore.controller;

import com.htd.bookstore.dto.CategoryResponse;
import com.htd.bookstore.model.Category;
import com.htd.bookstore.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get all categories.
     *
     * @return list of category responses
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CategoryResponse> responses = categories.stream()
                .map(CategoryResponse::new)
                .toList();

        return ResponseEntity.ok(responses);
    }



}

