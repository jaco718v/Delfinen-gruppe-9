package utilities;

import database.FileHandler;
import membership.*;
import swimclub.User;
import ui.UI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Utility {
    private final FileHandler fileHandler = new FileHandler();

    public boolean tryParseInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception E) {
            return false;
        }
        return true;
    }

    public String capitalizeString(String capitalizeWord) {
        if (capitalizeWord.length() > 0) {
            capitalizeWord = capitalizeWord.substring(0, 1).toUpperCase() + capitalizeWord.substring(1).toLowerCase();
        }
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

        if (firstWordEndIndex != -1) {
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
            return "null";
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
        return calculateAmountOfYears(birthDay, birthMonth, birthYear);
    }

    private int calculateAmountOfYears(int day, int month, int year) {
        int age;
        if ((day < LocalDateTime.now().getDayOfMonth()) && (month < LocalDateTime.now().getMonth().getValue())) {
            age = LocalDateTime.now().getYear() - year - 1;
        } else {
            age = LocalDateTime.now().getYear() - year;
        }
        return age;
    }

    public int convertDateToAgeInMonths(String string) {
        Scanner dateInputScanner = new Scanner(string);
        dateInputScanner.useDelimiter("/");
        int day = Integer.parseInt(dateInputScanner.next());
        int month = Integer.parseInt(dateInputScanner.next());
        int year = Integer.parseInt(dateInputScanner.next());
        return calculateAmountOfMonths(day, month, year);
    }

    private int calculateAmountOfMonths(int day, int month, int year) {
        int age = LocalDateTime.now().getYear() - year;
        int ageMonths;
        if ((day < LocalDateTime.now().getDayOfMonth()) && (month < LocalDateTime.now().getMonth().getValue())) {
            ageMonths = age * 12 + LocalDateTime.now().getMonth().getValue() - month + 1;
        } else if ((day >= LocalDateTime.now().getDayOfMonth()) && (month < LocalDateTime.now().getMonth().getValue())) {
            ageMonths = (age * 12) + LocalDateTime.now().getMonth().getValue() - month;
        } else if ((day < LocalDateTime.now().getDayOfMonth()) && (month >= LocalDateTime.now().getMonth().getValue())) {
            ageMonths = (age * 12) + LocalDateTime.now().getMonth().getValue() - month + 1;
        } else {
            ageMonths = (age * 12) + LocalDateTime.now().getMonth().getValue() - month;
        }
        return ageMonths;
    }

    public String addDotsToStringToFillXCharacters(String string, int takeUpCharacters) {
        if (!string.equals("")) {
            for (int i = 0; i < takeUpCharacters; i++) {
                if (i >= string.length()) {
                    string += ".";
                }
            }
            return string;
        } else {
            return null;
        }
    }

    public String addSpacesToStringToFillXCharacters(String string, int takeUpCharacters) {
        if (!string.equals("")) {
            for (int i = 0; i < takeUpCharacters; i++) {
                if (i >= string.length()) {
                    string += " ";
                }
            }
            return string;
        } else {
            return null;
        }
    }

    public String addMemberId() {
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

    public Team findCompetitiveTeam(ArrayList<Team> teamArray, Enum.TeamType teamType, Enum.AgeGroup ageGroup) {
        for (Team team : teamArray) {
            if (team.getTeamType().equals(teamType) && team.getAgeGroup().equals(ageGroup)) {
                return team;
            }
        }
        return null;
    }

    public void displayMembers(User loggedInUser, ArrayList<Team> teamArray, boolean teamsOnly, boolean competitiveOnly, String language) {
        UI ui = new UI(language);
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN) || (loggedInUser.getUserType() == Enum.UserType.COACH)) {
            ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
            int teamNumber = 0;
            for (Team team : teamArray) {
                teamNumber += 1;
                ui.displayTeamInformation(teamNumber, team);
                if (!teamsOnly) {
                    for (String[] strArray : memberData) {
                        int age = convertDateToAge(strArray[3]);
                        if ((team.getAgeGroup() == Enum.AgeGroup.U18) && (age < 18)) {
                            if ((strArray[5].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) {
                                ui.displayMemberInformation(strArray);
                            } else if ((strArray[5].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR) && (!competitiveOnly)) {
                                ui.displayMemberInformation(strArray);
                            }
                        } else if ((team.getAgeGroup() == Enum.AgeGroup.O18) && (age >= 18)) {
                            if ((strArray[5].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) {
                                ui.displayMemberInformation(strArray);
                            } else if ((strArray[5].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR) && (!competitiveOnly)) {
                                ui.displayMemberInformation(strArray);
                            }
                        }
                    }
                }
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public double calculateArrearsAmount(String[] subArray) {
        double arrearsAmount = 0;
        if (subArray[5].equals("false")) {
            int memberAge = convertDateToAge(subArray[3]);
            double passivePrice = 500;
            double activeU18Price = 1000;
            double activeO18Price = 1600;
            double active060Price = activeO18Price * (1 - 0.25);
            int membershipMonths = convertDateToAgeInMonths(subArray[1]);
            int arrearsMonths = convertDateToAgeInMonths(subArray[6]);
            int monthsSinceNewYears = calculateAmountOfMonths(1, 1, LocalDateTime.now().getYear());
            int monthsUntilNewYears = 12 - monthsSinceNewYears;
            if (subArray[4].equals("false")) {
                arrearsAmount = getArrearsAmount(passivePrice, membershipMonths, arrearsMonths, monthsSinceNewYears, monthsUntilNewYears);
            } else {
                if (memberAge < 18) {
                    arrearsAmount = getArrearsAmount(activeU18Price, membershipMonths, arrearsMonths, monthsSinceNewYears, monthsUntilNewYears);
                } else if (memberAge < 60) {
                    arrearsAmount = getArrearsAmount(activeO18Price, membershipMonths, arrearsMonths, monthsSinceNewYears, monthsUntilNewYears);
                } else {
                    arrearsAmount = getArrearsAmount(active060Price, membershipMonths, arrearsMonths, monthsSinceNewYears, monthsUntilNewYears);
                }
            }
        }
        return arrearsAmount;
    }

    private double getArrearsAmount(double memberPrice, int membershipMonths, int arrearsMonths, int monthsSinceNewYears, int monthsUntilNewYears) {
        double arrearsAmount;
        if (membershipMonths < monthsSinceNewYears) {
            arrearsAmount = memberPrice / 12 * (membershipMonths + monthsUntilNewYears);
        } else {
            if ((arrearsMonths/12) > 0) {
                arrearsAmount = memberPrice * Math.ceil((double)(arrearsMonths + monthsUntilNewYears)/12);
            } else {
                arrearsAmount = memberPrice;
            }
        }
        return arrearsAmount;
    }
}
