package com.demo;

import java.sql.*;

public class ResultDAO {

    public void saveResult(String username, int score) {
        try (Connection conn = DBConnection.getConnection()) {
           
            PreparedStatement psUser = conn.prepareStatement("SELECT id FROM users WHERE username=?");
            psUser.setString(1, username);
            ResultSet rs = psUser.executeQuery();
            if (rs.next()) {
            int userId = rs.getInt("id");

            
            PreparedStatement psResult = conn.prepareStatement(
            "INSERT INTO results(user_id, score) VALUES(?, ?)"
              );
            psResult.setInt(1, userId);
            psResult.setInt(2, score);
            psResult.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
