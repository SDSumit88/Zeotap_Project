package com.example.ruleEngine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Rule {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ruleString; // Original rule string
	@Lob
	private String ruleJson;   // AST serialized to JSON

	// Constructors, Getters, and Setters
	public Rule() {}

	public Rule(String ruleString, String ruleJson) {
		this.ruleString = ruleString;
		this.ruleJson = ruleJson;
	}
	
	 public Rule(String ruleString, Long id) {
	        this.ruleString = ruleString;
	        this.id = id;
	    }
	 
	 public Rule(String ruleString) {
	        this.ruleString = ruleString;
	        this.id = null; // or assign a default value if necessary
	    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleString() {
		return ruleString;
	}

	public void setRuleString(String ruleString) {
		this.ruleString = ruleString;
	}

	public String getRuleJson() {
		return ruleJson;
	}

	public void setRuleJson(String ruleJson) {
		this.ruleJson = ruleJson;
	}
}

