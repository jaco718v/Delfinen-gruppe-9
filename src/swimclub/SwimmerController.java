package swimclub;

import database.FileHandler;
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

    public void addCouchToTeam() {
        // TODO: Add coach (User) to Team and save to Teams.csv
    }

    public void showAllSwimmers() {
        // TODO: Show all records from all swimmers
    }

    private boolean updateRecord(ArrayList<String[]> recordList, String[] newRecord) {
        boolean previousRecordFound = false;
        for (String[] record : recordList) {
            if (record[0].equalsIgnoreCase(newRecord[0]) && record[1].equalsIgnoreCase(newRecord[1])) {
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
            String memberName = input.findCompetitiveMemberNameByID(teamArray);
            if (memberName != null) {
                ArrayList<String[]> recordList = fileHandler.readCSV("Records.csv");
                ArrayList<String[]> data = new ArrayList<>();
                String recordType = input.recordTypeChoice(memberName);
                String swimDiscipline = input.addSwimDisciplineToRecord(teamArray, memberName);
                String recordInSeconds = input.addRecordInSeconds();
                String date = input.addDate();
                if (recordType.equals("regular")) {
                    String[] newRecord = {memberName, swimDiscipline, recordInSeconds, date, " ", " "};
                    data.add(newRecord);
                    boolean updateStatus = updateRecord(recordList, newRecord);
                    if (!updateStatus) {
                        fileHandler.writeToCSV("Records.csv", data);
                    }
                }
                if (recordType.equals("competitive")) {
                    addCompetitiveRecordToMember(data, memberName, swimDiscipline, recordInSeconds, date);
                }
                ui.displayRecordAddSucces(recordType);
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    private void addCompetitiveRecordToMember(ArrayList<String[]> data, String memberName, String swimDiscipline, String recordInSeconds, String date) {
        ui.displayEnterConventionName();
        String convention = input.enterString();
        String placing = input.addPlacing();
        data.add(new String[]{memberName, swimDiscipline, recordInSeconds, date, convention, placing});
        fileHandler.writeToCSV("Records.csv", data);
    }

    public void showTopSwimmers(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            String swimDiscipline = input.addSwimDisciplineToRecordViaInput();
            Enum.AgeGroup ageGroupEnum = input.decideAgeGroup();
            ArrayList<String[]> ageGroup = util.findCompetitiveTeam(teamArray, ageGroupEnum);
            RecordComparator rc = new RecordComparator();
            ArrayList<String[]> recordData = fileHandler.readCSV("Records.csv");
            recordData = util.removeIrrelevantRecords(recordData, ageGroup, swimDiscipline);
            recordData.sort(rc);
            if (recordData.size() >= 1) {
                ui.displayTopFive(recordData);
            } else {
                ui.displayTopFiveError();
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void showMemberRecords(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            String memberName = input.findCompetitiveMemberNameByID(teamArray);
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
