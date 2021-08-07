package com.company.project.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.project.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String>{

}
