package com.jk.service.impl;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jk.dto.PageableResponse;
import com.jk.dto.UserDto;
import com.jk.entities.User;
import com.jk.exptions.ResourceNotFoundException;
import com.jk.helper.Helper;
import com.jk.repo.UserRepository;
import com.jk.service.UserService;

@Service
public class UserServiceImpl implements UserService {
  
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${user.profile.image.path}")
	private String imagePath;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// generate unique id in string format
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
		System.out.println(userDto);
		// converting dto -> Entity
		User user = dtoToEntity(userDto);
		User savedUser = userRepo.save(user);
		// converting entity -> dto
		UserDto newDto = entityToDto(savedUser);
		return newDto;
	}

	
	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found with given Id"));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setGender(userDto.getGender());
		user.setPassword(userDto.getPassword());
		user.setImageName(userDto.getImageName());
		User updatedUser= userRepo.save(user);
		UserDto updatedDto = entityToDto(updatedUser);
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found with given Id"));
        
		// delete user Proile image
		// images/users/abc.png
		String fullPath = imagePath+user.getImageName();
		try {
			Path path = Paths.get(fullPath);
			Files.delete(path);
		} catch(NoSuchFileException ex) {
			logger.info("User Image not found in folder");
			ex.printStackTrace();	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		//delete User
		userRepo.delete(user);
	}

	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		// pageNumber default start from Zero
		Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
		Page<User> page = userRepo.findAll(pageable);
		
		PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
		return response;
	}

	@Override
	public UserDto getUserById(String userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found with given Id"));
		UserDto userDto = entityToDto(user);
		return userDto;
	}


	@Override
	public UserDto getUserByEmail(String email) {
		User user = userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found with given email Id"));
		UserDto userDto = entityToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> userList = userRepo.findByNameContaining(keyword);
		List<UserDto> dtoList = userList.stream().map(user->entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}
	
	
	private UserDto entityToDto(User savedUser) {
//     UserDto userDto = UserDto.builder()
//		               .userId(savedUser.getUserId())
//                       .name(savedUser.getName())
//                       .email(savedUser.getEmail())
//                        .password(savedUser.getPassword())
//                       .about(savedUser.getAbout())
//                       .gender(savedUser.getGender())
//                       .imageName(savedUser.getImageName()).build();
		      return mapper.map(savedUser, UserDto.class);
	}

	private User dtoToEntity(UserDto userDto) {
//		User user = User.builder()
//		            .userId(userDto.getUserId())
//		            .name(userDto.getName())
//		            .email(userDto.getEmail())
//		            .password(userDto.getPassword())
//		            .about(userDto.getAbout())
//		            .gender(userDto.getGender())
//		            .imageName(userDto.getImageName()).build();
		return mapper.map(userDto, User.class);
	}


}
