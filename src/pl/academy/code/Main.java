package pl.academy.code;

import pl.academy.code.db.UserRepository;
import pl.academy.code.gui.GUI;
import pl.academy.code.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserRepository.connect();
        /*List<User> usersFromDatabse = UserRepository.getAllUsers();

        for(User user: usersFromDatabse) {
            System.out.println(user.getId() + " - " + user.getLogin() + " - " + user.getPassword());
        }*/
        GUI.appController();
    }
}
