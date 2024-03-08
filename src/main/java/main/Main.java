package main;

import database.*;
import entity.*;
import serveses.LoginToMyAppAsAdmin;
import serveses.LoginToMyAppAsServiceProvider;
import serveses.LoginAsUser;

import org.mindrot.jbcrypt.BCrypt;

import java.util.*;
import java.util.logging.Logger;

public class Main {
    private static User user;
    private static final Logger logger = LoggerUtility.getLogger();

    public static void displayEnterValidNumber(){
        logger.warning("|                  PLEASE ENTER VALID NUMBER :)                         |\n");
    }
    public static void displayUpLine(){
        logger.info("________________________________________________________________________\n");
    }
    public static void displayDownLine(){
        logger.info("|_______________________________________________________________________|\n");
    }
    public static void displayEmpty(){
        logger.info("|                                                                       |\n");
    }
    public static void displayStarsLine(){
        logger.info("|        *******************************************************        |\n");
    }

    //Home Page
    public static void menu(){
        displayUpLine();
        displayEmpty();
        displayStarsLine();
        logger.info("|        *               Welcome to Zafafcom  :)               *        |\n");
        logger.info("|        *               Wedding Planning System               *        |\n");
        displayStarsLine();
        displayEmpty();
        logger.info("|              ENTER THE NUMBER OF ACTION YOU WANT TO TAKE              |\n");
        displayStarsLine();
        displayEmpty();
        logger.info("|               1- Login to System as an Admin                          |\n");
        logger.info("|               2- Login to System as a Service Provider                |\n");
        logger.info("|               3- Login to System as a User                            |\n");
        logger.info("|               4- Create a new account (sign up to the system)         |\n");
        displayDownLine();
        logger.info("\n");
    }

    // Hash the password using BCrypt and return the hashed value
    private static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // login page form
    public static String[] loginPage(){
        displayUpLine();
        displayEmpty();
        displayStarsLine();
        logger.warning("|        *               WELCOME TO LOGIN PAGE:)               *        |\n");
        displayStarsLine();
        displayDownLine();
        logger.info("\n");

        String[] input = new String[2];
        Scanner scanner = new Scanner(System.in);
        logger.info(" - Enter your email: ");
        input[0] = scanner.next().toLowerCase(Locale.ROOT); // Convert to lowercase
        logger.info(" - Enter your password: ");
        input[1] = scanner.next();

        return input;
    }

    //login page (for user)
    public static void userLogin(String email, String enteredPassword) {
        LoginAsUser userLogin = new LoginAsUser();
        user = userLogin.loggInCheck(email, enteredPassword);

        if (user != null) {
            userPage(user);
        } // end of successfully logged in
        else {
            // Login failed due to incorrect password
            displayUpLine();
            logger.warning("|   Login failed! Please check your email and password and try again.   |\n");
            logger.warning("|                 1- Re-enter email and password                        |\n");
            logger.warning("|                 2- Don't have an account? Sign up for a new account   |\n");
            logger.warning("|                 3- Back to home page                                  |\n");
            displayDownLine();
            logger.info("\n");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Re-enter email and password
                    String[] loginInfo = loginPage();
                    userLogin(loginInfo[0], loginInfo[1]);
                    break;
                case 2:
                    // Sign up for a new account
                    signUpPage();
                    break;
                case 3:
                    // Back to the main menu
                    menu();
                    break;
                default:
                    displayUpLine();
                    displayEnterValidNumber();
                    displayDownLine();
                    break;
            }
        }// end of failed logging in
    }// end of login page for user

    // user page
    public static void userPage(User loggedInUser) {
        Scanner scanner = new Scanner(System.in);
        int userChoice;

//        while (true){
            displayUpLine();
            displayEmpty();
            displayStarsLine();
            logger.warning("|        *                   WELCOME " + loggedInUser.getName() + ":)                    *        |\n");
            displayStarsLine();
            displayEmpty();
            logger.info("|              ENTER THE NUMBER OF ACTION YOU WANT TO TAKE              |\n");
            displayStarsLine();
            displayEmpty();
            logger.info("|------------------------------- User Page -----------------------------|\n");
            logger.info("|               1- Show  Services                                       |\n");
            logger.info("|               2- Show Details of my Reservations                      |\n");
            logger.info("|               3- My Profile                                           |\n");
            logger.info("|               4- Log out                                              |\n");
            displayDownLine();
            logger.info("\n");

            try {
                userChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Clear buffer (avoid infinite loop)
                scanner.nextLine();
                displayUpLine();
                logger.warning("|                            Invalid input.                             |\n");
                logger.warning("|                   Please enter a number (1 to 4).                    |\n");
                displayDownLine();
                userChoice = -1;
            }

            switch (userChoice) {
                case 1:
                    // Show services
                    // TODO: Add functionality for showing services
                    break;

                case 2:
                    // Show details of my reservations
                    // TODO: Add functionality for showing reservations
                    break;

                case 3:
                    userProfile(loggedInUser);
                    break;

                case 4:
                    // Log out
                    logout();
                    break;

                default:
                    displayUpLine();
                    displayEnterValidNumber();
                    displayDownLine();
                    break;
            }
//        }
    }

    //sign up page (register user)
    public static void signUpPage(){
        displayUpLine();
        displayEmpty();
        displayStarsLine();
        logger.warning("|        *               WELCOME TO SIGNUP PAGE:)              *        |\n");
        displayStarsLine();
        displayDownLine();
        logger.info("\n");

        user = new User();
        Scanner scanner = new Scanner(System.in);

        boolean validId = false;
        boolean validPhoneNumber = false;
        boolean validEmail = false;

        do {
            try {
                logger.info(" - Enter your ID (numbers only): ");
                user.setId(Integer.parseInt(scanner.next()));
                validId = true;
            } catch (NumberFormatException e) {
                displayUpLine();
                logger.warning("|                 Please enter a valid numerical ID.                    |\n");
                logger.warning("|                   **YOU NEED TO SIGN UP AGAIN**                       |\n");
                displayDownLine();
            }
        } while (!validId);

        logger.info(" - Enter your Name: ");
        user.setName(scanner.next());

        do {
            logger.info(" - Enter your Phone Number (numbers only): ");
            String phoneNumber = scanner.next();
            if (phoneNumber.matches("\\d+")) {
                user.setPhoneNumber(phoneNumber);
                validPhoneNumber = true;
            } else {
                displayUpLine();
                logger.warning("|             Please enter a valid numerical Phone Number.              |\n");
                logger.warning("|                   **YOU NEED TO SIGN UP AGAIN**                       |\n");
                displayDownLine();
            }
        } while (!validPhoneNumber);

        logger.info(" - Enter your Address: ");
        user.setAddress(scanner.next());

        logger.info(" - Enter your City: ");
        user.setCity(scanner.next());

        logger.info(" - Enter your Street: ");
        user.setStreet(scanner.next());

        do {
            logger.info(" - Enter your Email: ");
            String email = scanner.next();

            // Check email format using regular expression
            if (email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                user.setEmail(email);
                validEmail = true;
            } else {
                displayUpLine();
                logger.warning("|             Please enter a valid email address.                       |\n");
                logger.warning("|     example for the correct format: tala12@gmail.com                  |\n");
                logger.warning("|               **YOU NEED TO SIGN UP AGAIN**                           |\n");
                displayDownLine();
            }
        } while (!validEmail);

        String password;
        // Validate password length
        do {
            logger.info(" - Enter your Password (must be at least 5 characters): ");
            password = scanner.next();

            if (password.length() < 5) {
                displayUpLine();
                logger.warning("|            Password must be at least 6 characters long.               |\n");
                logger.warning("|                  Please enter a stronger password.                    |\n");
                displayDownLine();
            }
        } while (password.length() < 5);
        // Hash the user's password before saving it to UserDB
        user.setPassword(hashPassword(password));

        // Check if the user with the same ID or email already exists
        while (UserDB.isUserExists(user.getId(), user.getEmail())) {
            displayUpLine();
            logger.warning("|           User with the same ID or email already exists.              |\n");
            logger.warning("|               Please re-enter your ID and Email.                      |\n");
            logger.warning("|                   **YOU NEED TO SIGN UP AGAIN**                       |\n");
            displayDownLine();
            do {
                try {
                    logger.info(" - Re-enter your ID (numbers only): ");
                    user.setId(Integer.parseInt(scanner.next()));
                    validId = true;
                } catch (NumberFormatException e) {
                    displayUpLine();
                    logger.warning("|           Please enter a valid numerical ID.              |\n");
                    logger.warning("|                   **YOU NEED TO SIGN UP AGAIN**           |\n");
                    displayDownLine();
                    validId = false;
                }
            } while (!validId);

            boolean validEmailReEnter = false;
            do {
                logger.info(" - Re-enter your Email: ");
                String reEnteredEmail = scanner.next();

                // Check email format using regular expression
                if (reEnteredEmail.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                    user.setEmail(reEnteredEmail);
                    validEmailReEnter = true;
                } else {
                    displayUpLine();
                    logger.warning("|             Please enter a valid email address.                       |\n");
                    logger.warning("|     example for the correct format: tala12@gmail.com                  |\n");
                    logger.warning("|               **YOU NEED TO SIGN UP AGAIN**                           |\n");
                    displayDownLine();
                }
            } while (!validEmailReEnter);
        }

        displayUpLine();
        logger.info("         ****************************************************         \n");
        logger.info("         * Thank you for signing up! Here are your details: *         \n");
        logger.info("         ****************************************************         \n");
        logger.info("               ID: "+user.getId()+"                                                      \n");
        logger.info("               Name: "+user.getName()+"                                                    \n");
        logger.info("               Phone Number: "+user.getPhoneNumber()+"                                            \n");
        logger.info("               Address: "+user.getAddress()+"                                                 \n");
        logger.info("               City: "+user.getCity()+"                                                    \n");
        logger.info("               Street: "+user.getStreet()+"                                                  \n");
        logger.info("               Email: "+user.getEmail()+"                                                   \n");
        logger.info("               Password: "+ "*".repeat(Math.max(0, password.length())) +"                                                \n");

        int choice;
        do {
            try {
                logger.info("         ****************************************************         \n");
                logger.info("         *    ENTER THE NUMBER OF ACTION YOU WANT TO TAKE   *         \n");
                logger.info("         ****************************************************         \n");
                logger.info("               1- Confirm Information to Sign Up                      \n");
                logger.info("               2- Edit Information                                    \n");
                logger.info("               3- Don't save and back Home                            \n");
                displayUpLine();
                logger.info("\n");

                choice = scanner.nextInt();
            }catch (InputMismatchException e){
                // Clear buffer (avoid infinite loop)
                scanner.nextLine();
                displayUpLine();
                logger.warning("|                            Invalid input.                             |\n");
                logger.warning("|                   Please enter a number (1, 2, or 3).                 |\n");
                displayDownLine();
                choice = -1;
            }
        } while (choice != 1 && choice != 2 && choice != 3);

        if (choice == 1) {
            // Confirm sign up
            User newUser = new User();
            newUser.setId(user.getId());
            newUser.setName(user.getName());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setAddress(user.getAddress());
            newUser.setCity(user.getCity());
            newUser.setStreet(user.getStreet());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());

            // Add the user to the database if not already exists
            UserDB.addUser(newUser);

            displayUpLine();
            logger.info("|                  Account created added successfully!                  |\n");
            //UserDB.displayUser(newUser); // Need to edit function in UserDB
            displayDownLine();
            logger.info("\n");
        } else if (choice == 2){
            // Edit information
            signUpPage(); // Re-run the sign-up page for editing
        } else {
            //Home
            //logger.info("No action taken. Returning to the Home page.");
            //menu();
            logger.info("\n");
        }
    }

    // logout method
    public static void logout() {
        displayUpLine();
        logger.info("|               Logout successful. Returning to the main menu.          |\n");
        displayDownLine();
        logger.info("\n");

        // Clear user data
        user = null;
    }

    // user profile page
    public static void userProfile(User loggedInUser) {
        Scanner scanner = new Scanner(System.in);
        int profileChoice;

        do {
            displayUpLine();
            displayEmpty();
            displayStarsLine();
            logger.info("|         *               USER PROFILE - " + loggedInUser.getName() + "              *         |\n");
            displayStarsLine();
            displayDownLine();
            logger.info("\n");

            logger.info("|               1- Show Account Information                              |\n");
            logger.info("|               2- Edit Information                                      |\n");
            logger.info("|               3- Back to User Page                                     |\n");
            displayDownLine();
            logger.info("\n");

            try {
                profileChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Clear buffer (avoid infinite loop)
                scanner.nextLine();
                displayUpLine();
                logger.warning("|                            Invalid input.                             |\n");
                logger.warning("|                   Please enter a number (1, 2, or 3).                 |\n");
                displayDownLine();
                profileChoice = -1;
            }

            switch (profileChoice) {
                case 1:
                    // Show Account Information
                    displayUpLine();
                    UserDB.displayUser(loggedInUser);
                    displayDownLine();
                    break;

                case 2:
                    // Edit Information
                    editUserProfile(loggedInUser);
                    break;

                case 3:
                    userPage(loggedInUser);
                    return;
//                  break;


                default:
                    displayUpLine();
                    displayEnterValidNumber();
                    displayDownLine();
                    break;
            }
        } while (true);
    }

    // edit user profile page
    public static void editUserProfile(User loggedInUser) {
        Scanner scanner = new Scanner(System.in);

        // Provide options for editing different fields
        int editChoice;
        do {
            displayUpLine();
            displayEmpty();
            displayStarsLine();
            logger.info("|      *               EDIT USER PROFILE - " + loggedInUser.getName() + "          *      |\n");
            displayStarsLine();
            displayDownLine();
            logger.info("\n");

            logger.info("|               1- Edit Name                                            |\n");
            logger.info("|               2- Edit Phone Number                                    |\n");
            logger.info("|               3- Edit Address                                         |\n");
            logger.info("|               4- Edit City                                            |\n");
            logger.info("|               5- Edit Street                                          |\n");
            logger.info("|               6- Edit Email                                           |\n");
            logger.info("|               7- Edit Password                                        |\n");
            logger.info("|               8- Back to User Profile Page                            |\n");
            displayDownLine();
            logger.info("\n");

            try {
                editChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Clear buffer (avoid infinite loop)
                scanner.nextLine();
                displayUpLine();
                logger.warning("|                            Invalid input.                             |\n");
                logger.warning("|                   Please enter a number (1 to 8).                    |\n");
                displayDownLine();
                editChoice = -1;
            }

            switch (editChoice) {
                case 1:
                    logger.info(" - Enter new Name: ");
                    loggedInUser.setName(scanner.next());
                    break;

                case 2:
                    logger.info(" - Enter new Phone Number: ");
                    loggedInUser.setPhoneNumber(scanner.next());
                    break;

                case 3:
                    logger.info(" - Enter new Address: ");
                    loggedInUser.setAddress(scanner.next());
                    break;

                case 4:
                    logger.info(" - Enter new City: ");
                    loggedInUser.setCity(scanner.next());
                    break;

                case 5:
                    logger.info(" - Enter new Street: ");
                    loggedInUser.setStreet(scanner.next());
                    break;

                case 6:
                    logger.info(" - Enter new Email: ");
                    loggedInUser.setEmail(scanner.next());
                    break;

                case 7:
                    // Hash and set new password
                    logger.info(" - Enter new Password (must be at least 5 characters): ");
                    String newPassword = scanner.next();
                    while (newPassword.length() < 5) {
                        displayUpLine();
                        logger.warning("|            Password must be at least 6 characters long.               |\n");
                        logger.warning("|                  Please enter a stronger password.                    |\n");
                        displayDownLine();
                        logger.info(" - Enter new Password (must be at least 5 characters): ");
                        newPassword = scanner.next();
                    }
                    loggedInUser.setPassword(hashPassword(newPassword));
                    break;

                case 8:
                    // Back to User Profile Page
                    return;

                default:
                    displayUpLine();
                    displayEnterValidNumber();
                    displayDownLine();
                    break;
            }
        } while (true);
    }


    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(true) {
            try {
                menu();
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                displayUpLine();
                displayEnterValidNumber();
                displayDownLine();
                scanner.nextLine();
                continue;
            }

            // login as Admin
            if (option == 1){
                loginPage();

                //                    logger.info("WELCOME Admin " + user.getName() + "\n");
//                    while (true) {
//                        logger.info("---------------Admin Options-----------------------------\n");
//                        logger.info("| 1- add new user /service provider|\n");
//                        logger.info("| 2- Show service provider           |\n");
//                        logger.info("| 3- Show users                          |\n");
//                        logger.info("| 4- Show services(delete)                          |\n");
//                        logger.info("| 5- show reservations (delete) |\n");
///                        logger.info("| 6- profile                        |\n");
////                        logger.info("| 7- requests list |\n");
////                        logger.info("| 8- logout|\n");
//                        logger.info("---------------------------------------------------------\n");
//
//                    }

            }

            // login as service provider
            else if (option == 2) {
                loginPage();

                //                    logger.info("WELCOME service provider " + user.getName() + "\n");
//                    while (true) {
//                        logger.info("---------------service provider Options-----------------------------\n");
//                        logger.info("| 1- add new service |\n");
//                        logger.info("| 2- edit service (search + edit + delete)            |\n");
//                        logger.info("| 3- show reservations (details + delete ) |\n");
///                        logger.info("| 4- profile                        |\n");
////                        logger.info("| 5- logout|\n");
//                        logger.info("---------------------------------------------------------\n");
//
//                    }
            }

//___________USER_______________________________________________________________________________________________________
            // login as user
            else if (option == 3 ) {
                String[] loginInfo = loginPage();
                userLogin(loginInfo[0], loginInfo[1]);

                int userChoice = scanner.nextInt();
                switch (userChoice) {
                    //service page
                    case 1:
                        // Show services
                        break;


                    // user reservations
                    case 2:
                        break;


                    // user profile
                    case 3:
                        if (user != null) {
                            userProfile(user);
                        } else {
                            displayUpLine();
                            logger.warning("| User not logged in. Please log in first.                              |\n");
                            displayDownLine();
                        }
                        break;


                    // Logout
                    case 4:
                        logout();
                        return;

                    default:
                        displayUpLine();
                        displayEnterValidNumber();
                        displayDownLine();
                        break;
                }
            }// end of option 3 (login as user)


//______________________________________________________________________________________________________________________
            // sign up user
            else if (option == 4) {
                signUpPage();
            }// end of option 4 (sing up)
//______________________________________________________________________________________________________________________

            // wrong input
            else {
                displayUpLine();
                displayEnterValidNumber();
                displayDownLine();
                //menu();
            }

        }// end of while
    }// end of static main
}// end of main class
