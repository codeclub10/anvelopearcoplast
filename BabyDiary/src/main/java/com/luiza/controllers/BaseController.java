package com.luiza.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class BaseController {

		@RequestMapping("/")
		public String home() {
			return "index";
		}

		@RequestMapping("/hello")
		public String hi() {
			return "hello";
		}
		
	}


