package com.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            Question q = new Question(
             rs.getInt("id"),                 
             rs.getString("question_text"),
             rs.getString("option1"),
             rs.getString("option2"),
             rs.getString("option3"),
             rs.getString("option4"),
             rs.getInt("correct_option")
                );
                questions.add(q);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching questions: " + e.getMessage());
        }
        return questions;
    }
}
