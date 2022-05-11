package swimclub;

import membership.Enum;
import membership.Team;
import membership.Member;
import membership.*;

import ui.UI;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private ArrayList<Team> teamArray = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private UI ui = new UI();
    private ArrayList<User> users = new ArrayList<>();
    private User loggedInUser;


    public static void main(String[] args) {
        Controller con = new Controller();
        con.run();
    }

    private void run() {

    }

    private void showExpectedSubscriptionFees() {
        if (users.size() > 0) {
            if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CASHIER)) {
                double subscriptionJunior = 1000;
                double subscriptionSenior = 1600;
                double subscriptionPassive = 500;
                int seniorThreshold = 60;
                double seniorDiscount = 0.25;
                double subscriptionSum = 0;
                for (Team team : teamArray) {
                    double subscription = subscriptionJunior;
                    if (team.getAgeGroup() == Enum.AgeGroup.O18) {
                        subscription = subscriptionSenior;
                        subscriptionSum -= subscriptionSenior * seniorDiscount * team.getActiveMembersAboveAge(seniorThreshold);
                    }
                    subscriptionSum += team.getActiveMembers() * subscription +
                        (team.getMemberList().size() - team.getActiveMembers()) * subscriptionPassive;
                }
                ui.showExpectedSubscriptionFees(subscriptionSum);
            } else {
                ui.loggedInUserNoPrivilege();
            }
        } else {
            ui.noRegisteredUsers();
        }
    }


    private void createTeams() {
        teamArray.add(new Team(Enum.TeamType.REGULAR, Enum.AgeGroup.U18));
        teamArray.add(new Team(Enum.TeamType.REGULAR, Enum.AgeGroup.O18));
        teamArray.add(new Team(Enum.TeamType.COMPETITIVE, Enum.AgeGroup.U18));
        teamArray.add(new Team(Enum.TeamType.COMPETITIVE, Enum.AgeGroup.O18));
    }

    public void addMember() {
        String memberName = sc.nextLine();
        String memberAgeString = sc.nextLine();

        if (tryParseInt(memberAgeString)) {
            int memberAge = Integer.parseInt(memberAgeString);
        }
    }


    private boolean tryParseInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception E) {
            return false;
        }
        return true;
    }

    private void addUser() {
        if (users.size() > 0) {
            if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
                String userName = addUserName();
                String userPassword = addUserPassword();
                String userType = addUserType();

                switch (userType) {
                    case "1" -> users.add(new User(userName, userPassword, Enum.UserType.ADMIN));
                    case "2" -> users.add(new User(userName, userPassword, Enum.UserType.CHAIRMAN));
                    case "3" -> users.add(new User(userName, userPassword, Enum.UserType.CASHIER));
                    case "4" -> users.add(new User(userName, userPassword, Enum.UserType.COACH));
                }
            } else {
                ui.loggedInUserNoPrivilege();
            }
        } else {
            ui.noRegisteredUsersCreatingAdmin();
            users.add(new User("admin", "admin", Enum.UserType.ADMIN));
        }
    }

    private String addUserName() {
        String userName = "";
        boolean enteredUserName = false;
        while (!enteredUserName) {
            ui.displayPleaseTypeLoginName();
            userName = sc.nextLine();
            if ((!userName.equals("")) && (!userName.equals(" "))) {
                enteredUserName = true;
            }
            if (!enteredUserName) {
                ui.displayPleaseEnterValidUser(userName);
            }
        }
        return userName;
    }

    private String addUserPassword() {
        String userPassword = "";
        boolean enteredUserPassword = false;
        while (!enteredUserPassword) {
            ui.displayPleaseTypeLoginPassword();
            userPassword = sc.nextLine();
            if ((!userPassword.equals("")) && (!userPassword.equals(" "))) {
                enteredUserPassword = true;
            }
            if (!enteredUserPassword) {
                ui.displayBadPassword(userPassword);
            }
        }
        return userPassword;
    }

    private String addUserType() {
        String userType = "";
        boolean enteredUserType = false;
        while (!enteredUserType) {
            ui.displayPleaseSelectUserType();
            userType = sc.nextLine();
            switch (userType) {
                case "1", "2", "3", "4" -> enteredUserType = true;
            }
            ui.addUser(enteredUserType);
            if (!enteredUserType) {
                ui.displayBadPassword(userType);
            }
        }
        return userType;
    }

    private void removeUser() {
        if (users.size() > 0) {
            if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
                // Jeres kode her inde
            }
        } else {
            ui.noRegisteredUsers();
        }
    }

    private void loginUser() {
        if (users.size() > 0) {
            String userName;
            int userIndex = -1;
            String userPassword;

            boolean enteredUserName = false;
            while (!enteredUserName) {
                ui.displayPleaseTypeLoginName();
                userName = sc.nextLine();
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getName().equals(userName)) {
                        userIndex = i;
                        enteredUserName = true;
                    }
                }
                if (!enteredUserName) {
                    ui.displayPleaseEnterValidUser(userName);
                }
            }

            boolean enteredUserPassword = false;
            while (!enteredUserPassword) {
                ui.displayPleaseTypeLoginPassword();
                userPassword = sc.nextLine();
                if (users.get(userIndex) != null) {
                    if (users.get(userIndex).getPassword().equals(userPassword)) {
                        enteredUserPassword = true;
                    }
                }
                ui.loginUser(enteredUserPassword);
                if (!enteredUserPassword) {
                    ui.displayWrongPassword();
                }
            }
            loggedInUser = users.get(userIndex);
        } else {
            ui.noRegisteredUsers();
        }
    }
}
