package com.searchengine.ui;

import com.searchengine.business.SearchEngineController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SearchForm extends JFrame {
    private JTextField searchField;
    private JTextArea resultsArea;

    public SearchForm() {
        setTitle("Search Engine Jurnal");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel dengan gambar latar
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("resources/background.jpg").getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Header
        JLabel header = new JLabel("Search Engine Jurnal", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        backgroundPanel.add(header, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new FlowLayout());

        searchField = new JTextField(30);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this::performSearch);

        inputPanel.add(new JLabel("Masukkan Kata Kunci:", SwingConstants.LEFT));
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        backgroundPanel.add(inputPanel, BorderLayout.CENTER);

        // Results Area
        resultsArea = new JTextArea();
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultsArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        backgroundPanel.add(scrollPane, BorderLayout.SOUTH);
    }

    private void performSearch(ActionEvent e) {
        String query = searchField.getText();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kata kunci tidak boleh kosong!");
            return;
        }

        try {
            SearchEngineController controller = new SearchEngineController();
            List<String> results = controller.searchJournals(query);

            resultsArea.setText("");
            for (String result : results) {
                resultsArea.append(result + "\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SearchForm form = new SearchForm();
            form.setVisible(true);
        });
    }
}
