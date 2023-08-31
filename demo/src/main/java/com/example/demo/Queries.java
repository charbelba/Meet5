package com.example.demo;

public interface Queries {
      String sqlUser = "INSERT INTO user (email, username) VALUES (?, ?)";
      String sqlVisit = "INSERT INTO user_visit (user_a_id, user_b_id, visit_time) VALUES (?, ?, ?)";
      String sqlLike = "INSERT INTO user_like (user_a_id, user_b_id, like_time) VALUES (?, ?, ?)";

      String sqlCheckFraud = "SELECT COUNT(*) FROM (" +
            "    SELECT user_a_id FROM user_visit WHERE user_a_id = ? AND visit_time >= ? " +
            "    UNION ALL " +
            "    SELECT user_a_id FROM user_like WHERE user_a_id = ? AND like_time >= ? " +
            ") AS combined_actions";
      String sqlUpdateFraudStatus = "UPDATE user SET is_fraud = true WHERE id = ?";


      String allProfileVisitors ="SELECT user_a_id AS visitor_id, visit_time" +
              "FROM user_visit" +
              "WHERE user_b_id = ?" +
              "ORDER BY visit_time DESC";
}
