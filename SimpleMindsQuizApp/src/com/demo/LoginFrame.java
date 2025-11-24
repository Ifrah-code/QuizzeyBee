package com.demo;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn;

    public LoginFrame() {
      
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       
        GradientPanel panel = new GradientPanel();
        panel.setLayout(new GridBagLayout());
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        
        JLabel titleLabel = new JLabel("SimpleMinds Quiz", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel, gbc);

        
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

       
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        panel.add(passLabel, gbc);

        
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        
        gbc.gridx = 0;
        gbc.gridy++;
        loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(255, 140, 0));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(loginBtn, gbc);

        gbc.gridx = 1;
        registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(0, 191, 255));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(registerBtn, gbc);

        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JTextArea rules = new JTextArea(
        	    "Quiz Rules (Be Smart):\n" +
        	    "1. No googling – AI knows your secrets.\n" +
        	    "2. Don’t ask friends – telepathy not allowed .\n" +
        	    "3. One answer per question – multiple choice, not multiple guesses .\n" +
        	    "4. No bribing the computer  – it’s incorruptible.\n" +
        	    "5. Wrong answers are just plot twists .\n" +
        	    "6. Take a deep breath before answering  – stress kills brain cells.\n" +
        	    "7. Have fun and pretend you’re on a game show ."
        	);
        	rules.setForeground(Color.WHITE);
        	rules.setFont(new Font("Arial", Font.PLAIN, 14));
        	rules.setEditable(false);
        	rules.setOpaque(false);
        	rules.setLineWrap(true);
        	rules.setWrapStyleWord(true);
        	panel.add(rules, gbc);

        
        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());

        setVisible(true);
    }

        private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                new QuizDashboardFrame(username).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users(username, password) VALUES(?, ?)"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "User Registered! Please Login.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Username already exists!");
        }
    }

   
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(72, 61, 139); // royal blue
            Color color2 = new Color(138, 43, 226); // blue violet
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
