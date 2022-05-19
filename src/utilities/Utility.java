package utilities;

import membership.Enum;
import membership.Team;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Utility {
    public boolean tryParseInt(String str) {
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

    public ArrayList<String[]> removeIrrelevantRecords(ArrayList<String[]> recordData, ArrayList<String[]> memberData, String swimDiscipline) {
        int counter1 = 0;
        int counter2 = 0;
        for (int i = 0; recordData.size() > i; i++) {
            counter1 = i - counter2;
            if (!recordData.get(counter1)[1].equals(swimDiscipline) || !recordData.get(counter1)[4].equals(" ")) {
                recordData.remove(counter1);
                counter2++;
            }
        }
        counter1 = 0;
        counter2 = 0;
        for (int i = 0; recordData.size() > i; i++) {
            boolean irrelevant = true;
            for (String[] strArray : memberData) {
                counter1 = i - counter2;
                if (strArray[1].equalsIgnoreCase(recordData.get(counter1)[0])) {
                    irrelevant = false;
                    break;
                }
            }
            if (irrelevant) {
                recordData.remove(counter1);
                counter2++;
            }
        }
        return recordData;
    }

    public ArrayList<String[]> findCompetitiveTeam(ArrayList<Team> teamArray, Enum.AgeGroup ageGroup) {
        if (ageGroup == Enum.AgeGroup.U18) {
            return teamArray.get(2).getMembers();
        }
        return teamArray.get(3).getMembers();
    }
}
