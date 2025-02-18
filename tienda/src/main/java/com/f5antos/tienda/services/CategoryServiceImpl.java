package com.f5antos.tienda.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.f5antos.tienda.model.Category;
import com.f5antos.tienda.payload.CategoryDTO;
import com.f5antos.tienda.payload.CategoryResponse;
import com.f5antos.tienda.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    
	@Autowired
    private ModelMapper modelMapper;
	
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
    	
    	Sort filtroOrden = sortOrder.equalsIgnoreCase("asc")
    			? Sort.by(sortBy).ascending()
    			: Sort.by(sortBy).descending();
    	
    	Pageable pageDetails = PageRequest.of(pageNumber, pageSize, filtroOrden);
    	Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
    	List<Category> categories = categoryPage.getContent();   	
    	
    	if(categories.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay categorías creadas todavía.");
    	
    	List<CategoryDTO> categoryDTOS = categories.stream()
    			.map(category -> modelMapper.map(category, CategoryDTO.class))
    			.toList();
    	
    	CategoryResponse categoryResponse = new CategoryResponse();
    	categoryResponse.setContent(categoryDTOS);
    	categoryResponse.setPageNumber(categoryPage.getNumber());
    	categoryResponse.setPageSize(categoryPage.getSize());
    	categoryResponse.setTotalElements(categoryPage.getTotalElements());
    	categoryResponse.setTotalPages(categoryPage.getTotalPages());
    	categoryResponse.setLastPage(categoryPage.isLast());
        
    	return categoryResponse;
    }

    @Override
    public CategoryDTO findCategory(Long categoryID) {
    	
    	Category category = categoryRepository.findById(categoryID)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID: " + categoryID + " NOT found."));;
    	
    	CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
    	
        return categoryDTO;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    	
    	Category category = modelMapper.map(categoryDTO, Category.class);
    	
    	//Si ya existe una con el mismo nombre
    	
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override
    public String deleteCategory(Long categoryID) {
        CategoryDTO categoryDTO = findCategory(categoryID); // Si no existe, lanza excepción automáticamente
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.delete(category);
        return "Category with ID: " + categoryID + " deleted.";
    }

    @Override
    public String updateCategory(Long categoryID, Category newInfo) {
        CategoryDTO existingCategory = findCategory(categoryID); // Si no existe, lanza excepción automáticamente
        existingCategory.setCategoryName(newInfo.getCategoryName());
        Category updatedCategory = modelMapper.map(existingCategory, Category.class);
        categoryRepository.save(updatedCategory);
        return "Category with ID: " + categoryID + " updated.";
    }
}
