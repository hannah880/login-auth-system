# Secure Authentication System

This is a beginner Java authentication system that allows users to register and log in securely.

## Features

- User registration
- User login
- Password strength validation
- Password hashing using PBKDF2 with HMAC-SHA256
- Random salt generation
- SQLite database storage
- Duplicate username prevention
- Failed login attempt tracking
- Account lockout after 3 failed login attempts
- Prepared statements to help prevent SQL injection

## Technologies Used

- Java
- SQLite
- JDBC
- IntelliJ IDEA
- Git & GitHub

## How It Works

When a user creates an account, their password is hashed using PBKDF2 with HMAC-SHA256 and a randomly generated salt.

The password hash is then stored in an SQLite database rather than storing the original password.

When a user logs in, the entered password is checked against the stored password hash.

The system also has account lockout after 3 failed login attempts.

## Project Structure

- Main.java - Contains the main menu and user input
- User.java - Contains passwords and account security
- AuthenticationSystem.java - Contains registration and login
- Database.java - Contains the SQLite database

## Security Features

- Password hashing
- Password salting
- Account lockout
- Prepared SQL statements
- Password requirements for strength and security

## What I Learned

- Java programming
- Object-oriented programming
- Authentication
- Password hashing and salting
- SQLite databases
- JDBC
- SQL
- Prepared statements
- Basic cybersecurity concepts
- Git and GitHub

## Future Improvements

- Password reset
- Email verification
- Multi-factored authentication

## Author

Hannah
