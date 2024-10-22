package com.example.ruleEngine.model;

public class ASTNode {
    private String type; // e.g., "operand" or "operator"
    private String value; // e.g., the condition or operator
    private ASTNode left; // Left child (if any)
    private ASTNode right; // Right child (if any)
    private String ruleString; // The string representation of the AST node
    private Long id;
 

    // Constructor for operators (with left and right nodes)
    public ASTNode(String type, ASTNode left, ASTNode right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value; // Set the value (the operator)
    }

    // Constructor for operands (no children)
    public ASTNode(String type, String value) {
    	 this.type = type;
         this.value = value;
         this.left = null;
         this.right = null;
    }
    
 

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ASTNode getLeft() {
        return left;
    }

    public void setLeft(ASTNode left) {
        this.left = left;
    }

    public ASTNode getRight() {
        return right;
    }

    public void setRight(ASTNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "ASTNode{" + "type='" + type + '\'' + ", value='" + value + '\'' + '}';
    }
  
    
    public ASTNode(String ruleString, Long id) {
        this.ruleString = ruleString;
        this.id = id;
    }

    // Getters
    public String getRuleString() {
        return ruleString;
    }

    public Long getId() {
        return id;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    
    

    public String getCombinedRuleString() {
    	 if (this.type.equals("operand")) {
    	        return this.value;  // Leaf node, just return the condition
    	    } else if (this.type.equals("operator")) {
    	        return "(" + this.left.getRuleString() + " " + this.value + " " + this.right.getRuleString() + ")";
    	    }
    	    return "";
    }

    private boolean isOperator(String value) {
        return value.equals("AND") || value.equals("OR");
    }
}
