package com.example.ruleEngine.service;

import java.util.List;
import java.util.Map;



import com.example.ruleEngine.entity.Rule;
import com.example.ruleEngine.model.ASTNode;

public interface RuleService {

	    ASTNode createRule(String ruleString);
	    ASTNode combineRules(List<String> rules);
	    boolean evaluateRule(ASTNode rule, Map<String, Object> data);
	    Rule saveRule(String ruleString, ASTNode ast);
	    String astToJson(ASTNode ast);
	    List<String> extractOperators(String ruleString);
	    boolean isValidRule(String ruleString);
	 // New method for evaluating rule by string
	    boolean evaluateRule(String ruleString, Map<String, Object> data);
	  
}
