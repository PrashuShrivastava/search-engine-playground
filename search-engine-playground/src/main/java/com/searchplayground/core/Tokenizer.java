package com.searchplayground.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private static final Logger logger = LoggerFactory.getLogger(Tokenizer.class);

    public List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();

        if (text == null || text.isEmpty()) {
            logger.debug("Empty input to tokenize()");
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

        logger.debug("tokenize() -> {} tokens", tokens.size());
        return tokens;
    }
}
