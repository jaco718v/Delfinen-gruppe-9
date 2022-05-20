package membership;

import utilities.Enum;

import java.util.ArrayList;

public abstract class Member {
  private String memberID;
  private String name;
  private String birthDate;
  private boolean isActive;
  private boolean isCompetitive;


  public Member(String memberID, String name, String birthDate, boolean isActive, boolean isCompetitive) {
    this.memberID = memberID;
    this.name = name;
    this.birthDate = birthDate;
    this.isActive = isActive;
    this.isCompetitive = isCompetitive;
  }

  public String getMemberID() {
    return memberID;
  }

  public String getName() {
    return name;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public boolean isActive() {
    return isActive;
  }

  public boolean isCompetitive() {
    return isCompetitive;
  }
}

