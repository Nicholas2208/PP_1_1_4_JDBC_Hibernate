package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String create_table = "CREATE TABLE IF NOT EXISTS usersdb.users (\n" +
                "    id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "    name VARCHAR(50),\n" +
                "    lastName VARCHAR(50),\n" +
                "    age TINYINT,\n" +
                "    PRIMARY KEY (id)\n" +
                ");";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(create_table);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String drop_table = "DROP TABLE IF EXISTS usersdb.users;";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(drop_table);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        String insertUser = "INSERT INTO users (name, lastName, age) VALUES (:name, :lastName, :age)";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(insertUser);
            query.setParameter("name", name);
            query.setParameter("lastName", lastName);
            query.setParameter("age", age);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        String deleteUser = "DELETE FROM users WHERE id = :id";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(deleteUser);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        String all_users = "SELECT id, name, lastName, age FROM usersdb.users;";
        try (Session session = Util.getSessionFactory().openSession()) {
            SQLQuery query = session.createSQLQuery(all_users);

            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                User user = new User();
                user.setId(Long.parseLong(row[0].toString()));
                user.setName(row[1].toString());
                user.setLastName(row[2].toString());
                user.setAge(Byte.parseByte(row[3].toString()));

                users.add(user);
            }

            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String clean_table = "DELETE FROM usersdb.users";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(clean_table).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
