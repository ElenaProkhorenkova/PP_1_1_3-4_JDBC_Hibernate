package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection conn = Util.connect();
        String sql = "CREATE TABLE IF NOT EXISTS User ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "lastName VARCHAR(255) NOT NULL,"
                + "age INT)";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Util.closeConn();
    }


    public void dropUsersTable() {
        Connection conn = Util.connect();
        String sql = "DROP TABLE IF EXISTS User ";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Util.closeConn();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection conn = Util.connect();
        String sql = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)";

        // Выполнение SQL запроса
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();
            conn.commit();


            System.out.println("User added!");

        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }
        Util.closeConn();
    }

    public void removeUserById(long id) throws SQLException {
        Connection conn = Util.connect();
        String sql = "DELETE FROM User WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            long rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                conn.commit();
                System.out.println("User ID " + id + " deleted.");
            } else {
                System.out.println("User ID " + id + " not found.");
            }
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }
        Util.closeConn();

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection conn = Util.connect();
        String sql = "SELECT * FROM User";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");
                users.add(new User(id, name, lastName, age));
                conn.commit();
            }

        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }
        Util.closeConn();

        return users;
    }

    public void cleanUsersTable() {
        Connection conn = Util.connect();
        String sql = "TRUNCATE TABLE User";

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("Table User is cleaned.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Util.closeConn();

    }
}
