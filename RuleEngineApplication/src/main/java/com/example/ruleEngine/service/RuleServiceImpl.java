package com.example.ruleEngine.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ruleEngine.entity.Rule;
import com.example.ruleEngine.model.ASTNode;
import com.example.ruleEngine.repository.RuleRepository;
import com.example.ruleEngine.util.RuleEvaluator;
import com.example.ruleEngine.util.RuleParser;

import jakarta.transaction.Transactional;
@Service
public class RuleServiceImpl implements RuleService{

	@Autowired
	private RuleRepository ruleRepository;

	private final RuleParser ruleParser = new RuleParser();
	private final RuleEvaluator ruleEvaluator = new RuleEvaluator();

	@Override
	public ASTNode createRule(String ruleString) {
		   System.out.println("Creating rule: " + ruleString);
		    
		    // Check for validity
		    if (!isValidRule(ruleString)) {
		        System.out.println("Invalid rule format: " + ruleString);
		        throw new IllegalArgumentException("Invalid rule: " + ruleString);
		    }

		    // Attempt to parse the rule string
		    ASTNode astNode = ruleParser.parseRule(ruleString);
		    
		    if (astNode == null) {
		        System.out.println("Parsed ASTNode is null for rule: " + ruleString);
		    } else {
		        System.out.println("Parsed ASTNode: " + astNode.toString()); // Make sure to implement toString in ASTNode for better output
		    }

		    return astNode;
	}



	@Override
	public ASTNode combineRules(List<String> rules) {
	    if (rules == null || rules.isEmpty()) {
	        throw new IllegalArgumentException("No rules to combine.");
	    }
	    
	    // Map to count the frequency of operators
	    Map<String, Integer> operatorCount = new HashMap<>();

	    // Count operators in the rules
	    for (String rule : rules) {
	        List<String> operators = extractOperators(rule);
	        if (operators.isEmpty()) {
	            throw new IllegalArgumentException("No operators found in rule: " + rule);
	        }

	        for (String operator : operators) {
	            operatorCount.put(operator, operatorCount.getOrDefault(operator, 0) + 1);
	        }
	    }

	    // If no operators were found, throw an exception
	    if (operatorCount.isEmpty()) {
	        throw new IllegalStateException("No operators found across all rules.");
	    }

	    // Determine the most frequent operator
	    String mostFrequentOperator = Collections.max(operatorCount.entrySet(), Map.Entry.comparingByValue()).getKey();
	    System.out.println("Most Frequent Operator: " + mostFrequentOperator);

	    // Initialize combined AST with the first rule
	    ASTNode combinedRoot = createRule(rules.get(0));
	    System.out.println("First rule AST: " + combinedRoot.getCombinedRuleString());

	    for (int i = 1; i < rules.size(); i++) {
	        ASTNode currentAST = createRule(rules.get(i));
	        System.out.println("Current rule AST: " + currentAST.getCombinedRuleString());

	        // Create a new operator node based on the most frequent operator
	        ASTNode newOperatorNode = new ASTNode("operator", combinedRoot, currentAST, mostFrequentOperator);
	        combinedRoot = newOperatorNode; // Update the combined root
	    }

	    return combinedRoot; // Return the combined AST
	}




@Override
public boolean evaluateRule(ASTNode rule, Map<String, Object> data) {

	return ruleEvaluator.evaluateRule(rule, data);
}


@Override
public Rule saveRule(String ruleString, ASTNode ast) {
	try {
		// Convert ASTNode to JSON
		String astJson = ruleParser.astToJson(ast);

		// Create a new Rule entity with the rule string and its AST in JSON
		Rule rule = new Rule(ruleString, astJson);
		System.out.println("Saving rule: " + ruleString);

		// Save the rule to the database
		return ruleRepository.save(rule);
	} catch (Exception e) {
		e.printStackTrace(); // Log any issues to the console
		throw e;
	}
}

@Override
public String astToJson(ASTNode ast) {
	return ruleParser.astToJson(ast);
}

@Override
public List<String> extractOperators(String ruleString) {
    List<String> operators = new ArrayList<>();

    // Add basic logical operators AND and OR
    if (ruleString.contains("AND")) {
        operators.add("AND");
    }
    if (ruleString.contains("OR")) {
        operators.add("OR");
    }

    // Check for comparison operators (>, <, >=, <=, ==, !=) if needed
    if (ruleString.contains(">")) {
        operators.add(">");
    }
    if (ruleString.contains("<")) {
        operators.add("<");
    }
    if (ruleString.contains(">=")) {
        operators.add(">=");
    }
    if (ruleString.contains("<=")) {
        operators.add("<=");
    }
    if (ruleString.contains("==")) {
        operators.add("==");
    }
    if (ruleString.contains("!=")) {
        operators.add("!=");
    }

    // Add more operators as needed for your rule syntax
    // ...

    return operators; // Return the list of found operators
}


@Override
public boolean isValidRule(String ruleString) {
	return ruleString.matches(".*(AND|OR).*"); 
}

@Override
public boolean evaluateRule(String ruleString, Map<String, Object> data) {

	if (!isValidRule(ruleString)) {
		throw new IllegalArgumentException("Invalid rule: " + ruleString);
	}

	  ASTNode rule;
	    try {
	        // Create the AST from the valid rule string
	        rule = createRule(ruleString);
	    } catch (Exception e) {
	        // Handle potential exceptions during AST creation
	        throw new RuntimeException("Error creating AST from rule: " + ruleString, e);
	    }

	    // Proceed with the evaluation
	    boolean result = ruleEvaluator.evaluateRule(rule, data);
	    // Log the result of the evaluation
	    System.out.println("Evaluating rule: " + ruleString + " with result: " + result);

	    return result;
}




}

