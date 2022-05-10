package membership;

public abstract class Member {
  private String name;
  private int age;
  private boolean active;
  private AgeGroup ageGroup;

  public Member(String name, int age, boolean active) {
    this.name = name;
    this.age = age;
    this.active = active;
    if (this.age < 18) {
      this.ageGroup = AgeGroup.U18;
    } else {
      this.ageGroup = AgeGroup.O18;
    }
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public boolean getActive() {
    return active;
  }

  public AgeGroup getAgeGroup() {
    return ageGroup;
  }
}
