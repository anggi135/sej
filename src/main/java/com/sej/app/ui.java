package com.sej.app;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;

public class ui {

    public static void main(String[] args) {
        // Create a new frame
        JFrame frame = new JFrame("Search Engine Jurnal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Logo panel
        JPanel logoPanel = new JPanel();
        JLabel logoLabel = new JLabel(new ImageIcon("/src/main/java/assets/logo.jpeg")); // Replace with actual path
        logoPanel.add(logoLabel);

        // Search panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel("Search:");
        JTextField searchField = new JTextField(40);
        JButton searchButton = new JButton("Search");
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(label, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchPanel.add(searchField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        searchPanel.add(searchButton, gbc);

        // Result area
        JEditorPane resultArea = new JEditorPane();
        resultArea.setEditable(false);
        resultArea.setContentType("text/html");
        resultArea.setText("<html><body></body></html>");
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add hyperlink listener
        resultArea.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI(e.getURL().toString()));
                    } else {
                        JOptionPane.showMessageDialog(frame, "Browsing is not supported on this platform.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to open URL: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Controller instance
        con controller = new con();

        // Search button action listener
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a search query.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Perform search
                String rawResult = controller.handleSearchRequest(query);

                // Parse and display results
                String parsedResult = parseResults(rawResult);
                resultArea.setText(parsedResult);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Display frame
        frame.setSize(800, 600);  // Set initial size
        frame.setLocationRelativeTo(null);  // Center the window
        frame.setVisible(true);
    }

    // Method to parse and format the JSON results as HTML
    private static String parseResults(String json) {
        StringBuilder result = new StringBuilder("<html><body style='font-family: Arial, sans-serif; line-height: 1.6; margin: 0; padding: 20px;'>");

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String title = item.getString("title");
                String link = item.getString("link");
                String snippet = item.optString("snippet", "No snippet available");
                String formattedUrl = item.optString("formattedUrl", "No URL available");

                result.append("<div style='margin-bottom: 20px; padding: 15px; border: 1px solid #ddd; border-radius: 5px;'>");
                result.append("<h2 style='margin: 0;'><a href='").append(link).append("' style='text-decoration: none; color: #1a73e8;'>").append(title).append("</a></h2>");
                result.append("<p style='margin: 5px 0; color: #555;'>").append(snippet).append("</p>");
                result.append("<p style='margin: 5px 0; font-size: 0.9em; color: #777;'><b>Sumber:</b> ").append(formattedUrl).append("</p>");
                result.append("</div>");
            }
        } catch (Exception e) {
            result.append("<p style='color: red;'>Error parsing results: ").append(e.getMessage()).append("</p>");
        }

        result.append("</body></html>");
        return result.toString();
    }
}
