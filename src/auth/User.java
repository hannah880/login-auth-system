package auth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Arrays;

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

    public boolean checkPasswordHash(String storedHash, String password) {

        try {
            String[] parts = storedHash.split(":");

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] originalHash = Base64.getDecoder().decode(parts[1]);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    65536,
                    256
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] newHash = factory.generateSecret(spec).getEncoded();

            return Arrays.equals(originalHash, newHash);

        } catch (Exception e) {
            return false;
        }
    }

    public void registerFailedAttempt() {

        failedAttempts++;

        if (failedAttempts >= MAX_ATTEMPTS) {
            locked = true;
            System.out.println(
                    "Account has been locked after 3 failed login attempts."
            );
        } else {
            System.out.println(
                    "Failed attempts: " + failedAttempts + "/" + MAX_ATTEMPTS
            );
        }
    }

    public void resetAttempts() {
        failedAttempts = 0;
    }

    private String hash(String password) {

        try {
            SecureRandom random = new SecureRandom();

            byte[] salt = new byte[16];
            random.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    65536,
                    256
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(salt)
                    + ":"
                    + Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}