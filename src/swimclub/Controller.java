package swimclub;

import membership.Enum;
import membership.*;
import ui.UI;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
private Team t = new Team();
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
    
    public void addMember(){
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
                String userName = "";
                String userPassword = "";
                String userType;

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

                boolean enteredUserType = false;
                while (!enteredUserType) {
                    ui.displayPleaseSelectUserType();
                    userType = sc.nextLine();
                    switch (userType) {
                        case "1" -> {
                            users.add(new User(userName, userPassword, Enum.UserType.ADMIN));
                            enteredUserType = true;
                        }
                        case "2" -> {
                            users.add(new User(userName, userPassword, Enum.UserType.CHAIRMAN));
                            enteredUserType = true;
                        }
                        case "3" -> {
                            users.add(new User(userName, userPassword, Enum.UserType.CASHIER));
                            enteredUserType = true;
                        }
                        case "4" -> {
                            users.add(new User(userName, userPassword, Enum.UserType.COACH));
                            enteredUserType = true;
                        }
                    }
                    ui.addUser(enteredUserType);
                    if (!enteredUserType) {
                        ui.displayBadPassword(userType);
                    }
                }
            } else {
                ui.loggedInUserNoPrivilege();
            }
        } else {
            ui.noRegisteredUsersCreatingAdmin();
            users.add(new User("admin", "admin", Enum.UserType.ADMIN));
        }
    }

    private void removeUser() {

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
