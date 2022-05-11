package swimclub;

import membership.Member;
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

    private void loginUser() {
        if (users.size() > 0) {
            String userName;
            int userIndex = -1;
            String userPassword;

            boolean enteredMemberName = false;
            while (!enteredMemberName) {
                ui.displayPleaseTypeLoginName();
                userName = sc.nextLine();
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getName().equals(userName)) {
                        userIndex = i;
                        enteredMemberName = true;
                    }
                }
                if (!enteredMemberName) {
                    ui.displayPleaseEnterValidUser();
                }
            }

            boolean enteredMemberPassword = false;
            while (!enteredMemberPassword) {
                ui.displayPleaseTypeLoginPassword();
                userPassword = sc.nextLine();
                if (users.get(userIndex) != null) {
                    if (users.get(userIndex).getPassword().equals(userPassword)) {
                        enteredMemberPassword = true;
                    }
                }
                ui.loginUser(enteredMemberPassword);
                if (!enteredMemberPassword) {
                    ui.displayWrongPassword();
                }
            }
            loggedInUser = users.get(userIndex);
        } else {
            ui.noRegisteredUsers();
        }
    }
}
