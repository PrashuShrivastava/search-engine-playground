package com.searchplayground.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class InvertedIndex {

    private static final Logger logger = LoggerFactory.getLogger(InvertedIndex.class);

    // token -> set of document IDs
    private final Map<String, Set<Integer>> index = new HashMap<>();
    private final Tokenizer tokenizer = new Tokenizer();

    public void addDocument(Document doc) {
        if (doc == null) {
            logger.warn("Attempted to add null document");
            return;
        }

        List<String> tokens = tokenizer.tokenize(doc.getContent());
        logger.debug("Adding document id={} tokens={}", doc.getId(), tokens);

        for (String token : tokens) {
            index.computeIfAbsent(token, k -> new HashSet<>())
                    .add(doc.getId());
        }

        logger.info("Document {} indexed ({} tokens)", doc.getId(), tokens.size());
    }

    public Set<Integer> searchSingleToken(String token) {
        if (token == null || token.isBlank()) {
            return Collections.emptySet();
        }
        token = token.toLowerCase();
        Set<Integer> result = index.getOrDefault(token, Collections.emptySet());
        logger.debug("searchSingleToken('{}') -> {}", token, result);
        return result;
    }

    /**
     * AND search: all tokens must be present.
     */
    public Set<Integer> searchAnd(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            return Collections.emptySet();
        }

        List<String> normalized = new ArrayList<>();
        for (String t : tokens) {
            if (t != null && !t.isBlank()) {
                normalized.add(t.toLowerCase());
            }
        }

        if (normalized.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Integer> result = null;

        for (String token : normalized) {
            Set<Integer> posting = index.getOrDefault(token, Collections.emptySet());

            if (result == null) {
                // first token
                result = new HashSet<>(posting);
            } else {
                // intersection for AND
                result.retainAll(posting);
            }

            if (result.isEmpty()) {
                logger.debug("AND search tokens={} -> empty after token={}", normalized, token);
                return Collections.emptySet();
            }
        }

        logger.debug("AND search tokens={} -> {}", normalized, result);
        return result != null ? result : Collections.emptySet();
    }

    /**
     * OR search: union of all posting lists.
     */
    public Set<Integer> searchOr(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Integer> result = new HashSet<>();

        for (String token : tokens) {
            if (token == null || token.isBlank())
                continue;

            token = token.toLowerCase();

            // Posting list for this token
            Set<Integer> posting = index.getOrDefault(token, Collections.emptySet());

            // Add all doc IDs from posting to result
            result.addAll(posting);
        }

        logger.debug("OR search tokens={} -> {}", tokens, result);
        return result;
    }

    public void printIndex() {
        logger.info("---- Inverted Index ----");
        for (Map.Entry<String, Set<Integer>> entry : index.entrySet()) {
            logger.info("{} -> {}", entry.getKey(), entry.getValue());
        }
    }
}
