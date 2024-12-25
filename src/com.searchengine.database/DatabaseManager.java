package com.searchengine.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:search_engine.db";

    public DatabaseManager() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String createTable = "CREATE TABLE IF NOT EXISTS search_history (" +
                                 "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                 "query TEXT, " +
                                 "title TEXT, " +
                                 "url TEXT)";
            Statement stmt = conn.createStatement();
            stmt.execute(createTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSearchHistory(String query, String title, String url) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String insert = "INSERT INTO search_history(query, title, url) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insert);
            pstmt.setString(1, query);
            pstmt.setString(2, title);
            pstmt.setString(3, url);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
