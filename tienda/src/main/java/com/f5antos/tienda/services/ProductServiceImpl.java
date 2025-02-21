package com.f5antos.tienda.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.f5antos.tienda.exceptions.ResourceNotFoundException;
import com.f5antos.tienda.model.Category;
import com.f5antos.tienda.model.Product;
import com.f5antos.tienda.payload.ProductDTO;
import com.f5antos.tienda.payload.ProductResponse;
import com.f5antos.tienda.repositories.CategoryRepository;
import com.f5antos.tienda.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public ProductDTO findProduct(Long productID) {
		Product product = productRepository.findById(productID)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID: " + productID + " NOT found."));
		
		ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
		
		return productDTO;
	}

	@Override
	public ProductResponse getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductDTO> productsDTOs = products.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productsDTOs);
		return productResponse;
	}		

	@Override
	public ProductResponse getAllProductsByCategory(Long categoryID) {
		Category category = categoryRepository.findById(categoryID)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryID", categoryID));
		
		List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
		
		List<ProductDTO> productsDTOs = products.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productsDTOs);
		
		return productResponse;
	}

	@Override
	public ProductResponse searchProductByKeyword(String keyword) {
		keyword = "%" + keyword + "%";
		List<Product> products = productRepository.findByProductNameLikeIgnoreCase(keyword);
		
		List<ProductDTO> productsDTOs = products.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productsDTOs);
		return productResponse;
	}

	@Override
	public ProductDTO createProduct(Long categoryID, Product product) {
		
		Category category = categoryRepository.findById(categoryID)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryID", categoryID));
		
		product.setImage("default.png");
		product.setCategory(category);
		double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
		product.setSpecialPrice(specialPrice);
		Product savedProduct = productRepository.save(product);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public String deleteProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateProduct() {
		// TODO Auto-generated method stub
		return null;
	}

}
