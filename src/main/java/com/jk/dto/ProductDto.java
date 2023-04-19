package com.jk.dto;

import java.util.Date;

import com.jk.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString 
public class ProductDto {
	
		private String productId;
		
		private String title;
		
		private String description;
		
		private int price;
		
		private int discountedPrice;
		
		private int quantity;
		
		private Date addedDate;
		
		private boolean live;
		
		private boolean stock;
		
		private String productImageName;
		
		private CategoryDto category;
}
