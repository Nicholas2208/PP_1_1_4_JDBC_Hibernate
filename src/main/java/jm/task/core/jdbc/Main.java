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

        User john = new User("John", "Doe", (byte) 40);
        userService.saveUser(john.getName(), john.getLastName(), john.getAge());
        User joanne = new User("Joanne", "Smith", (byte) 35);
        userService.saveUser(joanne.getName(), joanne.getLastName(), joanne.getAge());
        User thomas = new User("Thomas", "Graham", (byte) 21);
        userService.saveUser(thomas.getName(), thomas.getLastName(), thomas.getAge());
        User david = new User("David", "Todd", (byte) 16);
        userService.saveUser(david.getName(), david.getLastName(), david.getAge());
        System.out.println("\n\n");

        //userService.removeUserById(3);

        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
