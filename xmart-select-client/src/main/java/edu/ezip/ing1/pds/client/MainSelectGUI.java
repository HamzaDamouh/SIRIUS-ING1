package edu.ezip.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class MainSelectGUI extends JFrame {
    private JButton retrieveUsersButton;
    private JButton retrieveRecipesButton;
    private JButton retrieveRecipeOfDayButton;
    private JTextArea resultTextArea;

    public MainSelectGUI() {
        setTitle("Data Retrieval");
        setSize(700, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize buttons
        retrieveUsersButton = new JButton("Retrieve Users");
        retrieveRecipesButton = new JButton("Retrieve Recipes");
        retrieveRecipeOfDayButton = new JButton("Retrieve Recipes Of the Day");

        // Text area to display results
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(retrieveUsersButton);
        buttonPanel.add(retrieveRecipesButton);
        buttonPanel.add(retrieveRecipeOfDayButton);

        // Adding listeners to buttons
        retrieveUsersButton.addActionListener(this::retrieveUsers);
        retrieveRecipesButton.addActionListener(this::retrieveRecipes);
        retrieveRecipeOfDayButton.addActionListener(this::retrieveRecipesOfDay);
        // Adding components to the frame
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void retrieveUsers(ActionEvent e) {
        try {
            String usersInfo = SelectUser.retrieveUsers();
            resultTextArea.setText(usersInfo);
        } catch (IOException | InterruptedException | SQLException ex) {
            ex.printStackTrace();
            resultTextArea.setText("An error occurred while retrieving users.");
        }
    }

    private void retrieveRecipesOfDay(ActionEvent e) {
        try {
            String recipesInfo = SelectRecipeOfDay.retrieveRecipesOfDay();
            resultTextArea.setText(recipesInfo);
        } catch (IOException | InterruptedException | SQLException ex) {
            ex.printStackTrace();
            resultTextArea.setText("An error occurred while retrieving recipes.");
        }
    }

    private void retrieveRecipes(ActionEvent e) {
        try {
            String recipesInfo = SelectRecipe.retrieveRecipes();
            resultTextArea.setText(recipesInfo);
        } catch (IOException | InterruptedException | SQLException ex) {
            ex.printStackTrace();
            resultTextArea.setText("An error occurred while retrieving recipes.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainSelectGUI::new);
    }
}
