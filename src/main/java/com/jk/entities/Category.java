package com.jk.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="categories")
public final class Category {
	
	@Id
	@Column(name="id")
	private String categoryId;
	
	@Column(name="category_title",length=60)
	private String title;
	
	@Column(name="category_description",length=	500)
	private String description;
	
	private String coverImage;
	
	@OneToMany(mappedBy="category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();



}
