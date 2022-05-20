package swimclub;

import database.FileHandler;
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

    public void showAllSwimmers() {
        // TODO: Show all records from all swimmers // Jeg tror ikke metoden er nødvendig mht. userStories - Jacob
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
            String memberID = input.findCompetitiveMemberByID(teamArray);
            if (memberID != null) {
                ArrayList<String[]> recordList = fileHandler.readCSV("Records.csv");
                ArrayList<String[]> data = new ArrayList<>();
                String recordType = input.recordTypeChoice(memberID);
                Enum.SwimDiscipline swimDiscipline = input.addSwimDisciplineToRecord(teamArray, memberID);
                double recordInSeconds = input.addRecordInSeconds();
                String date = input.addDate();
                if (recordType.equals("regular")) {
                    String[] newRecord = {memberID, String.valueOf(swimDiscipline), String.valueOf(recordInSeconds), date, " ", " "};
                    data.add(newRecord);
                    boolean updateStatus = updateRecordInCSV(recordList, newRecord);
                    if (!updateStatus) {
                        fileHandler.writeToCSV("Records.csv", data);
                    }
                }
                if (recordType.equals("competitive")) {
                    addCompetitiveRecordToMember(data, memberID, String.valueOf(swimDiscipline), String.valueOf(recordInSeconds), date);
                }
                ui.displayRecordAddSucces(recordType);
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    private void addCompetitiveRecordToMember(ArrayList<String[]> data, String memberID, String swimDiscipline, String recordInSeconds, String date) {
        ui.displayEnterConventionName();
        String convention = input.enterString();
        int placing = input.addPlacing();
        data.add(new String[]{memberID, swimDiscipline, recordInSeconds, date, convention, String.valueOf(placing)});
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
            String memberName = input.findCompetitiveMemberByID(teamArray);
            if (memberName != null) {
                ArrayList<String[]> recordList = fileHandler.readCSV("Records.csv");
                ArrayList<String[]> memberRecords = new ArrayList<>();
                for (String[] records : recordList) {
                    if (records[0].equals(memberName)) {
                        memberRecords.add(records);
                    }
                }
                ui.displayMemberRecords(memberRecords);
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }
}
