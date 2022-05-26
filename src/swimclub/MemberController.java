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

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemberController {
    private final InputHandler input = new InputHandler();
    private final FileHandler fileHandler = new FileHandler();
    private final Utility util = new Utility();

    public void addMember(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> newMemberData = new ArrayList<>();

            String memberId = util.addMemberId();
            if (!memberId.equals("FULL")) {
                String memberSince = String.format("%02d", LocalDateTime.now().getDayOfMonth()) + "/" + String.format("%02d", LocalDateTime.now().getMonthValue()) + "/" + LocalDateTime.now().getYear();
                String memberName = input.addMemberName(con);
                String memberBirthDate = input.addMemberBirthDay(con) + "/" + input.addMemberBirthMonth(con) + "/" + input.addMemberBirthYear(con);
                String isActive = input.addPassiveOrActive(con);
                String isCompetitive = input.addCompetitiveMember(con);
                String swimDiscipline = "";
                if (isCompetitive.equals("true")) {
                    swimDiscipline = String.valueOf(input.addSwimDisciplineToRecordViaInput(con));
                }

                newMemberData.add(new String[]{memberId, memberSince, memberName, memberBirthDate, isActive, isCompetitive, swimDiscipline});
                boolean success = fileHandler.writeToCSV("Members.csv", newMemberData);
                boolean isActiveBool = isActive.equals("true");
                for (Team team : con.getTeamArray()) {
                    if ((team.getTeamType() == Enum.TeamType.COMPETITIVE) && (isCompetitive.equals("true")) && (team.getAgeGroup() == Enum.AgeGroup.U18) && (util.convertDateToAge(memberBirthDate) < 18)) {
                        team.getMemberList().add(new MemberCompetitive(memberId, memberSince, memberName, memberBirthDate, isActiveBool, Enum.SwimDiscipline.valueOf(swimDiscipline)));
                    } else if ((team.getTeamType() == Enum.TeamType.COMPETITIVE) && (isCompetitive.equals("true")) && (team.getAgeGroup() == Enum.AgeGroup.O18) && (util.convertDateToAge(memberBirthDate) >= 18)) {
                        team.getMemberList().add(new MemberCompetitive(memberId, memberSince, memberName, memberBirthDate, isActiveBool, Enum.SwimDiscipline.valueOf(swimDiscipline)));
                    } else if ((team.getTeamType() == Enum.TeamType.REGULAR) && (isCompetitive.equals("false")) && (team.getAgeGroup() == Enum.AgeGroup.U18) && (util.convertDateToAge(memberBirthDate) < 18)) {
                        team.getMemberList().add(new MemberRegular(memberId, memberSince, memberName, memberBirthDate, isActiveBool));
                    } else if ((team.getTeamType() == Enum.TeamType.REGULAR) && (isCompetitive.equals("false")) && (team.getAgeGroup() == Enum.AgeGroup.O18) && (util.convertDateToAge(memberBirthDate) >= 18)) {
                        team.getMemberList().add(new MemberRegular(memberId, memberSince, memberName, memberBirthDate, isActiveBool));
                    }
                }
                con.getSubscriptionController().updateSubscriptions();
                ui.displayAddMember(success);
            } else {
                ui.displayMemberListFull();
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void editMember(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
            showMembers(con);
            String editMember = input.enterMemberId(con);
            for (int i = 0; i < memberData.size(); i++) {
                String[] array = memberData.get(i);
                if (array[0].equals(editMember)) {
                    String command = input.enterMemberEdit(array, con);
                    memberData.remove(i);
                    memberData.add(i, array);
                    fileHandler.overwriteCSV("Members.csv", memberData);
                    boolean editedMember = false;
                    for (Team team : con.getTeamArray()) {
                        if (editedMember) {
                            break;
                        }
                        for (int j = 0; j < team.getMemberList().size(); j++) {
                            Member currentMember = team.getMemberList().get(j);
                            if (editMember.equals(currentMember.getId())) {
                                boolean isActive = array[5].equals("true");
                                boolean isCompetitive = array[6].equals("true");
                                switch (command) {
                                    case "1" -> {
                                        currentMember.setName(array[2]);
                                        editedMember = true;
                                    }
                                    case "2" -> {
                                        currentMember.setBirthDate(array[3]);
                                        editedMember = true;
                                    }
                                    case "3" -> {
                                        currentMember.setActive(isActive);
                                        editedMember = true;
                                    }
                                    case "4" -> {
                                        team.getMemberList().remove(j);
                                        if (isCompetitive) {
                                            team.getMemberList().add(j, new MemberCompetitive(currentMember.getId(), currentMember.getMemberSinceDate(), currentMember.getName(), currentMember.getBirthDate(), currentMember.getActive(), Enum.SwimDiscipline.valueOf(array[5])));
                                        } else {
                                            team.getMemberList().add(j, new MemberRegular(currentMember.getId(), currentMember.getMemberSinceDate(), currentMember.getName(), currentMember.getBirthDate(), currentMember.getActive()));
                                        }
                                        editedMember = true;
                                    }
                                    case "5" -> {
                                        if (isCompetitive) {
                                            ((MemberCompetitive) currentMember).setSwimDiscipline(Enum.SwimDiscipline.valueOf(array[6]));
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
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void removeMember(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
            ArrayList<String[]> subData = fileHandler.readCSV("Subscriptions.csv");
            showMembers(con);
            ui.displayPleaseEnterMemberId();
            String memberId = input.enterMemberId(con);
            boolean removedMember = false;
            boolean removedMemberInList = false;
            for (int i = 0; i < memberData.size(); i++) {
                String[] memberArray = memberData.get(i);
                if (memberArray[0].equals(memberId)) {
                    memberData.remove(i);
                    fileHandler.overwriteCSV("Members.csv", memberData);
                    for (Team team : con.getTeamArray()) {
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
                    ui.displayRemoveMember(true);
                    removedMember = true;
                    break;
                }
            }
            if (!removedMember) {
                ui.displayRemoveMember(false);
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void showMembers(Controller con) {
        util.displayMembers(con.getLoggedInUser(), con.getTeamArray(), false, false, con.getLanguage());
    }
}
