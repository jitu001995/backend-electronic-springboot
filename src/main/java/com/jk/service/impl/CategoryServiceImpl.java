package com.jk.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jk.dto.CategoryDto;
import com.jk.dto.PageableResponse;
import com.jk.entities.Category;
import com.jk.exptions.ResourceNotFoundException;
import com.jk.helper.Helper;
import com.jk.repo.CategoryRepository;
import com.jk.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
 
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		// creating categoryId:randomly
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);
		Category category = mapper.map(categoryDto, Category.class);
		Category savedCategory= categoryRepo.save(category);
		return mapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {
		 
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found Exception !!"));
		
		// update category details
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		category.setCoverImage(categoryDto.getCoverImage());
		
		Category updateCategory = categoryRepo.save(category);
		
		return mapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public void delete(String categoryId) {
		// TODO Auto-generated method stub
        categoryRepo.deleteById(categoryId);
	}

	@Override
	public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize,String sortBy,String sortDir) {
		
        Sort sort = (sortDir.equalsIgnoreCase("des"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		 Page<Category> page = categoryRepo.findAll(pageable);
		
		 PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
		 return pageableResponse;
	}

	@Override
	public CategoryDto get(String categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found Exception !!"));
		return mapper.map(category,CategoryDto.class);
	}

}
