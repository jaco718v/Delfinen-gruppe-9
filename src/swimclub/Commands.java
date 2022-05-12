package swimclub;
import membership.Enum;
import ui.UI;

import java.util.Scanner;

public class Commands {
    private final UI ui = new UI();
    public void commands(Controller con, Scanner sc) {
        if (con.getLoggedInUser() != null) {
            switch (con.getLoggedInUser().getUserType()) {
                case ADMIN -> {
                    ui.listCommandsAdmin();
                    String command = sc.nextLine();
                    switch (command) {
                        case "1" -> userMenu(con, sc);
                        case "2" -> memberMenu(con, sc);
                        case "3" -> con.removeUser();
                        case "4" -> con.addMember();
                        case "5" -> con.editMember();
                        case "6" -> con.removeMember();
                        case "7" -> con.showExpectedSubscriptionFees();
                        case "8" -> con.showSubscriptionsInArrears();
                        case "9" -> con.showTopSwimmers();
                        case "0" -> con.exit();
                        default -> ui.displayNoSuchCommand(command);
                    }
                }
                case CHAIRMAN -> {
                    ui.listCommandsChairman();
                    String command = sc.nextLine();
                    switch (command) {
                        case "1" -> con.loginUser();
                        case "2" -> con.addUser();
                        case "3" -> con.removeUser();
                        case "4" -> con.addMember();
                        case "5" -> con.editMember();
                        case "6" -> con.removeMember();
                        case "0" -> con.exit();
                        default -> ui.displayNoSuchCommand(command);
                    }
                }
                case CASHIER -> {
                    ui.listCommandsCashier();
                    String command = sc.nextLine();
                    switch (command) {
                        case "1" -> con.loginUser();
                        case "7" -> con.showExpectedSubscriptionFees();
                        case "8" -> con.showSubscriptionsInArrears();
                        case "0" -> con.exit();
                        default -> ui.displayNoSuchCommand(command);
                    }
                }
                case COACH -> {
                    ui.listCommandsCoach();
                    String command = sc.nextLine();
                    switch (command) {
                        case "1" -> con.loginUser();
                        case "9" -> con.showTopSwimmers();
                        case "0" -> con.exit();
                        default -> ui.displayNoSuchCommand(command);
                    }
                }
            }
        } else {
            ui.listCommandsNotLoggedIn();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.loginUser();
                case "0" -> con.exit();
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }

    private void memberMenu(Controller con, Scanner sc) {
        ui.listCommandsMemberMenu();
        String command = sc.nextLine();
        switch (command) {
            case "1" -> con.addMember();
            case "2" -> con.editMember();
            case "3" -> con.removeMember();
            case "0" -> commands(con, sc);
            default -> ui.displayNoSuchCommand(command);
        }
    }

    private void userMenu(Controller con, Scanner sc) {
        ui.listCommandsUserMenu();
        String command = sc.nextLine();
        switch (command) {
            case "1" -> con.loginUser();
            case "2" -> con.addUser();
            case "3" -> con.removeUser();
            case "0" -> commands(con, sc);
            default -> ui.displayNoSuchCommand(command);
        }
    }
}
