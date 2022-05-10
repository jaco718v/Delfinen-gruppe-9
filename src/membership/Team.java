package membership;

import java.util.ArrayList;

public class Team {
  private ArrayList<Member> memberList = new ArrayList<Member>();
  //private User coach;
  private TeamType teamType;

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


