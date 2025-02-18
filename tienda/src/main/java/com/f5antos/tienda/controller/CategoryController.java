package com.f5antos.tienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.f5antos.tienda.config.AppConstants;
import com.f5antos.tienda.model.Category;
import com.f5antos.tienda.payload.CategoryDTO;
import com.f5antos.tienda.payload.CategoryResponse;
import com.f5antos.tienda.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api") // Prefijo común para las rutas
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_DEFAULT) Integer pageNumber, 
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder){
		CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(categoryResponse);
	}
	
	@GetMapping("/public/categories/{categoryID}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long categoryID) {
        return ResponseEntity.ok(categoryService.findCategory(categoryID));
    }
	
	@PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }
	
	@DeleteMapping("/admin/categories/{categoryID}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryID) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryID));
    }
	
	@PutMapping("/admin/categories/{categoryID}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryID, @RequestBody Category newCategoryInfo) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryID, newCategoryInfo));
    }
	
}
