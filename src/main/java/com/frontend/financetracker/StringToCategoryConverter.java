package com.frontend.financetracker;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    private final CategoryService categoryService;

    public StringToCategoryConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Category convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        try {
            Long id = Long.parseLong(source);
            return categoryService.getCategoryById(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid category ID: " + source);
        }
    }
}
