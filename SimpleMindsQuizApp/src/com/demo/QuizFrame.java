package com.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizFrame extends JFrame {
    private String username;
    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;

    private JLabel questionLabel;
    private JRadioButton option1, option2, option3, option4;
    private ButtonGroup optionsGroup;
    private JButton nextButton;

    public QuizFrame(String username) {
    this.username = username;
    setTitle("Quiz - " + username);
    setSize(700, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

       questions = loadQuestions();
       if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions in DB!");
            dispose();
            return;
        }

        
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setForeground(Color.WHITE);
        mainPanel.add(questionLabel, BorderLayout.NORTH);

    
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setOpaque(false); // transparent

        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

      
        option1.setFont(new Font("Arial", Font.PLAIN, 16));
        option2.setFont(new Font("Arial", Font.PLAIN, 16));
        option3.setFont(new Font("Arial", Font.PLAIN, 16));
        option4.setFont(new Font("Arial", Font.PLAIN, 16));

        option1.setForeground(Color.WHITE);
        option2.setForeground(Color.WHITE);
        option3.setForeground(Color.WHITE);
        option4.setForeground(Color.WHITE);

       
        option1.setOpaque(false);
        option2.setOpaque(false);
        option3.setOpaque(false);
        option4.setOpaque(false);

        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);

        optionsPanel.add(option1);
        optionsPanel.add(option2);
        optionsPanel.add(option3);
        optionsPanel.add(option4);

        
        JPanel optionsWrapper = new JPanel();
        optionsWrapper.setOpaque(false);
        optionsWrapper.setLayout(new GridBagLayout());
        optionsWrapper.add(optionsPanel);
        mainPanel.add(optionsWrapper, BorderLayout.CENTER);

       
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setBackground(new Color(255, 140, 0));
        nextButton.setForeground(Color.WHITE);
        mainPanel.add(nextButton, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            if (!isOptionSelected()) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Please select an answer before moving on!");
                return;
            }
            checkAnswer();
            currentIndex++;
            if (currentIndex < questions.size()) {
                loadQuestion(currentIndex);
            } else {
                showScore();
            }
        });

        loadQuestion(0);
        setVisible(true);
    }

    private List<Question> loadQuestions() {
        List<Question> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM questions")) {

            while (rs.next()) {
            list.add(new Question(
             rs.getInt("id"),
             rs.getString("question_text"),
             rs.getString("option1"),
             rs.getString("option2"),
             rs.getString("option3"),
             rs.getString("option4"),
             rs.getInt("correct_option")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

        private void loadQuestion(int index) {
        Question q = questions.get(index);
        questionLabel.setText((index + 1) + ". " + q.getQuestionText());
        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());
        option4.setText(q.getOption4());
        optionsGroup.clearSelection();
    }

    private boolean isOptionSelected() {
        return option1.isSelected() || option2.isSelected() ||
               option3.isSelected() || option4.isSelected();
    }

    private void checkAnswer() {
        Question q = questions.get(currentIndex);
        int selected = -1;
        if (option1.isSelected()) selected = 1;
        else if (option2.isSelected()) selected = 2;
        else if (option3.isSelected()) selected = 3;
        else if (option4.isSelected()) selected = 4;

        if (selected == q.getCorrectOption()) {
            score++;
        }
    }

    private void showScore() {
        new ResultDAO().saveResult(username, score);
        JOptionPane.showMessageDialog(this,
       "Quiz Over!\nYour Score: " + score + "/" + questions.size(),
         "Result",
          JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new QuizDashboardFrame(username).setVisible(true);
    }

    // Gradient panel
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(72, 61, 139);
            Color color2 = new Color(138, 43, 226);
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
