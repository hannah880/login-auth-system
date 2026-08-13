package auth;

import java.util.ArrayList;

public class AuthenticationSystem {

    private ArrayList<User> users = new ArrayList<>();

    public boolean registerUser(String username, String password) {

        // Check that username and password aren't empty
        if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {

            System.out.println("Username and password cannot be empty.");
            return false;
        }
            if (!isStrongPassword(password)) {
                System.out.println("Password must be at least 8 characters long, contain an uppercase letter and a number.");
                return false;
            }

        // Check password strength
        if (!isStrongPassword(password)) {
            System.out.println("Password must be at least 8 characters long, contain an uppercase letter and a number.");
            return false;
        }

        // Check if username already exists
        for (User user : users) {

            if (user.getUsername().equals(username)) {
                System.out.println("Username already taken.");
                return false;
            }
        }

        // Create the new user
        User newUser = new User(username, password);
        users.add(newUser);

        System.out.println("Account created!");
        return true;
    }

    public boolean loginUser(String username, String password) {

        for (User user : users) {

            if (user.getUsername().equals(username)) {

                // Check if account is locked
                if (user.isLocked()) {
                    System.out.println("Account is locked due to many failed attempts.");
                    return false;
                }

                // Check password
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

    // Checks whether a password is strong enough
    private boolean isStrongPassword(String password) {

        if (password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasNumber = false;

        for (char character : password.toCharArray()) {

            if (Character.isUpperCase(character)) {
                hasUppercase = true;
            }

            if (Character.isDigit(character)) {
                hasNumber = true;
            }
        }

        return hasUppercase && hasNumber;
    }
}