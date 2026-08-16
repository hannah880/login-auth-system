package auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private String username;
    private String passwordHash;
    private int failedAttempts = 0;
    private boolean locked = false;

    private static final int MAX_ATTEMPTS = 3;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hash(password);
    }

    public String getUsername() {
        return username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean checkPassword(String password) {
        return passwordHash.equals(hash(password));
    }

    public boolean checkPasswordHash(String storedHash, String password) {
        return storedHash.equals(hash(password));
    }

    public void registerFailedAttempt() {
        failedAttempts++;

        if (failedAttempts >= MAX_ATTEMPTS) {
            locked = true;
            System.out.println("Account has been locked after 3 failed login attempts.");
        } else {
            System.out.println("Failed attempts: " + failedAttempts + "/" + MAX_ATTEMPTS);
        }
    }

    public void resetAttempts() {
        failedAttempts = 0;
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}