package swimclub;

import membership.Enum;
import membership.Team;

import java.util.ArrayList;

public class Controller {
  ArrayList<Team> teamArray = new ArrayList<>();

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
}
