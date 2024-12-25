package com.searchengine.business;

import com.searchengine.api.GoogleSearchAPI;
import com.searchengine.database.DatabaseManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class SearchEngineController {
    private final DatabaseManager databaseManager = new DatabaseManager();

    public List<String> searchJournals(String query) throws Exception {
        String jsonResponse = GoogleSearchAPI.search(query);

        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray items = jsonObject.getAsJsonArray("items");

        List<String> results = new ArrayList<>();
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                JsonObject item = items.get(i).getAsJsonObject();
                String title = item.get("title").getAsString();
                String link = item.get("link").getAsString();
                results.add(title + " - " + link);

                databaseManager.saveSearchHistory(query, title, link);
            }
        }
        return results;
    }
}
