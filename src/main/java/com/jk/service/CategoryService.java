package com.jk.service;

import com.jk.dto.CategoryDto;
import com.jk.dto.PageableResponse;

public interface CategoryService {
   
	//create
	CategoryDto create(CategoryDto categoryDto);
	//update
	public CategoryDto update(CategoryDto categoryDto, String categoryId);
	//delete
	void delete(String categoryId);
	//getAll
	public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize,String sortBy,String sortDir); 
	//getSingleCategory detail
	CategoryDto get(String categoryId);
	//search
}
