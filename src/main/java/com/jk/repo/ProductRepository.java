package com.jk.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.entities.Category;
import com.jk.entities.Product;

public interface ProductRepository extends JpaRepository<Product,String> {
 
	
	Page<Product> findByTitleContaining(String subtitle,Pageable pageable);
	
	Page<Product> findByLiveTrue(Pageable pageable);
	
	Page<Product> findByCategory(Category category,Pageable pageable);

}
