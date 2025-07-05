package com.sej.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class api {

    private static final String API_KEY = ahajah-apinya";
    private static final String CX = "id-cx";

    public String search(String query) throws Exception {
        String urlString = String.format(
            "https://www.googleapis.com/customsearch/v1?q=%s&key=%s&cx=%s",
            query.replace(" ", "+"),
            API_KEY,
            CX
        );

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            return response.toString();
        } else {
            throw new Exception("Failed to fetch search results. HTTP code: " + responseCode);
        }
    }
}
