package swimclub;

import database.FileHandler;
import membership.Enum;
import membership.Team;
import ui.UI;
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
    private final FileHandler fileHandler = new FileHandler();

    public String addUserName() {
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

    public String addMemberId() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        String returnValue = "FULL";
        int memberIdInt = 0;
        int lastIndex = memberData.size() - 1;
        if (lastIndex != -1) {
            String[] memberArray = memberData.get(lastIndex);
            if (util.tryParseInt(memberArray[0])) {
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
}
