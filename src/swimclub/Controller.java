package swimclub;

import membership.RecordTime;
import ui.Commands;
import utilities.Enum;
import membership.Team;
import ui.InputHandler;
import ui.UI;
import java.util.ArrayList;

public class Controller {
    private final UI ui = new UI();
    private final Commands cmds = new Commands();
    private final InputHandler input = new InputHandler();
    private final UserController userController = new UserController();
    private final MemberController memberController = new MemberController();
    private final SubscriptionController subscriptionController = new SubscriptionController();
    private final SwimmerController swimmerController = new SwimmerController();
    private final ArrayList<Team> teamArray = new ArrayList<>();
    private final ArrayList<RecordTime> recordTimes = new ArrayList<>();
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
        for (int i = 0; i < Enum.TeamType.values().length; i++) {
            for (int j = 0; j < Enum.AgeGroup.values().length; j++) {
                teamArray.add(new Team(Enum.TeamType.values()[i], Enum.AgeGroup.values()[j]));
                // TODO: add coach to team if they are saved in Teams.csv file
            }
        }
        // TODO: add Member class members to each Team's memberList from Members.csv file
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

    public ArrayList<RecordTime> getRecordTimes() {
        return recordTimes;
    }
}
