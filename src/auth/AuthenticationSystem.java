package auth;

import java.util.ArrayList;

public class AuthenticationSystem {

    private ArrayList<User> users = new ArrayList<>();

    public boolean registerUser(String username, String password) {

        // Check that username and password aren't empty
        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {

            System.out.println("Username and password cannot be empty.");
            return false;
        }

        // Check password strength
        if (!isStrongPassword(password)) {
            System.out.println("Password must be at least 8 characters long, contain an uppercase letter and a number.");
            return false;
        }

        // Check if username already exists (in the database, not just this session)
        if (Database.usernameExists(username)) {
            System.out.println("Username already taken.");
            return false;
        }

        // Create the user
        User newUser = new User(username, password);
        users.add(newUser);

        // Save user to database
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

        if (checkPassword(storedHash, password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Incorrect password.");
            return false;
        }
    }

    private boolean checkPassword(String storedHash, String password) {
        User tempUser = new User("", "");
        return tempUser.checkPasswordHash(storedHash, password);
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