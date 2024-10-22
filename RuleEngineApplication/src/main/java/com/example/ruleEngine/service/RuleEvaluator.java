package com.example.ruleEngine.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.ruleEngine.model.ASTNode;

@Component
public class RuleEvaluator {

    public boolean evaluateRule(ASTNode node, Map<String, Object> data) {
    	 if (node == null) {
    	        return false; // Base case: if the node is null, return false
    	    }

    	    switch (node.getType()) {
    	        case "operand":
    	            // Check if the node represents an attribute to compare
    	            String attribute = node.getValue(); // Example: "age"
    	            Object actualValue = data.get(attribute); // Get the value from the data map
    	            
    	            // The comparison should be evaluated in the context of its parent operator node
    	            if (node.getLeft() != null && node.getRight() != null) {
    	                String operator = node.getLeft().getValue(); // e.g., ">"
    	                Object expectedValue = evaluateNode(node.getRight(), data); // Get the value to compare against
    	                return compareValues(actualValue, expectedValue, operator);
    	            }
    	            return false;

    	        case "operator":
    	            // Evaluate left and right children based on the operator
    	            boolean leftResult = evaluateRule(node.getLeft(), data);
    	            boolean rightResult = evaluateRule(node.getRight(), data);
    	            return evaluateOperator(node.getValue(), leftResult, rightResult);

    	        default:
    	            return false; // If the node type is unknown
        }
    }

    private Object evaluateNode(ASTNode node, Map<String, Object> data) {
        if (node.getType().equals("operand")) {
            return data.get(node.getValue());
        } else if (node.getType().equals("operator")) {
            // If it's an operator, we should evaluate its children
            return evaluateRule(node, data);
        }
        return null;
    }

    private boolean compareValues(Object actualValue, Object expectedValue, String operator) {
        switch (operator) {
            case ">":
                return ((Comparable) actualValue).compareTo(expectedValue) > 0;
            case "<":
                return ((Comparable) actualValue).compareTo(expectedValue) < 0;
            case "=":
            case "==":
                return actualValue.equals(expectedValue);
            case "!=":
                return !actualValue.equals(expectedValue);
            // Add more operators as necessary (e.g., >=, <=, etc.)
            default:
                return false;
        }
    }

    private boolean evaluateOperator(String operator, boolean leftResult, boolean rightResult) {
        switch (operator) {
            case "AND":
                return leftResult && rightResult;
            case "OR":
                return leftResult || rightResult;
            // Add more logical operators as necessary
            default:
                return false;
        }
    }
}
