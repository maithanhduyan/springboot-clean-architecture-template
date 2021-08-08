package com.company.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {

	@RequestMapping(value = { "", "/", "index", "/index.html" })
	public String viewHome(Model model) {
		return "blog/index";
	}

	@RequestMapping(value = { "about", "/about.html" })
	public String viewAbout(Model model) {
		return "blog/about";
	}
	
	@RequestMapping(value = { "contact", "/contact.html" })
	public String viewContact(Model model) {
		return "blog/contact";
	}
	
	@RequestMapping(value = { "blog", "/blog.html" })
	public String viewBlog(Model model) {
		return "blog/blog";
	}

}
