package com.company.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String home() {
		return "shop/index";
	}

	@RequestMapping(value = { "/cart", "/cart.html" }, method = RequestMethod.GET)
	public String viewCart() {
		return "shop/cart";
	}
}
