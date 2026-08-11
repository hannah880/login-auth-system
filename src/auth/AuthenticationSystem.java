package auth;

import java.util.ArrayList;
public class AuthenticationSystem {

private ArrayList<User> users = new ArrayList<>();


    public boolean registerUser(String username, String password) {

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            System.out.println("Username and password cannot be empty.");
            return false;
}
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already taken.");
                return false;

            }
        }
        User newUser = new User(username, password);
        users.add(newUser);
        System.out.println("Account created!");
        return true;
    }

    public boolean loginUser(String username, String password) {

        for (User user : users) {
            if (user.getUsername().equals(username)) {

                if (user.isLocked()) {
                    System.out.println("Accont is locked due to many failed attempts.");
                    return false;
                }
                if (user.checkPassword(password)) {
                    user.resetAttempts();
                    return true;
                } else {
                    user.registerFailedAttempt();
                    System.out.println("Incorrect password.");
                    return false;
                }
            }
        }
        System.out.println("Username not found.");
        return false;
    }
}

