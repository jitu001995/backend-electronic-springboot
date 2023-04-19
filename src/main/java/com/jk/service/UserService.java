package com.jk.service;

import java.util.List;

import com.jk.dto.PageableResponse;
import com.jk.dto.UserDto;

public interface UserService {
  
	UserDto createUser(UserDto userDto);
	
	//update
	UserDto updateUser(UserDto userDto, String userId);

    // delete
	void deleteUser(String userId);
	
	// get all users
	PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	// get Single User By userId
	UserDto getUserById(String userId);
	
	// get Single User By Email
	UserDto getUserByEmail(String email);
	
	// search User 
	List<UserDto> searchUser(String keyword);
}
