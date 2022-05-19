package membership;

import database.FileHandler;
import swimclub.User;
import utilities.Enum;
import utilities.Utility;

import java.util.ArrayList;

public class Team {
    private FileHandler fileHandler = new FileHandler();
    private final Utility util = new Utility();
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

    public ArrayList<String[]> getMembers() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        ArrayList<String[]> returnData = new ArrayList<>();
        for (String[] strArray : memberData) {
            int age = util.convertDateToAge(strArray[2]);
            if ((ageGroup == Enum.AgeGroup.U18) && (age < 18)) {
                if (((strArray[4].equals("true")) && (teamType == Enum.TeamType.COMPETITIVE)) || ((strArray[4].equals("false")) && (teamType == Enum.TeamType.REGULAR))) {
                    returnData.add(strArray);
                }
            } else if ((ageGroup == Enum.AgeGroup.O18) && (age >= 18)) {
                if (((strArray[4].equals("true")) && (teamType == Enum.TeamType.COMPETITIVE)) || ((strArray[4].equals("false")) && (teamType == Enum.TeamType.REGULAR))) {
                    returnData.add(strArray);
                }
            }
        }
        return returnData;
    }


    public int getActiveMembers() {
        int activeMembers = 0;
        ArrayList<String[]> memberData = getMembers();
        for (String[] strArray : memberData) {
            if (strArray[3].equals("true")) {
                activeMembers++;
            }
        }
        return activeMembers;
    }

    public int getActiveMembersAboveAge(int ageThreshold) {
        int membersAboveAge = 0;
        ArrayList<String[]> memberData = getMembers();
        for (String[] strArray : memberData) {
            if (Integer.parseInt(strArray[2]) > ageThreshold && strArray[3].equals("true")) {
                membersAboveAge++;
            }
        }
        return membersAboveAge;
    }

    public String findCompetitiveMemberNameWithID(String memberID){
        ArrayList<String[]> memberData = getMembers();
        for (String[] strArray : memberData) {
            if (strArray[0].equalsIgnoreCase(memberID) && strArray[4].equals("true")) {
                return strArray[1];
            }
        }
        return null;
    }

    public boolean findMemberCompetitiveStatus(String name){
        ArrayList<String[]> memberData = getMembers();
        for (String[] strArray : memberData) {
            if (strArray[1].equalsIgnoreCase(name)) {
                return Boolean.parseBoolean(strArray[3]);
            }
        }
        return false;
    }

    public String findMemberSwimDiscipline(String name){
        ArrayList<String[]> memberData = getMembers();
        for (String[] strArray : memberData) {
            if (strArray[1].equalsIgnoreCase(name)) {
                return strArray[5];
            }
        }
        return null;
    }

    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }


}