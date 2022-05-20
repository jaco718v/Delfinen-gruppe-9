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



    /*ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
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

     */


    public ArrayList<Member> getMembers() {
        return memberList;
    }

    public ArrayList<Member> getActiveMembers() {
        ArrayList<Member> activeMembers = new ArrayList<>();
        for(Member member : this.memberList){
            if(member instanceof MemberCompetitive){
                activeMembers.add(member);
            }
        }
        return activeMembers;
    }

    public ArrayList<Member> getActiveMembersAboveAge(int ageThreshold) {
        ArrayList<Member> membersAboveAge = new ArrayList<>();
        ArrayList<Member> activeMembers = getActiveMembers();
        for(Member member : activeMembers){
            if(util.convertDateToAge(member.getBirthDate())>ageThreshold){
                membersAboveAge.add(member);
            }
        }
        return membersAboveAge;
    }

    public String findCompetitiveMemberNameWithID(String memberID){
        ArrayList<Member> memberData = getMembers();
        for (Member member : memberData) {
            if (member.getId().equals(memberID)){
                return member.getName();
            }
        }
        return null;
    }


    public boolean confirmCompetitiveMemberID(String memberID){
        ArrayList<Member> memberData = getMembers();
        for (Member member : memberData) {
            if (member.getId().equals(memberID)){
                return true;
            }
        }
        return false;
    }

    public MemberCompetitive findCompetitiveMemberWithID(String memberID){
        ArrayList<Member> memberData = getMembers();
        for (Member member : memberData) {
            if (member.getId().equals(memberID)){
                return (MemberCompetitive)member;
            }
        }
        return null;
    }

    public Enum.SwimDiscipline findMemberSwimDiscipline(String memberID){
        ArrayList<Member> memberData = getMembers();
        for (Member member : memberData) {
            if (member.getId().equals(memberID) && member instanceof MemberCompetitive) {
                return ((MemberCompetitive)member).getSwimDiscipline();
            }
        }
        return null;
    }

    public ArrayList<RecordTime> findRecordsOfSwimmer(String memberID) {
        ArrayList<RecordTime> recordList = new ArrayList<>();
        for (Member member : getMembers()) {
            if(memberID.equals(member.getMemberID()))
                recordList.addAll(((MemberCompetitive)member).getBestPracticeRecords());
                recordList.addAll(((MemberCompetitive) member).getCompetitions());

        }
        return recordList;
    }

    public ArrayList<RecordTimePractice> findRecordsOfSwimDiscipline(Enum.SwimDiscipline swimDiscipline) {
        ArrayList<RecordTimePractice> recordList = new ArrayList<>();
        for (Member member : getMembers()) {
            for(RecordTimePractice record : ((MemberCompetitive)member).getBestPracticeRecords()) {
                if(record.getSWIM_DISCIPLINE().equals(swimDiscipline)){
                    recordList.add(record);
                }
            }
        }
        return recordList;
    }

    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }
}