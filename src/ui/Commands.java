package ui;
import swimclub.Controller;

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
                case "1" -> con.getUserController().loginUser(con);
                case "0" -> con.exit();
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }

    private void swimmerMenu(Controller con, Scanner sc) {
        ui.displaySelectedSwimmerMenu();
        boolean menuOpen = true;
        while (menuOpen) {
            ui.listCommandsSwimmerMenu();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.getSwimmerController().showTopSwimmers(con.getLoggedInUser(), con.getTeamArray());
                case "2" -> con.getSwimmerController().showAllSwimmers();
                case "3" -> con.getSwimmerController().addRecordToMember(con.getLoggedInUser(), con.getTeamArray(), con.getMemberController());
                case "4" -> con.getSwimmerController().showMemberRecords(con.getLoggedInUser(), con.getTeamArray());
                case "0" -> {
                    ui.displayReturningToMainMenu();
                    menuOpen = false;
                }
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }

    private void subscriptionMenu(Controller con, Scanner sc) {
        ui.displaySelectedSubscriptionMenu();
        boolean menuOpen = true;
        while (menuOpen) {
            ui.listCommandsSubscriptionMenu();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.getSubscriptionController().setPaymentStatus(con.getLoggedInUser());
                case "2" -> con.getSubscriptionController().showSubscriptions(con.getLoggedInUser());
                case "3" -> con.getSubscriptionController().showExpectedSubscriptionFees(con.getLoggedInUser(), con.getTeamArray());
                case "4" -> con.getSubscriptionController().showSubscriptionsInArrears(con.getLoggedInUser());
                case "0" -> {
                    ui.displayReturningToMainMenu();
                    menuOpen = false;
                }
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }

    private void memberMenu(Controller con, Scanner sc) {
        ui.displaySelectedMemberMenu();
        boolean menuOpen = true;
        while (menuOpen) {
            ui.listCommandsMemberMenu();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.getMemberController().addMember(con, con.getLoggedInUser());
                case "2" -> con.getMemberController().editMember(con, con.getLoggedInUser(), con.getTeamArray());
                case "3" -> con.getMemberController().removeMember(con.getLoggedInUser(), con.getTeamArray());
                case "4" -> con.getMemberController().showMembers(con.getLoggedInUser(), con.getTeamArray());
                case "0" -> {
                    ui.displayReturningToMainMenu();
                    menuOpen = false;
                }
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }

    private void userMenu(Controller con, Scanner sc) {
        ui.displaySelectedUserMenu();
        boolean menuOpen = true;
        while (menuOpen) {
            ui.listCommandsUserMenu();
            String command = sc.nextLine();
            switch (command) {
                case "1" -> con.getUserController().loginUser(con);
                case "2" -> con.getUserController().addUser(con.getLoggedInUser());
                case "3" -> con.getUserController().removeUser(con.getLoggedInUser());
                case "4" -> con.getUserController().showUsers(con.getLoggedInUser());
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
                case "1" -> con.getUserController().loginUser(con);
                case "0" -> {
                    ui.displayReturningToMainMenu();
                    menuOpen = false;
                }
                default -> ui.displayNoSuchCommand(command);
            }
        }
    }
}
