package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    static Connection usersdbConn = null;
    static PreparedStatement usersdbPrepareStat = null;

   public static Connection getDbConnection() {

       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           System.out.println("MySQL JDBC Driver Registered!");
           usersdbConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/usersdb", "root", "nbuser");

           if (usersdbConn != null) {
               System.out.println("Connection Successful!");
           } else {
               System.out.println("MySQL Connection Failed!");
           }

           return usersdbConn;
       } catch (SQLException | ClassNotFoundException e) {
           e.printStackTrace();
           return null;
       }
   }

}
