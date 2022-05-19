package swimclub;

import database.FileHandler;
import database.RecordComparator;
import membership.Enum;
import membership.Team;
import membership.*;
import ui.InputHandler;
import ui.UI;
import utilities.Utility;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private final Scanner sc = new Scanner(System.in);
    private final FileHandler fileHandler = new FileHandler();
    private final UI ui = new UI();
    private final Commands cmds = new Commands();
    private final Utility util = new Utility();
    private final InputHandler input = new InputHandler();
    private boolean isRunning = true;
    private User loggedInUser;
    private ArrayList<Team> teamArray = new ArrayList<>();

    public static void main(String[] args) {
        Controller con = new Controller();
        con.run();
    }

    private void run() {
        createTeams();
        while (isRunning) {
            cmds.commands(this, sc);
        }
    }

    private void createTeams() {
        for (int i = 0; i < Enum.TeamType.values().length; i++) {
            for (int j = 0; j < Enum.AgeGroup.values().length; j++) {
                teamArray.add(new Team(Enum.TeamType.values()[i], Enum.AgeGroup.values()[j]));
            }
        }
    }

    public void exit() {
        ui.displayShuttingDown();
        isRunning = false;
    }

    public void loginUser() {
        ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
        if (userData.size() > 0) {
            int userIndex = -1;
            String userName = "";
            String userPassword = "";

            boolean enteredUserName = false;
            while (!enteredUserName) {
                ui.displayPleaseTypeLoginName();
                userName = sc.nextLine();
                for (int i = 0; i < userData.size(); i++) {
                    String[] strArray = userData.get(i);
                    if (strArray[0].equals(userName)) {
                        userIndex = i;
                        enteredUserName = true;
                    }
                }
                if (!enteredUserName) {
                    ui.displayPleaseEnterValidUser(userName);
                }
            }

            boolean enteredUserPassword = false;
            while (!enteredUserPassword) {
                ui.displayPleaseTypeLoginPassword();
                userPassword = sc.nextLine();
                if (userData.get(userIndex) != null) {
                    String[] strArray = userData.get(userIndex);
                    if (strArray[1].equals(userPassword)) {
                        enteredUserPassword = true;
                    }
                }
                ui.loginUser(enteredUserPassword);
                if (!enteredUserPassword) {
                    ui.displayWrongPassword();
                }
            }
            String[] strArray = userData.get(userIndex);
            Enum.UserType userType = Enum.UserType.valueOf(strArray[2]);
            loggedInUser = new User(userName, userPassword, userType);
        } else {
            ui.noRegisteredUsers();
            addUser();
        }
    }

    public void addUser() {
        ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
        if (userData.size() > 0) {
            if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
                String userName = input.addUserName();
                String userPassword = input.addUserPassword();
                String userType = input.addUserType();

                switch (userType) {
                    case "1" -> {
                        ArrayList<String[]> newData = new ArrayList<>();
                        newData.add(new String[]{userName, userPassword, Enum.UserType.ADMIN.name()});
                        fileHandler.writeToCSV("Users.csv", newData);
                    }
                    case "2" -> {
                        ArrayList<String[]> newData = new ArrayList<>();
                        newData.add(new String[]{userName, userPassword, Enum.UserType.CHAIRMAN.name()});
                        fileHandler.writeToCSV("Users.csv", newData);
                    }
                    case "3" -> {
                        ArrayList<String[]> newData = new ArrayList<>();
                        newData.add(new String[]{userName, userPassword, Enum.UserType.CASHIER.name()});
                        fileHandler.writeToCSV("Users.csv", newData);
                    }
                    case "4" -> {
                        ArrayList<String[]> newData = new ArrayList<>();
                        newData.add(new String[]{userName, userPassword, Enum.UserType.COACH.name()});
                        fileHandler.writeToCSV("Users.csv", newData);
                    }
                }
            } else {
                ui.loggedInUserNoPrivilege();
            }
        } else {
            ui.noRegisteredUsersCreatingAdmin();
            userData.add(new String[]{"admin", "admin", Enum.UserType.ADMIN.name()});
            fileHandler.writeToCSV("Users.csv", userData);
        }
    }

    public void removeUser() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
            if (userData.size() > 0) {
                String userName = "";
                boolean enteredUserName = false;
                while (!enteredUserName) {
                    ui.displayPleaseTypeLoginName();
                    userName = sc.nextLine();
                    for (int i = 0; i < userData.size(); i++) {
                        String[] strArray = userData.get(i);
                        if (userName.equals(strArray[0])) {
                            enteredUserName = true;
                            userData.remove(i);
                            fileHandler.overwriteCSV("Users.csv", userData);
                            break;
                        }
                    }
                    ui.removeUser(enteredUserName);
                    if (!enteredUserName) {
                        ui.displayPleaseEnterValidUser(userName);
                    }
                }
            } else {
                ui.noRegisteredUsers();
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void showUsers() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Users.csv");
        for (String[] strArray : memberData) {
            ui.displayUserInformation(strArray, loggedInUser);
        }
    }

    public void addMember() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> newMemberData = new ArrayList<>();

            String memberId = util.addMemberId();
            if (!memberId.equals("FULL")) {
                String memberName = input.addMemberName();
                String memberBirthDate = input.addMemberBirthDay() + "/" + input.addMemberBirthMonth() + "/" + input.addMemberBirthYear();
                String isActive = input.addPassiveOrActive();
                String isCompetitive = input.addCompetitiveMember();
                String swimDiscipline = " ";
                if (isCompetitive.equals("true")) {
                    swimDiscipline = input.addSwimDisciplineToRecordViaInput();
                }

                newMemberData.add(new String[]{memberId, memberName, memberBirthDate, isActive, isCompetitive, swimDiscipline});
                boolean success = fileHandler.writeToCSV("Members.csv", newMemberData);
                updateSubscriptions();
                ui.addMember(success);
            } else {
                ui.displayMemberListFull();
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void editMember() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            // edit member kode her
            ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
            showMembers();
            ui.typeMemberIdPlease();
            String editMember = sc.nextLine(); // indtast memberid
            for (int i = 0; i < memberData.size(); i++) {
                String[] array = memberData.get(i);
                if (array[0].equals(editMember)) {
                    ui.whatToChange();
                    String command = sc.nextLine();
                    switch (command) {
                        case "1" -> array[1] = input.addMemberName();
                        case "2" -> array[2] = input.addMemberBirthDay() + "/" + input.addMemberBirthMonth() + "/" + input.addMemberBirthYear();
                        case "3" -> array[3] = input.addPassiveOrActive();
                        case "4" -> array[4] = input.addCompetitiveMember();
                    }
                    memberData.remove(i);
                    memberData.add(i, array);
                    fileHandler.overwriteCSV("Members.csv", memberData);
                    break;
                }
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void removeMember() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
            ArrayList<String[]> subData = fileHandler.readCSV("Subscriptions.csv");
            showMembers();
            ui.displayPleaseEnterMemberId();
            String memberId = sc.nextLine();
            boolean removedMember = false;
            for (int i = 0; i < memberData.size(); i++) {
                String[] memberArray = memberData.get(i);
                if (memberArray[0].equals(memberId)) {
                    memberData.remove(i);
                    fileHandler.overwriteCSV("Members.csv", memberData);
                    for (int j = 0; j < subData.size(); j++) {
                        String[] subArray = subData.get(j);
                        if (subArray[0].equals(memberId)) {
                            subData.remove(j);
                            fileHandler.overwriteCSV("Subscriptions.csv", subData);
                            break;
                        }
                    }
                    ui.removeMember(true);
                    removedMember = true;
                    break;
                }
            }
            if (!removedMember) {
                ui.removeMember(false);
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void showMembers() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        int teamNumber = 0;
        for (Team team : teamArray) {
            teamNumber += 1;
            ui.displayTeamInformation(teamNumber, team);

            for (String[] strArray : memberData) {
                int age = util.convertDateToAge(strArray[2]);
                if ((team.getAgeGroup() == Enum.AgeGroup.U18) && (age < 18)) {
                    if (((strArray[4].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) || ((strArray[4].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR))) {
                        ui.displayMemberInformation(strArray);
                    }
                } else if ((team.getAgeGroup() == Enum.AgeGroup.O18) && (age >= 18)) {
                    if (((strArray[4].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) || ((strArray[4].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR))) {
                        ui.displayMemberInformation(strArray);
                    }
                }
            }
        }
    }

    public void setPaymentStatus() {
        showSubscriptions();
        ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");

        ui.displayPleaseEnterUserId();
        String userIdInput = sc.nextLine();
        String foundId = "-1";
        for (int i = 0; i < subscriptionData.size(); i++) {
            String[] strArray = subscriptionData.get(i);
            if (strArray[0].equals(userIdInput)) {
                foundId = Integer.toString(i);
            }
        }
        if (!foundId.equals("-1")) {
            int foundIdInt = -1;
            if (util.tryParseInt(foundId)) {
                foundIdInt = Integer.parseInt(foundId);
            }
            String[] strArray = subscriptionData.get(foundIdInt);
            ui.displayPleaseEnterPaymentStatus();
            String userPaymentStatusInput = sc.nextLine();
            if (userPaymentStatusInput.equals("1")) {
                strArray[4] = "true";
            } else if (userPaymentStatusInput.equals("2")) {
                strArray[4] = "false";
            }
            subscriptionData.remove(foundIdInt);
            subscriptionData.add(foundIdInt, strArray);
            String[] printArray = subscriptionData.get(foundIdInt);
            ui.displayUpdatedMemberSubscription(printArray);
            fileHandler.overwriteCSV("Subscriptions.csv", subscriptionData);
        } else {
            ui.displayNoSuchMemberFound();
        }
    }

    public void updateSubscriptions() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
        for (String[] strArray : memberData) {
            boolean isRegistered = false;
            for (String[] subArray : subscriptionData) {
                if (subArray[0].equals(strArray[0])) {
                    isRegistered = true;
                    break;
                }
            }
            if (!isRegistered) {
                subscriptionData.add(new String[]{strArray[0], strArray[1], strArray[2], strArray[3], "false"});
                fileHandler.overwriteCSV("Subscriptions.csv", subscriptionData);
                System.out.println("Registered member subscription.");
            }
        }
    }

    public void showSubscriptions() {
        updateSubscriptions();
        ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
        for (String[] strArray : subscriptionData) {
            ui.displaySubscription(strArray);
        }
    }

    public void showExpectedSubscriptionFees() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CASHIER)) {
            double subscriptionJunior = 1000;
            double subscriptionSenior = 1600;
            double subscriptionPassive = 500;
            int seniorThreshold = 60;
            double seniorDiscount = 0.25;
            double subscriptionSum = 0;
            for (Team team : teamArray) {
                double subscription = subscriptionJunior;
                if (team.getAgeGroup() == Enum.AgeGroup.O18) {
                    subscription = subscriptionSenior;
                    subscriptionSum -= subscriptionSenior * seniorDiscount * team.getActiveMembersAboveAge(seniorThreshold);
                }
                subscriptionSum += team.getActiveMembers() * subscription + (team.getMembers().size() - team.getActiveMembers()) * subscriptionPassive;
            }
            ui.showExpectedSubscriptionFees(subscriptionSum);
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void showSubscriptionsInArrears() {
        ui.displaySubscriptionsInArrears();
        updateSubscriptions();
        ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
        for (String[] strArray : subscriptionData) {
            if (strArray[4].equals("false")) {
                ui.displaySubscription(strArray);
            }
        }
    }

    public void showAllSwimmers() {

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

    public void addRecordToMember() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            showMembers();
            String memberName = input.findCompetitiveMemberNameByID(teamArray);     //Change to id?
            if (memberName != null) {
                ArrayList<String[]> recordList = fileHandler.readCSV("Records.csv");
                ArrayList<String[]> data = new ArrayList<>();
                String recordType = input.recordTypeChoice(memberName);
                String swimDiscipline = input.addSwimDisciplineToRecord(teamArray, memberName);
                //String swimDiscipline = addSwimDisciplineToRecordViaInput();// En alternativ metode der tager input
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
        String convention = sc.nextLine();
        String placing = input.addPlacing();
        data.add(new String[]{memberName, swimDiscipline, recordInSeconds, date, convention, placing});
        fileHandler.writeToCSV("Records.csv", data);
    }

    public void showTopSwimmers() {
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
    }

    public void showMemberRecords() {
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
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
