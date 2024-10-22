

package com.example.ruleEngine.model; // Update this line based on your package structure

public class EvaluationResponse {
    private boolean success;
    private boolean result; // This will hold the evaluation result
    private String message; // This can hold any error message

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

