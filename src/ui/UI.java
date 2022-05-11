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
            System.out.println("Failed to add user.");
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

    public void showExpectedSubscriptionFees() {
        System.out.println("Expected subscription fees:");
    }

    public void showMembersInArrears() {
        System.out.println("Members in arrears:");
    }

    public void showTopSwimmers() {
        System.out.println("Top swimmers:");
    }

    public void displayPleaseTypeLoginName() {
        System.out.println("Please type user login name: ");
    }

    public void displayPleaseTypeLoginPassword() {
        System.out.println("Please type user login password: ");
    }

    public void displayPleaseEnterValidUser() {
        System.out.println("Please enter a valid user.");
    }

    public void displayWrongPassword() {
        System.out.println("Wrong password, try again.");
    }

    public void noRegisteredUsers() {
        System.out.println("Can't login, no registered users.");
    }
}
