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
            String removeName = sc.nextLine();

            for(int i = 0; i < memberData.size(); i++){
                String[] array = memberData.get(i);
                if (array[0].equals(removeName)) {
                    memberData.remove(i);
                    fileHandler.overwriteCSV("Members.csv", memberData);
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

    }

    public void showSubscriptions() {

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

    }

    public void showTopSwimmers() {

    }

    public void showAllSwimmers() {

    }

    private String FindMemberByName(){
        ui.displayPleaseTypeMemberName();
        String name = sc.nextLine();
        for(Team team : teamArray){
                  if(team.findMemberByName(name)){
                      return name;
                  }
        }
        ui.memberNotFound();
        return null;
    }

    public String addSwimDisciplineToRecord(){
        ui.displayEnterSwimDiscipline();
        String swimDiscipline = null;
        while(swimDiscipline==null){
            try {
                swimDiscipline = String.valueOf(Enum.SwimDiscipline.valueOf(sc.nextLine().toUpperCase()));
            }
            catch (IllegalArgumentException iae){
                ui.displayEnterSwimDisciplineException();
            }
        }
        return  swimDiscipline;
    }



   public String recordTypeChoice(String memberName){
       String recordType = null;
       for(Team team : teamArray){
           if(team.findMemberCompetitiveStatus(memberName)){
           ui.DisplayRecordTypeChoice();
           while(recordType==null || !recordType.equalsIgnoreCase("regular") && !recordType.equalsIgnoreCase("competitive")){
               recordType = sc.next();
           }
        }
       }
       return recordType;
    }

    public String addRecordInSeconds(){
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
        String recordInSecondsString = String.valueOf(recordInSeconds);
        return recordInSecondsString;
    }

    public String addDate(){
        ui.displayEnterDate();
        int day = 0;
        int month = 0;
        int year = 0;
        String dateInput = null;
        int[] date = {day,month,year};
        while (year == 0  || day > 31 || month > 12) {
            try {
                dateInput = sc.nextLine();
                Scanner dateInputScanner = new Scanner(dateInput);
                dateInputScanner.useDelimiter("/");
                day = Integer.parseInt(dateInputScanner.next());
                month = Integer.parseInt(dateInputScanner.next());
                year = Integer.parseInt(dateInputScanner.next());
            } catch (NoSuchElementException | NumberFormatException nsee) {
                ui.displayEnterDateException();
            }
        }
        return dateInput;
    }

    public boolean updateRecord(ArrayList<String[]> recordList, String[] newRecord){
       boolean previousRecordFound = false;
       for(String[] record : recordList){
           if(record[0].equalsIgnoreCase(newRecord[0]) && record[1].equalsIgnoreCase(newRecord[1])){
               record = newRecord;
               previousRecordFound = true;
               fileHandler.overwriteCSV("Records",recordList);
           }
       }
       return previousRecordFound;
    }

    private void addRecordToMember(){
        String memberName = FindMemberByName();
        if(memberName!=null){
            ArrayList<String[]> recordList = fileHandler.readCSV("Records.csv");
            ArrayList<String[]> data = new ArrayList<>();
            String recordType = recordTypeChoice(memberName);
            String swimDiscipline = addSwimDisciplineToRecord();
            String recordInSeconds = addRecordInSeconds();
            String date = addDate();


            if(recordType.equals("regular")){
                String[] newRecord = {memberName,swimDiscipline,recordInSeconds,date,null,null};
                data.add(newRecord);
                boolean updateStatus = updateRecord(recordList,newRecord);
                if(!updateStatus){
                fileHandler.writeToCSV("Records",data);
                }
            }
            else{
               addCompetitiveRecordToMember(data,memberName,swimDiscipline,recordInSeconds,date);
            }
        }
    }

    public String addPlacing(){
        ui.displayEnterPlacing();
        int place = 0;
        while(place==0){
            try {
                place = sc.nextInt();
            }catch (InputMismatchException ime){
                ui.displayEnterPlacingException();
            }
        }
        return String.valueOf(place);
    }

    private void addCompetitiveRecordToMember(ArrayList<String[]> data, String memberName, String swimDiscipline, String recordInSeconds,String date){
        ui.displayEnterConventionName();
        String convention = sc.nextLine();
        String placing = addPlacing();
        data.add(new String[]{memberName,swimDiscipline,recordInSeconds,date,convention,placing});
        fileHandler.writeToCSV("Records",data);
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
