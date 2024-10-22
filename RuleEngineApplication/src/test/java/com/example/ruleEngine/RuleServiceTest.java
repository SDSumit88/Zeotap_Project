package com.example.ruleEngine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ruleEngine.model.ASTNode;
import com.example.ruleEngine.service.RuleServiceImpl;

@SpringBootTest
public class RuleServiceTest {

    @InjectMocks
    private RuleServiceImpl ruleService;

    @Test
    public void testCreateIndividualRule() {
        String ruleString = "age > 30 AND salary > 5000";
        ASTNode astNode = ruleService.createRule(ruleString);

        assertNotNull(astNode);
        assertEquals("AND", astNode.getValue());
        
        // Adjust the comparison to normalize spaces
        assertEquals("age>30", astNode.getLeft().getValue().replaceAll("\\s+", ""));
        assertEquals("salary>5000", astNode.getRight().getValue().replaceAll("\\s+", ""));
    }

    @Test
    public void testCombineRules() {
    	 {
    	    // Sample rules as given
    	    List<String> rules = Arrays.asList(
    	        "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)",
    	        "((age > 30 AND department = 'Marketing')) AND (salary > 20000 OR experience > 5)"
    	    );
    	    System.out.println("Combining nodes: " + rules);

    	    // Attempt to combine the rules
    	    try {
    	        ASTNode combinedRule = ruleService.combineRules(rules);

    	        // Ensure the combined rule is not null and is structured as expected
    	        assertNotNull(combinedRule);

    	        // Additional assertions based on your specific implementation can be done here
    	        // For example, you could check the type of the root node of the AST and its children
    	        // assertEquals(expectedType, combinedRule.getType());
    	    } catch (IllegalArgumentException e) {
    	        fail("Expected valid combined rules but got an error: " + e.getMessage());
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        fail("Unexpected error occurred while combining rules: " + e.getMessage());
    	    }
    	}

    }

    @Test
    public void testEvaluateRule() {
        String ruleString = "age > 30 AND salary > 5000";
        ASTNode astNode = ruleService.createRule(ruleString);

        Map<String, Object> data = new HashMap<>();
        data.put("age", 35);
        data.put("salary", 6000);

        boolean result = ruleService.evaluateRule(astNode, data);
        assertTrue(result);
    }

    @Test
    public void testCombineAdditionalRules() {
    	 List<String> additionalRules = Arrays.asList(
    		        "age < 40 AND salary >= 30000",   // Simple rule
    		        "experience >= 2 OR (department = 'HR' AND age <= 25)", // Complex rule
    		        "(salary < 60000 AND experience <= 5) OR (age >= 50 AND salary > 80000)" // Nested rule
    		    );

    		    // Attempt to combine the additional rules
    		    try {
    		        ASTNode combinedAdditionalRule = ruleService.combineRules(additionalRules);

    		        // Ensure the combined rule is not null and check its structure
    		        assertNotNull(combinedAdditionalRule);

    		        // Additional assertions to verify the structure of the resulting AST
    		        // For example, check if the root node is of expected type or check its children
    		        // assertEquals(expectedType, combinedAdditionalRule.getType());
    		        
    		        // Validate that the expected logic is correctly reflected in the combined AST
    		        // You can inspect the nodes if your AST has a method to traverse or print its structure.
    		        // e.g., assertEquals(expectedStructure, combinedAdditionalRule.toString());
    		        
    		    } catch (IllegalArgumentException e) {
    		        fail("Expected valid combined rules but got an error: " + e.getMessage());
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		        fail("Unexpected error occurred while combining additional rules: " + e.getMessage());
    		    }
    }
}
