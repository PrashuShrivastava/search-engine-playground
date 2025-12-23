package com.searchplayground.core;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();

        if (text == null || text.isEmpty()) {
            return tokens;
        }

        // Lowercase
        text = text.toLowerCase();

        // Replace non-letters with spaces
        text = text.replaceAll("[^a-z]", " ");

        // Split on whitespace
        String[] parts = text.split("\\s+");

        for (String part : parts) {
            if (!part.isBlank()) {
                tokens.add(part);
            }
        }

        return tokens;
    }
}
