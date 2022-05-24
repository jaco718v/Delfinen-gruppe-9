package ui;

import database.FileHandler;
import swimclub.Controller;
import swimclub.User;
import utilities.Enum;
import membership.Team;
import utilities.Utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputHandler {
    private final Scanner sc = new Scanner(System.in);
    private final Utility util = new Utility();
    private final FileHandler fileHandler = new FileHandler();

    public Scanner getSc() {
        return sc;
    }

    public String addUserName(Controller con) {
        UI ui = new UI(con.getLanguage());
        String userName = "";
        boolean enteredUserName = false;
        while (!enteredUserName) {
            ui.displayPleaseTypeUserLoginName();
            userName = sc.nextLine();
            if ((!userName.equals("")) && (!userName.equals(" ")) && (!userName.equalsIgnoreCase("chairman")) && (!userName.equalsIgnoreCase("cashier")) && (!userName.equalsIgnoreCase("coach"))) {
                enteredUserName = true;
            }
            if (!enteredUserName) {
                ui.displayPleaseEnterValidName(userName);
            }
        }
        return util.writeNameParts(userName);
    }

    public String addUserPassword(Controller con) {
        UI ui = new UI(con.getLanguage());
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

    public String addUserType(Controller con) {
        UI ui = new UI(con.getLanguage());
        String userType = "";
        boolean enteredUserType = false;
        while (!enteredUserType) {
            ui.displayPleaseSelectUserType();
            userType = sc.nextLine();
            switch (userType) {
                case "1", "2", "3", "4" -> enteredUserType = true;
            }
            ui.displayAddUser(enteredUserType);
            if (!enteredUserType) {
                ui.displayBadPassword(userType);
            }
        }
        return userType;
    }

    public String addMemberName(Controller con) {
        UI ui = new UI(con.getLanguage());
        String memberName = "";
        boolean enteredMemberName = false;
        while (!enteredMemberName) {
            ui.displayPleaseEnterMemberName();
            memberName = sc.nextLine();
            if ((!memberName.equals("")) && (!memberName.equals(" ")) && (!util.tryParseInt(memberName))) {
                enteredMemberName = true;
            }
            if (!enteredMemberName) {
                ui.displayPleaseEnterValidName(memberName);
            }
        }
        return util.writeNameParts(memberName);
    }

    public String addMemberBirthDay(Controller con) {
        UI ui = new UI(con.getLanguage());
        String memberBirthDay = "";
        int birthDay = 0;
        boolean enteredMemberBirthDay = false;
        while (!enteredMemberBirthDay) {
            ui.displayPleaseEnterMemberBirthDay();
            memberBirthDay = sc.nextLine();
            if ((util.tryParseInt(memberBirthDay)) && (Integer.parseInt(memberBirthDay) >= 1) && (Integer.parseInt(memberBirthDay) <= 31)) {
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

    public String addMemberBirthMonth(Controller con) {
        UI ui = new UI(con.getLanguage());
        String memberBirthMonth = "";
        int birthMonth = 0;
        boolean enteredMemberBirthMonth = false;
        while (!enteredMemberBirthMonth) {
            ui.displayPleaseEnterMemberBirthMonth();
            memberBirthMonth = sc.nextLine();
            if ((util.tryParseInt(memberBirthMonth)) && (Integer.parseInt(memberBirthMonth) >= 1) && (Integer.parseInt(memberBirthMonth) <= 12)) {
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

    public String addMemberBirthYear(Controller con) {
        UI ui = new UI(con.getLanguage());
        String memberBirthYear = "";
        int birthYear = 0;
        boolean enteredMemberBirthYear = false;
        while (!enteredMemberBirthYear) {
            ui.displayPleaseEnterMemberYear();
            memberBirthYear = sc.nextLine();
            if ((util.tryParseInt(memberBirthYear)) && (Integer.parseInt(memberBirthYear) >= LocalDateTime.now().getYear() - 120) && (Integer.parseInt(memberBirthYear) <= LocalDateTime.now().getYear())) {
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

    public String addPassiveOrActive(Controller con) {
        UI ui = new UI(con.getLanguage());
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

    public String addCompetitiveMember(Controller con) {
        UI ui = new UI(con.getLanguage());
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

    public Enum.TeamType chooseTeamType(Controller con) {
        UI ui = new UI(con.getLanguage());
        ui.displayChooseTeamTypeCoach();
        Enum.TeamType teamType = null;
        while(teamType==null){
            String input = sc.nextLine();
            switch (input) {
                case "1" -> teamType = Enum.TeamType.REGULAR;
                case "2" -> teamType = Enum.TeamType.COMPETITIVE;
                default -> ui.displayChooseTeamTypeCoachError();
            }
        }
        return teamType;
    }

    public Enum.AgeGroup chooseAgeGroup(Controller con) {
        UI ui = new UI(con.getLanguage());
        ui.displayChooseAgeGroupCoach();
        Enum.AgeGroup ageGroup = null;
        while(ageGroup==null){
            String input = sc.nextLine();
            switch (input) {
                case "1" -> ageGroup = Enum.AgeGroup.U18;
                case "2" -> ageGroup = Enum.AgeGroup.O18;
                default -> ui.displayChooseAgeGroupCoachError();
            }
        }
        return ageGroup;
    }

    public String enterCompetitiveMemberID(Controller con) {
        UI ui = new UI(con.getLanguage());
        ui.displayPleaseTypeMemberID();
        String memberID = sc.nextLine();
        return memberID;
    }

    public Enum.SwimDiscipline addSwimDisciplineToRecord(Team team, String memberID) {
        Enum.SwimDiscipline swimDiscipline;
            swimDiscipline = team.findMemberSwimDiscipline(memberID);
        return swimDiscipline;
    }

    public Enum.SwimDiscipline addSwimDisciplineToRecordViaInput(Controller con) {
        UI ui = new UI(con.getLanguage());
        ui.displayEnterSwimDiscipline();
        Enum.SwimDiscipline swimDiscipline = null;
        while (swimDiscipline == null) {
            try {
                String choice = sc.nextLine();
                switch (choice) {
                    case "1" -> swimDiscipline = Enum.SwimDiscipline.valueOf("BUTTERFLY");
                    case "2" -> swimDiscipline = Enum.SwimDiscipline.valueOf("CRAWL");
                    case "3" -> swimDiscipline = Enum.SwimDiscipline.valueOf("BACKCRAWL");
                    case "4" -> swimDiscipline = Enum.SwimDiscipline.valueOf("BREAST");
                }
            } catch (InputMismatchException ime) {
                ui.displayEnterSwimDisciplineException();
            }
        }
        return swimDiscipline;
    }

    public String recordTypeChoice(Controller con) {
        UI ui = new UI(con.getLanguage());
        String recordType = null;
        ui.displayRecordTypeChoice();
        while (recordType == null || !recordType.equalsIgnoreCase("1") && !recordType.equalsIgnoreCase("2")) {
            recordType = sc.nextLine();
        }
        if (recordType.equals("1")) {
            recordType = "practice";
        }
        if (recordType.equals("2")) {
            recordType = "competition";
        }
        return recordType;
    }

    public double addRecordInSeconds(Controller con) {
        UI ui = new UI(con.getLanguage());
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
        return recordDouble;
    }

    public String addDate(Controller con) {
        UI ui = new UI(con.getLanguage());
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

    public int addPlacing(Controller con) {
        UI ui = new UI(con.getLanguage());
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
        return placeInt;
    }

    public Enum.AgeGroup decideAgeGroup(Controller con) {
        UI ui = new UI(con.getLanguage());
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

    public String[] enterUserName(ArrayList<String[]> userData, Controller con) {
        UI ui = new UI(con.getLanguage());
        String[] returnStrings = new String[2];
        boolean enteredUserName = false;
        while (!enteredUserName) {
            ui.displayPleaseTypeUserLoginName();
            returnStrings[0] = sc.nextLine();
            for (int i = 0; i < userData.size(); i++) {
                String[] strArray = userData.get(i);
                if (strArray[0].equals(returnStrings[0])) {
                    returnStrings[1] = Integer.toString(i);
                    enteredUserName = true;
                }
            }
            if (!enteredUserName) {
                ui.displayPleaseEnterValidName(returnStrings[0]);
            }
        }
        return returnStrings;
    }

    public String enterUserPassword(ArrayList<String[]> userData, int userIndex, Controller con) {
        UI ui = new UI(con.getLanguage());
        String userPassword = "";

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
            ui.displayLoginUser(enteredUserPassword);
            if (!enteredUserPassword) {
                ui.displayWrongPassword();
            }
        }
        return userPassword;
    }

    public String enterMemberId(Controller con) {
        UI ui = new UI(con.getLanguage());
        String memberId = "";

        boolean enteredUserId = false;
        while (!enteredUserId) {
            ui.displayPleaseEnterMemberId();
            memberId = sc.nextLine();
            if ((util.tryParseInt(memberId)) && (Integer.parseInt(memberId) < 10000) && (Integer.parseInt(memberId) > 0)) {
                enteredUserId = true;
            }
            if (!enteredUserId) {
                ui.displayMemberIdOutOfRange();
            }
        }
        return memberId;
    }

    public int enterUserNameGetId(ArrayList<String[]> userData, Controller con) {
        UI ui = new UI(con.getLanguage());
        String userName = "";
        int userID = -1;
        boolean enteredUserName = false;
        while (!enteredUserName) {
            ui.displayPleaseTypeUserLoginName();
            userName = sc.nextLine();
            for (int i = 0; i < userData.size(); i++) {
                String[] strArray = userData.get(i);
                if (userName.equals(strArray[0])) {
                    enteredUserName = true;
                    userID = i;
                    break;
                }
            }
            ui.displayRemoveUser(enteredUserName);
            if (!enteredUserName) {
                ui.displayPleaseEnterValidName(userName);
            }
        }
        return userID;
    }

    public String enterUserId(ArrayList<String[]> subscriptionData, Controller con) {
        UI ui = new UI(con.getLanguage());
        String foundId = "-1";
        boolean enteredUserId = false;
        while (!enteredUserId) {

            ui.displayPleaseEnterUserId();
            String userIdInput = sc.nextLine();
            for (int i = 0; i < subscriptionData.size(); i++) {
                String[] strArray = subscriptionData.get(i);
                if (strArray[0].equals(userIdInput)) {
                    foundId = Integer.toString(i);
                    enteredUserId = true;
                }
            }
            if (foundId.equals("-1")) {
                ui.displayNoSuchMemberFound();
            }
        }
        return foundId;
    }

    public String enterUserPaymentStatus(Controller con) {
        UI ui = new UI(con.getLanguage());
        String paymentStatusSelection = "";
        boolean enteredPaymentStatus = false;
        while (!enteredPaymentStatus) {
            ui.displayPleaseEnterPaymentStatus();
            paymentStatusSelection = sc.nextLine();
            if ((paymentStatusSelection.equals("1")) || (paymentStatusSelection.equals("2"))) {
                enteredPaymentStatus = true;
            } else {
                ui.displayNoSuchCommand(paymentStatusSelection);
            }
        }
        return paymentStatusSelection;
    }

    public String enterString() {
        return sc.nextLine();
    }

    public String enterMemberEditTypeCompetitive(Controller con) {
        UI ui = new UI(con.getLanguage());
        String input = "";
        boolean enteredType = false;
        while (!enteredType) {
            input = sc.nextLine();
            if ((input.equals("1")) || (input.equals("2")) || (input.equals("3")) || (input.equals("4")) || (input.equals("5"))) {
                enteredType = true;
            } else {
                ui.displayInvalidInputPleaseEnterNumberBetweenXandY(1, 5);
            }
        }
        return input;
    }

    public String enterMemberEditType(Controller con) {
        UI ui = new UI(con.getLanguage());
        String input = "";
        boolean enteredType = false;
        while (!enteredType) {
            input = sc.nextLine();
            if ((input.equals("1")) || (input.equals("2")) || (input.equals("3")) || (input.equals("4"))) {
                enteredType = true;
            } else {
                ui.displayInvalidInputPleaseEnterNumberBetweenXandY(1, 5);
            }
        }
        return input;
    }

    public User enterCoachToAddToTeam(Controller con) {
        UI ui = new UI(con.getLanguage());
        User coach = null;
        String coachId = "";
        boolean enteredCoachId = false;
        while (!enteredCoachId) {
            ui.displayPleaseTypeCoachId();
            coachId = sc.nextLine();
            ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
            for (int i = 0; i < userData.size(); i++) {
                String[] userArray = userData.get(i);
                if ((userArray[2].equals("COACH")) && (coachId.equals(Integer.toString(i)))) {
                    enteredCoachId = true;
                    coach = new User(userArray[0], userArray[1], Enum.UserType.valueOf(userArray[2]));
                    break;
                }
            }
            if (!enteredCoachId) {
                ui.displayPleaseEnterValidCoachId(coachId);
            }
        }
        return coach;
    }

    public Team enterTeamToAddCoachTo(Controller con) {
        UI ui = new UI(con.getLanguage());
        Team team = null;
        String teamId = "";
        boolean enteredTeam = false;
        while (!enteredTeam) {
            ui.displayPleaseTypeTeamNumber();
            teamId = sc.nextLine();
            if ((util.tryParseInt(teamId)) && (Integer.parseInt(teamId) <= con.getTeamArray().size()) && (Integer.parseInt(teamId) > 0)) {
                for (int i = 0; i < con.getTeamArray().size(); i++) {
                    Team currentTeam = con.getTeamArray().get(i);
                    if (i+1 == Integer.parseInt(teamId)) {
                        team = currentTeam;
                        enteredTeam = true;
                        break;
                    }
                }
            }
            if (!enteredTeam) {
                ui.displayPleaseEnterValidTeam(teamId);
            }
        }
        return team;
    }

    public Team enterTeamToRemoveCoachFrom(Controller con) {
        UI ui = new UI(con.getLanguage());
        Team team = null;
        String teamId = "";
        boolean enteredTeam = false;
        while (!enteredTeam) {
            ui.displayPleaseTypeTeamNumber();
            teamId = sc.nextLine();
            if ((util.tryParseInt(teamId)) && (Integer.parseInt(teamId) <= con.getTeamArray().size()) && (Integer.parseInt(teamId) > 0)) {
                for (int i = 0; i < con.getTeamArray().size(); i++) {
                    Team currentTeam = con.getTeamArray().get(i);
                    if (i+1 == Integer.parseInt(teamId)) {
                        if (currentTeam.getCoach() != null) {
                            team = currentTeam;
                            enteredTeam = true;
                            break;
                        } else {
                            ui.displayNoCoachOnSelectedTeam();
                        }
                    }
                }
            }
            if (!enteredTeam) {
                ui.displayPleaseEnterValidTeam(teamId);
            }
        }
        return team;
    }

    public String enterMemberEdit(String[] array, Controller con) {
        UI ui = new UI(con.getLanguage());
        String command = "";
        boolean selectedEditType = false;
        while (!selectedEditType) {
            if (array[5].equals("true")) {
                ui.displayWhatToChangeCompetitive();
                command = enterMemberEditTypeCompetitive(con);
                switch (command) {
                    case "1" -> {
                        System.out.println("Editing member name.");
                        array[2] = addMemberName(con);
                        selectedEditType = true;
                    }
                    case "2" -> {
                        System.out.println("Editing member birth date.");
                        array[3] = addMemberBirthDay(con) + "/" + addMemberBirthMonth(con) + "/" + addMemberBirthYear(con);
                        selectedEditType = true;
                    }
                    case "3" -> {
                        System.out.println("Editing member activity.");
                        array[4] = addPassiveOrActive(con);
                        selectedEditType = true;
                    }
                    case "4" -> {
                        System.out.println("Editing member type.");
                        array[5] = addCompetitiveMember(con);
                        selectedEditType = true;
                    }
                    case "5" -> {
                        System.out.println("Editing member swim discipline.");
                        array[6] = String.valueOf(addSwimDisciplineToRecordViaInput(con));
                        selectedEditType = true;
                    }
                }
            } else {
                ui.displayWhatToChange();
                command = enterMemberEditType(con);
                switch (command) {
                    case "1" -> {
                        array[2] = addMemberName(con);
                        selectedEditType = true;
                    }
                    case "2" -> {
                        array[3] = addMemberBirthDay(con) + "/" + addMemberBirthMonth(con) + "/" + addMemberBirthYear(con);
                        selectedEditType = true;
                    }
                    case "3" -> {
                        array[4] = addPassiveOrActive(con);
                        selectedEditType = true;
                    }
                    case "4" -> {
                        array[5] = addCompetitiveMember(con);
                        selectedEditType = true;
                    }
                }
            }
        }
        return command;
    }

    public String selectLanguage(Controller con) {
        UI ui = new UI(con.getLanguage());
        String language = "";
        String input = "";
        boolean enteredLanguage = false;
        while (!enteredLanguage) {
            ui.displayPleaseSelectLanguage();
            input = sc.nextLine();
            if (input.equals("1")) {
                language = "en";
                enteredLanguage = true;
            } else if (input.equals("2")) {
                language = "da";
                enteredLanguage = true;
            }
            if (!enteredLanguage) {
                ui.displayPleaseEnterValidLanguage(language);
            }
        }
        return language;
    }
}
