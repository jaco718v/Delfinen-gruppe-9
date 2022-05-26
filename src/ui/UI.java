package ui;

import membership.RecordTime;
import membership.RecordTimeCompetition;
import membership.RecordTimePractice;
import utilities.Enum;
import membership.Team;
import swimclub.User;
import utilities.Utility;

import java.util.ArrayList;

public class UI {
    public UI(String language) {
        setLanguage(language);
    }

    private final Utility util = new Utility();
    private final UILanguageTranslations lt = new UILanguageTranslations();
    private String userCommands = "User Commands:";
    private String loginUserMenu = "1. Login user";
    private String menuBack = "0. Back";
    private String selectCommand = "Select command: ";
    private String addUserMenu = "2. Add user";
    private String removeUserMenu = "3. Remove user";
    private String showUsersMenu = "4. Show users";
    private String memberCommands = "Member Commands:";
    private String addMemberMenu = "1. Add member";
    private String editMemberMenu = "2. Edit member";
    private String removeMemberMenu = "3. Remove member";
    private String showMembersMenu = "4. Show members";
    private String subscriptionCommands = "Subscription Commands:";
    private String setPaymentStatusMenu = "1. Set payment status";
    private String showSubscriptionsMenu = "2. Show subscriptions";
    private String showExpectedSubFeesMenu = "3. Show expected subscription fees";
    private String showMembersInArrearsMenu = "4. Show members in arrears";
    private String swimmerCommands = "Swimmer Commands:";
    private String addCoachMenu = "1. Add coach to team";
    private String removeCoachMenu = "2. Remove coach from team";
    private String showTopSwimmersMenu = "3. Show top swimmers";
    private String showAllSwimmersMenu = "4. Show all swimmer records";
    private String addSwimmerRecordMenu = "5. Add swimmer record";
    private String viewSwimmerRecordsMenu = "6. View swimmer records";
    private String adminCommands = "Admin Commands:";
    private String userMenu1 = "1. User menu";
    private String memberMenu2 = "2. Member menu";
    private String subscriptionMenu3 = "3. Subscription menu";
    private String swimmerMenu4 = "4. Swimmer menu";
    private String exitProgramMenu = "0. Exit program";
    private String chairmanCommands = "Chairman Commands:";
    private String cashierCommands = "Cashier Commands:";
    private String subscriptionMenu2 = "2. Subscription menu";
    private String coachCommands = "Coach Commands:";
    private String swimmerMenu2 = "2. Swimmer menu";
    private String loginCommands = "Login Commands:";
    private String noSuchCommand = "No such command: ";
    private String addMemberSuccess = "Successfully added member.";
    private String addMemberFail = "Failed to add member.";
    private String removeMemberSuccess = "Successfully removed member.";
    private String removeMemberFail = "Failed to remove member.";
    private String loginSuccess = "Login successful.";
    private String loginFail = "Login failed.";
    private String addUserSuccess = "Successfully added user.";
    private String addUserFail = "Failed to add user. Please select user type between 1-";
    private String removeUserSuccess = "Successfully removed user.";
    private String removeUserFail = "Failed to remove user.";
    private String typeUserLogin = "Please type user login name: ";
    private String invalidName = "Please enter a valid name. You entered: ";
    private String typeUserPassword = "Please type user login password: ";
    private String invalidPassword = "Invalid password, try something else than: ";
    private String selectUserTypes = "Please select one of the following user types:";
    private String admin1 = "1. Admin";
    private String chairman2 = "2. Chairman";
    private String cashier3 = "3. Cashier";
    private String coach4 = "4. Coach";
    private String selectUserType = "Select user type: ";
    private String pleaseEnterId = "Please enter the ID of the User: ";
    private String noSuchMember = "No such member found.";
    private String pleaseEnterMemberName = "Please enter Member Name: ";
    private String pleaseEnterBirthDay = "Please enter birth day: ";
    private String invalidBirthDay = "Please enter a valid birth day.";
    private String pleaseEnterBirthMonth = "Please enter birth month: ";
    private String invalidBirthMonth = "Please enter a valid birth month.";
    private String pleaseEnterBirthYear = "Please enter birth year: ";
    private String invalidBirthYear = "Please enter a valid birth year.";
    private String activeOrPassiveMember = "Is this an active or passive member?";
    private String active1Menu = "1. Active";
    private String passive2Menu = "2. Passive";
    private String memberNowActive = "Member is now active.";
    private String memberNowPassive = "Member is now passive.";
    private String defaultInvalid = "You've have entered invalid information.";
    private String compOrReg = "Is this a competitive or regular member?";
    private String competitive1Menu = "1. Competitive";
    private String regular2Menu = "2. Regular";
    private String noSuchOption = "No such option.";
    private String pleaseEnterRecordDate = "Please enter date of the record in the following format: dd/mm/yyyy: ";
    private String invalidRecordDate = "Error in input, please only type in the format: dd/mm/yyyy";
    private String expectedSubscriptionFees = "Expected subscription fees:";
    private String pleaseEnterIdOfMember = "Please enter the ID of the member: ";
    private String invalidCompetitiveMemberId = "MemberID not found in database of competitive members.";
    private String noRecordsFound = "No records found in database.";
    private String recordTypes = "Record-types: ";
    private String practiceRecord1 = "1. Practice record";
    private String competitionPlacement2 = "2. Competition placement";
    private String pleaseEnterRecordType = "Choose the record-type: ";
    private String swimDisciplines = "Swim disciplines:";
    private String butterflyMenu1 = "1. Butterfly";
    private String crawlMenu2 = "2. Crawl";
    private String backCrawlMenu3 = "3. Back crawl";
    private String breaststrokeMenu4 = "4. Breaststroke";
    private String chooseSwimDiscipline = "Choose swim discipline: ";
    private String invalidSwimDiscipline = "Error in input, please enter an integer between 1-4.";
    private String pleaseEnterRecordTimeSeconds = "Enter the newly recorded time in seconds: ";
    private String inputError = "Error in input, try again.";
    private String pleaseEnterCompetition = "Enter the name of the competition or convention: ";
    private String pleaseEnterPlacement = "Enter the participants placement in the competition: ";
    private String inputErrorNumber = "Error in input, enter a valid number.";
    private String successfullyAdded = "Successfully added ";
    private String recordToSwimmer = " record to swimmer.";
    private String ageGroups = "Age groups:";
    private String u18Menu1 = "1. U18";
    private String o18Menu2 = "2. O18";
    private String chooseAgeGroup = "Choose age group: ";
    private String ageGroupTop5Error = "Error in input, choose 1-2.";
    private String top5swimmers = " swimmers";
    private String noRecordsInDiscipline = "There are no swimmer records in that swim discipline and/or age group";
    private String memberColon = "Member: ";
    private String swimDisciplineColon = " Swim discipline: ";
    private String personalBestTrainingRecord = "Personal best training record: ";
    private String competitionPlacementsColon = "Competition placements:";
    private String chooseTypeOfTeamToAssignTo = "Choose the type of team to assign to:";
    private String regularMenu1 = "1. Regular";
    private String competitiveMenu2 = "2. Competitive";
    private String invalidTypeCoach = "Input either 1 or 2.";
    private String frontCrawl = "Front Crawl";
    private String backCrawl = "Back Crawl";
    private String butterfly = "Butterfly";
    private String breastStroke = "Breast Stroke";
    private String wrongPassword = "Wrong password, try again.";
    private String cantLoginNoRegisteredUsers = "Can't login, no registered users.";
    private String noRegisteredUsersCreateAdmin = "No registered users, creating default admin user.";
    private String loginNameAdmin = "Login name: admin";
    private String loginPasswordAdmin = "Login password: admin";
    private String userNoPrivilege = "Logged in user does not have privileges for this action.";
    private String memberIdColon = "MemberID: ";
    private String memberSinceColon = " - Member since: ";
    private String memberNameColon = " - Name: ";
    private String memberBirthDateColon = " - Birth date: ";
    private String memberAgeColon = " - Age: ";
    private String memberActive = " - Active ";
    private String memberPassive = " - Passive";
    private String memberCompetitive = " - Competitive";
    private String memberRegular = " - Regular    ";
    private String teamInfoTeam = "Team ";
    private String teamInfoTeamType = " - Team type: ";
    private String teamInfoAgeGroup = " - Age group: ";
    private String teamInfoCoach = " - Coach: ";
    private String returnToMainMenu = "Returning to main menu.";
    private String systemShutdown = "Program shutting down.";
    private String userInfoUserId = "UserID: ";
    private String userInfoPassword = " - Password: ";
    private String userInfoUserType = " - User Type: ";
    private String hasPaid = " - Has paid";
    private String isInArrears = " - Is in arrears";
    private String setNewPaymentStatus = "Set new payment status:";
    private String hasPaidMenu1 = "1. Has paid";
    private String inArrearsMenu2 = "2. In arrears";
    private String selectedSwimmerMenu = "Selected Swimmer Menu.";
    private String selectedUserMenu = "Selected User Menu.";
    private String selectedMemberMenu = "Selected Member Menu.";
    private String selectedSubscriptionMenu = "Selected Subscription Menu.";
    private String pleaseEnterMemberId = "Please enter MemberID: ";
    private String memberListFull = "Can't add new member, member list is full!";
    private String updatedMemberSubscription = "Updated member subscription:";
    private String whatDoYouWantToChange = "What do you want to change?";
    private String editNameMenu1 = "1. Name";
    private String editBirthDateMenu2 = "2. Birth date";
    private String editActiveMenu3 = "3. Active or passive";
    private String editCompetitiveMenu4 = "4. Regular or competitive";
    private String editDisciplineMenu5 = "5. Swimming discipline";
    private String memberIdOutOfRange = "MemberID out of range.";
    private String invalidCoachId = "Please enter a valid coach ID. You typed: ";
    private String pleaseEnterCoachUserId = "Please enter a coach UserID: ";
    private String invalidTeam = "PLease enter a valid team. You typed: ";
    private String pleaseEnterTeamNumber = "Please enter a Team number: ";
    private String addedCoachColon = "Added coach: ";
    private String addedCoachToTeam = " to Team ";
    private String noCoachesRegistered = "No coaches registered.";
    private String removedCoachColon = "Removed coach: ";
    private String removedCoachFromTeam = " from Team ";
    private String noCoachOnTeam = "No coach on selected team.";
    private String invalidUserId = "Please enter a valid UserID. You entered: ";
    private String invalidInputNumber = "Invalid input. Please enter a number between ";
    private String invalidInputAnd = " and ";
    private String selectLanguages = "Select one of the following languages:";
    private String englishMenu1 = "1. English";
    private String danishMenu2 = "2. Danish";
    private String selectLanguage = "Select language: ";
    private String pleaseEnterValidLanguage = "Please enter a valid language.";
    private String welcome = "Welcome to the swimclub management program.";

    // Welcome

    public void displayWelcome() {
        System.out.println();
        System.out.println(welcome);
    }

    // Commands UI

    public void listCommandsUserMenuLoginOnly() {
        System.out.println();
        System.out.println(userCommands);
        System.out.println(loginUserMenu);
        System.out.println(menuBack);
        System.out.print(selectCommand);
    }

    public void listCommandsUserMenu() {
        System.out.println();
        System.out.println(userCommands);
        System.out.println(loginUserMenu);
        System.out.println(addUserMenu);
        System.out.println(removeUserMenu);
        System.out.println(showUsersMenu);
        System.out.println(menuBack);
        System.out.print(selectCommand);
    }

    public void listCommandsMemberMenu() {
        System.out.println();
        System.out.println(memberCommands);
        System.out.println(addMemberMenu);
        System.out.println(editMemberMenu);
        System.out.println(removeMemberMenu);
        System.out.println(showMembersMenu);
        System.out.println(menuBack);
        System.out.print(selectCommand);
    }

    public void listCommandsSubscriptionMenu() {
        System.out.println();
        System.out.println(subscriptionCommands);
        System.out.println(setPaymentStatusMenu);
        System.out.println(showSubscriptionsMenu);
        System.out.println(showExpectedSubFeesMenu);
        System.out.println(showMembersInArrearsMenu);
        System.out.println(menuBack);
        System.out.print(selectCommand);
    }

    public void listCommandsSwimmerMenu() {
        System.out.println();
        System.out.println(swimmerCommands);
        System.out.println(addCoachMenu);
        System.out.println(removeCoachMenu);
        System.out.println(showTopSwimmersMenu);
        System.out.println(showAllSwimmersMenu);
        System.out.println(addSwimmerRecordMenu);
        System.out.println(viewSwimmerRecordsMenu);
        System.out.println(menuBack);
        System.out.print(selectCommand);
    }

    public void listCommandsAdmin() {
        System.out.println();
        System.out.println(adminCommands);
        System.out.println(userMenu1);
        System.out.println(memberMenu2);
        System.out.println(subscriptionMenu3);
        System.out.println(swimmerMenu4);
        System.out.println(exitProgramMenu);
        System.out.print(selectCommand);
    }

    public void listCommandsChairman() {
        System.out.println();
        System.out.println(chairmanCommands);
        System.out.println(userMenu1);
        System.out.println(memberMenu2);
        System.out.println(exitProgramMenu);
        System.out.print(selectCommand);
    }

    public void listCommandsCashier() {
        System.out.println();
        System.out.println(cashierCommands);
        System.out.println(userMenu1);
        System.out.println(subscriptionMenu2);
        System.out.println(exitProgramMenu);
        System.out.print(selectCommand);
    }

    public void listCommandsCoach() {
        System.out.println();
        System.out.println(coachCommands);
        System.out.println(userMenu1);
        System.out.println(swimmerMenu2);
        System.out.println(exitProgramMenu);
        System.out.print(selectCommand);
    }

    public void listCommandsNotLoggedIn() {
        System.out.println();
        System.out.println(loginCommands);
        System.out.println(loginUserMenu);
        System.out.println(exitProgramMenu);
        System.out.print(selectCommand);
    }

    public void displayNoSuchCommand(String command) {
        System.out.println();
        System.out.println(noSuchCommand + command);
    }

    // Member Controller UI

    public void displayAddMember(boolean success) {
        if (success) {
            System.out.println(addMemberSuccess);
        } else {
            System.out.println(addMemberFail);
        }
    }

    public void displayRemoveMember(boolean success) {
        if (success) {
            System.out.println(removeMemberSuccess);
        } else {
            System.out.println(removeMemberFail);
        }
    }

    // User Controller UI

    public void displayLoginUser(boolean success) {
        if (success) {
            System.out.println(loginSuccess);
        } else {
            System.out.println(loginFail);
        }
    }

    public void displayAddUser(boolean success) {
        if (success) {
            System.out.println(addUserSuccess);
        } else {
            System.out.println(addUserFail + Enum.UserType.values().length + ".");
        }
    }

    public void displayRemoveUser(boolean success) {
        if (success) {
            System.out.println(removeUserSuccess);
        } else {
            System.out.println(removeUserFail);
        }
    }

    // Input Handler UI

    public void displayPleaseSelectLanguage() {
        System.out.println();
        System.out.println(selectLanguages);
        System.out.println(englishMenu1);
        System.out.println(danishMenu2);
        System.out.print(selectLanguage);
    }

    public void displayPleaseEnterValidLanguage(String language) {
        System.out.println();
        System.out.println(pleaseEnterValidLanguage);
    }

    public void displayPleaseTypeUserLoginName() {
        System.out.println();
        System.out.print(typeUserLogin);
    }

    public void displayPleaseEnterValidName(String memberName) {
        System.out.println(invalidName + memberName);
    }

    public void displayPleaseTypeLoginPassword() {
        System.out.print(typeUserPassword);
    }

    public void displayBadPassword(String userPassword) {
        System.out.println(invalidPassword + userPassword);
    }

    public void displayPleaseSelectUserType() {
        System.out.println();
        System.out.println(selectUserTypes);
        System.out.println(admin1);
        System.out.println(chairman2);
        System.out.println(cashier3);
        System.out.println(coach4);
        System.out.print(selectUserType);
    }

    public void displayPleaseEnterUserId() {
        System.out.println();
        System.out.print(pleaseEnterId);
    }

    public void displayNoSuchMemberFound() {
        System.out.println(noSuchMember);
    }

    public void displayPleaseEnterMemberName() {
        System.out.println();
        System.out.print(pleaseEnterMemberName);
    }

    public void displayPleaseEnterMemberBirthDay() {
        System.out.println();
        System.out.print(pleaseEnterBirthDay);
    }

    public void displayPleaseEnterValidBirthDay() {
        System.out.println(invalidBirthDay);
    }

    public void displayPleaseEnterMemberBirthMonth() {
        System.out.println();
        System.out.print(pleaseEnterBirthMonth);
    }

    public void displayPleaseEnterValidBirthMonth() {
        System.out.println(invalidBirthMonth);
    }

    public void displayPleaseEnterMemberYear() {
        System.out.println();
        System.out.print(pleaseEnterBirthYear);
    }

    public void displayPleaseEnterValidBirthYear() {
        System.out.println(invalidBirthYear);
    }

    public void displayActiveOrPassiveOptions() {
        System.out.println();
        System.out.println(activeOrPassiveMember);
        System.out.println(active1Menu);
        System.out.println(passive2Menu);
        System.out.print(selectCommand);
    }

    public void displayActiveOrPassiveOutcome(boolean b) {
        if (b) {
            System.out.println(memberNowActive);
        } else {
            System.out.println(memberNowPassive);
        }
    }

    public void displayDefaultOption() {
        System.out.println(defaultInvalid);
    }

    public void displayCompOrRegOptions() {
        System.out.println();
        System.out.println(compOrReg);
        System.out.println(competitive1Menu);
        System.out.println(regular2Menu);
        System.out.print(selectCommand);
    }

    public void displayCompOrReg() {
        System.out.println(noSuchOption);
    }

    public void displayEnterDate() {
        System.out.println();
        System.out.print(pleaseEnterRecordDate);
    }

    public void displayEnterDateException() {
        System.out.println(invalidRecordDate);
    }

    public void displayExpectedSubscriptionFees(double subscriptionFees) {
        System.out.println();
        System.out.println(expectedSubscriptionFees);
        System.out.println(subscriptionFees + " kr.");
    }

    public void displayPleaseTypeMemberID() {
        System.out.println();
        System.out.print(pleaseEnterIdOfMember);
    }

    public void displayMemberIDNotFound() {
        System.out.println(invalidCompetitiveMemberId);
    }

    public void displayNoMemberRecords() {
        System.out.println(noRecordsFound);
    }

    public void displayRecordTypeChoice() {
        System.out.println();
        System.out.println(recordTypes);
        System.out.println(practiceRecord1);
        System.out.println(competitionPlacement2);
        System.out.print(pleaseEnterRecordType);
    }

    public void displayEnterSwimDiscipline() {
        System.out.println();
        System.out.println(swimDisciplines);
        System.out.println(butterflyMenu1);
        System.out.println(crawlMenu2);
        System.out.println(backCrawlMenu3);
        System.out.println(breaststrokeMenu4);
        System.out.print(chooseSwimDiscipline);
    }

    public void displayEnterSwimDisciplineException() {
        System.out.println(invalidSwimDiscipline);
    }

    public void displayEnterRecordInSeconds() {
        System.out.println();
        System.out.print(pleaseEnterRecordTimeSeconds);
    }

    public void displayEnterRecordInSecondsException() {
        System.out.println(inputError);
    }

    public void displayEnterConventionName() {
        System.out.println();
        System.out.print(pleaseEnterCompetition);
    }

    public void displayEnterPlacing() {
        System.out.println();
        System.out.print(pleaseEnterPlacement);
    }

    public void displayEnterPlacingException() {
        System.out.println(inputErrorNumber);
    }

    public void displayRecordAddSucces(String recordType) {
        System.out.println(successfullyAdded + recordType + recordToSwimmer);
    }

    public void displayDecideAgeGroupTopFive() {
        System.out.println();
        System.out.println(ageGroups);
        System.out.println(u18Menu1);
        System.out.println(o18Menu2);
        System.out.print(chooseAgeGroup);
    }

    public void displayDecideAgeGroupTopFiveError() {
        System.out.println(ageGroupTop5Error);
    }

    public void displayTopFive(ArrayList<RecordTimePractice> swimDisciplineRecords, Team team) {
        System.out.println();
        System.out.println("Top 5 " + turnEnumSwimDisciplineToString(swimDisciplineRecords.get(0).getSWIM_DISCIPLINE()) + top5swimmers);
        System.out.println("1. " + swimDisciplineRecords.get(0).getRECORD_IN_SECONDS() + "s\t\t" + team.findCompetitiveMemberNameWithID(swimDisciplineRecords.get(0).getMemberID()));
        if (swimDisciplineRecords.size() > 1) {
            System.out.println("2. " + swimDisciplineRecords.get(1).getRECORD_IN_SECONDS() + "s\t\t" + team.findCompetitiveMemberNameWithID(swimDisciplineRecords.get(1).getMemberID()));
        }
        if (swimDisciplineRecords.size() > 2) {
            System.out.println("3. " + swimDisciplineRecords.get(2).getRECORD_IN_SECONDS() + "s\t\t" + team.findCompetitiveMemberNameWithID(swimDisciplineRecords.get(2).getMemberID()));
        }
        if (swimDisciplineRecords.size() > 3) {
            System.out.println("4. " + swimDisciplineRecords.get(3).getRECORD_IN_SECONDS() + "s\t\t" + team.findCompetitiveMemberNameWithID(swimDisciplineRecords.get(3).getMemberID()));
        }
        if (swimDisciplineRecords.size() > 4) {
            System.out.println("5. " + swimDisciplineRecords.get(4).getRECORD_IN_SECONDS() + "s\t\t" + team.findCompetitiveMemberNameWithID(swimDisciplineRecords.get(4).getMemberID()));
        }
    }

    public void displayTopFiveError() {
        System.out.println(noRecordsInDiscipline);
    }

    public void displayMemberRecords(ArrayList<RecordTime> playerRecords, String memberID, String memberName) {
        System.out.println();
        System.out.println("ID: " + memberID + "\t" + memberColon + memberName + "\t" + swimDisciplineColon + turnEnumSwimDisciplineToString(playerRecords.get(0).getSWIM_DISCIPLINE()));
        System.out.print(personalBestTrainingRecord);
        for (RecordTime record : playerRecords) {
            if (record instanceof RecordTimePractice) {
                System.out.println(record.getRECORD_IN_SECONDS() + "s\t " + record.getDATE_OF_RECORD());
            }
        }
        System.out.println(competitionPlacementsColon);
        for (RecordTime record : playerRecords) {
            if (record instanceof RecordTimeCompetition) {
                System.out.println(((RecordTimeCompetition) record).getConvention() + "\t placement: " + ((RecordTimeCompetition) record).getPlacing() + "\t Time: " + record.getRECORD_IN_SECONDS() + "s\t" + record.getDATE_OF_RECORD());
            }
        }
    }

    public void displayChooseTeamTypeCoach() {
        System.out.println();
        System.out.println(chooseTypeOfTeamToAssignTo);
        System.out.println(regularMenu1);
        System.out.println(competitiveMenu2);
    }

    public void displayChooseTeamTypeCoachError() {
        System.out.println(invalidTypeCoach);
    }

    public void displayChooseAgeGroupCoach() {
        System.out.println();
        System.out.println(ageGroups);
        System.out.println(u18Menu1);
        System.out.println(o18Menu2);
        System.out.print(selectCommand);
    }

    public void displayChooseAgeGroupCoachError() {
        System.out.println(invalidTypeCoach);
    }

    public String turnEnumSwimDisciplineToString(Enum.SwimDiscipline swimDiscipline) {
        String swimString = null;
        switch (swimDiscipline) {
            case CRAWL -> swimString = frontCrawl;
            case BACKCRAWL -> swimString = backCrawl;
            case BUTTERFLY -> swimString = butterfly;
            case BREAST -> swimString = breastStroke;
        }
        return swimString;
    }

    public void displayWrongPassword() {
        System.out.println(wrongPassword);
    }

    public void displayNoRegisteredUsers() {
        System.out.println(cantLoginNoRegisteredUsers);
    }

    public void displayNoRegisteredUsersCreatingAdmin() {
        System.out.println();
        System.out.println(noRegisteredUsersCreateAdmin);
        System.out.println(loginNameAdmin);
        System.out.println(loginPasswordAdmin);
    }

    public void displayLoggedInUserNoPrivilege() {
        System.out.println(userNoPrivilege);
    }

    public void displayMemberInformation(String[] strArray) {
        System.out.print(memberIdColon + strArray[0]);
        System.out.print(memberSinceColon + strArray[1]);
        System.out.print(memberNameColon + util.addDotsToStringToFillXCharacters(strArray[2], 35));
        System.out.print(memberBirthDateColon + strArray[3]);
        int age = util.convertDateToAge(strArray[3]);
        System.out.print(memberAgeColon + age);
        if (age < 100) {
            System.out.print(" ");
        }
        if (strArray[4].equals("true")) {
            System.out.print(memberActive);
        } else if (strArray[4].equals("false")) {
            System.out.print(memberPassive);
        }
        if (strArray[5].equals("true")) {
            System.out.print(memberCompetitive);
        } else if (strArray[5].equals("false")) {
            System.out.print(memberRegular);
        }
        System.out.println();
    }

    public void displayTeamInformation(int teamNumber, Team team) {
        System.out.print(teamInfoTeam + teamNumber + teamInfoTeamType + util.addSpacesToStringToFillXCharacters(util.capitalizeString(team.getTeamType().name()), 11) + teamInfoAgeGroup + team.getAgeGroup().name());
        if (team.getCoach() != null) {
            System.out.println(teamInfoCoach + team.getCoach().getName());
        } else {
            System.out.println();
        }
    }

    public void displayReturningToMainMenu() {
        System.out.println(returnToMainMenu);
    }

    public void displayShuttingDown() {
        System.out.println(systemShutdown);
    }

    public void displayUserInformation(String[] strArray, User user, int index) {
        String password = "";
        if (user.getUserType() == Enum.UserType.ADMIN) {
            System.out.println(userInfoUserId + index + memberNameColon + util.addDotsToStringToFillXCharacters(strArray[0], 35) + userInfoPassword + util.addDotsToStringToFillXCharacters(strArray[1], 20) + userInfoUserType + strArray[2]);
        } else {
            for (int i = 0; i < strArray[1].length(); i++) {
                password += "*";
            }
            System.out.println(userInfoUserId + index + memberNameColon + util.addDotsToStringToFillXCharacters(strArray[0], 35) + userInfoPassword + util.addDotsToStringToFillXCharacters(password, 20) + userInfoUserType + strArray[2]);
        }
    }

    public void displaySubscription(String[] strArray, double arrearsAmount) {
        System.out.print(memberIdColon + strArray[0] + memberNameColon + util.addDotsToStringToFillXCharacters(strArray[2], 35) + memberAgeColon + util.convertDateToAge(strArray[3]));
        if (strArray[4].equals("true")) {
            System.out.print(memberActive);
        } else if (strArray[4].equals("false")) {
            System.out.print(memberPassive);
        }
        if (strArray[5].equals("true")) {
            System.out.print(hasPaid);
        } else if (strArray[5].equals("false")) {
            System.out.print(isInArrears);
            System.out.printf(" - %.2fkr.", arrearsAmount);
        }
        System.out.println();
    }

    public void displayPleaseEnterPaymentStatus() {
        System.out.println();
        System.out.println(setNewPaymentStatus);
        System.out.println(hasPaidMenu1);
        System.out.println(inArrearsMenu2);
        System.out.print(selectCommand);
    }

    public void displaySelectedSwimmerMenu() {
        System.out.println();
        System.out.println(selectedSwimmerMenu);
    }

    public void displaySelectedUserMenu() {
        System.out.println();
        System.out.println(selectedUserMenu);
    }

    public void displaySelectedMemberMenu() {
        System.out.println();
        System.out.println(selectedMemberMenu);
    }

    public void displaySelectedSubscriptionMenu() {
        System.out.println();
        System.out.println(selectedSubscriptionMenu);
    }

    public void displayPleaseEnterMemberId() {
        System.out.println();
        System.out.print(pleaseEnterMemberId);
    }

    public void displayMemberListFull() {
        System.out.println(memberListFull);
    }

    public void displayUpdatedMemberSubscription(String[] strArray, double arrearsAmount) {
        System.out.println(updatedMemberSubscription);
        displaySubscription(strArray, arrearsAmount);
    }

    public void displayWhatToChangeCompetitive() {
        System.out.println();
        System.out.println(whatDoYouWantToChange);
        System.out.println(editNameMenu1);
        System.out.println(editBirthDateMenu2);
        System.out.println(editActiveMenu3);
        System.out.println(editCompetitiveMenu4);
        System.out.println(editDisciplineMenu5);
        System.out.print(selectCommand);
    }

    public void displayWhatToChange() {
        System.out.println();
        System.out.println(whatDoYouWantToChange);
        System.out.println(editNameMenu1);
        System.out.println(editBirthDateMenu2);
        System.out.println(editActiveMenu3);
        System.out.println(editCompetitiveMenu4);
        System.out.print(selectCommand);
    }

    public void displayMemberIdOutOfRange() {
        System.out.println(memberIdOutOfRange);
    }

    public void displayPleaseEnterValidCoachId(String coachId) {
        System.out.println(invalidCoachId + coachId);
    }

    public void displayPleaseTypeCoachId() {
        System.out.println();
        System.out.print(pleaseEnterCoachUserId);
    }

    public void displayPleaseEnterValidTeam(String team) {
        System.out.println(invalidTeam + team);
    }

    public void displayPleaseTypeTeamNumber() {
        System.out.println();
        System.out.print(pleaseEnterTeamNumber);
    }

    public void displayCoachAddedToTeam(String name, int i) {
        System.out.println(addedCoachColon + name + addedCoachToTeam + i);
    }

    public void displayNoCoachesRegistered() {
        System.out.println(noCoachesRegistered);
    }

    public void displayCoachRemovedFromTeam(String name, int i) {
        System.out.println(removedCoachColon + name + removedCoachFromTeam + i);
    }

    public void displayNoCoachOnSelectedTeam() {
        System.out.println(noCoachOnTeam);
    }

    public void displayPleaseEnterValidUserId(int userID) {
        System.out.println(invalidUserId + userID);
    }

    public void displayInvalidInputPleaseEnterNumberBetweenXandY(int x, int y) {
        System.out.println(invalidInputNumber + x + invalidInputAnd + y + ".");
    }

    public void setLanguage(String language) {
        String[] translations;
        if (language.equals("en")) {
            translations = setLanguageEn();
        } else if (language.equals("da")) {
            translations = setLanguageDa();
        } else {
            translations = setLanguageEn();
        }
        userCommands = translations[0];
        loginUserMenu = translations[1];
        menuBack = translations[2];
        selectCommand = translations[3];
        addUserMenu = translations[4];
        removeUserMenu = translations[5];
        showUsersMenu = translations[6];
        memberCommands = translations[7];
        addMemberMenu = translations[8];
        editMemberMenu = translations[9];
        removeMemberMenu = translations[10];
        showMembersMenu = translations[11];
        subscriptionCommands = translations[12];
        setPaymentStatusMenu = translations[13];
        showSubscriptionsMenu = translations[14];
        showExpectedSubFeesMenu = translations[15];
        showMembersInArrearsMenu = translations[16];
        swimmerCommands = translations[17];
        addCoachMenu = translations[18];
        removeCoachMenu = translations[19];
        showTopSwimmersMenu = translations[20];
        showAllSwimmersMenu = translations[21];
        addSwimmerRecordMenu = translations[22];
        viewSwimmerRecordsMenu = translations[23];
        adminCommands = translations[24];
        userMenu1 = translations[25];
        memberMenu2 = translations[26];
        subscriptionMenu3 = translations[27];
        swimmerMenu4 = translations[28];
        exitProgramMenu = translations[29];
        chairmanCommands = translations[30];
        cashierCommands = translations[31];
        subscriptionMenu2 = translations[32];
        coachCommands = translations[33];
        swimmerMenu2 = translations[34];
        loginCommands = translations[35];
        noSuchCommand = translations[36];
        addMemberSuccess = translations[37];
        addMemberFail = translations[38];
        removeMemberSuccess = translations[39];
        removeMemberFail = translations[40];
        loginSuccess = translations[41];
        loginFail = translations[42];
        addUserSuccess = translations[43];
        addUserFail = translations[44];
        removeUserSuccess = translations[45];
        removeUserFail = translations[46];
        typeUserLogin = translations[47];
        invalidName = translations[48];
        typeUserPassword = translations[49];
        invalidPassword = translations[50];
        selectUserTypes = translations[51];
        admin1 = translations[52];
        chairman2 = translations[53];
        cashier3 = translations[54];
        coach4 = translations[55];
        selectUserType = translations[56];
        pleaseEnterId = translations[57];
        noSuchMember = translations[58];
        pleaseEnterMemberName = translations[59];
        pleaseEnterBirthDay = translations[60];
        invalidBirthDay = translations[61];
        pleaseEnterBirthMonth = translations[62];
        invalidBirthMonth = translations[63];
        pleaseEnterBirthYear = translations[64];
        invalidBirthYear = translations[65];
        activeOrPassiveMember = translations[66];
        active1Menu = translations[67];
        passive2Menu = translations[68];
        memberNowActive = translations[69];
        memberNowPassive = translations[70];
        defaultInvalid = translations[71];
        compOrReg = translations[72];
        competitive1Menu = translations[73];
        regular2Menu = translations[74];
        noSuchOption = translations[75];
        pleaseEnterRecordDate = translations[76];
        invalidRecordDate = translations[77];
        expectedSubscriptionFees = translations[78];
        pleaseEnterIdOfMember = translations[79];
        invalidCompetitiveMemberId = translations[80];
        noRecordsFound = translations[81];
        recordTypes = translations[82];
        practiceRecord1 = translations[83];
        competitionPlacement2 = translations[84];
        pleaseEnterRecordType = translations[85];
        swimDisciplines = translations[86];
        butterflyMenu1 = translations[87];
        crawlMenu2 = translations[88];
        backCrawlMenu3 = translations[89];
        breaststrokeMenu4 = translations[90];
        chooseSwimDiscipline = translations[91];
        invalidSwimDiscipline = translations[92];
        pleaseEnterRecordTimeSeconds = translations[93];
        inputError = translations[94];
        pleaseEnterCompetition = translations[95];
        pleaseEnterPlacement = translations[96];
        inputErrorNumber = translations[97];
        successfullyAdded = translations[98];
        recordToSwimmer = translations[99];
        ageGroups = translations[100];
        u18Menu1 = translations[101];
        o18Menu2 = translations[102];
        chooseAgeGroup = translations[103];
        ageGroupTop5Error = translations[104];
        top5swimmers = translations[105];
        noRecordsInDiscipline = translations[106];
        memberColon = translations[107];
        swimDisciplineColon = translations[108];
        personalBestTrainingRecord = translations[109];
        competitionPlacementsColon = translations[110];
        chooseTypeOfTeamToAssignTo = translations[111];
        regularMenu1 = translations[112];
        competitiveMenu2 = translations[113];
        invalidTypeCoach = translations[114];
        frontCrawl = translations[115];
        backCrawl = translations[116];
        butterfly = translations[117];
        breastStroke = translations[118];
        wrongPassword = translations[119];
        cantLoginNoRegisteredUsers = translations[120];
        noRegisteredUsersCreateAdmin = translations[121];
        loginNameAdmin = translations[122];
        loginPasswordAdmin = translations[123];
        userNoPrivilege = translations[124];
        memberIdColon = translations[125];
        memberSinceColon = translations[126];
        memberNameColon = translations[127];
        memberBirthDateColon = translations[128];
        memberAgeColon = translations[129];
        memberActive = translations[130];
        memberPassive = translations[131];
        memberCompetitive = translations[132];
        memberRegular = translations[133];
        teamInfoTeam = translations[134];
        teamInfoTeamType = translations[135];
        teamInfoAgeGroup = translations[136];
        teamInfoCoach = translations[137];
        returnToMainMenu = translations[138];
        systemShutdown = translations[139];
        userInfoUserId = translations[140];
        userInfoPassword = translations[141];
        userInfoUserType = translations[142];
        hasPaid = translations[143];
        isInArrears = translations[144];
        setNewPaymentStatus = translations[145];
        hasPaidMenu1 = translations[146];
        inArrearsMenu2 = translations[147];
        selectedSwimmerMenu = translations[148];
        selectedUserMenu = translations[149];
        selectedMemberMenu = translations[150];
        selectedSubscriptionMenu = translations[151];
        pleaseEnterMemberId = translations[152];
        memberListFull = translations[153];
        updatedMemberSubscription = translations[154];
        whatDoYouWantToChange = translations[155];
        editNameMenu1 = translations[156];
        editBirthDateMenu2 = translations[157];
        editActiveMenu3 = translations[158];
        editCompetitiveMenu4 = translations[159];
        editDisciplineMenu5 = translations[160];
        memberIdOutOfRange = translations[161];
        invalidCoachId = translations[162];
        pleaseEnterCoachUserId = translations[163];
        invalidTeam = translations[164];
        pleaseEnterTeamNumber = translations[165];
        addedCoachColon = translations[166];
        addedCoachToTeam = translations[167];
        noCoachesRegistered = translations[168];
        removedCoachColon = translations[169];
        removedCoachFromTeam = translations[170];
        noCoachOnTeam = translations[171];
        invalidUserId = translations[172];
        invalidInputNumber = translations[173];
        invalidInputAnd = translations[174];
        selectLanguages = translations[175];
        englishMenu1 = translations[176];
        danishMenu2 = translations[177];
        selectLanguage = translations[178];
        pleaseEnterValidLanguage = translations[179];
        welcome = translations[180];
    }

    private String[] setLanguageEn() {
        String[] translations = lt.getEnglish();
        return translations;
    }

    private String[] setLanguageDa() {
        String[] translations = lt.getDanish();
        return translations;
    }
}
