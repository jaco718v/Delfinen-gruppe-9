package swimclub;

import membership.Enum;
import membership.Member;
import membership.Team;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {


  @Test
  public void test(){
    assertEquals(20000,showExpectedSubscriptionFeesTest());
  }

  public double showExpectedSubscriptionFeesTest() {
    ArrayList<Team> teamArray = new ArrayList<>();
    teamArray.add(new Team(Enum.TeamType.REGULAR,Enum.AgeGroup.U18));
    teamArray.add(new Team(Enum.TeamType.REGULAR,Enum.AgeGroup.O18));
    //teamArray.add(new Team(Enum.TeamType.COMPETITIVE,Enum.AgeGroup.U18));
    //teamArray.add(new Team(Enum.TeamType.COMPETITIVE,Enum.AgeGroup.O18));

    double subscriptionJunior = 1000;
      double subscriptionSenior = 1600;
      double subscriptionPassive = 500;
      int seniorThreshold = 60;
      double seniorDiscount = 0.25;
      double subscriptionSum = 0;
      //Test Stubs
      int activeMembers = 7;
      int memberListSize = 10;
      int memberAboveAge = 3;

      for (Team team : teamArray) {
        double subscription = subscriptionJunior;
        if(team.getAgeGroup()== Enum.AgeGroup.O18){
          subscription = subscriptionSenior;
          subscriptionSum-= subscriptionSenior*seniorDiscount*memberAboveAge;
        }
        subscriptionSum += activeMembers * subscription +
            (memberListSize - activeMembers) * subscriptionPassive;
        }
      return subscriptionSum;
      //7*1000 + 3*500 = 8500
      //7*1600 + 3*500 - 1600*0,25*3 =11500
      //20000
    }

}