package auth;

import java.util.ArrayList;

public class AuthenticationSystem {

    private ArrayList<User> users = new ArrayList<>();

    public boolean registerUser(String username, String password) {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {

            System.out.println("Username and password cannot be empty.");
            return false;
        }

        if (!isStrongPassword(password)) {
            System.out.println(
                    "Password must be at least 8 characters long, " +
                            "contain an uppercase letter and a number."
            );
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

        if (Database.addUser(username, newUser.getPasswordHash())) {
            System.out.println("Account created!");
            return true;
        }

        return false;
    }

    public boolean loginUser(String username, String password) {

        String storedHash = Database.getPasswordHash(username);

        if (storedHash == null) {
            System.out.println("Username not found.");
            return false;
        }

        for (User user : users) {

            if (user.getUsername().equals(username)) {

                if (user.isLocked()) {
                    System.out.println(
                            "Account is locked due to many failed attempts."
                    );
                    return false;
                }

                if (user.checkPasswordHash(storedHash, password)) {
                    user.resetAttempts();
                    return true;
                }

                user.registerFailedAttempt();
                System.out.println("Incorrect password.");
                return false;
            }
        }

        return false;
    }

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