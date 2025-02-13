package com.f5antos.tienda.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.f5antos.tienda.model.Category;
import com.f5antos.tienda.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategory(Long categoryID) {
        return categoryRepository.findById(categoryID)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID: " + categoryID + " NOT found."));
    }

    @Override
    public String createCategory(Category category) {
        categoryRepository.save(category);
        return "Category created.";
    }

    @Override
    public String deleteCategory(Long categoryID) {
        Category category = findCategory(categoryID); // Si no existe, lanza excepción automáticamente
        categoryRepository.delete(category);
        return "Category with ID: " + categoryID + " deleted.";
    }

    @Override
    public String updateCategory(Long categoryID, Category newInfo) {
        Category existingCategory = findCategory(categoryID); // Si no existe, lanza excepción automáticamente
        existingCategory.setCategoryName(newInfo.getCategoryName());
        categoryRepository.save(existingCategory);
        return "Category with ID: " + categoryID + " updated.";
    }
}
