package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection conn = null;
    Statement statement = null;
    PreparedStatement ps = null;
    public UserDaoJDBCImpl() {
         conn = Util.getDbConnection();
    }

    public void createUsersTable() {
        String create_table = "CREATE TABLE IF NOT EXISTS usersdb.users (\n" +
                "    id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "    name VARCHAR(50),\n" +
                "    lastName VARCHAR(50),\n" +
                "    age TINYINT,\n" +
                "    PRIMARY KEY (id)\n" +
                ");";

        try {
            statement = conn.createStatement();
            statement.execute(create_table);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void dropUsersTable() {
        try {
            String dropUsers = "DROP TABLE IF EXISTS users;";
            statement = conn.createStatement();
            statement.execute(dropUsers);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String insertUser = "INSERT INTO users (name, lastName, age) values (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertUser);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            ps.executeUpdate();
            ps.close();
            System.out.printf("\nUser с именем %s добавлен в базу данных", name);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try {
            String selectAll = "SELECT * FROM users";
            ps = conn.prepareStatement(selectAll);
            ResultSet rs = ps.executeQuery();
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
        try {
            String truncateUsers = "TRUNCATE users;";
            statement = conn.createStatement();
            statement.execute(truncateUsers);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
