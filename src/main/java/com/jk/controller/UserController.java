package com.jk.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.jk.dto.UserDto;
import com.jk.service.FileService;
import com.jk.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	//create Usr
	@PostMapping
	public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto) throws InterruptedException{
		System.out.println("Request in create User");
		//using for checking spineer in react
		//Thread.sleep(5000);
		UserDto userDto1 = userService.createUser(userDto);
		return new ResponseEntity<>(userDto1,HttpStatus.CREATED);
		
	}
	
	// update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(
			@PathVariable("userId") String userId,
			@Valid @RequestBody UserDto userDto){
		
		UserDto updatedUserDto1 = userService.updateUser(userDto,userId);
		return new ResponseEntity<>(updatedUserDto1,HttpStatus.OK);
		
	}
	
	//delete
	@DeleteMapping("/{userId}")
	public  ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
		userService.deleteUser(userId);
		ApiResponseMessage message=	ApiResponseMessage.builder().message("User is Deleted Successfully !!")
		                              .success(true).status(HttpStatus.OK).build();
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	// get all 
	@GetMapping
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
			@RequestParam(value="pageNumber",defaultValue="0", required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue="name",required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false) String sortDir){
		
			return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	//get Single
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable String userId){
		return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
	}
	/// get by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
		return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
	}
	//search user
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords){
		return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
	}
	
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image,
			                                             @PathVariable("userId") String userId ) throws IOException{
		String imageName= fileService.uploadFile(image,imageUploadPath);
		 
		UserDto user =userService.getUserById(userId);
		user.setImageName(imageName);
		UserDto userDto = userService.updateUser(user, userId);
		
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
		return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
	}
	
	
	//serve image 
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId,HttpServletResponse response) throws IOException{
		
		//
		UserDto user = userService.getUserById(userId);
		
		logger.info("User Image Name : {} " ,user.getImageName());
		
		InputStream resource = null;
		try {
			resource = fileService.getResource(imageUploadPath,user.getImageName());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
		
}

