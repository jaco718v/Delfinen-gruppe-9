package ui;

public class UI {

    public void loginUser(boolean success) {
        if (success) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Login failed.");
        }
    }

    public void addUser(boolean success) {
        if (success) {
            System.out.println("Successfully added user.");
        } else {
            System.out.println("Failed to add user. Please select user type between 1-4.");
        }
    }

    public void removeUser(boolean success) {
        if (success) {
            System.out.println("Successfully removed user.");
        } else {
            System.out.println("Failed to remove user.");
        }
    }

    public void addMember(boolean success) {
        if (success) {
            System.out.println("Successfully added member.");
        } else {
            System.out.println("Failed to add member.");
        }
    }

    public void removeMember(boolean success) {
        if (success) {
            System.out.println("Successfully removed member.");
        } else {
            System.out.println("Failed to remove member.");
        }
    }

    public void showExpectedSubscriptionFees(double subscriptionFees) {
        System.out.println("Expected subscription fees:");
        System.out.println(subscriptionFees+" kr.");
    }

    public void showMembersInArrears() {
        System.out.println("Members in arrears:");
    }

    public void showTopSwimmers() {
        System.out.println("Top swimmers:");
    }

    public void displayPleaseTypeLoginName() {
        System.out.print("Please type user login name: ");
    }

    public void displayPleaseTypeLoginPassword() {
        System.out.print("Please type user login password: ");
    }

    public void displayPleaseEnterValidUser(String userName) {
        System.out.println("Please enter a valid user. You entered: " + userName);
    }

    public void displayWrongPassword() {
        System.out.println("Wrong password, try again.");
    }

    public void noRegisteredUsers() {
        System.out.println("Can't login, no registered users.");
    }

    public void noRegisteredUsersCreatingAdmin() {
        System.out.println("No registered users, creating default admin user.");
        System.out.println("Login name: admin");
        System.out.println("Login password: admin");
    }

    public void loggedInUserNoPrivilege() {
        System.out.println("Logged in user does not have privileges for this action.");
    }

    public void displayBadPassword(String userPassword) {
        System.out.println("Bad password, try something else than: " + userPassword);
    }

    public void displayPleaseSelectUserType() {
        System.out.println("Please select one of the following user types:");
        System.out.println("1. ADMIN");
        System.out.println("2. CHAIRMAN");
        System.out.println("3. CASHIER");
        System.out.println("4. COACH");
        System.out.print("Select user type: ");
    }

    public void displayNoSuchCommand(String command) {
        System.out.println("No such command: " + command);
    }

    public void listCommandsNotLoggedIn() {
        System.out.println("Commands:");
        System.out.println("1. Login user");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }

    public void listCommandsAdmin() {
        System.out.println("Commands:");
        System.out.println("1. Login user");
        System.out.println("2. Add user");
        System.out.println("3. Remove user");
        System.out.println("4. Add member");
        System.out.println("5. Edit member");
        System.out.println("6. Remove member");
        System.out.println("7. Show subscriptions");
        System.out.println("8. Show subscriptions in arrears");
        System.out.println("9. Show top swimmers");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }

    public void listCommandsChairman() {
        System.out.println("Commands:");
        System.out.println("1. Login user");
        System.out.println("2. Add user");
        System.out.println("3. Remove user");
        System.out.println("4. Add member");
        System.out.println("5. Edit member");
        System.out.println("6. Remove member");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }

    public void listCommandsCashier() {
        System.out.println("Commands:");
        System.out.println("1. Login user");
        System.out.println("7. Show subscriptions");
        System.out.println("8. Show subscriptions in arrears");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }

    public void listCommandsCoach() {
        System.out.println("Commands:");
        System.out.println("1. Login user");
        System.out.println("9. Show top swimmers");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }
}
