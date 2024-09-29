package com.frontend.financetracker;

import com.frontend.financetracker.Models.Category;
import com.frontend.financetracker.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<String, Category> {
    @Autowired
    private CategoryService categoryService;

    @Override
    public Category convert(String source) {
        Long id = Long.parseLong(source);
        return categoryService.getCategoryById(id);
    }
}

