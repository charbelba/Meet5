package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * This class provides services related to user interactions, such as recording visits and likes.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Records a user visit based on the provided request.
     *
     * @param request The request containing visit information.
     * @return An Optional containing a success message or failure message.
     */
    public Optional<String> recordVisit(UserVisitRequestModel request) {

        try {
            jdbcTemplate.update(Queries.sqlVisit,
                    request.getUserAId(),
                    request.getUserBId(),
                    Timestamp.valueOf(request.getVisitTime()));

            checkIfUserFraud(request.getUserAId());
            return Optional.of("Visit recorded successfully.");
        } catch (DataAccessException e) {
            logger.error("Failed to record visit.", e);
            return Optional.of("Failed to record visit.");
        }
    }

    /**
     * Records a user like based on the provided request.
     *
     * @param request The request containing like information.
     * @return An Optional containing a success message or failure message.
     */
    public Optional<String> recordLike(UserLikeRequestModel request) {
        try {
            jdbcTemplate.update(Queries.sqlLike, request.getUserAId()
                    ,request.getUserBId()
                    ,Timestamp.valueOf(request.getLikeTime()));

            checkIfUserFraud(request.getUserAId());
            return Optional.of("Like recorded successfully.");
        } catch (DataAccessException e) {
            logger.error("Failed to record like.", e);
            return Optional.of("Failed to record like.");
        }
    }

    /**
     * Checks if a user's activities indicate fraudulent behavior.
     *
     * @param userId The ID of the user to check for fraud.
     */
    private void checkIfUserFraud(Long userId) {
        logger.debug("Checking if user is fraud");
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minus(10, ChronoUnit.MINUTES);

        try {
            int combinedCount = jdbcTemplate.queryForObject(Queries.sqlCheckFraud, Integer.class, userId, tenMinutesAgo, userId, tenMinutesAgo);

            if (combinedCount >= 200) {
                markUserAsFraud(userId);
            }
        } catch (DataAccessException e) {
            logger.error("Failed to check user fraud status.", e);
        }
    }

    /**
     * Marks a user as fraudulent.
     *
     * @param userId The ID of the user to mark as fraud.
     */
    private void markUserAsFraud(Long userId) {
        try {
            jdbcTemplate.update(Queries.sqlUpdateFraudStatus, userId);
            logger.info("User with ID {} marked as fraud.", userId);
        } catch (DataAccessException e) {
            logger.error("Failed to mark user as fraud.", e);
        }
    }

    /**
     * Creates a test user.
     */
    public void createUser() {
        try {
            jdbcTemplate.update(Queries.sqlUser, "email", "cba");
        } catch (DataAccessException e) {
            logger.error("Test query failed.", e);
        }
    }

    /**
     * Bulk inserts a list of user visits into the database.
     *
     * @param visits The list of user visits to insert.
     */
    public void bulkInsertVisits(List<UserVisitRequestModel> visits) {
        String sql = "INSERT INTO user_visit (user_a_id, user_b_id, visit_time) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                UserVisitRequestModel visit = visits.get(i);
                preparedStatement.setLong(1, visit.getUserAId());
                preparedStatement.setLong(2, visit.getUserBId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            }

            @Override
            public int getBatchSize() {
                return visits.size();
            }
        });
    }

    /**
     * Bulk inserts a list of user likes into the database.
     *
     * @param likes The list of user likes to insert.
     */
    public void bulkInsertLikes(List<UserLikeRequestModel> likes) {
        String sql = "INSERT INTO user_like (user_a_id, user_b_id, like_time) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                UserLikeRequestModel like = likes.get(i);
                preparedStatement.setLong(1, like.getUserAId());
                preparedStatement.setLong(2, like.getUserBId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            }

            @Override
            public int getBatchSize() {
                return likes.size();
            }
        });
    }
}
