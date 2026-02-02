package com.htd.bookstore.dto;

import com.htd.bookstore.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long categoryId;
    private String name;

    public CategoryResponse(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
    }
}

