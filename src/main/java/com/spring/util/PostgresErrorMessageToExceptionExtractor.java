package com.spring.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class PostgresErrorMessageToExceptionExtractor {

    public String getExceptionDetails(RuntimeException ex) {
        String message = ex.getCause().getCause().getMessage();

        if (isErrorDublicateKeyValueError(message)) {
            return getDublicateKeyValueErrorDetails(message);
        }
        return "Unhandled";
    }

    private boolean isErrorDublicateKeyValueError(String message) {
        String regex = "ERROR: duplicate key value violates unique constraint";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(message);
        boolean bool = matcher.find();
        return bool;
    }

    private String getDublicateKeyValueErrorDetails(String message) {
        String emailRegex = "\\(email\\)";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(message);
        String dublicateKeyName = matcher.find() ? "email" : "username";
        if (dublicateKeyName == "email") {
            return "Email is already used";
        }
        return "Username is already used";
    }

}