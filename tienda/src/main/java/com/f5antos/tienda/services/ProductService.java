package com.f5antos.tienda.services;

import com.f5antos.tienda.model.Product;
import com.f5antos.tienda.payload.ProductDTO;
import com.f5antos.tienda.payload.ProductResponse;

public interface ProductService {
	ProductDTO findProduct(Long productID);
	ProductResponse getAllProducts();
	ProductResponse getAllProductsByCategory(Long categoryID);
	ProductResponse searchProductByKeyword(String keyword);
	ProductDTO createProduct(Long categoryID, Product product);
	String deleteProduct();
	String updateProduct();
}
