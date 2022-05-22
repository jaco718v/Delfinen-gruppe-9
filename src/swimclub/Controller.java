package swimclub;

import database.FileHandler;
import membership.*;
import ui.Commands;
import ui.InputHandler;
import ui.UI;
import utilities.Utility;
import utilities.Enum;

import java.util.ArrayList;

public class Controller {
    private final UI ui = new UI();
    private final Commands cmds = new Commands();
    private final InputHandler input = new InputHandler();
    private final FileHandler fileHandler = new FileHandler();
    private final Utility util = new Utility();
    private final UserController userController = new UserController();
    private final MemberController memberController = new MemberController();
    private final SubscriptionController subscriptionController = new SubscriptionController();
    private final SwimmerController swimmerController = new SwimmerController();
    private final ArrayList<Team> teamArray = new ArrayList<>();
    private User loggedInUser;
    private boolean isRunning = true;

    public static void main(String[] args) {
        Controller con = new Controller();
        con.run();
    }

    private void run() {
        createTeams();
        while (isRunning) {
            cmds.commands(this, input.getSc());
        }
    }

    private void createTeams() {
        ArrayList<String[]> teamData = fileHandler.readCSV("Teams.csv");
        ArrayList<String[]> newTeamData = new ArrayList<>();
        for (int i = 0; i < Enum.TeamType.values().length; i++) {
            for (int j = 0; j < Enum.AgeGroup.values().length; j++) {
                Team newTeam = new Team(Enum.TeamType.values()[i], Enum.AgeGroup.values()[j]);
                teamArray.add(newTeam);
                if (teamData.size() > 0) {
                    for (String[] strArray : teamData) {
                        if ((strArray[0].equals(Enum.TeamType.values()[i].name())) && (strArray[1].equals(Enum.AgeGroup.values()[j].name()))) {
                            try {
                                if (strArray[2] != null) {
                                    newTeam.setCoach(new User(strArray[2], strArray[3], Enum.UserType.valueOf(strArray[4])));
                                    newTeamData.add(new String[] { newTeam.getTeamType().name(), newTeam.getAgeGroup().name(), newTeam.getCoach().getName(), newTeam.getCoach().getPassword(), newTeam.getCoach().getUserType().name() });
                                }
                            } catch (ArrayIndexOutOfBoundsException aioobe) {
                                newTeamData.add(new String[] { newTeam.getTeamType().name(), newTeam.getAgeGroup().name() });
                            }
                        }
                    }
                } else {
                    newTeamData.add(new String[] { newTeam.getTeamType().name(), newTeam.getAgeGroup().name() });
                }
            }
        }
        fileHandler.overwriteCSV("Teams.csv", newTeamData);
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        for (Team team : teamArray) {
            for (String[] strArray : memberData) {
                Enum.TeamType teamType;
                Enum.AgeGroup ageGroup;
                int age = util.convertDateToAge(strArray[2]);
                if (strArray[4].equals("true")) {
                    teamType = Enum.TeamType.COMPETITIVE;
                } else {
                    teamType = Enum.TeamType.REGULAR;
                }
                if (age < 18) {
                    ageGroup = Enum.AgeGroup.U18;
                } else {
                    ageGroup = Enum.AgeGroup.O18;
                }
                boolean isActive = strArray[3].equals("true");
                if ((team.getTeamType() == teamType) && (team.getAgeGroup() == ageGroup)) {
                    ArrayList<Member> memberList = team.getMemberList();
                    if (teamType == Enum.TeamType.COMPETITIVE) {
                        memberList.add(new MemberCompetitive(strArray[0], strArray[1], strArray[2], isActive,Enum.SwimDiscipline.valueOf(strArray[5])));
                    } else {
                        memberList.add(new MemberRegular(strArray[0], strArray[1], strArray[2], isActive));
                    }
                    team.setMemberList(memberList);
                }
            }
        }
        loadRecords();
    }

    public void loadRecords() {
        ArrayList<String[]> memberRecords = fileHandler.readCSV("Records.csv");
        for (Team team : teamArray) {
            if (team.getTeamType().equals(Enum.TeamType.COMPETITIVE)) {
                for (Member member : team.getMembers()) {
                    for (String[] strArray : memberRecords) {
                        if (member.getId().equals(strArray[0])) {
                            if (strArray[4].equals("practice")) {
                                ((MemberCompetitive) member).AddRecordPractice(new RecordTimePractice(strArray[0], Enum.SwimDiscipline.valueOf(strArray[1]), Double.parseDouble(strArray[2]), strArray[3]));
                            }
                            if (strArray.length>5) {
                                ((MemberCompetitive) member).AddRecordCompetition(new RecordTimeCompetition(strArray[0], Enum.SwimDiscipline.valueOf(strArray[1]), Double.parseDouble(strArray[2]), strArray[3], strArray[4], Integer.parseInt(strArray[5])));

                            }
                        }
                    }
                }
            }
        }
    }

    public void exit() {
        ui.displayShuttingDown();
        isRunning = false;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public UserController getUserController() {
        return userController;
    }

    public MemberController getMemberController() {
        return memberController;
    }

    public SubscriptionController getSubscriptionController() {
        return subscriptionController;
    }

    public SwimmerController getSwimmerController() {
        return swimmerController;
    }

    public ArrayList<Team> getTeamArray() {
        return teamArray;
    }
}
