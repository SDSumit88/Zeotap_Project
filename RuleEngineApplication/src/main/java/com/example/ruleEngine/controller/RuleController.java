package com.example.ruleEngine.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ruleEngine.model.ASTNode;
import com.example.ruleEngine.model.EvaluationResponse;
import com.example.ruleEngine.repository.RuleRepository;
import com.example.ruleEngine.service.RuleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/rules")
public class RuleController {
	    @Autowired
	    private RuleService ruleService;
	    
	    @PostMapping("/create")
	    public String createRule(@RequestParam("ruleString") String ruleString, Model model) {
	        System.out.println("Parsing rule: " + ruleString);

	        try {
	            // Validate the rule string before parsing
	            if (!isValidRule(ruleString)) {
	                throw new IllegalArgumentException("Invalid rule syntax: " + ruleString);
	            }

	            // Use the service to create the ASTNode from the rule string
	            ASTNode ruleAST = ruleService.createRule(ruleString);

	            // Save the rule along with its AST representation
	            if (ruleAST != null) {
	                ruleService.saveRule(ruleString, ruleAST); // Save the rule to the database
	            }

	            // Convert the AST to a pretty-printed JSON string for displaying in the view
	            ObjectMapper objectMapper = new ObjectMapper();
	            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	            String astJson = objectMapper.writeValueAsString(ruleAST);

	            model.addAttribute("resultMessage", "Rule created successfully: " + astJson);
	            model.addAttribute("astRepresentation", astJson);
	        } catch (Exception e) {
	            model.addAttribute("resultMessage", "Error creating rule: " + e.getMessage());
	            model.addAttribute("astRepresentation", "{}");
	        }
	        return "result"; // Return result.html
	    }

	 

	    private boolean isValidRule(String ruleString) {
	    	// Update the regex to check for comparison and logical operators
	        return ruleString.matches(".*(AND|OR|>|<|>=|<=|==|!=).*");
	    }
	    
	    
	    @PostMapping("/evaluate")
	    public String evaluateRule(@RequestParam("ruleStringEvaluate") String ruleString,
	                               @RequestParam("age") int age,
	                               @RequestParam("department") String department,
	                               @RequestParam("salary") int salary,
	                               @RequestParam("experience") int experience,
	                               Model model) {
	        Map<String, Object> data = new HashMap<>();
	        data.put("age", age);
	        data.put("department", department);
	        data.put("salary", salary);
	        data.put("experience", experience);

	        try {
	            // Validate the rule string
	            if (!ruleService.isValidRule(ruleString)) {
	                model.addAttribute("resultMessage", "Invalid rule syntax: " + ruleString);
	                return "evaluationResult"; // Redirect to evaluationResult.html
	            }

	            // Create the AST from the valid rule string
	            ASTNode rule = ruleService.createRule(ruleString);
	            boolean result = ruleService.evaluateRule(rule, data);
	            

	            // Pass the result to the view
	            model.addAttribute("resultMessage", "Evaluation result: " + result);
	            return "evaluationResult"; // Redirect to evaluationResult.html
	        } catch (IllegalArgumentException e) {
	            model.addAttribute("resultMessage", "Invalid rule: " + e.getMessage());
	            return "evaluationResult"; // Redirect to evaluationResult.html
	        } catch (Exception e) {
	            model.addAttribute("resultMessage", "Error evaluating rule: " + e.getMessage());
	            return "evaluationResult"; // Redirect to evaluationResult.html
	        }
	    }


	    }
	    
	
