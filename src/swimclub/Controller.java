package swimclub;

import database.FileHandler;
import membership.Enum;
import membership.Team;
import membership.*;

import ui.UI;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Controller {
    // Team t = new Team();
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
        teamArray.add(new Team(Enum.TeamType.REGULAR, Enum.AgeGroup.U18));
        teamArray.add(new Team(Enum.TeamType.REGULAR, Enum.AgeGroup.O18));
        teamArray.add(new Team(Enum.TeamType.COMPETITIVE, Enum.AgeGroup.U18));
        teamArray.add(new Team(Enum.TeamType.COMPETITIVE, Enum.AgeGroup.O18));
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
        return userName;
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

    private String addMemberName(){
          String memberName  = "";
          boolean enteredmemberName = false;
          while (!enteredmemberName)
        {
              ui.displayMemberName();
              memberName = sc.nextLine();
              if ((!memberName.equals("")) && (!memberName.equals(" "))) {
                  enteredmemberName = true;
              }
              if (!enteredmemberName) {
                  ui.displayPleaseEnterValidName(memberName);
              }
          }
          return memberName;


    }

    private String addMemberAge() {
        int memberAgee = 0;
        String memberAge = "";

        boolean enteredmemberAgee = false;
        while (!enteredmemberAgee) {
            ui.displayMemberAge(memberAgee);
        memberAge = sc.nextLine();
        enteredmemberAgee = true;
          }

        if (!enteredmemberAgee) {
            ui.displayMemberAge(memberAgee);
        } return memberAge;
      }

    private String addPassiveOrActive(){

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
           default ->  ui.displayDefaultOption();
       }  return value;
      }

    private String addCompetitiveMember(){
        ui.displayCompOrRegOptions();
        String competitive = "false";
        String input = sc.nextLine();
        switch (input) {
            case "1" -> competitive = "true";
            case "2" -> competitive = "false";
            default -> ui.displayCompOrReg();
        }  return competitive;
    }

    public void addMember() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> memberData = new ArrayList<>();

            String memberName = addMemberName();
            String memberAge = addMemberAge();
            String isActive = addPassiveOrActive();
            String isCompetitive = addCompetitiveMember();

            memberData.add(new String[] {memberName, memberAge, isActive, isCompetitive});
            boolean success = fileHandler.writeToCSV("Members.csv", memberData);
            updateSubscriptions();
            ui.addMember(success);
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }



    public void editMember() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            // edit member kode her
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void removeMember() {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList <String[]> memberData = fileHandler.readCSV("Members.csv");
            ArrayList <String[]> subData = fileHandler.readCSV("Subscriptions.csv");
            ui.displayPleaseEnterMemberName();
            String removeName = sc.nextLine();

            for(int i = 0; i < memberData.size(); i++){
                String[] array = memberData.get(i);
                if (array[0].equals(removeName)) {
                    memberData.remove(i);
                    subData.remove(i);
                    fileHandler.overwriteCSV("Members.csv", memberData);
                    fileHandler.overwriteCSV("Subscriptions.csv", subData);
                    ui.removeMember(true);
                    break;
                } else {
                    ui.removeMember(false);
                }
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

            for (int i = 0; i < memberData.size(); i++) {
                String[] strArray = memberData.get(i);
                int age = Integer.parseInt(strArray[1]);
                if ((team.getAgeGroup() == Enum.AgeGroup.U18) && (age < 18)) {
                    if (((strArray[3].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) || ((strArray[3].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR))) {
                        ui.displayMemberInformation(strArray, i);
                    }
                } else if ((team.getAgeGroup() == Enum.AgeGroup.O18) && (age >= 18)) {
                    if (((strArray[3].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) || ((strArray[3].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR))) {
                        ui.displayMemberInformation(strArray, i);
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
            String[] newArray = new String[5];
            ui.displayPleaseEnterPaymentStatus();
            String userPaymentStatusInput = sc.nextLine();
            newArray[0] = strArray[0];
            newArray[1] = strArray[1];
            newArray[2] = strArray[2];
            newArray[3] = strArray[3];
            if (userPaymentStatusInput.equals("1")) {
                newArray[4] = "true";
                System.out.println("Set to paid");
            } else if (userPaymentStatusInput.equals("2")) {
                newArray[4] = "false";
                System.out.println("Set to in arrears");
            }
            subscriptionData.remove(foundIdInt);
            subscriptionData.add(foundIdInt, newArray);
            String[] printArray = subscriptionData.get(foundIdInt);
            System.out.println("Updated member subscription: " + printArray[0] + " " + printArray[1] + " " + printArray[2] + " " + printArray[3] + " " + printArray[4]);
            fileHandler.overwriteCSV("Subscriptions.csv", subscriptionData);
        }
    }

    public void updateSubscriptions() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
        for (int i = 0; i < memberData.size(); i++) {
            String[] strArray = memberData.get(i);

            String memberId = Integer.toString(i);
            String memberName = strArray[0];
            String memberAge = strArray[1];
            String isActive = strArray[2];
            String hasPaid = "false";

            boolean isRegistered = false;
            for (String[] subArray : subscriptionData) {
                if (subArray[0].equals(memberId)) {
                    isRegistered = true;
                    break;
                }
            }
            if (!isRegistered) {
                subscriptionData.add(new String[] { memberId, memberName, memberAge, isActive, hasPaid });
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
                subscriptionSum += team.getActiveMembers() * subscription + (team.getMemberList().size() - team.getActiveMembers()) * subscriptionPassive;
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

    public void showTopSwimmers() {

    }

    public void showAllSwimmers() {

    }

    private Member FindSwimmerByName(){
        ui.displayPleaseTypeMemberName();
        String name = sc.nextLine();
        for(Team team : teamArray){     //Skal måske flyttes - Information expert
            for(Member member :team.getMemberList()){
              if(member.getName().equalsIgnoreCase(name)){
                  return member;
              }
            }
        }
        return null;
    }

    private void addRecordToMember(){
        Member member = FindSwimmerByName();
        if (member==null){
            ui.memberNotFound();
        }
        String recordType = null;
        if(member instanceof MemberCompetitive){
            ui.recordTypeChoice();
            while(recordType==null || recordType.equalsIgnoreCase("regular") || recordType.equalsIgnoreCase("competitive")){
                recordType = sc.next();
            }

        }
        if(member!=null){
            ui.displayEnterSwimDiscipline();
            Enum.SwimDiscipline swimDiscipline = null;
            while(swimDiscipline==null){
                try {
                    swimDiscipline= Enum.SwimDiscipline.valueOf(sc.nextLine().toUpperCase());
                }
                catch (IllegalArgumentException iae){
                ui.displayEnterSwimDisciplineException();
                }
            }
            ui.displayEnterRecordInSeconds();
            double recordInSeconds = 0;
            while(recordInSeconds==0){
                try {
                    recordInSeconds = sc.nextDouble();
                }
                catch (InputMismatchException ime){
                    ui.displayEnterRecordInSecondsException();
                }
            }
            ui.displayEnterDate();
            int day = 0;
            int month = 0;
            int year = 0;
            while (year == 0  || day > 31 || month > 12) {
                try {
                    String date = sc.nextLine();
                    Scanner dateInputScanner = new Scanner(date);
                    dateInputScanner.useDelimiter("/");
                    day = Integer.parseInt(dateInputScanner.next());
                    month = Integer.parseInt(dateInputScanner.next());
                    year = Integer.parseInt(dateInputScanner.next());
                } catch (NoSuchElementException | NumberFormatException nsee) {
                    ui.displayEnterDateException();
                }
            }
            if(recordType==null || recordType.equals("regular")){
                member.removePreviousRecord(swimDiscipline);
                member.getBestPracticeRecords().add(new RecordTimeRegular(swimDiscipline,recordInSeconds,day,month,year));
            }
            else{
               addCompetitiveRecordToMember((MemberCompetitive)member,swimDiscipline,recordInSeconds,day,month,year);
            }

        }


    }

    private void addCompetitiveRecordToMember(MemberCompetitive member, Enum.SwimDiscipline swimDiscipline, double recordInSeconds,int day,int month,int year){
        ui.displayEnterConventionName();
        String convention = sc.nextLine();
        ui.displayEnterPlacing();
        int place = 0;
        while(place==0){
        try {
            place = sc.nextInt();
        }catch (InputMismatchException ime){
            ui.displayEnterPlacingException();
        }
        }
        member.getCompetitions().add(new RecordTimeCompetitive(swimDiscipline,recordInSeconds,day,month,year,convention,place));
    }


    private boolean tryParseInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception E) {
            return false;
        }
        return true;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
