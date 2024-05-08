package edu.ezip.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class MainCaloriesForUserGUI extends JFrame {

    private JTextField emailTextField;
    private JButton submitButton;
    private JTextArea resultTextArea;

    public MainCaloriesForUserGUI() {
        setTitle("Calorie Retrieval");
        setSize(700, 500);  // Adjusted size for specific functionality
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel with label and text field
        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel emailLabel = new JLabel("Enter User Email: ");
        emailTextField = new JTextField();
        inputPanel.add(emailLabel, BorderLayout.WEST);
        inputPanel.add(emailTextField, BorderLayout.CENTER);

        // Button to submit the email
        submitButton = new JButton("Retrieve Calories");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String userEmail = emailTextField.getText();
                    if (userEmail.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Email field cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String caloriesInfo = SelectUser.retrieveUsers();
                    resultTextArea.setText(caloriesInfo);
                } catch (IOException | InterruptedException | SQLException ex) {
                    ex.printStackTrace();
                    resultTextArea.setText("An error occurred while retrieving calories.");
                }
            }
        });

        // Text area to display results
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        // Panel to hold the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCaloriesForUserGUI();
            }
        });
    }
}
