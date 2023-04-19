package com.jk.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jk.dto.ApiResponseMessage;
import com.jk.dto.ImageResponse;
import com.jk.dto.PageableResponse;
import com.jk.dto.ProductDto;
import com.jk.dto.UserDto;
import com.jk.service.FileService;
import com.jk.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/products")
public  class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${product.image.path}")
	private String imagePath;
  
	//create 
	@PostMapping()
	  public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto ){
		    
		   ProductDto createdProduct = productService.create(productDto);
		  
		  return new ResponseEntity<>(createdProduct,HttpStatus.CREATED);
	  }
	
	//update 
	@PutMapping("/{productId}")
	  public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId,@RequestBody ProductDto productDto ){
		    
		   ProductDto createdProduct = productService.update(productDto,productId);
		  
		  return new ResponseEntity<>(createdProduct,HttpStatus.OK);
	  }
	
	//delete
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId){
		productService.delete(productId);
		ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product is deleted successfully !!").status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);
	}
	
	// get Single
	
	@GetMapping("/{productId}")
	  public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
		    
		   ProductDto productDto = productService.getSingle(productId);
		  
		  return new ResponseEntity<>(productDto,HttpStatus.OK);
	  }
	
	// get all
	@GetMapping()
	  public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
			  @RequestParam(value="pageNumber",defaultValue="0", required=false) int pageNumber,
				@RequestParam(value="pageSize",defaultValue="10", required=false) int pageSize,
				@RequestParam(value="sortBy",defaultValue="title", required=false) String sortBy,
				@RequestParam(value="sortDir",defaultValue="asc", required=false) String sortDir
			  ){
		    
		   PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber,pageSize,sortBy,sortDir);
		  
		  return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
	  }
	
	
	// get all live
	@GetMapping("/live")
	  public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProduct(
			  @RequestParam(value="pageNumber",defaultValue="0", required=false) int pageNumber,
				@RequestParam(value="pageSize",defaultValue="10", required=false) int pageSize,
				@RequestParam(value="sortBy",defaultValue="title", required=false) String sortBy,
				@RequestParam(value="sortDir",defaultValue="asc", required=false) String sortDir
			  ){
		    
		   PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber,pageSize,sortBy,sortDir);
		  
		  return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
	  }
	
	//search all
	@GetMapping("/search/{query}")
	  public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
			  @PathVariable String query,
			  @RequestParam(value="pageNumber",defaultValue="0", required=false) int pageNumber,
				@RequestParam(value="pageSize",defaultValue="10", required=false) int pageSize,
				@RequestParam(value="sortBy",defaultValue="title", required=false) String sortBy,
				@RequestParam(value="sortDir",defaultValue="asc", required=false) String sortDir
			  ){
		    
		   PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query ,pageNumber,pageSize,sortBy,sortDir);
		  
		  return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
	  }
	
	//upload image
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadProductImage(
			@PathVariable String productId,
			@RequestParam("productImage") MultipartFile image
			) throws IOException{
		  
		String fileName  = fileService.uploadFile(image, imagePath);
		// get Product for uploading image
		ProductDto productDto = productService.getSingle(productId);
		
		productDto.setProductImageName(fileName);
		
		ProductDto updatedProduct = productService.update(productDto, productId);
		
		ImageResponse response = ImageResponse.builder().imageName(updatedProduct.getProductImageName()).message("Product Image is successfully uploaded !!!").status(HttpStatus.CREATED).success(true).build();
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	//serve image 
	//serve image 
		@GetMapping("/image/{productId}")
		public void serveUserImage(@PathVariable String productId,HttpServletResponse response) throws IOException{
			
			//
			ProductDto productDto = productService.getSingle(productId);
			
			//logger.info("User Image Name : {} " ,user.getImageName());
			
			InputStream resource = null;
			try {
				resource = fileService.getResource(imagePath,productDto.getProductImageName());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		}
}
