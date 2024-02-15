package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
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

    @Override
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

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction tx = null;
        Session session = Util.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            tx.commit();
            log.info("User added!");
        } catch (Exception ex) {
            if (tx != null && tx.getStatus().canRollback()) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        Session session = Util.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            tx.commit();
            log.info("User ID " + id + "is deleted.");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> users = null;

        try {
            Query<User> query = session.createQuery("SELECT u FROM User u", User.class);
            users = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        Session session = Util.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            Query query = session.createNativeQuery("TRUNCATE TABLE User");
            query.executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
