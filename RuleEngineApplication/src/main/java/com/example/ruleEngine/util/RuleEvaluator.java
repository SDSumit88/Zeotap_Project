package com.example.ruleEngine.util;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.ruleEngine.model.ASTNode;

public class RuleEvaluator {

	public boolean evaluateRule(ASTNode rule, Map<String, Object> data) {
		  if (rule.getType().equals("operand")) {
		        return evaluateCondition(rule.getValue(), data);
		    }

		    boolean leftResult = evaluateRule(rule.getLeft(), data);
		    boolean rightResult = evaluateRule(rule.getRight(), data);

		    if (rule.getValue().equals("AND")) {
		        return leftResult && rightResult;
		    } else if (rule.getValue().equals("OR")) {
		        return leftResult || rightResult;
		    }

		    return false;
	}


	private boolean evaluateCondition(String condition, Map<String, Object> data) {
		System.out.println("Evaluating condition: " + condition);
	    
	    // Using regex to split condition into attribute, operator, and value
	    Pattern pattern = Pattern.compile("([a-zA-Z]+)([<>=!]+)(.+)");
	    Matcher matcher = pattern.matcher(condition);
	    if (matcher.matches()) {
	        String attribute = matcher.group(1);
	        String operator = matcher.group(2);
	        String value = matcher.group(3).trim();

	        // Check if the attribute exists in the data map
	        if (!data.containsKey(attribute)) {
	            throw new IllegalArgumentException("Attribute not found: " + attribute);
	        }

	        Object attributeValue = data.get(attribute);
	        return compareValues(attributeValue, operator, value);
	    } else {
	        throw new IllegalArgumentException("Invalid condition: " + condition);
	    }
	}

	private boolean compareValues(Object attributeValue, String operator, String value) {
		 // Handle numeric comparisons
	    if (attributeValue instanceof Number) {
	        double numericValue = Double.parseDouble(value);
	        double attributeNumericValue = ((Number) attributeValue).doubleValue();
	        
	        switch (operator) {
	            case ">":
	                return attributeNumericValue > numericValue;
	            case "<":
	                return attributeNumericValue < numericValue;
	            case "=":
	                return attributeNumericValue == numericValue;
	            // Add more operators as needed
	        }
	    } else if (attributeValue instanceof String) {
	        // Handle string comparison (consider quotes)
	        String trimmedValue = value.replace("'", "");
	        switch (operator) {
	            case "=":
	                return attributeValue.equals(trimmedValue);
	            // Add more string operators as needed
	        }
	    }

	    return false;
	}

	}

	

	

	

