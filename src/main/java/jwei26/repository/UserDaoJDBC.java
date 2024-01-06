package jwei26.repository;

import jwei26.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC {
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/FindYourDish_db";
    private static final String USER = "admin";
    private static final String PASS = "FindYourDish123!";

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql;
            sql = "SELECT * FROM users";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Long id = rs.getLong("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                Timestamp registrationDate = rs.getTimestamp("registration_date");

                User user = new User();
                user.setUserId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setRegistrationDate(registrationDate);
                users.add(user);
            }
        } catch(SQLException e) {
            //retry
            //log.error()
        } finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                //log.error
            }
        }

        return users;
    }
}
