package com.f5antos.tienda.services;

import java.util.List;
import java.util.Optional;

import com.f5antos.tienda.model.Category;
import com.f5antos.tienda.payload.CategoryDTO;
import com.f5antos.tienda.payload.CategoryResponse;

public interface CategoryService {
	CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	String deleteCategory(Long categoryID);
	CategoryDTO findCategory(Long categoryID);
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	String updateCategory(Long categoryID, Category newCategory);
}
