package com.jk.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jk.exptions.BadApiRequestException;
import com.jk.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	
	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		
		String originalFilename = file.getOriginalFilename();
		logger.info("Filename : {}", originalFilename);
		 String filename = UUID.randomUUID().toString();
		 String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		
		 String fileNameWithExtension = filename+extension;
		 String fullPathWithFileName= path+fileNameWithExtension;
		 logger.info("Full image path:{}",fullPathWithFileName);
		
		 if(extension.equalsIgnoreCase(".jfif")||extension.equalsIgnoreCase(".png")|| extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".jpeg")) {
			 
			 //file save
			 logger.info("File extension is {} ",extension);
			 File folder = new File(path);
			 if(!folder.exists()) {
				 
				 //create the folder
				 folder.mkdirs();
			 }  
			 
			 Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			 return fileNameWithExtension;
			 
		 }else {
			 throw new BadApiRequestException("File with this "+extension+" not allowed");
		 }
		 
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		String fullPath = path+File.separator+name;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}

}
