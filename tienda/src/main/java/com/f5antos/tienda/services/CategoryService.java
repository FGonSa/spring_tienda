package com.f5antos.tienda.services;

import java.util.List;
import java.util.Optional;

import com.f5antos.tienda.model.Category;

public interface CategoryService {
	List<Category> getAllCategories();
	String deleteCategory(Long categoryID);
	Category findCategory(Long categoryID);
	String createCategory(Category category);
	String updateCategory(Long categoryID, Category newCategory);
}
