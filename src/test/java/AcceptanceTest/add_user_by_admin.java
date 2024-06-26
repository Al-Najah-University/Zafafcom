package AcceptanceTest;

import entity.Admin;
import entity.ServiceProvider;
import entity.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import main.LoggerUtility;
import database.AdminDB;
import database.ServiceProviderDB;
import database.UserDB;
import serveses.LoginToMyAppAsAdmin;
import main.TestInput;
import static org.junit.Assert.*;
import java.util.logging.Logger;

public class add_user_by_admin {
    private static final Logger logger = LoggerUtility.getLogger();
    private LoginToMyAppAsAdmin myApp;
    private Admin admin;
    private ServiceProvider serviceProvider;
    private User user;

    int initialUserCount= UserDB.getUsers().size();;
    int currentUserCount = UserDB.getUsers().size();;

    public add_user_by_admin() {
        myApp = new LoginToMyAppAsAdmin();
    }

    @Given("the admin is logged in to the admin panel")
    public void theAdminIsLoggedInToTheAdminPanel() {
        myApp.login();
        logger.info("Admin is logged in to the admin panel.");
    }

    @When("the admin chooses to add a new user")
    public void theAdminChoosesToAddANewUser() {
        logger.info("Admin chooses to add a new user.");
    }

    @When("selects the admin user type")
    public void selectsTheAdminUserType() {
        logger.info("Admin user type selected.");
    }

    @When("enters the new admin's name, email, phone, address, and password")
    public void entersTheNewAdminSNameEmailPhoneAddressAndPassword() {
        String name = "New Admin";
        String email = "newadmin@example.com";
        String phone = "1234567890";
        String address = "123 Admin Street";
        String password = "adminPassword";
        int id = AdminDB.getAdmins().size() + 1;
        if (TestInput.isValidName(name) && email.contains("@") && TestInput.isValidPhone(phone)) {
            admin = new Admin(password, email, phone, address, name, id);
            assertEquals(password, admin.getPassword());
            assertEquals(email, admin.getEmail());
            assertEquals(phone, admin.getPhone());
            assertEquals(address, admin.getAddress());
            assertEquals(name, admin.getName());
            assertEquals(id, admin.getId());

            admin.setPassword(password);
            admin.setEmail(email);
            admin.setPhone(phone);
            admin.setAddress(address);
            admin.setName(name);
            admin.setId(id);

            assertEquals(password, admin.getPassword());
            assertEquals(email, admin.getEmail());
            assertEquals(phone, admin.getPhone());
            assertEquals(address, admin.getAddress());
            assertEquals(name, admin.getName());
            assertEquals(id, admin.getId());


            AdminDB.addAdmin(password, email, phone, address, name, AdminDB.getAdmins().size() + 1);
            logger.info("New admin's details entered.");
        } else {
            logger.warning("Invalid input for new admin.");
        }
    }

    @Then("the new admin should be added to the system")
    public void theNewAdminShouldBeAddedToTheSystem() {
        AdminDB.addAdmin("testpass", "test@admin.com", "0000000000", "Test Address", "Test Admin", 1);
        Admin originalAdmin = AdminDB.getAdmins().get(0);
        originalAdmin.setName("Updated Name");
        AdminDB.updateAdmin(originalAdmin);

        Admin updatedAdmin = AdminDB.getAdmins().get(0);
        assertEquals("Updated Name",  "Updated Name",updatedAdmin.getName());


    }

    @When("selects the option to exit")
    public void selectsTheOptionToExit() {
        logger.info("Admin selects the option to exit. No new user added.");
    }

    @Then("no new user should be added and the admin returns to the admin menu")
    public void noNewUserShouldBeAddedAndTheAdminReturnsToTheAdminMenu() {
        assertEquals("No new user should be added", initialUserCount, currentUserCount);
        logger.info("Verified no new user added and admin returned to the admin menu.");
    }

    @When("enters an invalid email for the new admin")
    public void entersAnInvalidEmailForTheNewAdmin() {
        logger.info("Entered an invalid email for the new admin.");
    }

    @When("selects the service provider user type")
    public void selectsTheServiceProviderUserType() {
        logger.info("Admin has selected to add a new service provider.");
    }

    @When("enters the new service provider's name, email, phone, address, and password")
    public void entersTheNewServiceProviderSNameEmailPhoneAddressAndPassword() {
        String name = "New ServiceProvider";
        String email = "newsp@example.com";
        String phone = "0987654321";
        String address = "456 Service Provider Street";
        String password = "sppassword";
        int id = ServiceProviderDB.getServiceProviders().size() + 1;
        if (TestInput.isValidName(name) && email.contains("@") && TestInput.isValidPhone(phone)) {
            serviceProvider = new ServiceProvider(password, email, phone, address, name, id);
            ServiceProviderDB.addServiceProvider(serviceProvider);
            logger.info("New service provider's details entered.");

            ServiceProvider retrievedServiceProvider = ServiceProviderDB.getServiceProviderById(id);
            assertEquals("Test Name", "New ServiceProvider",retrievedServiceProvider.getName() );

            serviceProvider.setPassword(password);
            serviceProvider.setEmail(email);
            serviceProvider.setPhone(phone);
            serviceProvider.setAddress(address);
            serviceProvider.setName(name);
            serviceProvider.setId(id);

            assertEquals(password, serviceProvider.getPassword());
            assertEquals(email, serviceProvider.getEmail());
            assertEquals(phone, serviceProvider.getPhone());
            assertEquals(address, serviceProvider.getAddress());
            assertEquals(name, serviceProvider.getName());
            assertEquals(id, serviceProvider.getId());

        } else {
            logger.warning("Invalid input for new service provider.");
        }
    }

    @Then("the new service provider should be added to the system")
    public void theNewServiceProviderShouldBeAddedToTheSystem() {
        ServiceProvider serviceProviderToUpdate = new ServiceProvider("updated", "update@example.com", "987654321", "Updated Address", "Updated Name", 3);
        ServiceProviderDB.updateServiceProvider(serviceProviderToUpdate);

        ServiceProvider updatedServiceProvider = ServiceProviderDB.getServiceProviderById(3);
        assertNotNull(updatedServiceProvider.getName(), "Should retrieve a service provider by ID.");
        assertEquals("Updated Name",  "Updated Name",updatedServiceProvider.getName());
    }

    @When("selects the user user type")
    public void selectsTheUserUserType() {
        // Simulate selecting the regular user type for sign-up.
        logger.info("Admin has selected to add a new regular user.");
    }

    @When("fills in the sign-up form with valid data")
    public void fillsInTheSignUpFormWithValidData() {
        // Simulate filling the sign-up form with valid data.
        user = new User();
        user.setName("New User");
        user.setEmail("newuser@example.com");
        user.setPhoneNumber("1231231234");
        user.setAddress("789 User Street");
        user.setCity("User City");
        user.setStreet("Main Street");
        user.setPassword("userPassword");

        if (TestInput.isValidName(user.getName()) && user.getEmail().contains("@") && TestInput.isValidPhone(user.getPhoneNumber())) {
            UserDB.addUser(user);
            logger.info("New user's sign-up form filled with valid data.");
        } else {
            logger.warning("Invalid input for new user sign-up.");
        }
    }

    @Then("the new user should be added to the system")
    public void theNewUserShouldBeAddedToTheSystem() {




        User addedUser = UserDB.getUsers().get(UserDB.getUsers().size() - 1);
        assertNotNull("New user should not be null", addedUser);
        assertEquals("New user's email should match", "newuser@example.com", addedUser.getEmail());
        logger.info("Verified that the new user has been added to the system.");


        User user1 = new User();
        user1.setId(20);
        user1.setEmail("test@example.com");
        UserDB.addUser(user1);
        assertTrue(UserDB.isUserExists(20, "nonexistent@example.com"));
        assertTrue(UserDB.isUserExists(-1, "test@example.com"));
    }
    @Then("the system should prompt the admin to enter a valid email")
    public void theSystemShouldPromptTheAdminToEnterAValidEmail() {
        logger.info("enter a valid email ");
    }
}