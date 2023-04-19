package com.jk.service.impl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jk.dto.PageableResponse;
import com.jk.dto.ProductDto;
import com.jk.entities.Category;
import com.jk.entities.Product;
import com.jk.exptions.ResourceNotFoundException;
import com.jk.helper.Helper;
import com.jk.repo.CategoryRepository;
import com.jk.repo.ProductRepository;
import com.jk.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
     
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public ProductDto create(ProductDto productDto) {
		
		Product product=mapper.map(productDto, Product.class);
		 //get random id
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);
		// added current date
		product.setAddedDate(new Date());
		Product saveProduct = productRepository.save(product);
		return mapper.map(saveProduct, ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto productDto, String productId) {
		
		Product product =productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found of given Id"));
		product.setProductId(product.getProductId());
		product.setTitle(productDto.getTitle());
		product.setDescription(product.getDescription());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setQuantity(productDto.getQuantity());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());
		product.setProductImageName(productDto.getProductImageName());
		
		// save the entity
		Product updatedProduct = productRepository.save(product);
		return mapper.map(updatedProduct,ProductDto.class);
	}

	@Override
	public void delete(String productId) {
		
		Product product =productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found of given Id"));
		productRepository.delete(product);
	}

	@Override
	public ProductDto getSingle(String productId) {
		
		Product product =productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found of given Id"));
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("des"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		   Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		   Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
			
			return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
		   
		  Sort sort = (sortDir.equalsIgnoreCase("des"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
	   Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
	   Page<Product> page = productRepository.findAll(pageable);
		
		return Helper.getPageableResponse(page, ProductDto.class);
	} 

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		 Sort sort = (sortDir.equalsIgnoreCase("des"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		   Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		   Page<Product> page = productRepository.findByLiveTrue(pageable);
			
			return Helper.getPageableResponse(page, ProductDto.class);

	}

	  @Override
	    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
	        //fetch the category from db:
	        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!"));
	        Product product = mapper.map(productDto, Product.class);

	        //product id
	        String productId = UUID.randomUUID().toString();
	        product.setProductId(productId);
	        //added
	        product.setAddedDate(new Date());
	        product.setCategory(category);
	        Product saveProduct = productRepository.save(product);
	        return mapper.map(saveProduct, ProductDto.class);


	    }

	@Override
	public ProductDto updateCategory(String productId, String categoryId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Product product  = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product of given Id Not found"));
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category of given Id not Found"));
		
		product.setCategory(category);
		 
		Product savedProduct = productRepository.save(product);
		
		return mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category of given Id not Found"));
		 Sort sort = (sortDir.equalsIgnoreCase("des"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page = productRepository.findByCategory(category,pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}
	
	
	
	

}
