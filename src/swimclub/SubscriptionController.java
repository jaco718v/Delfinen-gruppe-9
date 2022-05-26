package swimclub;

import database.FileHandler;
import utilities.Enum;
import membership.Team;
import ui.InputHandler;
import ui.UI;
import utilities.Utility;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SubscriptionController {
    private final InputHandler input = new InputHandler();
    private final FileHandler fileHandler = new FileHandler();
    private final Utility util = new Utility();

    public void setPaymentStatus(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CASHIER)) {
            showSubscriptions(con);
            ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");

            String foundId = input.enterUserId(subscriptionData, con);
            if (!foundId.equals("-1")) {
                int foundIdInt = -1;
                if (util.tryParseInt(foundId)) {
                    foundIdInt = Integer.parseInt(foundId);
                }
                String[] subArray = subscriptionData.get(foundIdInt);
                String userPaymentStatusInput = input.enterUserPaymentStatus(con);
                if (userPaymentStatusInput.equals("1")) {
                    subArray[5] = "true";
                } else if (userPaymentStatusInput.equals("2")) {
                    subArray[5] = "false";
                }
                subscriptionData.remove(foundIdInt);
                subscriptionData.add(foundIdInt, subArray);
                String[] printArray = subscriptionData.get(foundIdInt);
                double arrearsAmount = util.calculateArrearsAmount(printArray);
                ui.displayUpdatedMemberSubscription(printArray, arrearsAmount);
                fileHandler.overwriteCSV("Subscriptions.csv", subscriptionData);
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void updateSubscriptions() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
        for (String[] memberArray : memberData) {
            boolean isRegistered = false;
            for (String[] subArray : subscriptionData) {
                if (subArray[0].equals(memberArray[0])) {
                    isRegistered = true;
                    break;
                }
            }
            if (!isRegistered) {
                int day = LocalDateTime.now().getDayOfMonth();
                int month = LocalDateTime.now().getMonthValue();
                int year = LocalDateTime.now().getYear();
                String inArrearsDate = String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + year;
                subscriptionData.add(new String[] { memberArray[0], memberArray[1], memberArray[2], memberArray[3], memberArray[4], "false", inArrearsDate });
                fileHandler.overwriteCSV("Subscriptions.csv", subscriptionData);
            }
        }
    }

    public void showSubscriptions(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CASHIER)) {
            updateSubscriptions();
            ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
            for (String[] subArray : subscriptionData) {
                double arrearsAmount = util.calculateArrearsAmount(subArray);
                ui.displaySubscription(subArray, arrearsAmount);
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }

    }

    public void showSubscriptionsInArrears(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CASHIER)) {
            updateSubscriptions();
            ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
            for (String[] subArray : subscriptionData) {
                if (subArray[5].equals("false")) {
                    double arrearsAmount = util.calculateArrearsAmount(subArray);
                    ui.displaySubscription(subArray, arrearsAmount);
                }
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void showExpectedSubscriptionFees(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CASHIER)) {
            double subscriptionJunior = 1000;
            double subscriptionSenior = 1600;
            double subscriptionPassive = 500;
            int seniorThreshold = 60;
            double seniorDiscount = 0.25;
            double subscriptionSum = 0;
            for (Team team : con.getTeamArray()) {
                double subscription = 0;
                if (team.getAgeGroup().equals(Enum.AgeGroup.U18)) {
                    subscription = subscriptionJunior;
                }
                if (team.getAgeGroup() == Enum.AgeGroup.O18) {
                    subscription = subscriptionSenior;
                    subscriptionSum -= subscriptionSenior * seniorDiscount * team.getActiveMembersAboveAge(seniorThreshold).size();
                }
                subscriptionSum += team.getCompetitiveMembers().size() * subscription + (team.getMembers().size() - team.getCompetitiveMembers().size()) * subscriptionPassive;
            }
            ui.displayExpectedSubscriptionFees(subscriptionSum);
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    /*
    public void calculateSubscriptionsArrears(User loggedInUser) {
        ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
        for (String[] strArray : subscriptionData) {
            if (LocalDateTime.now().getMonth() == Month.JANUARY.//første dag på året) {
                    strArray[4].equals("false");
        }
    }

    public void calculateArrearsAtRegistrationDate() {
        // Lav en metode som tager tilmeldningsdatoen og regner restancen ud, hvis man f.eks melder sig ind midt i år.


    }
    */
}



