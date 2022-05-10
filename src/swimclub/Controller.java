package swimclub;

import membership.Member;
import membership.Team;

import java.util.Scanner;

public class Controller {
private Team t = new Team();
private Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
	   Controller con = new Controller();
       con.run();
    }

    private void run() {

    }
    
    public void addMember(){
      String memberName = sc.nextLine();
      int memberAge = sc.next();




      

    }
}
