package swimclub;

import database.FileHandler;
import database.RecordComparator;
import membership.Enum;
import membership.Team;
import membership.*;

import ui.UI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Controller {
    private final Scanner sc = new Scanner(System.in);
    private final FileHandler fileHandler = new FileHandler();
    private final UI ui = new UI();
    private final Commands cmds = new Commands();
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
                String userName = addUserName();
                String userPassword = addUserPassword();
                String userType = addUserType();

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

    private String addUserName() {
        String userName = "";
        boolean enteredUserName = false;
        while (!enteredUserName) {
            ui.displayPleaseTypeLoginName();
            userName = sc.nextLine();
            if ((!userName.equals("")) && (!userName.equals(" "))) {
                enteredUserName = true;
            }
            if (!enteredUserName) {
                ui.displayPleaseEnterValidUser(userName);
            }
        }
        return writeNameParts(userName);
    }

    private String addUserPassword() {
        String userPassword = "";
        boolean enteredUserPassword = false;
        while (!enteredUserPassword) {
            ui.displayPleaseTypeLoginPassword();
            userPassword = sc.nextLine();
            if ((!userPassword.equals("")) && (!userPassword.equals(" "))) {
                enteredUserPassword = true;
            }
            if (!enteredUserPassword) {
                ui.displayBadPassword(userPassword);
            }
        }
        return userPassword;
    }

    private String addUserType() {
        String userType = "";
        boolean enteredUserType = false;
        while (!enteredUserType) {
            ui.displayPleaseSelectUserType();
            userType = sc.nextLine();
            switch (userType) {
                case "1", "2", "3", "4" -> enteredUserType = true;
            }
            ui.addUser(enteredUserType);
            if (!enteredUserType) {
                ui.displayBadPassword(userType);
            }
        }
        return userType;
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

    private String addMemberId() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        String returnValue = "FULL";
        int memberIdInt = 0;
        int lastIndex = memberData.size() - 1;
        if (lastIndex != -1) {
            String[] memberArray = memberData.get(lastIndex);
            if (tryParseInt(memberArray[0])) {
                memberIdInt = Integer.parseInt(memberArray[0]);
            }
        }
        if (memberIdInt + 1 < 10000) {
            memberIdInt += 1;
            returnValue = String.format("%04d", memberIdInt);
        } else {
            boolean foundNewMemberId = false;
            for (int i = 1; i < 10000; i++) {
                for (String[] memberArray : memberData) {
                    int thisMemberIdInt = Integer.parseInt(memberArray[0]);
                    if (thisMemberIdInt != i) {
                        foundNewMemberId = true;
                        break;
                    }
                }
                if (foundNewMemberId) {
                    returnValue = String.format("%04d", i);
                    break;
                }
            }
        }
        return returnValue;
    }

    private String addMemberName() {
        String memberName = "";
        boolean enteredMemberName = false;
        while (!enteredMemberName) {
            ui.displayPleaseEnterMemberName();
            memberName = sc.nextLine();
            if ((!memberName.equals("")) && (!memberName.equals(" ")) && (!tryParseInt(memberName))) {
                enteredMemberName = true;
            }
            if (!enteredMemberName) {
                ui.displayPleaseEnterValidName(memberName);
            }
        }
        return writeNameParts(memberName);
    }

    private String addMemberBirthDay() {
        String memberBirthDay = "";
        int birthDay = 0;
        boolean enteredMemberBirthDay = false;
        while (!enteredMemberBirthDay) {
            ui.displayPleaseEnterMemberBirthDay();
            memberBirthDay = sc.nextLine();
            if ((tryParseInt(memberBirthDay)) && (Integer.parseInt(memberBirthDay) >= 1) && (Integer.parseInt(memberBirthDay) <= 31)) {
                enteredMemberBirthDay = true;
                birthDay = Integer.parseInt(memberBirthDay);
            } else {
                ui.displayPleaseEnterValidBirthDay();
            }
        }
        if (birthDay != 0) {
            return String.format("%02d", birthDay);
        } else {
            return null;
        }
    }

    private String addMemberBirthMonth() {
        String memberBirthMonth = "";
        int birthMonth = 0;
        boolean enteredMemberBirthMonth = false;
        while (!enteredMemberBirthMonth) {
            ui.displayPleaseEnterMemberBirthMonth();
            memberBirthMonth = sc.nextLine();
            if ((tryParseInt(memberBirthMonth)) && (Integer.parseInt(memberBirthMonth) >= 1) && (Integer.parseInt(memberBirthMonth) <= 12)) {
                enteredMemberBirthMonth = true;
                birthMonth = Integer.parseInt(memberBirthMonth);
            } else {
                ui.displayPleaseEnterValidBirthMonth();
            }
        }
        if (birthMonth != 0) {
            return String.format("%02d", birthMonth);
        } else {
            return null;
        }
    }

    private String addMemberBirthYear() {
        String memberBirthYear = "";
        int birthYear = 0;
        boolean enteredMemberBirthYear = false;
        while (!enteredMemberBirthYear) {
            ui.displayPleaseEnterMemberYear();
            memberBirthYear = sc.nextLine();
            if ((tryParseInt(memberBirthYear)) && (Integer.parseInt(memberBirthYear) >= LocalDateTime.now().getYear() - 120) && (Integer.parseInt(memberBirthYear) <= LocalDateTime.now().getYear())) {
                enteredMemberBirthYear = true;
                birthYear = Integer.parseInt(memberBirthYear);
            } else {
                ui.displayPleaseEnterValidBirthYear();
            }
        }
        if (birthYear != 0) {
            return String.format("%04d", birthYear);
        } else {
            return null;
        }
    }

    private String addPassiveOrActive() {
        String value = "false";
        ui.displayActiveOrPassiveOptions();
        String input = sc.nextLine();
        switch (input) {
            case "1" -> {
                value = "true";
                ui.displayActiveOrPassiveOutcome(true);
            }
            case "2" -> {
                value = "false";
                ui.displayActiveOrPassiveOutcome(false);
            }
            default -> ui.displayDefaultOption();
        }
        return value;
    }

    private String addCompetitiveMember() {
        ui.displayCompOrRegOptions();
        String competitive = "false";
        String input = sc.nextLine();
        switch (input) {
            case "1" -> competitive = "true";
            case "2" -> competitive = "false";
            default -> ui.displayCompOrReg();
        }
        return competitive;
    }

    public void addMember() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> newMemberData = new ArrayList<>();

            String memberId = addMemberId();
            if (!memberId.equals("FULL")) {
                String memberName = addMemberName();
                String memberBirthDate = addMemberBirthDay() + "/" + addMemberBirthMonth() + "/" + addMemberBirthYear();
                String isActive = addPassiveOrActive();
                String isCompetitive = addCompetitiveMember();
                String swimDiscipline = " ";
                if (isCompetitive.equals("true")) {
                    swimDiscipline = addSwimDisciplineToRecordViaInput();
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
                        case "1" -> array[1] = addMemberName();
                        case "2" -> array[2] = addMemberBirthDay() + "/" + addMemberBirthMonth() + "/" + addMemberBirthYear();
                        case "3" -> array[3] = addPassiveOrActive();
                        case "4" -> array[4] = addCompetitiveMember();
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
            ui.displayTeamInformation(this, teamNumber, team);

            for (String[] strArray : memberData) {
                int age = convertDateToAge(strArray[2]);
                if ((team.getAgeGroup() == Enum.AgeGroup.U18) && (age < 18)) {
                    if (((strArray[4].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) || ((strArray[4].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR))) {
                        ui.displayMemberInformation(this, strArray);
                    }
                } else if ((team.getAgeGroup() == Enum.AgeGroup.O18) && (age >= 18)) {
                    if (((strArray[4].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) || ((strArray[4].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR))) {
                        ui.displayMemberInformation(this, strArray);
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
            if (tryParseInt(foundId)) {
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

    private String findCompetitiveMemberNameByID() {
        showMembers();
        ui.displayPleaseTypeMemberID();
        String memberID = sc.nextLine();
        for (Team team : teamArray) {
            String memberName = team.findCompetitiveMemberNameWithID(memberID);
            if (memberName != null) {
                return memberName;
            }
        }
        ui.memberIDNotFound();
        return null;
    }

    private String addSwimDisciplineToRecord(String name) {
        String swimDiscipline = null;
        for (Team team : teamArray) {
            swimDiscipline = team.findMemberSwimDiscipline(name);
            if (swimDiscipline != null) {
                return swimDiscipline;
            }
        }

        return null;
    }

    private String addSwimDisciplineToRecordViaInput() {
        ui.displayEnterSwimDiscipline();
        String swimDiscipline = null;
        while (swimDiscipline == null) {
            try {
                String choice = sc.nextLine();
                switch (choice) {
                    case "1" -> swimDiscipline = "butterfly";
                    case "2" -> swimDiscipline = "crawl";
                    case "3" -> swimDiscipline = "backcrawl";
                    case "4" -> swimDiscipline = "breast";
                }
            } catch (InputMismatchException ime) {
                ui.displayEnterSwimDisciplineException();
            }
        }
        return swimDiscipline;
    }

    private String recordTypeChoice() {
        String recordType = null;
        ui.DisplayRecordTypeChoice();
        while (recordType == null || !recordType.equalsIgnoreCase("1") && !recordType.equalsIgnoreCase("2")) {
            recordType = sc.nextLine();
        }
        if (recordType.equals("1")) {
            recordType = "regular";
        }
        if (recordType.equals("2")) {
            recordType = "competitive";
        }
        return recordType;
    }

    private String addRecordInSeconds() {
        ui.displayEnterRecordInSeconds();
        double recordDouble = 0;
        String recordInSeconds = null;
        while (recordInSeconds == null || recordDouble <= 0) {
            try {
                recordInSeconds = sc.nextLine();
                recordDouble = Double.parseDouble(recordInSeconds);
            } catch (NumberFormatException | NoSuchElementException nfe) {
                ui.displayEnterRecordInSecondsException();
            }
        }
        return String.format("%.2f",recordDouble);
    }

    private String addDate() {
        ui.displayEnterDate();
        int day = 0;
        int month = 0;
        int year = 0;
        String dateInput = null;
        int[] date = {day, month, year};
        while (year == 0 || day > 31 || month > 12) {
            try {
                dateInput = sc.nextLine();
                Scanner dateInputScanner = new Scanner(dateInput);
                dateInputScanner.useDelimiter("/");
                day = Integer.parseInt(dateInputScanner.next());
                month = Integer.parseInt(dateInputScanner.next());
                year = Integer.parseInt(dateInputScanner.next());
                if (day > 31 || month > 12) {
                    ui.displayEnterDateException();
                }
            } catch (NoSuchElementException | NumberFormatException nsee) {
                ui.displayEnterDateException();
            }
        }
        return dateInput;
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
            String memberName = findCompetitiveMemberNameByID();     //Change to id?
            if (memberName != null) {
                ArrayList<String[]> recordList = fileHandler.readCSV("Records.csv");
                ArrayList<String[]> data = new ArrayList<>();
                String recordType = recordTypeChoice();
                String swimDiscipline = addSwimDisciplineToRecord(memberName);
                String recordInSeconds = addRecordInSeconds();
                String date = addDate();
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

    private String addPlacing() {
        ui.displayEnterPlacing();
        int placeInt = 0;
        String place = null;
        while (place == null || placeInt <= 0) {
            try {
                place = sc.nextLine();
                placeInt = Integer.parseInt(place);
            } catch (NumberFormatException | NoSuchElementException nfe) {
                ui.displayEnterPlacingException();
            }
        }
        return place;
    }

    private void addCompetitiveRecordToMember(ArrayList<String[]> data, String memberName, String swimDiscipline, String recordInSeconds, String date) {
        ui.displayEnterConventionName();
        String convention = sc.nextLine();
        String placing = addPlacing();
        data.add(new String[]{memberName, swimDiscipline, recordInSeconds, date, convention, placing});
        fileHandler.writeToCSV("Records.csv", data);
    }

    private Enum.AgeGroup decideAgeGroup() {
        ui.displayDecideAgeGroupTopFive();
        int choice = 0;
        while (choice != 1 && choice != 2) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException ime) {
                ui.displayDecideAgeGroupTopFiveError();
            }
        }
        if (choice == 1) {
            return Enum.AgeGroup.U18;
        }
        return Enum.AgeGroup.O18;
    }


    private void removeIrrelevantRecords(ArrayList<String[]> recordData, ArrayList<String[]> memberData, String swimDiscipline) {
        for (int i = 0; recordData.size() > i; i++) {
            if (!recordData.get(i)[1].equals(swimDiscipline) || !recordData.get(i)[4].equals(" ")) {
                recordData.remove(i);
                i--;
            }
        }
        for (int i = 0; recordData.size() > i; i++) {
            boolean irrelevant = true;
            for (String[] strArray : memberData) {
                if (strArray[1].equalsIgnoreCase(recordData.get(i)[0])) {
                    irrelevant = false;
                    break;
                }
            }
            if (irrelevant) {
                recordData.remove(i);
                i--;
            }
        }
    }

    private ArrayList<String[]> findCompetitiveTeam(Enum.AgeGroup ageGroup) {
            for(Team team : teamArray){
                if(team.getTeamType().equals(Enum.TeamType.COMPETITIVE) && team.getAgeGroup().equals(Enum.AgeGroup.U18) && ageGroup.equals(Enum.AgeGroup.U18)) {
                    return team.getMembers();}
                if(team.getTeamType().equals(Enum.TeamType.COMPETITIVE) && team.getAgeGroup().equals(Enum.AgeGroup.O18) && ageGroup.equals(Enum.AgeGroup.O18)) {
                    return team.getMembers();}
                }
      return null;
    }

    public void showTopSwimmers() {
        String swimDiscipline = addSwimDisciplineToRecordViaInput();
        Enum.AgeGroup ageGroupEnum = decideAgeGroup();
        ArrayList<String[]> ageGroup = findCompetitiveTeam(ageGroupEnum);
        ArrayList<String[]> recordData = fileHandler.readCSV("Records.csv");
        RecordComparator rc = new RecordComparator();
        removeIrrelevantRecords(recordData, ageGroup, swimDiscipline);
        recordData.sort(rc);
        if (recordData.size() >= 1) {
            ui.displayTopFive(recordData);
        } else {
            ui.displayTopFiveError();
        }
    }

    public void showMemberRecords() {
        String memberName = findCompetitiveMemberNameByID();
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

    private boolean tryParseInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception E) {
            return false;
        }
        return true;
    }

    public String capitalizeString(String capitalizeWord) {
        capitalizeWord = capitalizeWord.substring(0, 1).toUpperCase() + capitalizeWord.substring(1).toLowerCase();
        return capitalizeWord;
    }

    public String writeNameParts(String myName) {
        String firstName;
        String middleName;
        String lastName;

        int firstWordStartIndex = 0;
        int firstWordEndIndex = myName.indexOf(" ");
        int secondWordStartIndex = myName.indexOf(" ") + 1;
        int secondWordEndIndex = myName.lastIndexOf(" ");
        int thirdWordStartIndex = myName.lastIndexOf(" ") + 1;

        if (secondWordStartIndex != thirdWordStartIndex) {
            middleName = myName.substring(secondWordStartIndex, secondWordEndIndex);
            middleName = capitalizeString(middleName);

            lastName = myName.substring(thirdWordStartIndex);
            lastName = capitalizeString(lastName);
        } else {
            middleName = "";
            lastName = myName.substring(secondWordStartIndex);
            lastName = capitalizeString(lastName);
        }
        if (firstWordEndIndex != -1) {
            firstName = myName.substring(firstWordStartIndex, firstWordEndIndex);
            firstName = capitalizeString(firstName);
            String[] fullNameArray;
            if (middleName.equals("")) {
                fullNameArray = writeFullName(firstName, null, lastName);
            } else {
                fullNameArray = writeFullName(firstName, middleName, lastName);
            }
            StringBuilder fullName = new StringBuilder();
            for (int i = 0; i < fullNameArray.length; i++) {
                fullName.append(fullNameArray[i]);
                if (i < fullNameArray.length - 1) {
                    fullName.append(" ");
                }
            }
            return fullName.toString();
        } else {
            return null;
        }
    }

    public String[] writeFullName(String firstName, String middleName, String lastName) {
        if (middleName != null) {
            firstName = capitalizeString(firstName);
            middleName = capitalizeString(middleName);
            lastName = capitalizeString(lastName);
            return new String[]{firstName, middleName, lastName};
        } else {
            firstName = capitalizeString(firstName);
            lastName = capitalizeString(lastName);
            return new String[]{firstName, lastName};
        }
    }

    public int convertDateToAge(String string) {
        Scanner dateInputScanner = new Scanner(string);
        dateInputScanner.useDelimiter("/");
        int birthDay = Integer.parseInt(dateInputScanner.next());
        int birthMonth = Integer.parseInt(dateInputScanner.next());
        int birthYear = Integer.parseInt(dateInputScanner.next());
        int age;
        if ((birthDay < LocalDateTime.now().getDayOfMonth()) && (birthMonth < LocalDateTime.now().getMonth().getValue())) {
            age = LocalDateTime.now().getYear() - birthYear - 1;
        } else {
            age = LocalDateTime.now().getYear() - birthYear;
        }
        return age;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
