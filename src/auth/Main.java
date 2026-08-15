package auth;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthenticationSystem authSystem = new AuthenticationSystem();
        Database.createTable();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Username: ");
                    String regUsername = scanner.nextLine();
                    System.out.print("Password: ");
                    String regPassword = scanner.nextLine();
                    authSystem.registerUser(regUsername, regPassword);
                    break;

                case "2":
                    System.out.print("Username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Password: ");
                    String loginPassword = scanner.nextLine();
                    if (authSystem.loginUser(loginUsername, loginPassword)) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case "3":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
