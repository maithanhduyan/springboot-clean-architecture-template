package com.company.project.services.product;

import java.util.List;

import com.company.project.domain.product.entity.Product;

public interface ProductService {

	public List<Product> findAll();
	
	public Product fetchById(String id);
}
