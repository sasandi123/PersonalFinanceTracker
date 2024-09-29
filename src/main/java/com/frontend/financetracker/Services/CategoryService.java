package com.frontend.financetracker.Services;

import com.frontend.financetracker.Models.Category;
import com.frontend.financetracker.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);

    }
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
    }
    public void updateCategory(Category category) {
        categoryRepository.save(category);

    }
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
