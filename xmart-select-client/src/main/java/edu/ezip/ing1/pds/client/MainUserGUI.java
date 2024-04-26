package edu.ezip.ing1.pds.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class MainUserGUI extends JFrame {
    private JButton executeButton;
    private JTextArea resultTextArea;

    public MainUserGUI() {
        setTitle("User Retrieval");
        setSize(700, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        executeButton = new JButton("Retrieve Users");
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String usersInfo = SelectUser.retrieveUsers();
                    resultTextArea.setText(usersInfo);
                } catch (IOException | InterruptedException | SQLException ex) {
                    ex.printStackTrace();
                    resultTextArea.setText("An error occurred while retrieving users.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(executeButton);

        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainUserGUI();
            }
        });
    }
}