
package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import org.hibernate.boot.model.relational.Database;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl a = new UserDaoJDBCImpl();
        a.createUsersTable();
        a.saveUser("Иван", "Иванов", (byte) 24);
        a.saveUser("Василий", "Петров", (byte) 42);
        a.saveUser("Алексей", "Алексеев", (byte) 30);
        a.saveUser("Елена", "Васечкина", (byte) 15);
        System.out.println(a.getAllUsers());
        a.cleanUsersTable();
        a.dropUsersTable();

    }
}
