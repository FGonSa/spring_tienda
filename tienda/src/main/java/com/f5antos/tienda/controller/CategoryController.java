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
import org.springframework.web.bind.annotation.RestController;

import com.f5antos.tienda.model.Category;
import com.f5antos.tienda.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api") // Prefijo común para las rutas
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/public/categories")
	public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/public/categories/{categoryID}")
	public ResponseEntity<Category> getCategory(@PathVariable Long categoryID) {
        return ResponseEntity.ok(categoryService.findCategory(categoryID));
    }
	
	@PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
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
