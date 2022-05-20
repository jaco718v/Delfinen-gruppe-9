package swimclub;

import database.FileHandler;
import membership.RecordTime;
import membership.RecordTimeCompetition;
import membership.RecordTimePractice;
import utilities.RecordComparator;
import utilities.Enum;
import membership.Team;
import ui.InputHandler;
import ui.UI;
import utilities.Utility;

import java.util.ArrayList;

public class SwimmerController {
    private final UI ui = new UI();
    private final InputHandler input = new InputHandler();
    private final FileHandler fileHandler = new FileHandler();
    private final Utility util = new Utility();

    public void addCoachToTeam() {
        // TODO: Add coach (User) to Team and save to Teams.csv (AgeGroup, isCompetitive, CoachName)
    }

    public void showAllSwimmers(ArrayList<Team> teamArray) {
        for(Team team : teamArray){
            if(team.getTeamType().equals(Enum.TeamType.COMPETITIVE)){
                for(int i = 0; i<team.getMembers().size(); i++){
                    ArrayList<RecordTime> memberRecords = team.findRecordsOfSwimmer(team.getMembers().get(i).getId());
                    if(memberRecords.size()!=0){
                        ui.displayMemberRecords(memberRecords,team.getMembers().get(i).getId());
                    }
                }
            }
        }
    }

    private boolean updateRecordInCSV(ArrayList<String[]> recordList, String[] newRecord) {
        boolean previousRecordFound = false;
        for (String[] record : recordList) {
            if (record[0].equalsIgnoreCase(newRecord[0]) && record[2].equalsIgnoreCase(newRecord[2])) {
                recordList.set(recordList.indexOf(record), newRecord);
                previousRecordFound = true;
                fileHandler.overwriteCSV("Records.csv", recordList);
            }
        }
        return previousRecordFound;
    }

    public void addRecordToMember(User loggedInUser, ArrayList<Team> teamArray, MemberController memberController) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            memberController.showMembers(loggedInUser, teamArray);
            String memberID = input.enterCompetitiveMemberID();
            Team team = findCompetitiveTeamWithID(teamArray,memberID);
            if (team != null) {
                ArrayList<String[]> recordList = fileHandler.readCSV("Records.csv");
                ArrayList<String[]> data = new ArrayList<>();
                String recordType = input.recordTypeChoice(memberID);
                Enum.SwimDiscipline swimDiscipline = input.addSwimDisciplineToRecord(teamArray, memberID);
                double recordInSeconds = input.addRecordInSeconds();
                String date = input.addDate();

                if (recordType.equals("regular")) {
                    team.findCompetitiveMemberWithID(memberID).AddRecordPractice(new RecordTimePractice(memberID,swimDiscipline,recordInSeconds,date));
                    String[] newRecord = {memberID, String.valueOf(swimDiscipline), String.valueOf(recordInSeconds), date, " ", " "};
                    data.add(newRecord);
                    boolean updateStatus = updateRecordInCSV(recordList, newRecord);
                    if (!updateStatus) {
                        fileHandler.writeToCSV("Records.csv", data);
                    }
                }
                if (recordType.equals("competitive")) {
                    addCompetitiveRecordToMember(team, data, memberID, swimDiscipline, recordInSeconds, date);
                }
                ui.displayRecordAddSucces(recordType);
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }


    private Team findCompetitiveTeamWithID(ArrayList<Team> teamArray, String memberID){
        for (Team team : teamArray){
            if(team.confirmCompetitiveMemberID(memberID)){
                return team;
            }
        }
        ui.memberIDNotFound();
        return null;
    }

    private void addCompetitiveRecordToMember(Team team, ArrayList<String[]> data, String memberID, Enum.SwimDiscipline swimDiscipline, double recordInSeconds, String date) {
        ui.displayEnterConventionName();
        String convention = input.enterString();
        int placing = input.addPlacing();
        team.findCompetitiveMemberWithID(memberID).AddRecordCompetition(new RecordTimeCompetition(memberID,swimDiscipline,recordInSeconds,date,convention,placing));
        data.add(new String[]{memberID, String.valueOf(swimDiscipline), String.valueOf(recordInSeconds), date, convention, String.valueOf(placing)});
        fileHandler.writeToCSV("Records.csv", data);
    }

    public void showTopSwimmers(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            Enum.SwimDiscipline swimDiscipline = input.addSwimDisciplineToRecordViaInput();
            Enum.AgeGroup ageGroupEnum = input.decideAgeGroup();
            Team teamOfAgeGroup = util.findCompetitiveTeam(teamArray, ageGroupEnum);
            RecordComparator rc = new RecordComparator();
            ArrayList<RecordTimePractice> swimDisciplineRecords = teamOfAgeGroup.findRecordsOfSwimDiscipline(swimDiscipline);
            swimDisciplineRecords.sort(rc);
            if (swimDisciplineRecords.size() >= 1) {
                ui.displayTopFive(swimDisciplineRecords,teamOfAgeGroup);
            } else {
                ui.displayTopFiveError();
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void showMemberRecords(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            String memberID = input.enterCompetitiveMemberID();
            Team team = findCompetitiveTeamWithID(teamArray,memberID);
            if (team != null) {
                ArrayList<RecordTime> memberRecords = team.findRecordsOfSwimmer(memberID);
                if(memberRecords.size()!=0){
                ui.displayMemberRecords(memberRecords,memberID);
                }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }
    }
}
