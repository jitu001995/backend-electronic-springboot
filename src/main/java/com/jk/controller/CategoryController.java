package com.jk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jk.dto.ApiResponseMessage;
import com.jk.dto.CategoryDto;
import com.jk.dto.PageableResponse;
import com.jk.dto.ProductDto;
import com.jk.service.CategoryService;
import com.jk.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;  
	
	@Autowired
	private ProductService productService;
   
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		// call service to save object
		CategoryDto categoryDto1 = categoryService.create(categoryDto);
		
		return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
		
	}
	
	// update 
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> upateCategory(
			@PathVariable String categoryId,
			@RequestBody CategoryDto categoryDto){
		CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
				return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
		
	}
	
	// delete category
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
		 System.out.println("In controller");
		categoryService.delete(categoryId);
		ApiResponseMessage apiResponseMessage=ApiResponseMessage.builder().message("Category  is deleted Successfully !!").status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
		
	}
	
	//get all
	@GetMapping("/getAllCategory")
	public ResponseEntity<PageableResponse<CategoryDto>> getAll(
			@RequestParam(value="pageNumber",defaultValue="0", required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10", required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue="title", required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue="asc", required=false) String sortDir
			){
		
		PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
		
		CategoryDto categoryDto = categoryService.get(categoryId);
		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}
	
	// create product with category
	@PostMapping("/{categoryId}/products")
	public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable("categoryId") String categoryId, @RequestBody ProductDto productDto){
	
		ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);
		
		return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
		
	}
	
	// update category of product
	@PutMapping("/{categoryId}/products/{productId}")
	public ResponseEntity<ProductDto> updateCategoryOfProduct(
			@PathVariable String categoryId,
			@PathVariable String productId
			){
		   
		  ProductDto productDto = productService.updateCategory(productId, categoryId);
		
		 return new ResponseEntity<>(productDto,HttpStatus.OK);
		
	}
	
	// get Products of categories
	// update category of product
	 @GetMapping("/{categoryId}/products")
	    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
	            @PathVariable String categoryId,
	            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
	            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
	            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
	            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

	    ) {

	        PageableResponse<ProductDto> response = productService.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
	        return new ResponseEntity<>(response, HttpStatus.OK);

	    }
}
