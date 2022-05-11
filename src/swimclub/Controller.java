package swimclub;

import membership.Enum;
import membership.Team;
import membership.Member;
import membership.*;

import ui.UI;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
  ArrayList<Team> teamArray = new ArrayList<>();
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

    private double showExpectedSubscriptionFees() {
      double subscriptionJunior = 1000;
      double subscriptionSenior = 1600;
      double subscriptionPassive = 500;
      int seniorThreshold = 60;
      double seniorDiscount = 0.25;
      double subscriptionSum = 0;
      for (Team team : teamArray) {
        double subscription = subscriptionJunior;
        if(team.getAgeGroup()== Enum.AgeGroup.O18){
          subscription = subscriptionSenior;
          subscriptionSum-= subscriptionSenior*seniorDiscount*team.getActiveMembersAboveAge(seniorThreshold);
        }
        subscriptionSum += team.getActiveMembers() * subscription +
            (team.getMemberList().size() - team.getActiveMembers()) * subscriptionPassive;
      }
      return subscriptionSum;
      }



    private void createTeams(){
      teamArray.add(new Team(Enum.TeamType.REGULAR,Enum.AgeGroup.U18));
      teamArray.add(new Team(Enum.TeamType.REGULAR,Enum.AgeGroup.O18));
      teamArray.add(new Team(Enum.TeamType.COMPETITIVE,Enum.AgeGroup.U18));
      teamArray.add(new Team(Enum.TeamType.COMPETITIVE,Enum.AgeGroup.O18));
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
