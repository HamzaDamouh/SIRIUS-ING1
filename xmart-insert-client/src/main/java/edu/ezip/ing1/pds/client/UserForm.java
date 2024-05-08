package edu.ezip.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.ezip.ing1.pds.business.dto.User;

import static java.lang.String.valueOf;

public class UserForm extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField genderField;
    private JTextField ageField;
    private JTextField heightField;
    private JTextField weightField;

    private JButton submitButton;

    private User user;

    public UserForm(){
        setTitle("User Information Form");
        setSize(800, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create text fields for user input
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        emailField = new JTextField(20);
        genderField = new JTextField(20);
        ageField = new JTextField(20);
        heightField = new JTextField(20);
        weightField = new JTextField(20);

        // Create submit button
        submitButton = new JButton("Submit");

        // Add action listener to submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });
        // Create layout for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2));
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Height (cm):"));
        formPanel.add(heightField);
        formPanel.add(new JLabel("Weight (kg):"));
        formPanel.add(weightField);
        formPanel.add(new JLabel(""));
        formPanel.add(submitButton);

        // Add form panel to frame
        add(formPanel);

        // Set the frame visible
        setVisible(true);
    }

    private void submitForm() {
        // Create a new User object with the input values
        user = new User();
        user.setFirstname(firstNameField.getText());
        user.setLastname(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setGender(genderField.getText());
        user.setAge(ageField.getText());
        user.setHeight(heightField.getText());
        user.setWeight(weightField.getText());

        // Calculate calories necessary for the day

        String calories = valueOf(calculateCalories(user));
        user.setCalories(calories);

        // Close the form
        dispose();
    }
    private double calculateCalories(User user) {
        // Calculate Basal Metabolic Rate (BMR)
        double bmr;
        if (user.getGender().equalsIgnoreCase("male")) {
            bmr = 10 * Double.parseDouble(user.getWeight()) +
                    6.25 * Double.parseDouble(user.getHeight()) -
                    5 * Double.parseDouble(user.getAge()) + 5;
        } else {
            bmr = 10 * Double.parseDouble(user.getWeight()) +
                    6.25 * Double.parseDouble(user.getHeight()) -
                    5 * Double.parseDouble(user.getAge()) - 161;
        }

        // Adjust for activity level (considering a sedentary lifestyle)
        // You can adjust this factor based on different activity levels
        double activityFactor = 1.2;
        double calories = bmr * activityFactor;

        return calories;
    }

    public User getUser() {
        return user;
    }

    public void waitForSubmission() {
        // Block until the form is submitted
        while (user == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
