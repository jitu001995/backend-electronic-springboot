package com.jk.service;

import java.util.List;

import com.jk.dto.PageableResponse;
import com.jk.dto.ProductDto;

public interface ProductService {
    
	//create 
	ProductDto create(ProductDto productDto);
	
	//update
	ProductDto update(ProductDto productDto,String productId);
	
	// delete
	void delete(String productId);
	
	//get Signle 
	ProductDto getSingle(String productId);
	
	PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	// get All : live
	PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);

	//create product with category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);


    //update category of product
    ProductDto updateCategory(String productId,String categoryId);

   PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy, String sortDir);

    //other methods
}
