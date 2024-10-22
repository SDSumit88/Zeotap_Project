package com.example.ruleEngine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {

	@GetMapping("/")
	public String redirectToIndex() {
		System.out.println("Redirecting to /index.html");
		return "index"; 
	}


	@GetMapping("/home")
	public String home() {
		return "home"; // Return home.html
	}

	@GetMapping("/rules/create")
	public String createRulePage() {
		return "result"; // Return create.html or the form for creating rules
	}

	@GetMapping("/rules/evaluate")
	public String evaluateRulePage() {
		return "evaluationResult"; // Return evaluate.html or the form for evaluating rules
	}



}
