package com.f5antos.tienda.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.f5antos.tienda.model.Product;
import com.f5antos.tienda.payload.ProductDTO;
import com.f5antos.tienda.payload.ProductResponse;

public interface ProductService {
	ProductDTO findProduct(Long productID);
	ProductResponse getAllProducts();
	ProductResponse getAllProductsByCategory(Long categoryID);
	ProductResponse searchProductByKeyword(String keyword);
	ProductDTO createProduct(Long categoryID, ProductDTO productDTO);
	String deleteProduct(Long productID);
	ProductDTO updateProduct(ProductDTO productDTO, Long productID);
	ProductDTO updateProductImage(Long productID, MultipartFile image) throws IOException;
}
