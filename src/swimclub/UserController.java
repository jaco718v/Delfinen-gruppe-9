package swimclub;

import database.FileHandler;
import membership.Team;
import utilities.Enum;
import ui.InputHandler;
import ui.UI;

import java.util.ArrayList;

public class UserController {
    private final FileHandler fileHandler = new FileHandler();
    private final InputHandler input = new InputHandler();

    public void loginUser(Controller con) {
        UI ui = new UI(con.getLanguage());
        ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
        User loggedInUser;
        if (userData.size() > 0) {
            String[] userReturnStrings = input.enterUserName(userData, con);
            int userIndex = Integer.parseInt(userReturnStrings[1]);
            String userName = userReturnStrings[0];
            String userPassword = input.enterUserPassword(userData, userIndex, con);

            String[] strArray = userData.get(userIndex);
            Enum.UserType userType = Enum.UserType.valueOf(strArray[2]);
            loggedInUser = new User(userName, userPassword, userType);
            con.setLoggedInUser(loggedInUser);
        } else {
            ui.displayNoRegisteredUsers();
            addUser(null);
        }
    }

    public void addUser(Controller con) {
        UI ui = new UI(con.getLanguage());
        ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
        if (userData.size() > 0) {
            if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CHAIRMAN)) {
                String userName = input.addUserName(con);
                String userPassword = input.addUserPassword(con);
                String userType = input.addUserType(con);

                ArrayList<String[]> newData = new ArrayList<>();
                switch (userType) {
                    case "1" -> {
                        newData.add(new String[]{userName, userPassword, Enum.UserType.ADMIN.name()});
                        fileHandler.writeToCSV("Users.csv", newData);
                    }
                    case "2" -> {
                        newData.add(new String[]{userName, userPassword, Enum.UserType.CHAIRMAN.name()});
                        fileHandler.writeToCSV("Users.csv", newData);
                    }
                    case "3" -> {
                        newData.add(new String[]{userName, userPassword, Enum.UserType.CASHIER.name()});
                        fileHandler.writeToCSV("Users.csv", newData);
                    }
                    case "4" -> {
                        newData.add(new String[]{userName, userPassword, Enum.UserType.COACH.name()});
                        fileHandler.writeToCSV("Users.csv", newData);
                    }
                }
            } else {
                ui.displayLoggedInUserNoPrivilege();
            }
        } else {
            ui.displayNoRegisteredUsersCreatingAdmin();
            userData.add(new String[]{"admin", "admin", Enum.UserType.ADMIN.name()});
            fileHandler.writeToCSV("Users.csv", userData);
        }
    }

    public void removeUser(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
            showUsers(con);
            if (userData.size() > 0) {
                int userID = input.enterUserNameGetId(userData, con);
                if (userID != -1) {
                    String[] strArray = userData.get(userID);
                    if (strArray[2].equals("COACH")) {
                        for (Team team : con.getTeamArray()) {
                            if ((team.getCoach() != null) && (strArray[0].equals(team.getCoach().getName()))) {
                                team.setCoach(null);
                            }
                        }
                        ArrayList<String[]> teamData = fileHandler.readCSV("Teams.csv");
                        for (int i = 0; i < teamData.size(); i++) {
                            String[] teamDataArray = teamData.get(i);
                            try {
                                if ((teamDataArray[2] != null) && (strArray[0].equals(teamDataArray[2]))) {
                                    teamData.remove(i);
                                    teamData.add(i, new String[]{teamDataArray[0], teamDataArray[1]});
                                }
                            } catch (ArrayIndexOutOfBoundsException ignored) {

                            }
                        }
                        fileHandler.overwriteCSV("Teams.csv", teamData);
                    }
                    userData.remove(userID);
                    fileHandler.overwriteCSV("Users.csv", userData);
                } else {
                    ui.displayPleaseEnterValidUserId(userID);
                }
            } else {
                ui.displayNoRegisteredUsers();
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void showUsers(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CHAIRMAN)) {
            ArrayList<String[]> userData = fileHandler.readCSV("Users.csv");
            ui.displayEmptyLine();
            for (int i = 0; i < userData.size(); i++) {
                String[] strArray = userData.get(i);
                ui.displayUserInformation(strArray, con.getLoggedInUser(), i);
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }
}
