package com.jk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class CategoryDto {
	
	
	private String categoryId;
	
	@NotBlank(message="title is required")
	@Size(min=4, message="title must be of minimum 4 characters !!")
	private String title;
	
	@NotBlank(message="Description required !!")
	private String description;
	
	private String coverImage;

}