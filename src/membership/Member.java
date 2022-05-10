package membership;

public abstract class Member {
  private String name;
  private int age;
  private boolean active;
  private Enum.AgeGroup ageGroup;

  public Member(String name, int age, boolean active) {
    this.name = name;
    this.age = age;
    this.active = active;
    if (this.age < 18) {
      this.ageGroup = Enum.AgeGroup.U18;
    } else {
      this.ageGroup = Enum.AgeGroup.O18;
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

  public Enum.AgeGroup getAgeGroup() {
    return ageGroup;
  }
}
