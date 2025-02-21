package com.f5antos.tienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.f5antos.tienda.model.Product;
import com.f5antos.tienda.payload.ProductDTO;
import com.f5antos.tienda.payload.ProductResponse;
import com.f5antos.tienda.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/public/products/{productID}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productID){
		return ResponseEntity.ok(productService.findProduct(productID));
	}
	
	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/public/categories/{categoryID}/products")
	public ResponseEntity<ProductResponse> getAllProductsByCategory(@PathVariable Long categoryID){
		return ResponseEntity.ok(productService.getAllProductsByCategory(categoryID));
	}
	
	@GetMapping("/public/products/keyword/{keyword}")
	public ResponseEntity<ProductResponse> getAllProductsByKeyword(@PathVariable String keyword){
		return ResponseEntity.ok(productService.searchProductByKeyword(keyword));
	}
	
	@PostMapping("/admin/categories/{categoryID}/product")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody Product product, @PathVariable Long categoryID){
		return ResponseEntity.ok(productService.createProduct(categoryID, product));
	}

}
