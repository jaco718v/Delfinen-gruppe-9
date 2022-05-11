package membership;

import java.util.ArrayList;

public class Team {
  private ArrayList<Member> memberList = new ArrayList<Member>();
  //private User coach;
  private Enum.TeamType teamType;
  private Enum.AgeGroup ageGroup;

  public Team(Enum.TeamType teamType, Enum.AgeGroup ageGroup) {
    this.teamType = teamType;
    this.ageGroup = ageGroup;
  }

  public Enum.AgeGroup getAgeGroup() {
    return ageGroup;
  }

  public int getActiveMembers(){
    int activeMembers = 0;
    for(Member member : memberList){
      if (member.getActive()){
        activeMembers++;
      }
    }
    return activeMembers;
  }

  public int getMembersAboveAge(int age){
    int membersAboveAge = 0;
    for(Member member : memberList){
      if (member.getAge()>age){
        membersAboveAge++;
      }
    }
    return membersAboveAge;
  }

  public void setMemberList(ArrayList<Member> memberList) {
    this.memberList = memberList;
  }

  public ArrayList<Member> getMemberList() {
    return memberList;
  }

  public void addMemberToTeamList(Member member){
    memberList.add(member);

  }

}