package com.searchplayground.core;

import java.util.*;

public class SearchEngineApp {

    public static void main(String[] args) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SearchEngineApp.class);

        logger.info("Starting SearchEngineApp");
        // 1. Create some sample documents
        List<Document> documents = List.of(
                new Document(1, "Chicken biryani with spicy masala"),
                new Document(2, "Fresh mutton and chicken available"),
                new Document(3, "Fish curry with coconut milk"),
                new Document(4, "Boneless chicken breast and chicken wings")
        );

        // 2. Build the inverted index
        InvertedIndex index = new InvertedIndex();
        for (Document doc : documents) {
            index.addDocument(doc);
        }

        index.printIndex();

        logger.info("Index built with {} entries", documents.size());

        // 3. Interactive search loop
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nType your search query.");
        System.out.println("  - AND search:  and chicken mutton");
        System.out.println("  - OR search:   or chicken mutton");
        System.out.println("  - Default AND: chicken mutton");
        System.out.println("Type 'exit' to quit.");

        while (true) {
            System.out.print("\nquery> ");
            String line = scanner.nextLine();

            if (line == null) {
                break;
            }

            line = line.trim();
            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            }

            if (line.isEmpty()) {
                System.out.println("Please enter some keywords.");
                continue;
            }

            // Split input into parts
            String[] parts = line.split("\\s+");
            Set<Integer> resultDocIds;
            List<String> tokens;

            // Decide query type based on first word
            if (parts[0].equalsIgnoreCase("and")) {
                // AND chicken mutton
                if (parts.length < 2) {
                    System.out.println("Please provide terms after 'and'.");
                    continue;
                }
                tokens = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));
                resultDocIds = index.searchAnd(tokens);
            } else if (parts[0].equalsIgnoreCase("or")) {
                // OR chicken mutton
                if (parts.length < 2) {
                    System.out.println("Please provide terms after 'or'.");
                    continue;
                }
                tokens = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));
                resultDocIds = index.searchOr(tokens);
            } else {
                // Default: AND search for all words
                tokens = Arrays.asList(parts);
                resultDocIds = index.searchAnd(tokens);
            }

            if (resultDocIds.isEmpty()) {
                System.out.println("No documents found.");
                logger.info("No documents matched for query: {}", line);
            } else {
                System.out.println("Matched document IDs: " + resultDocIds);

                logger.info("Query '{}' matched {} documents", line, resultDocIds.size());

                for (Document doc : documents) {
                    if (resultDocIds.contains(doc.getId())) {
                        System.out.println("Doc " + doc.getId() + ": " + doc.getContent());
                    }
                }
            }
        }

        scanner.close();
        logger.info("SearchEngineApp exiting");
    }
}
