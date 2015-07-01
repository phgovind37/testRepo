package com.conquer.test.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.conquer.test.jpa.User;
import com.conquer.test.service.HomeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HomeService homeService;
	
	 @Autowired
	    public HomeController(HomeService service) {
	        this.homeService = service;
	    }
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		List<User> users = homeService.getAllUsers();
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("users", users );
		
		return "home";
	}
	
	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String adduser(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		User user = new User();
		homeService.addUser(user);
		model.addAttribute("user", user);
		
		return "addUser";
	}
	
}
