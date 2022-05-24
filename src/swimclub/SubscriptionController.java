package swimclub;

import database.FileHandler;
import utilities.Enum;
import membership.Team;
import ui.InputHandler;
import ui.UI;
import utilities.Utility;

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
                String[] strArray = subscriptionData.get(foundIdInt);
                String userPaymentStatusInput = input.enterUserPaymentStatus(con);
                if (userPaymentStatusInput.equals("1")) {
                    strArray[4] = "true";
                } else if (userPaymentStatusInput.equals("2")) {
                    strArray[4] = "false";
                }
                subscriptionData.remove(foundIdInt);
                subscriptionData.add(foundIdInt, strArray);
                String[] printArray = subscriptionData.get(foundIdInt);
                ui.displayUpdatedMemberSubscription(printArray);
                fileHandler.overwriteCSV("Subscriptions.csv", subscriptionData);
            }
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void updateSubscriptions() {
        ArrayList<String[]> memberData = fileHandler.readCSV("Members.csv");
        ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
        for (String[] strArray : memberData) {
            boolean isRegistered = false;
            for (String[] subArray : subscriptionData) {
                if (subArray[0].equals(strArray[0])) {
                    isRegistered = true;
                    break;
                }
            }
            if (!isRegistered) {
                subscriptionData.add(new String[]{strArray[0], strArray[1], strArray[2], strArray[3], "false"});
                fileHandler.overwriteCSV("Subscriptions.csv", subscriptionData);
            }
        }
    }

    public void showSubscriptions(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CASHIER)) {
            updateSubscriptions();
            ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
            for (String[] strArray : subscriptionData) {
                ui.displaySubscription(strArray);
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
            ui.showExpectedSubscriptionFees(subscriptionSum);
        } else {
            ui.displayLoggedInUserNoPrivilege();
        }
    }

    public void showSubscriptionsInArrears(Controller con) {
        UI ui = new UI(con.getLanguage());
        if ((con.getLoggedInUser().getUserType() == Enum.UserType.ADMIN) || (con.getLoggedInUser().getUserType() == Enum.UserType.CASHIER)) {
            updateSubscriptions();
            ArrayList<String[]> subscriptionData = fileHandler.readCSV("Subscriptions.csv");
            for (String[] strArray : subscriptionData) {
                if (strArray[4].equals("false")) {
                    ui.displaySubscription(strArray);
                }
            }
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



