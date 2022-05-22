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

    public void addCoachToTeam(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            int amountOfCoaches = getAmountOfCoaches();
            if (amountOfCoaches > 0) {
                User selectedCoach;
                if (loggedInUser.getUserType() == Enum.UserType.ADMIN) {
                    showCoaches(loggedInUser);
                    selectedCoach = input.enterCoachToAddToTeam();
                } else {
                    selectedCoach = loggedInUser;
                }
                util.displayMembers(loggedInUser, teamArray, true);
                Team selectedTeam = input.enterTeamToAddCoachTo(teamArray);
                selectedTeam.setCoach(selectedCoach);
                ArrayList<String[]> teamData = fileHandler.readCSV("Teams.csv");
                for (int i = 0; i < teamArray.size(); i++) {
                    if (teamArray.get(i) == selectedTeam) {
                        String[] teamDataArray = new String[5];
                        teamDataArray[0] = selectedTeam.getTeamType().name();
                        teamDataArray[1] = selectedTeam.getAgeGroup().name();
                        teamDataArray[2] = selectedCoach.getName();
                        teamDataArray[3] = selectedCoach.getPassword();
                        teamDataArray[4] = selectedCoach.getUserType().name();
                        if ((teamData.size() > 0) && (teamData.size()-1 >= i)) {
                            teamData.remove(i);
                            teamData.add(i, teamDataArray);
                        } else {
                            teamData.add(teamDataArray);
                        }
                        fileHandler.overwriteCSV("Teams.csv", teamData);
                        ui.displayCoachAddedToTeam(selectedCoach.getName(), i+1);
                        break;
                    }
                }
            } else {
                ui.displayNoCoachesRegistered();
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void removeCoachFromTeam(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            int amountOfCoaches = getAmountOfTeamsWithCoaches();
            if (amountOfCoaches > 0) {
                util.displayMembers(loggedInUser, teamArray, true);
                Team selectedTeam = input.enterTeamToRemoveCoachFrom(teamArray);
                User removedCoach = selectedTeam.getCoach();
                selectedTeam.setCoach(null);
                ArrayList<String[]> teamData = fileHandler.readCSV("Teams.csv");
                for (int i = 0; i < teamData.size(); i++) {
                    String[] teamDataArray = teamData.get(i);
                    if (teamDataArray[0].equals(selectedTeam.getTeamType().name()) && (teamDataArray[1].equals(selectedTeam.getAgeGroup().name()))) {
                        if ((teamData.size() > 0) && (teamData.size()-1 >= i)) {
                            teamData.remove(i);
                            teamData.add(i, new String[] { selectedTeam.getTeamType().name(), selectedTeam.getAgeGroup().name() });
                        }
                        fileHandler.overwriteCSV("Teams.csv", teamData);
                        ui.displayCoachRemovedFromTeam(removedCoach.getName(), i+1);
                        break;
                    }
                }
            } else {
                ui.displayNoCoachesRegistered();
            }
        } else {
            ui.loggedInUserNoPrivilege();
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

    private void showCoaches(User loggedInUser) {
        ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
        for (int i = 0; i < userData.size(); i++) {
            String[] strArray = userData.get(i);
            try {
                if (strArray[2].equals("COACH")) {
                    ui.displayUserInformation(strArray, loggedInUser, i);
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }
    }

    public void showAllSwimmers(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            for(Team team : teamArray){
                if(team.getTeamType().equals(Enum.TeamType.COMPETITIVE)){
                    for(int i = 0; i<team.getMembers().size(); i++){
                        ArrayList<RecordTime> memberRecords = team.findRecordsOfSwimmer(team.getMembers().get(i).getId());
                        if(memberRecords.size()!=0){
                            String memberName = team.findCompetitiveMemberNameWithID(team.getMembers().get(i).getId());
                            ui.displayMemberRecords(memberRecords,team.getMembers().get(i).getId(),memberName);
                        }
                    }
                }
            }
        } else {
            ui.loggedInUserNoPrivilege();
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

    public void addRecordToMember(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            util.displayMembers(loggedInUser, teamArray, false);
            String memberID = input.enterCompetitiveMemberID();
            Team team = findCompetitiveTeamWithID(teamArray,memberID);
            if (team != null) {
                ArrayList<String[]> recordList = fileHandler.readCSV("Records.csv");
                ArrayList<String[]> data = new ArrayList<>();
                String recordType = input.recordTypeChoice(memberID);
                Enum.SwimDiscipline swimDiscipline = input.addSwimDisciplineToRecord(team, memberID);
                double recordInSeconds = input.addRecordInSeconds();
                String date = input.addDate();

                if (recordType.equals("practice")) {
                    team.findCompetitiveMemberWithID(memberID).AddRecordPractice(new RecordTimePractice(memberID,swimDiscipline,recordInSeconds,date));
                    String[] newRecord = {memberID, String.valueOf(swimDiscipline), String.valueOf(recordInSeconds), date, "practice"};
                    data.add(newRecord);
                    boolean updateStatus = updateRecordInCSV(recordList, newRecord);
                    if (!updateStatus) {
                        fileHandler.writeToCSV("Records.csv", data);
                    }
                }
                if (recordType.equals("competition")) {
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
        data.add(new String[]{memberID, String.valueOf(swimDiscipline), String.valueOf(recordInSeconds), date, convention, String.valueOf(placing),"competition"});
        fileHandler.writeToCSV("Records.csv", data);
    }

    public void showTopSwimmers(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            Enum.SwimDiscipline swimDiscipline = input.addSwimDisciplineToRecordViaInput();
            Enum.AgeGroup ageGroupEnum = input.decideAgeGroup();
            Team teamOfAgeGroup = util.findCompetitiveTeam(teamArray,Enum.TeamType.COMPETITIVE, ageGroupEnum);
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
            util.displayMembers(loggedInUser, teamArray, false);
            String memberID = input.enterCompetitiveMemberID();
            Team team = findCompetitiveTeamWithID(teamArray,memberID);
            if (team != null) {
                String memberName = team.findCompetitiveMemberNameWithID(memberID);
                ArrayList<RecordTime> memberRecords = team.findRecordsOfSwimmer(memberID);
                if(memberRecords.size()!=0){
                ui.displayMemberRecords(memberRecords,memberID,memberName);
                }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }
    }
}
