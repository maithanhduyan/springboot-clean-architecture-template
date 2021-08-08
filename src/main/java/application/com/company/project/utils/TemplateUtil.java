package com.company.project.utils;

import org.springframework.stereotype.Component;

@Component
public class TemplateUtil {

	public static final String ACTIVE = "active";

	public static String activeBlogNav(String url, String navMenu) {
		String _active = "";
		if(url.contains(navMenu)) {
			_active = ACTIVE;
		}
		return _active;
	}

}
