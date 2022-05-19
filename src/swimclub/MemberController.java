package swimclub;

import database.FileHandler;
import utilities.Enum;
import membership.Team;
import ui.InputHandler;
import ui.UI;
import utilities.Utility;

import java.util.ArrayList;

public class MemberController {
    private final UI ui = new UI();
    private final InputHandler input = new InputHandler();
    private final FileHandler fileHandler = new FileHandler();
    private final Utility util = new Utility();

    public void addMember(Controller con, User loggedInUser) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> newMemberData = new ArrayList<>();

            String memberId = util.addMemberId();
            if (!memberId.equals("FULL")) {
                String memberName = input.addMemberName();
                String memberBirthDate = input.addMemberBirthDay() + "/" + input.addMemberBirthMonth() + "/" + input.addMemberBirthYear();
                String isActive = input.addPassiveOrActive();
                String isCompetitive = input.addCompetitiveMember();
                String swimDiscipline = " ";
                if (isCompetitive.equals("true")) {
                    swimDiscipline = input.addSwimDisciplineToRecordViaInput();
                }

                newMemberData.add(new String[]{ memberId, memberName, memberBirthDate, isActive, isCompetitive, swimDiscipline });
                boolean success = fileHandler.writeToCSV("Members.csv", newMemberData);
                // TODO: add Member to memberList in correct Team
                con.getSubscriptionController().updateSubscriptions();
                ui.addMember(success);
            } else {
                ui.displayMemberListFull();
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void editMember(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            // edit member kode her
            ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
            showMembers(loggedInUser, teamArray);
            ui.typeMemberIdPlease();
            String editMember = input.enterMemberId();
            for (int i = 0; i < memberData.size(); i++) {
                String[] array = memberData.get(i);
                if (array[0].equals(editMember)) {
                    ui.whatToChange();
                    String command = input.enterMemberEditType();
                    switch (command) {
                        case "1" -> array[1] = input.addMemberName();
                        case "2" -> array[2] = input.addMemberBirthDay() + "/" + input.addMemberBirthMonth() + "/" + input.addMemberBirthYear();
                        case "3" -> array[3] = input.addPassiveOrActive();
                        case "4" -> array[4] = input.addCompetitiveMember();
                    }
                    memberData.remove(i);
                    memberData.add(i, array);
                    fileHandler.overwriteCSV("Members.csv", memberData);
                    // TODO: edit Member in memberList in correct Team
                    break;
                }
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void removeMember(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
            ArrayList<String[]> subData = fileHandler.readCSV("Subscriptions.csv");
            showMembers(loggedInUser, teamArray);
            ui.displayPleaseEnterMemberId();
            String memberId = input.enterMemberId();
            boolean removedMember = false;
            for (int i = 0; i < memberData.size(); i++) {
                String[] memberArray = memberData.get(i);
                if (memberArray[0].equals(memberId)) {
                    memberData.remove(i);
                    fileHandler.overwriteCSV("Members.csv", memberData);
                    // TODO: remove member from memberList in correct Team
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

    public void showMembers(User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {

            ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
            int teamNumber = 0;
            for (Team team : teamArray) {
                teamNumber += 1;
                ui.displayTeamInformation(teamNumber, team);

                for (String[] strArray : memberData) {
                    int age = util.convertDateToAge(strArray[2]);
                    if ((team.getAgeGroup() == Enum.AgeGroup.U18) && (age < 18)) {
                        if (((strArray[4].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) || ((strArray[4].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR))) {
                            ui.displayMemberInformation(strArray);
                        }
                    } else if ((team.getAgeGroup() == Enum.AgeGroup.O18) && (age >= 18)) {
                        if (((strArray[4].equals("true")) && (team.getTeamType() == Enum.TeamType.COMPETITIVE)) || ((strArray[4].equals("false")) && (team.getTeamType() == Enum.TeamType.REGULAR))) {
                            ui.displayMemberInformation(strArray);
                        }
                    }
                }
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }
}
