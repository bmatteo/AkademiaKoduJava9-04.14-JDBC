package pl.academy.code.gui;

import pl.academy.code.db.UserRepository;
import pl.academy.code.model.User;

import java.util.Scanner;

public class GUI {
    private static int helloMenu() {
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            Scanner scanner = new Scanner(System.in);
            //int choice = Integer.parseInt(scanner.nextLine());

            String choice = scanner.nextLine();

            if(choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                return Integer.parseInt(choice);
            }
        }
    }

    private static User register() {
        while (true) {
            System.out.println("Login: ");
            Scanner scanner = new Scanner(System.in);
            String login = scanner.nextLine();

            System.out.println("Password: ");
            String password = scanner.nextLine();

            System.out.println("Repeat password: ");
            String repeatedPassword = scanner.nextLine();

            if(password.equals(repeatedPassword)) {
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);

                return user;
            } else {
                System.out.println("Passwords does not mach !!");
            }
        }
    }

    private static User login() {
        System.out.println("Login: ");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();

        System.out.println("Password: ");
        String password = scanner.nextLine();

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        return user;
    }

    public static void appController() {
        while (true) {
            int choice = helloMenu();
            switch (choice) {
                case 1:
                    User user = register();
                    if(!UserRepository.checkIfUserExist(user)) {
                        UserRepository.saveUser(user);
                    }
                    break;
                case 2:
                    User userToAuth = login();
                    boolean isOk = UserRepository.authenticateUser(userToAuth);
                    if(isOk) {
                        System.out.println("Zalogowano !!!");
                    } else {
                        System.out.println("Niezalogowano !!!");
                    }
                    break;
                case 3:
                    return;
            }
        }
    }
}
