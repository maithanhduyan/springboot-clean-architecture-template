package com.company.project.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.project.domain.product.entity.Product;
import com.company.project.services.product.ProductService;

@Controller
public class ProductController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	@RequestMapping(value = { "/product" }, method = RequestMethod.GET)
	public String viewProductPage(Model model) {
		LOG.info("Get Product List");
		List<Product> productList = productService.findAll();
		LOG.info("Product List Count: " + productList.size());
		model.addAttribute("productList", productList);
		return "product";
	}
}
