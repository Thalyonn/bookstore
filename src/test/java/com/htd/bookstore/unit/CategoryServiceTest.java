package com.htd.bookstore.unit;

import com.htd.bookstore.model.Category;
import com.htd.bookstore.repository.CategoryRepository;
import com.htd.bookstore.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;

    @Test
    void findCategoryByNameExists() {
        Category expectedCategory = new Category();
        expectedCategory.setName("Fantasy");
        when(categoryRepository.findByName("Fantasy")).thenReturn(Optional.of(expectedCategory));

        Category result = categoryService.findByName("Fantasy");

        assertNotNull(result);
        assertEquals("Fantasy", result.getName());
        verify(categoryRepository).findByName("Fantasy");
    }
    @Test
    void findCategoryByNameNotExists() {
        Category expectedCategory = new Category();
        expectedCategory.setName("History");
        when(categoryRepository.findByName("History")).thenReturn(Optional.empty());

        Category result = categoryService.findByName("History");

        assertNull(result);
    }
}
