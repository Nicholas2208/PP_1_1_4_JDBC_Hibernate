package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Util {
    private static Connection usersdbConn = null;
    public static Connection getDbConnection() {

       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           usersdbConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/usersdb", "root", "nbuser");
       } catch (SQLException | ClassNotFoundException e) {
           e.printStackTrace();
       }

        return usersdbConn;
   }

}
