package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public Connection getConnection() {
        return Util.getDbConnection();
    }

    public void createUsersTable() {
        String create_table = "CREATE TABLE IF NOT EXISTS usersdb.users (\n" +
                "    id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "    name VARCHAR(50),\n" +
                "    lastName VARCHAR(50),\n" +
                "    age TINYINT,\n" +
                "    PRIMARY KEY (id)\n" +
                ");";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(create_table)) {
            preparedStatement.execute(create_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String dropUsers = "DROP TABLE IF EXISTS users;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dropUsers)) {
            preparedStatement.execute(dropUsers);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUser = "INSERT INTO users (name, lastName, age) values (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.printf("\nUser с именем %s добавлен в базу данных", name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String removeUser = "DELETE FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String selectAll = "SELECT * FROM users";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAll)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");
                User user = new User(name, lastName, age);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allUsers;
    }

    public void cleanUsersTable() {
        String truncateUsers = "TRUNCATE users;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(truncateUsers)) {
            preparedStatement.execute(truncateUsers);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
