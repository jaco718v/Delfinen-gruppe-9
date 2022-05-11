package membership;

import java.util.ArrayList;

public class Team {
    private ArrayList<Member> memberList = new ArrayList<>();
    private User coach;
    private final Enum.TeamType teamType;
    private final Enum.AgeGroup ageGroup;

    public Team(Enum.TeamType teamType, Enum.AgeGroup ageGroup) {
        this.teamType = teamType;
        this.ageGroup = ageGroup;
    }

    public Enum.AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public Enum.TeamType getTeamType() {
        return teamType;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public int getActiveMembers() {
        int activeMembers = 0;
        for (Member member : memberList) {
            if (member.getActive()) {
                activeMembers++;
            }
        }
        return activeMembers;
    }

    public int getActiveMembersAboveAge(int age) {
        int membersAboveAge = 0;
        for (Member member : memberList) {
            if (member.getAge() > age && member.getActive()) {
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


}