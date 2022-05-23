package swimclub;

import database.FileHandler;
import membership.Member;
import membership.MemberCompetitive;
import membership.MemberRegular;
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
                String swimDiscipline = "";
                if (isCompetitive.equals("true")) {
                    swimDiscipline = String.valueOf(input.addSwimDisciplineToRecordViaInput());
                }

                newMemberData.add(new String[]{ memberId, memberName, memberBirthDate, isActive, isCompetitive, swimDiscipline });
                boolean success = fileHandler.writeToCSV("Members.csv", newMemberData);
                boolean isActiveBool = isActive.equals("true");
                for (Team team : con.getTeamArray()) {
                    if ((team.getTeamType() == Enum.TeamType.COMPETITIVE) && (isCompetitive.equals("true")) && (team.getAgeGroup() == Enum.AgeGroup.U18) && (util.convertDateToAge(memberBirthDate) < 18)) {
                        team.getMemberList().add(new MemberCompetitive(memberId, memberName, memberBirthDate, isActiveBool,Enum.SwimDiscipline.valueOf(swimDiscipline)));
                    } else if ((team.getTeamType() == Enum.TeamType.COMPETITIVE) && (isCompetitive.equals("true")) && (team.getAgeGroup() == Enum.AgeGroup.O18) && (util.convertDateToAge(memberBirthDate) >= 18)) {
                        team.getMemberList().add(new MemberCompetitive(memberId, memberName, memberBirthDate, isActiveBool,Enum.SwimDiscipline.valueOf(swimDiscipline)));
                    } else if ((team.getTeamType() == Enum.TeamType.REGULAR) && (isCompetitive.equals("false")) && (team.getAgeGroup() == Enum.AgeGroup.U18) && (util.convertDateToAge(memberBirthDate) < 18)) {
                        team.getMemberList().add(new MemberRegular(memberId, memberName, memberBirthDate, isActiveBool));
                    } else if ((team.getTeamType() == Enum.TeamType.REGULAR) && (isCompetitive.equals("false")) && (team.getAgeGroup() == Enum.AgeGroup.O18) && (util.convertDateToAge(memberBirthDate) >= 18)) {
                        team.getMemberList().add(new MemberRegular(memberId, memberName, memberBirthDate, isActiveBool));
                    }
                }
                con.getSubscriptionController().updateSubscriptions();
                ui.addMember(success);
            } else {
                ui.displayMemberListFull();
            }
        } else {
            ui.loggedInUserNoPrivilege();
        }
    }

    public void editMember(Controller con, User loggedInUser, ArrayList<Team> teamArray) {
        if ((loggedInUser.getUserType() == Enum.UserType.ADMIN) || (loggedInUser.getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
            showMembers(loggedInUser, teamArray);
            ui.typeMemberIdPlease();
            String editMember = input.enterMemberId();
            for (int i = 0; i < memberData.size(); i++) {
                String[] array = memberData.get(i);
                if (array[0].equals(editMember)) {
                    String command = "";
                    boolean selectedEditType = false;
                    while (!selectedEditType) {
                        if (array[4].equals("true")) {
                            ui.whatToChangeCompetitive();
                            command = input.enterMemberEditTypeCompetitive();
                            switch (command) {
                                case "1" -> {
                                    array[1] = input.addMemberName();
                                    selectedEditType = true;
                                }
                                case "2" -> {
                                    array[2] = input.addMemberBirthDay() + "/" + input.addMemberBirthMonth() + "/" + input.addMemberBirthYear();
                                    selectedEditType = true;
                                }
                                case "3" -> {
                                    array[3] = input.addPassiveOrActive();
                                    selectedEditType = true;
                                }
                                case "4" -> {
                                    array[4] = input.addCompetitiveMember();
                                    selectedEditType = true;
                                }
                                case "5" -> {
                                    array[5] = String.valueOf(input.addSwimDisciplineToRecordViaInput());
                                    selectedEditType = true;
                                }
                            }
                        } else {
                            ui.whatToChange();
                            command = input.enterMemberEditType();
                            switch (command) {
                                case "1" -> {
                                    array[1] = input.addMemberName();
                                    selectedEditType = true;
                                }
                                case "2" -> {
                                    array[2] = input.addMemberBirthDay() + "/" + input.addMemberBirthMonth() + "/" + input.addMemberBirthYear();
                                    selectedEditType = true;
                                }
                                case "3" -> {
                                    array[3] = input.addPassiveOrActive();
                                    selectedEditType = true;
                                }
                                case "4" -> {
                                    array[4] = input.addCompetitiveMember();
                                    selectedEditType = true;
                                }
                            }

                        }
                        memberData.remove(i);
                        memberData.add(i, array);
                        fileHandler.overwriteCSV("Members.csv", memberData);
                    }
                    boolean editedMember = false;
                    for (Team team : con.getTeamArray()) {
                        if (editedMember) {
                            break;
                        }
                        for (int j = 0; j < team.getMemberList().size(); j++) {
                            Member currentMember = team.getMemberList().get(j);
                            if (editMember.equals(currentMember.getId())) {
                                boolean isActive = array[3].equals("true");
                                boolean isCompetitive = array[4].equals("true");
                                switch (command) {
                                    case "1" -> {
                                        currentMember.setName(array[1]);
                                        editedMember = true;
                                    }
                                    case "2" -> {
                                        currentMember.setBirthDate(array[2]);
                                        editedMember = true;
                                    }
                                    case "3" -> {
                                        currentMember.setActive(isActive);
                                        editedMember = true;
                                    }
                                    case "4" -> {
                                        team.getMemberList().remove(j);
                                        if (isCompetitive) {
                                            team.getMemberList().add(j, new MemberCompetitive(currentMember.getId(), currentMember.getName(), currentMember.getBirthDate(), currentMember.getActive(),Enum.SwimDiscipline.valueOf(array[5])));
                                        } else {
                                            team.getMemberList().add(j, new MemberRegular(currentMember.getId(), currentMember.getName(), currentMember.getBirthDate(), currentMember.getActive()));
                                        }
                                        editedMember = true;
                                    }
                                    case "5" -> {
                                        if (isCompetitive) {
                                            ((MemberCompetitive) currentMember).setSwimDiscipline(Enum.SwimDiscipline.valueOf(array[5]));
                                            editedMember = true;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
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
            boolean removedMemberInList = false;
            for (int i = 0; i < memberData.size(); i++) {
                String[] memberArray = memberData.get(i);
                if (memberArray[0].equals(memberId)) {
                    memberData.remove(i);
                    fileHandler.overwriteCSV("Members.csv", memberData);
                    for (Team team : teamArray) {
                        ArrayList<Member> memberList = team.getMemberList();
                        for (int j = 0; j < memberList.size(); j++) {
                            if (memberList.get(j).getId().equals(memberId)) {
                                memberList.remove(j);
                                team.setMemberList(memberList);
                                removedMemberInList = true;
                                break;
                            }
                        }
                        if (removedMemberInList) {
                            break;
                        }
                    }
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
        util.displayMembers(loggedInUser, teamArray, false, false);
    }
}
