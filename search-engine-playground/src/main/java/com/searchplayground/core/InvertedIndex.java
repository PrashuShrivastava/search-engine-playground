package com.searchplayground.core;

import java.util.*;

public class InvertedIndex {

    // token -> set of document IDs
    private final Map<String, Set<Integer>> index = new HashMap<>();
    private final Tokenizer tokenizer = new Tokenizer();

    public void addDocument(Document doc) {
        List<String> tokens = tokenizer.tokenize(doc.getContent());

        for (String token : tokens) {
            index.computeIfAbsent(token, k -> new HashSet<>())
                    .add(doc.getId());
        }
    }

    public Set<Integer> searchSingleToken(String token) {
        if (token == null || token.isBlank()) {
            return Collections.emptySet();
        }
        token = token.toLowerCase();
        return index.getOrDefault(token, Collections.emptySet());
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
                return Collections.emptySet();
            }
        }

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

        return result;
    }

    public void printIndex() {
        System.out.println("---- Inverted Index ----");
        for (Map.Entry<String, Set<Integer>> entry : index.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
