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
    private final InputHandler input = new InputHandler();
    private final FileHandler fileHandler = new FileHandler();
    private final Utility util = new Utility();

    public void addCoachToTeam(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.COACH)) {
            int amountOfCoaches = getAmountOfCoaches();
            if (amountOfCoaches > 0) {
                User selectedCoach;
                if (con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) {
                    showCoaches(con);
                    selectedCoach = input.enterCoachToAddToTeam(con);
                } else {
                    selectedCoach = con.getLoggedInUser();
                }
                util.displayMembers(con.getLoggedInUser(), con.getTeamArray(), true, false, con.getLanguage());
                Team selectedTeam = input.enterTeamToAddCoachTo(con);
                selectedTeam.setCoach(selectedCoach);
                ArrayList<String[]> teamData = fileHandler.readCSV("Teams.csv");
                for (int i = 0; i < con.getTeamArray().size(); i++) {
                    if (con.getTeamArray().get(i) == selectedTeam) {
                        String[] teamDataArray = new String[5];
                        teamDataArray[0] = selectedTeam.getTeamType().name();
                        teamDataArray[1] = selectedTeam.getAgeGroup().name();
                        teamDataArray[2] = selectedCoach.getName();
                        teamDataArray[3] = selectedCoach.getPassword();
                        teamDataArray[4] = selectedCoach.getUserType().name();
                        if ((teamData.size() > 0) && (teamData.size() - 1 >= i)) {
                            teamData.remove(i);
                            teamData.add(i, teamDataArray);
                        } else {
                            teamData.add(teamDataArray);
                        }
                        fileHandler.overwriteCSV("Teams.csv", teamData);
                        ui.displayCoachAddedToTeam(selectedCoach.getName(), i + 1);
                        break;
                    }
                }
            } else {
                ui.displayNoCoachesRegistered();
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void removeCoachFromTeam(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.COACH)) {
            int amountOfCoaches = getAmountOfTeamsWithCoaches();
            if (amountOfCoaches > 0) {
                util.displayMembers(con.getLoggedInUser(), con.getTeamArray(), true, false, con.getLanguage());
                Team selectedTeam = input.enterTeamToRemoveCoachFrom(con);
                User removedCoach = selectedTeam.getCoach();
                selectedTeam.setCoach(null);
                ArrayList<String[]> teamData = fileHandler.readCSV("Teams.csv");
                for (int i = 0; i < teamData.size(); i++) {
                    String[] teamDataArray = teamData.get(i);
                    if (teamDataArray[0].equals(selectedTeam.getTeamType().name()) && (teamDataArray[1].equals(selectedTeam.getAgeGroup().name()))) {
                        if ((teamData.size() > 0) && (teamData.size() - 1 >= i)) {
                            teamData.remove(i);
                            teamData.add(i, new String[]{selectedTeam.getTeamType().name(), selectedTeam.getAgeGroup().name()});
                        }
                        fileHandler.overwriteCSV("Teams.csv", teamData);
                        ui.displayCoachRemovedFromTeam(removedCoach.getName(), i + 1);
                        break;
                    }
                }
            } else {
                ui.displayNoCoachesRegistered();
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    private int getAmountOfTeamsWithCoaches() {
        ArrayList<String[]> userData = fileHandler.readCSV("Teams.csv");
        int amountOfCoaches = 0;
        for (String[] strArray : userData) {
            try {
                if (strArray[4].equals("COACH")) {
                    amountOfCoaches += 1;
                }
            } catch (Exception ignored) {

            }
        }
        return amountOfCoaches;
    }

    private int getAmountOfCoaches() {
        ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
        int amountOfCoaches = 0;
        for (String[] strArray : userData) {
            try {
                if (strArray[2].equals("COACH")) {
                    amountOfCoaches += 1;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }
        return amountOfCoaches;
    }

    private void showCoaches(Controller con) {
        UI ui = new UI(con.getLanguage());
        ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
        for (int i = 0; i < userData.size(); i++) {
            String[] strArray = userData.get(i);
            try {
                if (strArray[2].equals("COACH")) {
                    ui.displayUserInformation(strArray, con.getLoggedInUser(), i);
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }
    }

    public void showAllSwimmers(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.COACH)) {
            boolean recordEmpty = true;
            for (Team team : con.getTeamArray()) {
                if (team.getTeamType().equals(Enum.TeamType.COMPETITIVE)) {
                    for (int i = 0; i < team.getMembers().size(); i++) {
                        ArrayList<RecordTime> memberRecords = team.findRecordsOfSwimmer(team.getMembers().get(i).getId());
                        if (memberRecords.size() != 0) {
                            recordEmpty = false;
                            String memberName = team.findCompetitiveMemberNameWithID(team.getMembers().get(i).getId());
                            ui.displayMemberRecords(memberRecords, team.getMembers().get(i).getId(), memberName);
                        }
                    }
                }
            }
            if (recordEmpty) {
                ui.displayNoMemberRecords();
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    private boolean updateRecordInCSV(ArrayList<String[]> recordList, String[] newRecord) {
        boolean previousRecordFound = false;
        for (String[] record : recordList) {
            if (record[0].equalsIgnoreCase(newRecord[0]) && record[1].equalsIgnoreCase(newRecord[1]) && record.length == 5) {
                recordList.set(recordList.indexOf(record), newRecord);
                previousRecordFound = true;
                fileHandler.overwriteCSV("Records.csv", recordList);
            }
        }
        return previousRecordFound;
    }

    public void addRecordToMember(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.COACH)) {
            util.displayMembers(con.getLoggedInUser(), con.getTeamArray(), false, true, con.getLanguage());
            String memberID = input.enterCompetitiveMemberID(con);
            Team team = findCompetitiveTeamWithID(con, memberID);
            if (team != null) {
                ArrayList<String[]> recordList = fileHandler.readCSV("Records.csv");
                ArrayList<String[]> data = new ArrayList<>();
                String recordType = input.recordTypeChoice(con);
                Enum.SwimDiscipline swimDiscipline = input.addSwimDisciplineToRecord(team, memberID);
                double recordInSeconds = input.addRecordInSeconds(con);
                String date = input.addDate(con);

                if (recordType.equals("practice")) {
                    team.findCompetitiveMemberWithID(memberID).addRecordPractice(new RecordTimePractice(memberID, swimDiscipline, recordInSeconds, date));
                    String[] newRecord = {memberID, String.valueOf(swimDiscipline), String.valueOf(recordInSeconds), date, "practice"};
                    data.add(newRecord);
                    boolean updateStatus = updateRecordInCSV(recordList, newRecord);
                    if (!updateStatus) {
                        fileHandler.writeToCSV("Records.csv", data);
                    }
                }
                if (recordType.equals("competition")) {
                    addCompetitiveRecordToMember(con, team, data, memberID, swimDiscipline, recordInSeconds, date);
                }
                ui.displayRecordAddSucces(recordType);
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    private Team findCompetitiveTeamWithID(Controller con, String memberID) {
        UI ui = new UI(con.getLanguage());
        for (Team team : con.getTeamArray()) {
            if (team.confirmCompetitiveMemberID(memberID) && team.getTeamType().equals(Enum.TeamType.COMPETITIVE)) {
                return team;
            }
        }
        ui.displayMemberIDNotFound();
        return null;
    }

    private void addCompetitiveRecordToMember(Controller con, Team team, ArrayList<String[]> data, String memberID, Enum.SwimDiscipline swimDiscipline, double recordInSeconds, String date) {
        UI ui = new UI(con.getLanguage());
        ui.displayEnterConventionName();
        String convention = input.enterString();
        int placing = input.addPlacing(con);
        team.findCompetitiveMemberWithID(memberID).addRecordCompetition(new RecordTimeCompetition(memberID, swimDiscipline, recordInSeconds, date, convention, placing));
        data.add(new String[]{memberID, String.valueOf(swimDiscipline), String.valueOf(recordInSeconds), date, convention, String.valueOf(placing), "competition"});
        fileHandler.writeToCSV("Records.csv", data);
    }

    public void showTopSwimmers(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.COACH)) {
            Enum.SwimDiscipline swimDiscipline = input.addSwimDisciplineToRecordViaInput(con);
            Enum.AgeGroup ageGroupEnum = input.decideAgeGroup(con);
            Team teamOfAgeGroup = util.findCompetitiveTeam(con.getTeamArray(), Enum.TeamType.COMPETITIVE, ageGroupEnum);
            RecordComparator rc = new RecordComparator();
            ArrayList<RecordTimePractice> swimDisciplineRecords = teamOfAgeGroup.findRecordsOfSwimDiscipline(swimDiscipline);
            swimDisciplineRecords.sort(rc);
            if (swimDisciplineRecords.size() >= 1) {
                ui.displayTopFive(swimDisciplineRecords, teamOfAgeGroup);
            } else {
                ui.displayTopFiveError();
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void showMemberRecords(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.COACH)) {
            util.displayMembers(con.getLoggedInUser(), con.getTeamArray(), false, true, con.getLanguage());
            String memberID = input.enterCompetitiveMemberID(con);
            Team team = findCompetitiveTeamWithID(con, memberID);
            if (team != null) {
                String memberName = team.findCompetitiveMemberNameWithID(memberID);
                ArrayList<RecordTime> memberRecords = team.findRecordsOfSwimmer(memberID);
                if (memberRecords.size() != 0) {
                    ui.displayMemberRecords(memberRecords, memberID, memberName);
                } else {
                    ui.displayNoMemberRecords();
                }
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }
}
