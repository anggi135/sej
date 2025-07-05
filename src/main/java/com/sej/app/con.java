package com.sej.app;

public class con {

    private final api api;

    public con() {
        api = new api();
    }

    public String handleSearchRequest(String query) {
        if (query == null || query.trim().isEmpty()) {
            return "Query cannot be empty!";
        }

        try {
            // Call the API to get search results
            return api.search(query);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing the request: " + e.getMessage();
        }
    }
}
