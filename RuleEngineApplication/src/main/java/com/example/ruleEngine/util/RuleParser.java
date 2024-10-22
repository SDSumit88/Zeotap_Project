package com.example.ruleEngine.util;

import java.util.Stack;

import com.example.ruleEngine.model.ASTNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RuleParser {
//	 Parse rule string to AST
	public ASTNode parseRule(String ruleString) {
		   System.out.println("Parsing rule: " + ruleString);
		    
		    // Remove all whitespace for simpler tokenization
		    ruleString = ruleString.replaceAll("\\s+", "");
		    
		    Stack<ASTNode> stack = new Stack<>();
		    Stack<String> operators = new Stack<>();
		    StringBuilder currentToken = new StringBuilder();
		    
		    for (int i = 0; i < ruleString.length(); i++) {
		        char c = ruleString.charAt(i);

		        if (c == '(') {
		            operators.push("(");
		        } else if (c == ')') {
		            if (currentToken.length() > 0) {
		                stack.push(new ASTNode("operand", currentToken.toString()));
		                currentToken.setLength(0);
		            }
		            while (!operators.isEmpty() && !operators.peek().equals("(")) {
		                String op = operators.pop();
		                ASTNode right = stack.pop();
		                ASTNode left = stack.pop();
		                stack.push(new ASTNode("operator", left, right, op));
		            }
		            operators.pop(); // Remove '('
		        } else if (ruleString.startsWith("AND", i)) {
		            if (currentToken.length() > 0) {
		                stack.push(new ASTNode("operand", currentToken.toString()));
		                currentToken.setLength(0);
		            }
		            operators.push("AND");
		            i += 2; // Skip 'AND'
		        } else if (ruleString.startsWith("OR", i)) {
		            if (currentToken.length() > 0) {
		                stack.push(new ASTNode("operand", currentToken.toString()));
		                currentToken.setLength(0);
		            }
		            operators.push("OR");
		            i += 1; // Skip 'OR'
		        } else {
		            currentToken.append(c);
		        }
		    }

		    // Final token processing
		    if (currentToken.length() > 0) {
		        stack.push(new ASTNode("operand", currentToken.toString()));
		    }
		    
		    while (!operators.isEmpty()) {
		        String op = operators.pop();
		        ASTNode right = stack.pop();
		        ASTNode left = stack.pop();
		        stack.push(new ASTNode("operator", left, right, op));
		    }
		    
		    return stack.isEmpty() ? null : stack.pop();
	    }
	


    // Convert AST to JSON
    public String astToJson(ASTNode astNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(astNode);
        } catch (Exception e) {
            throw new RuntimeException("Error converting AST to JSON", e);
        }
    }
}
