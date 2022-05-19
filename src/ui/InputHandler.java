package ui;

import database.FileHandler;
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
    private final UI ui = new UI();
    private final Utility util = new Utility();

    public Scanner getSc() {
        return sc;
    }

    public String addUserName() {
        String userName = "";
        boolean enteredUserName = false;
        while (!enteredUserName) {
            ui.displayPleaseTypeUserLoginName();
            userName = sc.nextLine();
            if ((!userName.equals("")) && (!userName.equals(" "))) {
                enteredUserName = true;
            }
            if (!enteredUserName) {
                ui.displayPleaseEnterValidUserName(userName);
            }
        }
        return util.writeNameParts(userName);
    }

    public String addUserPassword() {
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

    public String addUserType() {
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

    public String addMemberName() {
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

    public String addMemberBirthDay() {
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

    public String addMemberBirthMonth() {
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

    public String addMemberBirthYear() {
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

    public String addPassiveOrActive() {
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

    public String addCompetitiveMember() {
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

    public String findCompetitiveMemberNameByID(ArrayList<Team> teamArray) {
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

    public String addSwimDisciplineToRecord(ArrayList<Team> teamArray, String name) {
        String swimDiscipline = null;
        for (Team team : teamArray) {
            swimDiscipline = team.findMemberSwimDiscipline(name);
            if (swimDiscipline != null) {
                return swimDiscipline;
            }
        }

        return null;
    }

    public String addSwimDisciplineToRecordViaInput() {
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

    public String recordTypeChoice(String memberName) {
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

    public String addRecordInSeconds() {
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
        return recordInSeconds;
    }

    public String addDate() {
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

    public String addPlacing() {
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

    public Enum.AgeGroup decideAgeGroup() {
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

    public String[] enterUserName(ArrayList<String[]> userData) {
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
                ui.displayPleaseEnterValidUserName(returnStrings[0]);
            }
        }
        return returnStrings;
    }

    public String enterUserPassword(ArrayList<String[]> userData, int userIndex) {
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
            ui.loginUser(enteredUserPassword);
            if (!enteredUserPassword) {
                ui.displayWrongPassword();
            }
        }
        return userPassword;
    }

    public String enterMemberId() {
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

    public int enterUserNameGetId(ArrayList<String[]> userData) {
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
            ui.removeUser(enteredUserName);
            if (!enteredUserName) {
                ui.displayPleaseEnterValidUserName(userName);
            }
        }
        return userID;
    }

    public String enterUserId(ArrayList<String[]> subscriptionData) {
        String foundId = "-1";
        boolean enteredUserId = false;
        while (!enteredUserId) {

            ui.displayPleaseEnterUserId();
            String userIdInput = sc.nextLine();
            for (int i = 0; i < subscriptionData.size(); i++) {
                String[] strArray = subscriptionData.get(i);
                if (strArray[0].equals(userIdInput)) {
                    foundId = Integer.toString(i);
                }
            }
            if (foundId.equals("-1")) {
                ui.displayNoSuchMemberFound();
            }
        }
        return foundId;
    }

    public String enterUserPaymentStatus() {
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

    public String enterMemberEditType() {
        String input = "";
        boolean enteredType = false;
        while (!enteredType) {
            input = sc.nextLine();
            if ((!input.equals("1")) && (!input.equals("2")) && (!input.equals("3")) && (!input.equals("4"))) {
                enteredType = true;
            }
        }
        return input;
    }
}
