package membership;

import database.FileHandler;

import java.util.ArrayList;

public class Team {
    private FileHandler fileHandler = new FileHandler();
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
            int age = Integer.parseInt(strArray[1]);
            if ((ageGroup == Enum.AgeGroup.U18) && (age < 18)) {
                if (((strArray[3].equals("true")) && (teamType == Enum.TeamType.COMPETITIVE)) || ((strArray[3].equals("false")) && (teamType == Enum.TeamType.REGULAR))) {
                    returnData.add(strArray);
                }
            } else if ((ageGroup == Enum.AgeGroup.O18) && (age >= 18)) {
                if (((strArray[3].equals("true")) && (teamType == Enum.TeamType.COMPETITIVE)) || ((strArray[3].equals("false")) && (teamType == Enum.TeamType.REGULAR))) {
                    returnData.add(strArray);
                }
            }
        }
        return returnData;
    }

    public int getTeamSize() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        for (String[] strArray : memberData) {
            int totalMembers = 0;
            int competitiveMembers = 0;
            int age = Integer.parseInt(strArray[1]);
            if (ageGroup == Enum.AgeGroup.U18) {
                if(age<18){
                totalMembers++;
                    if(strArray[4].equals("true")){
                    competitiveMembers++;
                }
                }
                if (teamType == Enum.TeamType.REGULAR){
                return totalMembers-competitiveMembers;
                }
                else return competitiveMembers;
            }
            if(ageGroup == Enum.AgeGroup.O18){
                if(age>=18){
                    totalMembers++;}
                if(strArray[3].equals("true")){
                    competitiveMembers++;
            }
                if (teamType== Enum.TeamType.REGULAR){
                    return totalMembers-competitiveMembers;
            }
                else return competitiveMembers;
            }
        }
        return 0;
    }


    public int getActiveMembers() {
        int activeMembers = 0;
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        for (String[] strArray : memberData) {
            if (strArray[2].equals("true")) {
                activeMembers++;
            }
        }
        return activeMembers;
    }

    public int getActiveMembersAboveAge(int ageThreshold) {
        int membersAboveAge = 0;
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        for (String[] strArray : memberData) {
            if (Integer.parseInt(strArray[1]) > ageThreshold && strArray[2].equals("true")) {
                membersAboveAge++;
            }
        }
        return membersAboveAge;
    }

    public boolean findMemberByName(String name){
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        for (String[] strArray : memberData) {
            if (strArray[0].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean findMemberCompetitiveStatus(String name){
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        for (String[] strArray : memberData) {
            if (strArray[0].equalsIgnoreCase(name)) {
                return Boolean.parseBoolean(strArray[3]);
            }
        }
        return false;
    }

    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }


}