package membership;

import utilities.Enum;
import utilities.Utility;

import java.util.ArrayList;

public abstract class Member {
  private Utility util = new Utility();
  private String id;
  private String name;
  private String birthDate;
  private boolean active;
  private Enum.AgeGroup ageGroup;

  public Member(String id, String name, String birthDate, boolean active) {
    this.id = id;
    this.name = name;
    this.birthDate = birthDate;
    this.active = active;
    int age = util.convertDateToAge(birthDate);
    if (age < 18) {
      this.ageGroup = Enum.AgeGroup.U18;
    } else {
      this.ageGroup = Enum.AgeGroup.O18;
    }
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Enum.AgeGroup getAgeGroup() {
    return ageGroup;
  }
}
