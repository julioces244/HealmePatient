package com.tecsup.apaza.healmepaciente.errors;

import java.util.List;
import java.util.Map;

public class ErrorResponse {

    public String message;
    public Map<String, List<String>> errors;

    public ErrorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
