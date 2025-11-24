package com.demo;

import javax.swing.*;
import java.awt.*;

public class QuizDashboardFrame extends JFrame {
    private String username;

    public QuizDashboardFrame(String username) {
        this.username = username;

        setTitle("Quiz Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        GradientPanel panel = new GradientPanel();
        panel.setLayout(new GridBagLayout());
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 1;
        panel.add(welcomeLabel, gbc);

        
        gbc.gridy++;
        JButton startQuizBtn = new JButton("Start Quiz");
        startQuizBtn.setFont(new Font("Arial", Font.BOLD, 18));
        startQuizBtn.setBackground(new Color(255, 140, 0)); // orange
        startQuizBtn.setForeground(Color.WHITE);
        startQuizBtn.setFocusPainted(false);
        panel.add(startQuizBtn, gbc);

        
        gbc.gridy++;
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 18));
        logoutBtn.setBackground(new Color(220, 20, 60)); // crimson
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        panel.add(logoutBtn, gbc);

        
        startQuizBtn.addActionListener(e -> {
            dispose();
            new QuizFrame(username).setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        setVisible(true);
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
