package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Nicholas", "White", (byte) 48);
        userService.saveUser("Helen", "Petrovsky", (byte) 52);
        userService.saveUser("Joe", "Black", (byte) 30);
        userService.saveUser("Arthur", "Peck", (byte) 35);

        //userService.removeUserById(3);

        List<User> allUsers = userService.getAllUsers();

        for (User user : allUsers) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
