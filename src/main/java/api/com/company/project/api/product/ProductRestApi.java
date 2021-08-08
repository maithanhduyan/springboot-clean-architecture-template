package com.company.project.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.domain.product.entity.Product;
import com.company.project.services.product.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductRestApi {

	@Autowired
	ProductService productService;

	@RequestMapping(value = "/fetchById", method = {RequestMethod.POST,RequestMethod.GET}, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Product fetchById(@RequestParam  String id) {
		return productService.fetchById(id);
	}

	@RequestMapping("/heathCheck")
	@ResponseBody
	public String heathCheck() {
		return "OK";
	}
}
