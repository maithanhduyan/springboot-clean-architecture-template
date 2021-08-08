package com.company.project.services.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.project.domain.product.entity.Product;
import com.company.project.domain.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product fetchById(String id) {
		Product product = null;
		try {
			product = productRepository.findById(id).orElse(null);
		} catch (Exception ex) {
			LOGGER.info(ex.getMessage());
		}
		return product;
	}

}
