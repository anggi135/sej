package com.searchengine.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GoogleSearchAPI {
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String CX = "YOUR_CX_ID";

    public static String search(String query) throws Exception {
        String url = "https://www.googleapis.com/customsearch/v1?q=" + query +
                     "&key=" + API_KEY + "&cx=" + CX;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
