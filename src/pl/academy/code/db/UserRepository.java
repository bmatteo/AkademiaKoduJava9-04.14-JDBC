package pl.academy.code.db;

import org.apache.commons.codec.digest.DigestUtils;
import pl.academy.code.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static Connection connection = null;

    public static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            UserRepository.connection = DriverManager.getConnection("jdbc:mysql://localhost/login?user=root&password=");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Zepsuło się łączenie do bazy !!");
            System.exit(0);
        }
    }

    /*public static void saveUser(User user) {
        String sql = "INSERT INTO tuser (login, pass) VALUES ('" + user.getLogin() + "', '" + user.getPassword() + "');";

        try {
            Statement s = UserRepository.connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public static void saveUser(User user) {
        String sql = "INSERT INTO tuser (login, pass) VALUES (?, ?)";

        try {
            PreparedStatement ps = UserRepository.connection.prepareStatement(sql);
            ps.setString(1, user.getLogin());
            ps.setString(2, DigestUtils.md5Hex(user.getPassword()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfUserExist(User user) {
        String sql = "SELECT * FROM tuser WHERE login = ?";

        try {
            PreparedStatement ps = UserRepository.connection.prepareStatement(sql);
            ps.setString(1, user.getLogin());

            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM tuser";

        try {
            PreparedStatement ps = UserRepository.connection.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                User user = new User();

                int idUserFromDatabase = resultSet.getInt("id");
                user.setId(idUserFromDatabase);

                String nameUserFromDatabase = resultSet.getString("login");
                user.setLogin(nameUserFromDatabase);

                String passwordUserFromDatabase = resultSet.getString("pass");
                user.setPassword(passwordUserFromDatabase);

                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static boolean authenticateUser(User user) {
        String sql = "SELECT * FROM tuser WHERE login = ?";

        try {
            PreparedStatement preparedStatement = UserRepository.connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getLogin());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String userPasswordFromDataBase = resultSet.getString("pass");

                return DigestUtils.md5Hex(user.getPassword())
                        .equals(userPasswordFromDataBase);
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
