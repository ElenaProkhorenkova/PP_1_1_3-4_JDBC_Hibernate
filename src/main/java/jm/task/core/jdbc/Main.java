
package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserDao a = new UserDaoHibernateImpl();
        a.createUsersTable();
        a.saveUser("Иван", "Иванов", (byte) 24);
        a.saveUser("Василий", "Петров", (byte) 42);
        a.removeUserById(1);
        a.saveUser("Алексей", "Алексеев", (byte) 30);
        a.saveUser("Елена", "Васечкина", (byte) 15);
        System.out.println(a.getAllUsers());
        a.cleanUsersTable();
        a.dropUsersTable();

    }
}
