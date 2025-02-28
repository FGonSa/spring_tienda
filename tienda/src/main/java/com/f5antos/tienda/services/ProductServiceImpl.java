package com.f5antos.tienda.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.f5antos.tienda.config.AppConstants;
import com.f5antos.tienda.exceptions.ResourceNotFoundException;
import com.f5antos.tienda.model.Category;
import com.f5antos.tienda.model.Product;
import com.f5antos.tienda.payload.CategoryDTO;
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
	
	@Value("${images.path}")
	private String imagePath;

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
	public ProductDTO createProduct(Long categoryID, ProductDTO productDTO) {
		
		Category category = categoryRepository.findById(categoryID)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryID", categoryID));
		
		productDTO.setImage("default.png");
		productDTO.setCategoryID(category.getCategoryID());
		productDTO.setCategoryName(category.getCategoryName());
		double specialPrice = productDTO.getPrice() - (productDTO.getDiscount() * 0.01) * productDTO.getPrice();
		productDTO.setSpecialPrice(specialPrice);
		Product product = modelMapper.map(productDTO, Product.class);
		Product savedProduct = productRepository.save(product);
		return productDTO;
	}

	@Override
	public String deleteProduct(Long productID) {
		
		Product product = productRepository.findById(productID)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productID", productID));
		
        productRepository.delete(product);
        return "Product with ID: " + productID + " deleted.";
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO, Long productID) {

		Product productoExistente = productRepository.findById(productID)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productID", productID));
		
		Category category = categoryRepository.findById(productDTO.getCategoryID())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", productDTO.getCategoryID()));
		
		productoExistente.setProductName(productDTO.getProductName());
		productoExistente.setDescription(productDTO.getDescription());
		productoExistente.setQuantity(productDTO.getQuantity());
		productoExistente.setDiscount(productDTO.getDiscount());
		productoExistente.setPrice(productDTO.getPrice());
		productoExistente.setSpecialPrice(productDTO.getSpecialPrice());
		productoExistente.setCategory(category);
		
		Product savedProduct = productRepository.save(productoExistente);		
		
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProductImage(Long productID, MultipartFile image) throws IOException {
		// Recuperar el producto de la DB
		Product productoExistente = productRepository.findById(productID)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productID", productID));		
		
		// Obtener el filename de la imagen
		String filename = uploadImage(imagePath, image);
		
		// Actualizar producto
		productoExistente.setImage(filename);
		
		// Guardar producto actualizado
		Product updatedProduct = productRepository.save(productoExistente);
		
		// devolver DTO después de mapearlo
		return modelMapper.map(updatedProduct, ProductDTO.class);
	}

	private String uploadImage(String imagesDir, MultipartFile file) throws IOException {
		
		// Filename fichero original
		String filenameOriginal = file.getOriginalFilename();
		
		// Generar un filename único para evitar conflictos con existentes
		String randomID = UUID.randomUUID().toString();
		String fileName = randomID.concat(filenameOriginal.substring(filenameOriginal.lastIndexOf(".")));
		
		// Check si el path existe
		String filePath = imagesDir + File.separator + fileName;
		File folder = new File(imagesDir);
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		// Upload
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName;
	}

}
