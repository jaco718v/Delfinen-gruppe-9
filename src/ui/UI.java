package ui;

import membership.Enum;
import membership.Team;
import membership.User;
import swimclub.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {

    public void loginUser(boolean success) {
        if (success) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Login failed.");
        }
    }

    public void addUser(boolean success) {
        if (success) {
            System.out.println("Successfully added user.");
        } else {
            System.out.println("Failed to add user. Please select user type between 1-4.");
        }
    }

    public void removeUser(boolean success) {
        if (success) {
            System.out.println("Successfully removed user.");
        } else {
            System.out.println("Failed to remove user.");
        }
    }

    public void addMember(boolean success) {

        if (success) {
            System.out.println("Successfully added member.");
        } else {
            System.out.println("Failed to add member.");
        }
    }

    public void removeMember(boolean success) {
        if (success) {
            System.out.println("Successfully removed member.");
        } else {
            System.out.println("Failed to remove member.");
        }
    }

    public void showExpectedSubscriptionFees(double subscriptionFees) {
        System.out.println("Expected subscription fees:");
        System.out.println(subscriptionFees + " kr.");
    }

    public void showMembersInArrears() {
        System.out.println("Members in arrears:");
    }

    public void showTopSwimmers() {
        System.out.println("Top swimmers:");
    }

    public void displayPleaseTypeMemberID(){
        System.out.println("Please enter the ID of the member whose record needs updating");
    }

    public void memberIDNotFound(){
        System.out.println("MemberID not found in database of competitive members");
    }

    public void DisplayRecordTypeChoice(){
        System.out.println("Choose the record-type:");
        System.out.println("1. Practice record");
        System.out.println("2. Competition placing");
    }

    public void displayEnterSwimDiscipline(){
        System.out.println("Choose swim discipline the swimmer specialises in:");
        System.out.println("1. Butterfly");
        System.out.println("2. Crawl");
        System.out.println("3. Back crawl");
        System.out.println("4. Breaststroke");
    }

    public void displayEnterSwimDisciplineException(){
        System.out.println("Error in input, please enter an integer between 1-4");
    }

    public void displayEnterRecordInSeconds(){
        System.out.println("Enter the newly recorded time in seconds");
    }

    public void displayEnterRecordInSecondsException(){
        System.out.println("Error in input, try again");
    }

    public void displayEnterDate(){
        System.out.println("Enter date of the record in the following format: dd/mm/year");
    }

    public void displayEnterDateException(){
        System.out.println("Error in input, please only type in the format: dd/mm/year");
    }

    public void displayEnterConventionName(){
        System.out.println("Enter the name of the competition or convention");
    }

    public void displayEnterPlacing(){
        System.out.println("Enter the participants placing in the competition");
    }

    public void displayEnterPlacingException(){
        System.out.println("Error in input, enter a valid number");
    }

    public void displayRecordAddSucces(String recordType){
        System.out.println("Successfully added "+recordType+ " record to swimmer");
    }

    public void displayDecideAgeGroupTopFive(){
        System.out.println("Choose age group");
        System.out.println("1. Junior");
        System.out.println("2. Senior");
    }

    public void displayDecideAgeGroupTopFiveError(){
        System.out.println("Error in input, choose 1-2F");
    }

    public void displayTopFive(ArrayList<String[]> topFive){
        System.out.println("Top 5 "+topFive.get(0)[1]+" swimmers");
        System.out.println("1. "+topFive.get(0)[2]+"s\t\t"+topFive.get(0)[0]);
        if(topFive.size()>1){
        System.out.println("2. "+topFive.get(1)[2]+"s\t\t"+topFive.get(1)[0]);}
        if(topFive.size()>2){
        System.out.println("3. "+topFive.get(2)[2]+"s\t\t"+topFive.get(2)[0]);}
        if(topFive.size()>3){
        System.out.println("4. "+topFive.get(3)[2]+"s\t\t"+topFive.get(3)[0]);}
        if(topFive.size()>4){
        System.out.println("5. "+topFive.get(4)[2]+"s\t\t"+topFive.get(4)[0]);}
    }

    public void displayTopFiveError(){
        System.out.println("There are fewer than 5 swimmers in that swim discipline");
    }

    public void displayMemberRecords(ArrayList<String[]> playerRecords){
        System.out.println("Member: "+playerRecords.get(0)[0]+"\t Swim discipline: "+playerRecords.get(0)[1]);
        System.out.print("Personal best training record: ");
        for(String[] record : playerRecords){
            if(record[4].equals(" ")){
                System.out.println(record[2]+"s\t "+record[3]);
            }
        }
        System.out.println("Competition placings:");
        for(String[] record : playerRecords){
            if(!record[4].equals(" ")){
                System.out.println(record[4]+"\t placing nr: "+record[5]+"\t Time: "+record[2]+"s\t"+record[3]);
            }
        }
    }

    public void displayPleaseTypeLoginName() {
        System.out.print("Please type user login name: ");
    }

    public void displayPleaseEnterValidName(String memberName) {
        System.out.println("Please enter a valid name. You entered: " + memberName);
    }

    public void displayPleaseEnterMemberAge(){
        System.out.print("Please enter member age: ");
    }

    public void displayPleaseEnterValidAge(int memberAge) {
        System.out.println("Please enter a valid name. You entered: " + memberAge);
    }


    public void displayActiveOrPassiveOptions(){
        System.out.println("""                               
                               Is this an active or passive member?
                               1. Active
                               2. Passive""");
        System.out.print("Select command: ");
    }

    public void displayDefaultOption(){
        System.out.println("You've have entered invalid information");
    }

    public void displayCompOrRegOptions() {
        System.out.println("""                               
            Is this an competitive or regular member?
            1. Competitive                             
            2. Regular """);
        System.out.print("Select command: ");
    }



    public void displayCompOrReg(){
        System.out.println("No such option.");
    }



    public void displayPleaseTypeLoginPassword() {
        System.out.print("Please type user login password: ");
    }

    public void displayPleaseEnterValidUser(String userName) {
        System.out.println("Please enter a valid user. You entered: " + userName);
    }

    public void displayWrongPassword() {
        System.out.println("Wrong password, try again.");
    }

    public void noRegisteredUsers() {
        System.out.println("Can't login, no registered users.");
    }

    public void noRegisteredUsersCreatingAdmin() {
        System.out.println("No registered users, creating default admin user.");
        System.out.println("Login name: admin");
        System.out.println("Login password: admin");
    }

    public void loggedInUserNoPrivilege() {
        System.out.println("Logged in user does not have privileges for this action.");
    }

    public void displayBadPassword(String userPassword) {
        System.out.println("Bad password, try something else than: " + userPassword);
    }

    public void displayPleaseSelectUserType() {
        System.out.println("Please select one of the following user types:");
        System.out.println("1. ADMIN");
        System.out.println("2. CHAIRMAN");
        System.out.println("3. CASHIER");
        System.out.println("4. COACH");
        System.out.print("Select user type: ");
    }

    public void displayActiveOrPassiveOutcome(boolean b) {
        if (b) {
            System.out.println("Member is active.");
        } else {
            System.out.println("Member is passive.");
        }
    }

    public void displayNoSuchCommand(String command) {
        System.out.println("No such command: " + command);
    }

    public void listCommandsNotLoggedIn() {
        System.out.println();
        System.out.println("Login Commands:");
        System.out.println("1. Login user");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }

    public void listCommandsAdmin() {
        System.out.println();
        System.out.println("Admin Commands:");
        System.out.println("1. User menu");
        System.out.println("2. Member menu");
        System.out.println("3. Subscription menu");
        System.out.println("4. Swimmer menu");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }

    public void listCommandsChairman() {
        System.out.println();
        System.out.println("Chairman Commands:");
        System.out.println("1. User menu");
        System.out.println("2. Member menu");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }

    public void listCommandsCashier() {
        System.out.println();
        System.out.println("Cashier Commands:");
        System.out.println("1. User menu");
        System.out.println("3. Subscription menu");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }

    public void listCommandsCoach() {
        System.out.println();
        System.out.println("Coach Commands:");
        System.out.println("1. User menu");
        System.out.println("9. Swimmer menu");
        System.out.println("0. Exit program");
        System.out.print("Select command: ");
    }

    public void listCommandsUserMenu() {
        System.out.println();
        System.out.println("User Commands:");
        System.out.println("1. Login user");
        System.out.println("2. Add user");
        System.out.println("3. Remove user");
        System.out.println("4. Show users");
        System.out.println("0. Back");
        System.out.print("Select command: ");
    }

    public void listCommandsUserMenuLoginOnly() {
        System.out.println();
        System.out.println("User Commands:");
        System.out.println("1. Login user");
        System.out.println("0. Back");
        System.out.print("Select command: ");
    }

    public void listCommandsMemberMenu() {
        System.out.println();
        System.out.println("Member Commands:");
        System.out.println("1. Add member");
        System.out.println("2. Edit member");
        System.out.println("3. Remove member");
        System.out.println("4. Show members");
        System.out.println("0. Back");
        System.out.print("Select command: ");
    }

    public void listCommandsSubscriptionMenu() {
        System.out.println();
        System.out.println("Subscription Commands:");
        System.out.println("1. Set payment status");
        System.out.println("2. Show subscriptions");
        System.out.println("3. Show expected subscription fees");
        System.out.println("4. Show members in arrears");
        System.out.println("0. Back");
        System.out.print("Select command: ");
    }

    public void listCommandsSwimmerMenu() {
        System.out.println();
        System.out.println("Swimmer Commands:");
        System.out.println("1. Show top swimmers");
        System.out.println("2. Show all swimmers");
        System.out.println("3. Add swimmer record");
        System.out.println("4. View swimmer records");
        System.out.println("0. Back");
        System.out.print("Select command: ");
    }

    public void displayMemberInformation(Controller con, String[] strArray) {
        System.out.print("MemberID: " + strArray[0]);
        System.out.print(" - Name: " + strArray[1]);
        System.out.print(" - Birth date: " + strArray[2]);
        System.out.print(" - Age: " + con.convertDateToAge(strArray[2]));
        if (strArray[3].equals("true")) {
            System.out.print(" - Active");
        } else if (strArray[3].equals("false")) {
            System.out.print(" - Passive");
        }
        if (strArray[4].equals("true")) {
            System.out.print(" - Competitive");
        } else if (strArray[4].equals("false")) {
            System.out.print(" - Regular");
        }
        System.out.println();
    }

    public void displayTeamInformation(Controller con, int teamNumber, Team team) {
        System.out.println("Team " + teamNumber + " - Team type: " + con.capitalizeString(team.getTeamType().name()) + " - Age group: " + team.getAgeGroup().name());
    }

    public void displayReturningToMainMenu() {
        System.out.println("Returning to main menu.");
    }

    public void displayShuttingDown() {
        System.out.println("System shutting down.");
    }

    public void displayUserInformation(String[] strArray, User user) {
        String password = "";
        if (user.getUserType() == Enum.UserType.ADMIN) {
            System.out.println("Name: " + strArray[0] + " - Password: " + strArray[1] + " - User Type: " + strArray[2]);
        } else {
            for (int i = 0; i < strArray[1].length(); i++) {
                password += "*";
            }
            System.out.println("Name: " + strArray[0] + " - Password: " + password + " - User Type: " + strArray[2]);
        }
    }

    public void displaySubscription(String[] strArray) {
        System.out.print("MemberID: " + strArray[0] + " - Name: " + strArray[1] + " - Age: " + strArray[2]);
        if (strArray[3].equals("true")) {
            System.out.print(" - Active");
        } else if (strArray[3].equals("false")) {
            System.out.print(" - Passive");
        }
        if (strArray[4].equals("true")) {
            System.out.print(" - Has paid");
        } else if (strArray[4].equals("false")) {
            System.out.print(" - Is in arrears");
        }
        System.out.println();
    }

    public void displaySubscriptionsInArrears() {
        System.out.println("Subscriptions in arrears:");
    }

    public void displayPleaseEnterUserId() {
        System.out.print("Please enter the ID of the User: ");
    }

    public void displayPleaseEnterPaymentStatus() {
        System.out.println("Set new payment status:");
        System.out.println("1. Has paid");
        System.out.println("2. In arrears");
        System.out.print("Select command: ");
    }

    public void displayPleaseEnterMemberName() {
        System.out.print("Please enter Member Name: ");
    }

    public void displaySelectedSwimmerMenu() {
        System.out.println("Selected Swimmer Menu.");
    }

    public void displaySelectedUserMenu() {
        System.out.println("Selected User Menu.");
    }

    public void displaySelectedMemberMenu() {
        System.out.println("Selected Member Menu.");
    }

    public void displaySelectedSubscriptionMenu() {
        System.out.println("Selected Subscription Menu.");
    }

    public void displayPleaseEnterMemberId() {
        System.out.print("Please enter Member ID: ");
    }

    public void displayMemberListFull() {
        System.out.println("Can't add new member, member list is full!");
    }

    public void displayUpdatedMemberSubscription(String[] strArray) {
        System.out.println("Updated member subscription:");
        displaySubscription(strArray);
    }

    public void whatToChange() {
        System.out.println("What do you want to change?");
        System.out.println("1. Name"+"\n2. Birth date"+"\n3. Active or passive"+ "\n4. Regular or competitive");
    }

    public void typeMemberIdPlease() {
        System.out.println("Please enter MemberID: ");
    }

    public void nameChanged() {
        System.out.println("Name has been changed.");
    }

    public void displayPleaseEnterMemberBirthDay() {
        System.out.print("Please enter birth day: ");
    }

    public void displayPleaseEnterMemberBirthMonth() {
        System.out.print("Please enter birth month: ");
    }

    public void displayPleaseEnterMemberYear() {
        System.out.print("Please enter birth year: ");
    }

    public void displayPleaseEnterValidBirthDay() {
        System.out.println("Please enter a valid birth day.");
    }

    public void displayPleaseEnterValidBirthMonth() {
        System.out.println("Please enter a valid birth month.");
    }

    public void displayPleaseEnterValidBirthYear() {
        System.out.println("Please enter a valid birth year.");
    }

    public void displayNoSuchMemberFound() {
        System.out.println("No such member found.");
    }
}
