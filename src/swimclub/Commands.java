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
                        case "3" -> subscriptionMenu(con, sc);
                        case "4" -> swimmerMenu(con, sc);
                        case "0" -> con.exit();
                        default -> ui.displayNoSuchCommand(command);
                    }
                }
                case CHAIRMAN -> {
                    ui.listCommandsChairman();
                    String command = sc.nextLine();
                    switch (command) {
                        case "1" -> userMenu(con, sc);
                        case "2" -> memberMenu(con, sc);
                        case "0" -> con.exit();
                        default -> ui.displayNoSuchCommand(command);
                    }
                }
                case CASHIER -> {
                    ui.listCommandsCashier();
                    String command = sc.nextLine();
                    switch (command) {
                        case "1" -> userMenuLoginOnly(con, sc);
                        case "2" -> subscriptionMenu(con, sc);
                        case "0" -> con.exit();
                        default -> ui.displayNoSuchCommand(command);
                    }
                }
                case COACH -> {
                    ui.listCommandsCoach();
                    String command = sc.nextLine();
                    switch (command) {
                        case "1" -> userMenuLoginOnly(con, sc);
                        case "2" -> swimmerMenu(con, sc);
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

    private void swimmerMenu(Controller con, Scanner sc) {
        boolean menuOpen = true;
        while (menuOpen) {
            ui.listCommandsSwimmerMenu();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.showTopSwimmers();
                case "2" -> con.showAllSwimmers();
                case "0" -> {
                    ui.displayReturningToMainMenu();
                    menuOpen = false;
                }
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }

    private void subscriptionMenu(Controller con, Scanner sc) {
        boolean menuOpen = true;
        while (menuOpen) {
            ui.listCommandsSubscriptionMenu();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.setPaymentStatus();
                case "2" -> con.showSubscriptions();
                case "3" -> con.showExpectedSubscriptionFees();
                case "4" -> con.showSubscriptionsInArrears();
                case "0" -> {
                    ui.displayReturningToMainMenu();
                    menuOpen = false;
                }
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }

    private void memberMenu(Controller con, Scanner sc) {
        boolean menuOpen = true;
        while (menuOpen) {
            ui.listCommandsMemberMenu();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.addMember();
                case "2" -> con.editMember();
                case "3" -> con.removeMember();
                case "4" -> con.showMembers();
                case "0" -> {
                    ui.displayReturningToMainMenu();
                    menuOpen = false;
                }
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }

    private void userMenu(Controller con, Scanner sc) {
        boolean menuOpen = true;
        while (menuOpen) {
            ui.listCommandsUserMenu();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.loginUser();
                case "2" -> con.addUser();
                case "3" -> con.removeUser();
                case "4" -> con.showUsers();
                case "0" -> {
                    ui.displayReturningToMainMenu();
                    menuOpen = false;
                }
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }

    private void userMenuLoginOnly(Controller con, Scanner sc) {
        boolean menuOpen = true;
        while (menuOpen) {
            ui.listCommandsUserMenuLoginOnly();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.loginUser();
                case "0" -> {
                    ui.displayReturningToMainMenu();
                    menuOpen = false;
                }
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }
}
